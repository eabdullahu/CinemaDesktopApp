/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.View;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class ContactController extends CommonMethods implements Initializable {
    @FXML
    private JFXButton contactbtn, logoutbtn, searchBtn, editProfileBtn, cinemabtn, hallsbtn, moviesbtn, submitButton, ticketsbtn, usersbtn;
    @FXML
    private Label userlabel;
    @FXML
    private AnchorPane mainhomepane, leftpane, toppane;
    @FXML
    private Label nameLabel, emailLabel, subjectLabel, messageLabel;
    @FXML
    private TextField nameField, subjectField, emailField;
    @FXML
    private TextArea messageText;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(consumator == null){
            logoutbtn.setText("Login");
            editProfileBtn.setVisible(false);
            moviesbtn.setVisible(false);
            hallsbtn.setVisible(false);
            cinemabtn.setVisible(false);
            ticketsbtn.setVisible(false);
            usersbtn.setVisible(false);
           
            contactbtn.setLayoutY(180.0);
            logoutbtn.setLayoutY(230.0);
        } else{
            if(consumator.getClientrole() == '0'){
                moviesbtn.setVisible(false);
                hallsbtn.setVisible(false);
                cinemabtn.setVisible(false);
                ticketsbtn.setVisible(false);
                usersbtn.setVisible(false);
                
                contactbtn.setLayoutY(180.0);
                editProfileBtn.setLayoutY(230.0);
                logoutbtn.setLayoutY(280.0);
            }
            StackPane stackPane = new StackPane();
            mainhomepane.getChildren().add(stackPane);
            editProfileBtn.setOnAction(event -> {
                editProfile(stackPane, mainhomepane);
            });
            
            AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
            AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);
        }
        returnAction(logoutbtn);
        page="Contact";
        leftpanecolor=leftpane.getStyle().substring(22,29);
        displayUserName(userlabel);
        moveWindow(toppane);
        moveWindow(leftpane);
        
    }
    
    @FXML
    public void sendContactEmail(ActionEvent event){
        boolean check = true;
        if(nameField.getText().isEmpty()){
            nameLabel.setTextFill(Color.web("#da0202"));
            check = false;
        }else {
            nameLabel.setTextFill(Color.web("#000000"));
        }
        if(subjectField.getText().isEmpty()){
            subjectLabel.setTextFill(Color.web("#da0202"));
            check = false;
        }else{
            subjectLabel.setTextFill(Color.web("#000000"));
        }
        if(messageText.getText().isEmpty()){
            messageLabel.setTextFill(Color.web("#da0202"));
            check = false;
        }else{
            messageLabel.setTextFill(Color.web("#000000"));
        }
        if(!(isValidEmailAddress(emailField.getText()))){
            emailLabel.setTextFill(Color.web("#da0202"));
            check = false;
        }else{
            emailLabel.setTextFill(Color.web("#000000"));
        }
        
        if(check){
            popButton(submitButton);
            submitButton.setText("Message sent");
            submitButton.setDisable(true);
        }
    }
}