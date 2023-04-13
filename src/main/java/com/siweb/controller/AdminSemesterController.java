package com.siweb.controller;

import com.siweb.model.AppModel;
import com.siweb.model.Semester;
import com.siweb.model.User;
import com.siweb.model.UserModel;
import com.siweb.view.SelectOption;
import com.siweb.view.builder.BuilderMFXComboBoxController;
import com.siweb.view.builder.BuilderMFXDatePickerController;
import com.siweb.view.builder.BuilderMFXTextFieldController;
import com.siweb.view.facade.FacadePaginatedTableController;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

/***
 * AdminSemesterController manages the users management page of admin
 */
public class AdminSemesterController extends BaseController {

    private FacadePaginatedTableController<Semester> semestersPaginatedTable;
    @FXML
    private TableView<Semester> semestersTable;
    @FXML
    private Pagination semestersTablePagination;
    @FXML
    private VBox semesterDetailVBox;
    @FXML
    private MFXScrollPane semesterDetailPane;

    @FXML
    private MFXButton semesterDeleteBtn;
    @FXML
    private MFXButton semesterSaveBtn;

    @FXML
    private HBox tableHeaderHBox;


    /***
     * initialize the page. Setup TableView, pagination, search field, and the ordering select
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    public void initialize(URL location, ResourceBundle resources) {

        // default ordering, will be used in TableView and the ordering select
        String defaultOrdering = "-date_end";

        // Create a new Facade class to easily manage the tableView with pagination
        semestersPaginatedTable = new FacadePaginatedTableController.Builder<Semester>(semesterModel, semestersTable, semestersTablePagination, "/academic/semester/", "#resultsCountLabel")
                .addColumn(new TableColumn<Semester, String>("Year"), "getYear", 160)
                .addColumn(new TableColumn<Semester, String>("Semester"), "getSemester", 160)
                .addColumn(new TableColumn<Semester, String>("Date Start"), "getDateStart", 160)
                .addColumn(new TableColumn<Semester, String>("Date End"), "getDateEnd", 160)
                .setPageSize(23)
                .setOrdering(defaultOrdering)
                .build();

        // Add a listener when select / deselect a row
        semestersPaginatedTable.addSelectionListener((obs, oldSelection, newSelection) -> {

            // disable the buttons and clear the detail box first, then enable them accordingly if needed below
            semesterDetailVBox.getChildren().clear();
            semesterDetailPane.setVvalue(0.0);
            semesterDeleteBtn.setDisable(true);
            semesterSaveBtn.setDisable(true);

            // create a basic fade transition on the detail box
            FadeTransition fade = new FadeTransition();
            fade.setDuration(Duration.millis(100));
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.setNode(semesterDetailVBox);
            fade.play();

            // if a user is selected, show the user's details and enable save / delete button.
            if (newSelection != null) {

                // enable save button
                semesterSaveBtn.setDisable(false);

                // enable delete button
                semesterDeleteBtn.setDisable(false);

                // show basic information
                semesterDetailVBox.getChildren().add(new Label("Semester Information"));
                semesterDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("id","ID").setText(newSelection.getId() + "").setDisable(true).build().get());
                semesterDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("year","Year").setText(newSelection.getYear()).build().get());
                semesterDetailVBox.getChildren().add(new BuilderMFXComboBoxController.Builder("semester", "Semester", List.of(new SelectOption("1"), new SelectOption("2"))).setValText(newSelection.getSemester()).build().get());
                semesterDetailVBox.getChildren().add(new BuilderMFXDatePickerController.Builder("date_start","Date Start").setText(newSelection.getDateStart()).build().get());
                semesterDetailVBox.getChildren().add(new BuilderMFXDatePickerController.Builder("date_end","Date End").setText(newSelection.getDateEnd()).build().get());

            }
        });


        // "search" button creation and listen to "ENTER" presses
        tableHeaderHBox.getChildren().add(new BuilderMFXTextFieldController.Builder("search", "Search").setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                semestersPaginatedTable.setSearch(((MFXTextField) AppModel.scene.lookup("#search")).getText());
                semestersPaginatedTable.refresh(true);
            }
        }).setPrefWidth(230).setFloatMode(FloatMode.INLINE).setPadding(new Insets(4,10,4,10)).build().get());



        // "order by" select and listen to changes
        tableHeaderHBox.getChildren().add(new BuilderMFXComboBoxController.Builder("order_by", "Order By", List.of(
                new SelectOption("Date end (ascending)", "date_end"),
                new SelectOption("Date end (descending)", "-date_end"),
                new SelectOption("Date start (ascending)", "date_start"),
                new SelectOption("Date start (descending)", "-date_start"),
                new SelectOption("Semester (ascending)", "semester"),
                new SelectOption("Semester (descending)", "-semester"),
                new SelectOption("Year (ascending)", "year"),
                new SelectOption("Year (descending)", "-year")
        )).addSelectionListener((obs, oldSelection, newSelection)->{
            semestersPaginatedTable.setOrdering(newSelection.getValText());
            semestersPaginatedTable.refresh(true);
        }).setValText(defaultOrdering).setPrefWidth(230).setFloatMode(FloatMode.INLINE).setPadding(new Insets(4,4,4,10)).build().get());

    }

    /***
     * After clicking on the new button, show an empty form on the right for admin to fill in.
     */
    public void semesterNew() {

        semesterDetailVBox.getChildren().clear();
        semesterDetailPane.setVvalue(0.0);
        semesterDeleteBtn.setDisable(true);

        semestersPaginatedTable.clearSelection();

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(100));
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setNode(semesterDetailVBox);
        fade.play();

