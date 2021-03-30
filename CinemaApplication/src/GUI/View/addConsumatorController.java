package GUI.View;

import BLL.Cinema;
import BLL.Consumator;
import BLL.Hall;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.ConsumatorRepository;
import DAL.HallRepository;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class addConsumatorController extends CommonMethods implements Initializable {

    @FXML
    private JFXButton closebtn, minimisebtn, editProfileBtn;

    @FXML
    private AnchorPane mainhomepane, leftpane,toppane;

    @FXML
    private JFXTreeTableView<Consumator> treeView;

    @FXML
    private JFXTextField nameField, emailField, surnameFields, phoneField;
    @FXML
    private JFXComboBox selectRole;

    @FXML
    private Label rowSelectedField;

    private Consumator selectedConsumator = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectRole.getSelectionModel().selectFirst();
        leftpanecolor = leftpane.getStyle().substring(22, 29);
        page = "Home";

        moveWindow(leftpane);
        moveWindow(toppane);

        disableAllFocus(mainhomepane);
        generateTable();

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);
    }

    private void generateTable() {

        JFXTreeTableColumn<Consumator, String> conNames = new JFXTreeTableColumn<>("Name");
        conNames.setPrefWidth(200);
        conNames.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getClientname()));
        JFXTreeTableColumn<Consumator, String> conSurname = new JFXTreeTableColumn<>("Surname");
        conSurname.setPrefWidth(200);
        conSurname.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getClientsurname()));
        JFXTreeTableColumn<Consumator, String> conEmail = new JFXTreeTableColumn<>("Email");
        conEmail.setPrefWidth(200);
        conEmail.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getClientemail()));
        JFXTreeTableColumn<Consumator, String> conTel = new JFXTreeTableColumn<>("Tel");
        conTel.setPrefWidth(200);
        conTel.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getClientphone()));


        ObservableList<Consumator> consumators = FXCollections.observableArrayList();


        List<Consumator> consumatorList = null;
        try {
            consumatorList = (new ConsumatorRepository().findAll());
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        consumators.addAll(consumatorList);

         TreeItem<Consumator> root = new RecursiveTreeItem<Consumator>( consumators, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(conNames, conSurname,conEmail,conTel);
        treeView.setRoot(root);
        treeView.setShowRoot(false);


    }

    public void updateFields(MouseEvent mouseEvent) {
        if(!treeView.getSelectionModel().isEmpty()) {
            selectedConsumator = treeView.getSelectionModel().getSelectedItem().getValue();
        }
        if(selectedConsumator != null) {
            rowSelectedField.setText(selectedConsumator.getClientid() + "");
            nameField.setText(selectedConsumator.getClientname());
            surnameFields.setText(selectedConsumator.getClientsurname());
            emailField.setText(selectedConsumator.getClientemail());
            phoneField.setText(selectedConsumator.getClientphone());
        }
    }

     boolean validate() {
        Boolean valid = true;

         if(nameField.getText().isEmpty()) {
             nameField.setStyle("-fx-border-color: #d21a1a");
             valid = false;
         }else nameField.setStyle("-fx-border-color: transparent");

        if(surnameFields.getText().isEmpty()) {
            surnameFields.setStyle("-fx-border-color: #d21a1a");
             valid = false;
         }else surnameFields.setStyle("-fx-border-color: transparent");

        if(emailField.getText().isEmpty()) {
            emailField.setStyle("-fx-border-color: #d21a1a");
             valid = false;
         }else emailField.setStyle("-fx-border-color: transparent");

        if(phoneField.getText().isEmpty()) {
            phoneField.setStyle("-fx-border-color: #d21a1a");
             valid = false;
         }else phoneField.setStyle("-fx-border-color: transparent");

        return valid;
    }

    public void edit(ActionEvent actionEvent) throws CinemaException {
        if( validate() && selectedConsumator != null ) {
            selectedConsumator.setClientname(nameField.getText());
            selectedConsumator.setClientemail(emailField.getText());
            selectedConsumator.setClientsurname(surnameFields.getText());
            selectedConsumator.setClientphone(phoneField.getText());

            new ConsumatorRepository().edit(selectedConsumator);

            clearFields();
            changetype(new ActionEvent());
        }
    }

    public void deleteCinema(ActionEvent actionEvent) throws CinemaException {
        new ConsumatorRepository().delete(treeView.getSelectionModel().getSelectedItem().getValue());
        generateTable();
        clearFields();
    }

    public void clearFields() {
        selectedConsumator = null;
        rowSelectedField.setText(" ");
        nameField.setText("");
        surnameFields.setText("");
        emailField.setText("");
        phoneField.setText("");
    }

    public void changetype(ActionEvent actionEvent) {
//        selectRole.getSelectionModel().getSelectedIndex()

        switch (selectRole.getSelectionModel().getSelectedIndex()){
            case 1:
                generateTable('0');
                break;
            case 2:
                generateTable('1');
                break;
            default:
                generateTable();
                break;

        }
    }

    private void generateTable(char i) {
        JFXTreeTableColumn<Consumator, String> conNames = new JFXTreeTableColumn<>("Name");
        conNames.setPrefWidth(200);
        conNames.setCellValueFactory(param -> {
            return  new SimpleStringProperty(param.getValue().getValue().getClientname());
        });
        JFXTreeTableColumn<Consumator, String> conSurname = new JFXTreeTableColumn<>("Surname");
        conSurname.setPrefWidth(200);
        conSurname.setCellValueFactory(param -> {
            return  new SimpleStringProperty(param.getValue().getValue().getClientsurname());
        });
        JFXTreeTableColumn<Consumator, String> conEmail = new JFXTreeTableColumn<>("Email");
        conEmail.setPrefWidth(200);
        conEmail.setCellValueFactory(param -> {
            return  new SimpleStringProperty(param.getValue().getValue().getClientemail());
        });
        JFXTreeTableColumn<Consumator, String> conTel = new JFXTreeTableColumn<>("Tel");
        conTel.setPrefWidth(200);
        conTel.setCellValueFactory(param -> {
            return  new SimpleStringProperty(param.getValue().getValue().getClientphone());
        });


        ObservableList<Consumator> consumators = FXCollections.observableArrayList();


        List<Consumator> consumatorList = null;
        System.out.println(i);
        try {
            consumatorList = (new ConsumatorRepository().findByRole(i));
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        consumators.addAll(consumatorList);

        TreeItem<Consumator> root = new RecursiveTreeItem<Consumator>( consumators, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(conNames, conSurname,conEmail,conTel);
        treeView.setRoot(root);
        treeView.setShowRoot(false);


    }
}
