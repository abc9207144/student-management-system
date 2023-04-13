package com.siweb.controller;

import com.siweb.model.AppModel;
import com.siweb.model.User;
import com.siweb.model.UserModel;
import com.siweb.view.SelectOption;
import com.siweb.view.builder.BuilderMFXComboBoxController;
import com.siweb.view.builder.BuilderMFXTextFieldController;
import com.siweb.view.facade.FacadePaginatedTableController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.enums.FloatMode;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LecturerProfilesController extends LecturerBaseController {
    private FacadePaginatedTableController<User> usersPaginatedTable;
    private final UserModel userModel = UserModel.getInstance();
    @FXML
    private TableView<User> usersTable;
    @FXML
    private Pagination usersTablePagination;
    @FXML
    private VBox userDetailVBox;
    @FXML
    private MFXScrollPane userDetailPane;

    @FXML
    private MFXButton userDeleteBtn;
    @FXML
    private MFXButton userSaveBtn;

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
        String defaultOrdering = "-date_joined";

        // Create a new Facade class to easily manage the tableView with pagination
        usersPaginatedTable = new FacadePaginatedTableController.Builder<User>(userModel, usersTable, usersTablePagination, "/user/", "#resultsCountLabel")
                .addColumn(new TableColumn<User, String>("Username"), "getUserName", 130)
                .addColumn(new TableColumn<User, String>("First Name"), "getFirstName", 130)
                .addColumn(new TableColumn<User, String>("Last Name"), "getLastName", 130)
                .addColumn(new TableColumn<User, String>("Email"), "getEmail", 240)
                .addColumn(new TableColumn<User, String>("Role"), "getProfileRole", 110)
                .setPageSize(23)
                .setOrdering(defaultOrdering)
                .build();




        // "search" button creation and listen to "ENTER" presses
        tableHeaderHBox.getChildren().add(new BuilderMFXTextFieldController.Builder("search", "Search").setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                usersPaginatedTable.setSearch(((MFXTextField) AppModel.scene.lookup("#search")).getText());
                usersPaginatedTable.refresh(true);
            }
        }).setPrefWidth(230).setFloatMode(FloatMode.INLINE).setPadding(new Insets(4, 10, 4, 10)).build().get());


        // "order by" select and listen to changes
        tableHeaderHBox.getChildren().add(new BuilderMFXComboBoxController.Builder("order_by", "Order By", List.of(
                new SelectOption("Date joined (ascending)", "date_joined"),
                new SelectOption("Date joined (descending)", "-date_joined"),
                new SelectOption("Username (ascending)", "username"),
                new SelectOption("Username (descending)", "-username"),
                new SelectOption("First Name (ascending)", "first_name"),
                new SelectOption("First Name (descending)", "-first_name"),
                new SelectOption("Last Name (ascending)", "last_name"),
                new SelectOption("Last Name (descending)", "-last_name"),
                new SelectOption("Email (ascending)", "email"),
                new SelectOption("Email (descending)", "-email")
        )).addSelectionListener((obs, oldSelection, newSelection) -> {
            usersPaginatedTable.setOrdering(newSelection.getValText());
            usersPaginatedTable.refresh(true);
        }).setValText(defaultOrdering).setPrefWidth(230).setFloatMode(FloatMode.INLINE).setPadding(new Insets(4, 4, 4, 10)).build().get());

    }
}