        semesterSaveBtn.setDisable(false);

        semesterDetailVBox.getChildren().add(new Label("Semester Information"));
        semesterDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("id","ID").setDisable(true).build().get());
        semesterDetailVBox.getChildren().add(new BuilderMFXTextFieldController.Builder("year","Year").build().get());
        semesterDetailVBox.getChildren().add(new BuilderMFXComboBoxController.Builder("semester", "Semester", List.of(new SelectOption("1"), new SelectOption("2"))).build().get());
        semesterDetailVBox.getChildren().add(new BuilderMFXDatePickerController.Builder("date_start","Date Start").build().get());
        semesterDetailVBox.getChildren().add(new BuilderMFXDatePickerController.Builder("date_end","Date End").build().get());
    }

    /***
     * After clicking on the save button.
     * 1. if a semester is selected, update the selected semester with a PUT method to the server.
     * 2. if no semester is selected, create a semester user with a POST method to the server.
     */
    public void semesterSave() {

        Semester selectedSemester = semestersPaginatedTable.getCurrentSelection();

        SelectOption semester = ((MFXComboBox<SelectOption>) AppModel.scene.lookup("#semester")).getValue();
        LocalDate date_start = ((MFXDatePicker) AppModel.scene.lookup("#date_start")).getValue();
        LocalDate date_end = ((MFXDatePicker) AppModel.scene.lookup("#date_end")).getValue();

        // Updating the existing user and refresh the table
        if(selectedSemester != null)
        {


            http.put("/academic/semester/" + selectedSemester.getId() + "/", Map.of(
                    "year",         ((MFXTextField) AppModel.scene.lookup("#year")).getText(),
                    "semester",     semester == null ? "" : semester.getValText(),
                    "date_start",   date_start == null ? "" : date_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "date_end",     date_end == null ? "" : date_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            ), (JSONObject jsonUser) -> {

                Platform.runLater(() -> {
                    semestersPaginatedTable.refresh(false);
                });

            });

        }

        // create a new semester and refresh the table
        else {

            http.post("/academic/semester/", Map.of(
                    "year",     ((MFXTextField) AppModel.scene.lookup("#year")).getText(),
                    "semester",     semester == null ? "" : semester.getValText(),
                    "date_start",   date_start == null ? "" : date_start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                    "date_end",     date_end == null ? "" : date_end.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            ), (JSONObject jsonUser) -> {

                Platform.runLater(() -> {

                    semesterDetailVBox.getChildren().clear();
                    semesterDetailPane.setVvalue(0.0);
                    semesterDeleteBtn.setDisable(true);
                    semesterSaveBtn.setDisable(true);

                    semestersPaginatedTable.refresh(false);
                });
            });
        }

    }


    /***
     * After clicking the delete button, a confirmation box is shown, with the option to delete the semester permanently.
     */
    public void semesterDelete() {

        Semester selectedSemester = semestersPaginatedTable.getCurrentSelection();

        if(selectedSemester != null)
        {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete confirmation");
            alert.setHeaderText("Are you sure to permanently delete the semester " + selectedSemester.getYear() + "-" + selectedSemester.getSemester() + "?");
            alert.setContentText("The following semester will be permanently deleted: " + "\nSemester: " + selectedSemester.getYear() + "-" + selectedSemester.getSemester() + "\nDate Start: " + selectedSemester.getDateStart() + "\nDate End: " + selectedSemester.getDateEnd());
            alert.initOwner(AppModel.stage);
            Optional<ButtonType> result = alert.showAndWait();
            if(!result.isPresent())
            {
                System.err.println("Dialog exited");
            }
            else if(result.get() == ButtonType.OK)
            {
                System.err.println("Dialog OK pressed");

                http.delete("/academic/semester/"+selectedSemester.getId()+"/", (JSONObject jsonUserCurrent) -> {

                    Platform.runLater(() -> {
                        semestersPaginatedTable.refresh(false);
                    });
                });

            }
            else if(result.get() == ButtonType.CANCEL)
            {
                System.err.println("Dialog cancel pressed");
            }
        }

    }

}
