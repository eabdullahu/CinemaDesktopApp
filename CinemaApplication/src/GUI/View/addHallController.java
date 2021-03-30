package GUI.View;

import BLL.Cinema;
import BLL.Hall;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.HallRepository;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class addHallController extends CommonMethods implements Initializable {

    @FXML
    private AnchorPane mainhomepane, leftpane,toppane;

    @FXML
    private JFXButton homebtn, addbtn, moviescreensbtn, minimisebtn, closebtn, editbtn, editProfileBtn;

    @FXML
    private Label rowSelectedField;

    @FXML
    private JFXTreeTableView<Hall> treeView;

    @FXML
    private JFXTextField HallNameField, HallColumField, HallRowField;

    @FXML
    private JFXComboBox<Cinema> hallCinemasBox;

    private Hall  selectedHall;
    private List<Cinema> cinemas;
    private Map<Integer,String> cinemaMap = new HashMap<>();


    @FXML
    void edit(ActionEvent event) throws CinemaException {
        int error = 0;
        if(HallNameField.getText().trim().equals("")){
            HallNameField.setStyle("-fx-border-color: #d21a1a");
            error++;
        }else HallNameField.setStyle("-fx-border-color: transparent");

        if(!isNumeric(HallRowField.getText().trim())) {
            HallRowField.setStyle("-fx-border-color: #d21a1a");
            error++;
        }else HallRowField.setStyle("-fx-border-color: transparent");

        if(!isNumeric(HallColumField.getText().trim())) {
            HallColumField.setStyle("-fx-border-color: #d21a1a");
            error++;
        }else HallColumField.setStyle("-fx-border-color: transparent");

        if(hallCinemasBox.getValue() == null){
            hallCinemasBox.setStyle("-fx-border-color: #d21a1a");
            error++;
        }else hallCinemasBox.setStyle("-fx-border-color: transparent");

        if(error > 0){
            return;
        }

        //check if there is a selected row, if not create a new Hall instance
        if(selectedHall == null) selectedHall = new Hall();

        //add the update
        selectedHall.setHallname(HallNameField.getText());
        selectedHall.setHallCinemaid(hallCinemasBox.getValue());
        selectedHall.setHallcolumns(Short.parseShort(HallColumField.getText()));
        selectedHall.setHallrow(Short.parseShort(HallRowField.getText()));

        //Insert into database
        new  HallRepository().edit(selectedHall);

        // Clear the fields for the next input
        clearFields();

        //Generate the updated field with the changes
        generateTable();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leftpanecolor = leftpane.getStyle().substring(22, 29);
        page = "Hall";

        moveWindow(leftpane);
        moveWindow(toppane);

        disableAllFocus(mainhomepane);
        generateTable();

        try {
            cinemas = new CinemaRepository().findAll();
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        if(cinemas.size() > 0){
            for (Cinema cinema : cinemas) {
                cinemaMap.put(cinema.getCinemaid(), cinema.getCinemaname());
            }
        }

        ObservableList<Cinema> jobsOList = FXCollections.observableArrayList(cinemas);
        hallCinemasBox.setItems(jobsOList);
        hallCinemasBox.setPromptText("Select Cinema");

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);

    }
    private void generateTable(){

        JFXTreeTableColumn<Hall, String> hallNames = new JFXTreeTableColumn<>("Name");
        hallNames.setPrefWidth(150);
        hallNames.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getHallname()));

        JFXTreeTableColumn<Hall, String> hallRows = new JFXTreeTableColumn<>("Hall Rows");
        hallRows.setPrefWidth(150);
        hallRows.setCellValueFactory(param -> new SimpleStringProperty("" + param.getValue().getValue().getHallrow()));

        JFXTreeTableColumn<Hall, String> hallColumns = new JFXTreeTableColumn<>("Hall Coulumns");
        hallColumns.setPrefWidth(150);
        hallColumns.setCellValueFactory(param -> new SimpleStringProperty("" + param.getValue().getValue().getHallcolumns()));

        JFXTreeTableColumn<Hall, String> hallCinemaName = new JFXTreeTableColumn<>("Cinema");
        hallCinemaName.setPrefWidth(150);
        hallCinemaName.setCellValueFactory(param -> new SimpleStringProperty(cinemaMap.get(param.getValue().getValue().getHallCinemaid().getCinemaid())));

        ObservableList<Hall> halls = FXCollections.observableArrayList();

        List<Hall> hallList = null;

        try {
            hallList = (new HallRepository().findAll());
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        for (Hall hall : hallList) {
            halls.add(hall);
        }

        final TreeItem<Hall> root = new RecursiveTreeItem<Hall>(halls, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(hallNames, hallRows, hallColumns, hallCinemaName);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }
    @FXML
    void clearFields(){

        //removes the selected hall instance
        selectedHall = null;

        //clear the fields
        HallNameField.setText("");
        HallColumField.setText("");
        HallRowField.setText("");
        rowSelectedField.setText("");

        //removes selected cinema
        hallCinemasBox.getSelectionModel().clearSelection();
    }
    public void updateFields(MouseEvent mouseEvent) {

        //Check for a selected row in the Hall list
        if(treeView.getSelectionModel().getSelectedItem() != null)
            selectedHall = treeView.getSelectionModel().getSelectedItem().getValue();

        if(selectedHall != null) {
            rowSelectedField.setText("" + selectedHall.getHallid());
            HallNameField.setText(selectedHall.getHallname());
            HallColumField.setText("" + selectedHall.getHallcolumns());
            HallRowField.setText("" + selectedHall.getHallrow());
            hallCinemasBox.getSelectionModel().select(selectedHall.getHallCinemaid());
        }
    }

    public void deleteHall(ActionEvent actionEvent) throws CinemaException {
        if(treeView.getSelectionModel().getSelectedItem().getValue() != null && deletePopup()){
            new HallRepository().delete(treeView.getSelectionModel().getSelectedItem().getValue());
            generateTable();
            clearFields();
        }
    }
}
