package com.siweb.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

/***
 * ObservableModel makes a model observable, can then be used in a TableView which listens to the changes of the observable list.
 * @param <E> The Object type. E.g. User
 */
public abstract class ObservableModel<E> {
    protected ObservableList<E> oList = FXCollections.observableArrayList();

    // add the object type from a JSONArray of JSONObjects
    public void add(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            add(jsonArray.getJSONObject(i));
        }
    }

    // add the object type from a JSONObject
    public abstract void add(JSONObject jsonObject);

    // return an unmodifiable observable list
    public ObservableList<E> get() {
        return FXCollections.unmodifiableObservableList(oList);
    }

    // Clear the observable list
    public void clear() {
        oList.clear();
    }
}
