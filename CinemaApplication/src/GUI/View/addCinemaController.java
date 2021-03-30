/*******************************************************************************
            Controller class and logic implementation for home.fxml
 ******************************************************************************/
package GUI.View;

import BLL.Cinema;
import DAL.CinemaException;
import DAL.CinemaRepository;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.net.URL;
import java.time.temporal.ValueRange;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class addCinemaController extends CommonMethods implements Initializable{
    @FXML
    private AnchorPane mainhomepane, leftpane,toppane;

    @FXML
    private JFXTreeTableView<Cinema> treeView;

    @FXML
    private JFXTextField cinemaNameField, cinemaLocationField, CinemaTelField;

    @FXML
    private JFXButton editProfileBtn;
    @FXML
    private Label rowSelectedField;
    private Cinema selectedCinema = null;
    private double x=0, y=0;
    /**
     *  Initialise method required for implementing initializable and,
     *  sets up and applies all effects and animations to nodes in logout.fxml
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        leftpanecolor = leftpane.getStyle().substring(22, 29);
        page = "Home";

        moveWindow(leftpane);
        moveWindow(toppane);
        generateTable();

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);
    }

    private void generateTable(){

        JFXTreeTableColumn<Cinema, String> cinemaNames = new JFXTreeTableColumn<>("Name");
        cinemaNames.setPrefWidth(200);
        cinemaNames.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getCinemaname()));

        JFXTreeTableColumn<Cinema, String> cinemaLocation = new JFXTreeTableColumn<>("Location");
        cinemaLocation.setPrefWidth(150);
        cinemaLocation.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getCinemalocation()));


        JFXTreeTableColumn<Cinema, String> cinemaPhones = new JFXTreeTableColumn<>("Phone");
        cinemaPhones.setPrefWidth(175);
        cinemaPhones.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getCinemaphone()));

        JFXTreeTableColumn<Cinema, String> cinemaID = new JFXTreeTableColumn<>("ID");
        cinemaID.setPrefWidth(50);
        cinemaID.setCellValueFactory(param -> new SimpleStringProperty("" + param.getValue().getValue().getCinemaid()));

        ObservableList<Cinema> cinemas = FXCollections.observableArrayList();


        List<Cinema> cinemaList = null;
        try {
            cinemaList = (new CinemaRepository().findAll());
        } catch (CinemaException e) {
            e.printStackTrace();
        }
        assert cinemaList != null;
        cinemas.addAll(cinemaList);

        final TreeItem<Cinema> root = new RecursiveTreeItem<Cinema>(cinemas, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(cinemaID, cinemaNames,cinemaLocation,cinemaPhones);
        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }
    public void edit(ActionEvent actionEvent) throws CinemaException {
        if(!validate())
            return;

        if(selectedCinema == null) {
            selectedCinema = new Cinema();
        }

        selectedCinema.setCinemaname(cinemaNameField.getText());
        selectedCinema.setCinemalocation(cinemaLocationField.getText());
        selectedCinema.setCinemaphone(CinemaTelField.getText());

        new  CinemaRepository().edit(selectedCinema);

        clearFields();
        generateTable();
    }

    @FXML
    private void clearFields(){
        treeView.getSelectionModel().clearSelection();
        selectedCinema = null;
        rowSelectedField.setText(" ");
        cinemaNameField.setText("");
        cinemaLocationField.setText("");
        CinemaTelField.setText("");

    }
    @FXML
    private boolean validate(){
        Boolean valide = true;

        if(cinemaNameField.getText().isEmpty()){
            cinemaNameField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else cinemaNameField.setStyle("-fx-border-color: transparent");

        if(cinemaLocationField.getText().isEmpty()){
            cinemaLocationField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else cinemaLocationField.setStyle("-fx-border-color: transparent");

        if(CinemaTelField.getText().isEmpty()){
            CinemaTelField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else CinemaTelField.setStyle("-fx-border-color: transparent");

        return valide;
    }
    public void updateFields(MouseEvent mouseEvent) {
        TreeItem<Cinema> selectedItem = treeView.getSelectionModel().getSelectedItem();
        selectedCinema = (selectedItem == null ? null : selectedItem.getValue());
        
        //selectedCinema = (Cinema)treeView.getSelectionModel().getSelectedItem().getValue();
        if(selectedCinema != null) {
            rowSelectedField.setText(String.valueOf(selectedCinema.getCinemaid()));
            cinemaNameField.setText(selectedCinema.getCinemaname());
            cinemaLocationField.setText(selectedCinema.getCinemalocation());
            CinemaTelField.setText(selectedCinema.getCinemaphone());
        }
    }

    public void deleteCinema(ActionEvent actionEvent) throws CinemaException {
        if(selectedCinema != null) {
            new CinemaRepository().delete(selectedCinema);
            generateTable();
        }
    }
}
