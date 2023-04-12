package com.siweb.controller.utility;

import com.siweb.App;

import com.siweb.model.AppModel;
import javafx.application.Platform;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import java.util.Map;
import java.util.function.*;

/***
 * HttpController is a Singleton Utility to handle all http requests
 * All Http requests are asynchronous
 * Implemented with JSON Web Token (JWT) authentication:
 *   - Access token is automatically refreshed by using the Refresh token
 *   - When both tokens are expired, redirect to login page
 */
public class UtilityHttpController {

    // Declares variables
    private static final UtilityHttpController instance = new UtilityHttpController();
    private final HttpClient client = HttpClient.newHttpClient();
    private String accessToken = "";
    private String refreshToken = "";
    private UtilityHttpController(){}

    // Returns the instance of the controller
    public static UtilityHttpController getInstance(){
        return instance;
    }


    /***
     * Handles log in attempts with username and password from the user.
     * @param username username of a user
     * @param password password of a user
     * @param listener callback consumer if logged in successfully
     */
    public void login(String username, String password, Consumer<JSONObject> listener) {
        post("/auth/login/", Map.of("username", username, "password", password), (JSONObject res) -> {

            // login success, save the tokens locally for authentication
            _setTokens(res.getString( "access" ), res.getString( "refresh" ));

            // Wait for the main thread before logging in
            Platform.runLater(()-> listener.accept(res));
        });
    }

    /***
     * Handles log out attempts from the user.
     * @param listener callback consumer if logged out successfully
     */
    public void logout(Consumer<JSONObject> listener) {
        post("/auth/logout/", Map.of("refresh", refreshToken), (JSONObject res) -> {

            // Remove tokens
            _setTokens("", "");

            // It's important to wait for the main thread before logging out
            Platform.runLater(()-> listener.accept(res));
        });
    }


    /***
     * Handles all authenticated POST requests (for login and logout, use login() and logout() instead)
     * @param uri relative URI of the request
     * @param data body of the request
     * @param listener callback consumer if getting a 2XX response
     */
    // Sender for all post requests
    public void post(String uri, Map<?, ?> data, Consumer<?> listener) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppModel.API_URI + uri))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + accessToken)
                .POST(BodyPublishers.ofString(new JSONObject(data).toString()))
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(res -> _responseHandler(res, "POST", uri, data, listener));

    }

    /***
     * Handles all authenticated PUT requests
     * @param uri relative URI of the request
     * @param data body of the request
     * @param listener callback consumer if getting a 2XX response
     */
    // Sender for all put requests
    public void put(String uri, Map<?, ?> data, Consumer<?> listener) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppModel.API_URI + uri))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + accessToken)
                .PUT(BodyPublishers.ofString(new JSONObject(data).toString()))
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(res -> _responseHandler(res, "PUT", uri, data, listener));

    }


    /***
     * Handles all authenticated GET requests
     * @param uri relative URI of the request
     * @param listener callback consumer if getting a 2XX response
     */
    public void get(String uri, Consumer<?> listener) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppModel.API_URI + uri))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(res -> _responseHandler(res, "GET", uri, Map.of(), listener));

    }

    /***
     * Handles all authenticated DELETE requests
     * @param uri relative URI of the request
     * @param listener callback consumer if getting a 2XX response
     */
    public void delete(String uri, Consumer<?> listener) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppModel.API_URI + uri))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + accessToken)
                .DELETE()
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(res -> _responseHandler(res, "DELETE", uri, Map.of(), listener));

    }


    /***
     * set tokens locally
     * @param accessToken access token
     * @param refreshToken refresh token
     */
    private void _setTokens(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        System.err.println("accessToken updated");
        System.err.println("refreshToken updated");
        System.err.println();
    }

    /***
     * response handler for all http requests, except for refreshing token which is handled in _refreshTokenAndRetry()
     * @param res http response of the request
     * @param reqMethod the request method, e.x. GET, POST
     * @param uri relative URI of the request
     * @param data body of the request
     * @param listener callback consumer if getting a 2XX response
     */
    private void _responseHandler(HttpResponse<?> res, String reqMethod, String uri, Map<?, ?> data, Consumer<?> listener) {

        // printing response in the console
        System.err.println(res);
        System.err.println(res.body());
        System.err.println();

        if(res.statusCode() >= 200 && res.statusCode() < 300)
        {
            // if the request is successful, parse the json and call the listener
            try
            {
                // check if it's a JSON with the structure "{...}"
                JSONObject resJSON;
                if(reqMethod.equals("DELETE")) { // this appears to be returning empty body from the API, let's keep it consistent and return an empty JSON
                    resJSON = new JSONObject("{}");
                } else {
                    resJSON = new JSONObject(res.body().toString());
                }

                ((Consumer<JSONObject>) listener).accept(resJSON);
            }
            catch (Exception e)
            {
                try
                {
                    // check if it's a JSON with the structure "[...]"
                    ((Consumer<JSONArray>) listener).accept(new JSONArray(res.body().toString()));
                }
                catch (Exception e2)
                {
                    // JSON not valid or listener is wrong
                    e.printStackTrace();
                }
            }
        }
        else if(res.statusCode() == 401 && !refreshToken.equals(""))
        {
            // access token not valid, automatically refresh the token and retry the request
            _refreshTokenAndRetry(reqMethod, uri, data, listener);
        }
        else {
            // error, show popup notifications
        }
    }

    /***
     * refreshing tokens and retry the failed request again
     * @param reqMethod the original request method, e.x. GET, POST
     * @param uri relative URI of the original request
     * @param data body of the original request
     * @param listener callback consumer of the original request if getting a 2XX response
     */
    private void _refreshTokenAndRetry(String reqMethod, String uri, Map<?, ?> data, Consumer<?> listener) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AppModel.API_URI + "/auth/token_refresh/"))
                .timeout(Duration.ofSeconds(20))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Cache-Control", "no-cache")
                .header("Authorization", "Bearer " + accessToken)
                .POST(BodyPublishers.ofString(new JSONObject(Map.of("refresh", refreshToken)).toString()))
                .build();
        client.sendAsync(request, BodyHandlers.ofString())
                .thenAccept(res -> {

                    if(res.statusCode() >= 200 && res.statusCode() < 300)
                    {
                        // We have successfully refreshed both tokens
                        JSONObject resJSON = new JSONObject(res.body().toString());

                        // set it locally
                        _setTokens(resJSON.getString("access"), resJSON.getString("refresh"));

                        System.err.println("Tokens refreshed. Retrying the request...");
                        System.err.println();

                        // retry the previous failed request
                        switch (reqMethod) {
                            case "GET" -> get(uri, listener);
                            case "POST" -> post(uri, data, listener);
                            case "PUT" -> put(uri, data, listener);
                            case "DELETE" -> delete(uri, listener);
                        }

                    }
                    else if(res.statusCode() == 401)
                    {
                        // refresh token expired, returning to login page
                        try
                        {
                            System.err.println("refresh token expired, returning to login page...");
                            System.err.println();
                            _setTokens("", "");
                            com.siweb.App.setRoot("login");

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    else if(res.statusCode() == 408 || res.statusCode() == 502 || res.statusCode() == 503) {
                        // Timeout, let user try again later.
                    }
                    else {
                        // Other http errors
                    }
                });
    }


}
