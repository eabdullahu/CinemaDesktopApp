/*******************************************************************************
            Controller class and logic implementation for login.fxml
 ******************************************************************************/
package GUI.View;

import BLL.Consumator;
import DAL.CinemaException;
import DAL.ConsumatorRepository;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

public class loginController extends CommonMethods implements Initializable{

    @FXML
    private JFXTextField userfield;
    @FXML
    private JFXProgressBar progressbar;
    @FXML
    private JFXPasswordField passfield;
    @FXML
    private JFXButton loginbtn, closebtn, minimisebtn;
    @FXML
    private AnchorPane mainloginpane;
    @FXML
    private StackPane stackpane;
    @FXML
    private Label errorlabel, forgotpassword, register;
    private boolean loginsuccess, vanished;
    private Consumator costumer = null;
    
    /**
     *  Initialise method that gets run whenever the login.fxml is loaded.
     *  Needed in order to initialise and set up all the logic for login.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableAllFocus(mainloginpane);
        errorlabel.setVisible(false);
        page="Login";

        if (CommonMethods.loggedout) {
            errorlabel.setText("           Logged out successfully");
            errorlabel.setVisible(true);
            FadeTransition ft=new FadeTransition(Duration.millis(1200), errorlabel);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.play();
            ft.setOnFinished(event -> {
                FadeTransition f=new FadeTransition(Duration.millis(1200), errorlabel);
                f.setFromValue(1);
                f.setToValue(0);
                f.play();
                f.setOnFinished(event1 -> {
                    errorlabel.setText("Error! Incorrect Password Or Username");
                    errorlabel.setVisible(false);
                });
            });
            CommonMethods.loggedout=false;
        }

        loginbtn.setOnMouseEntered(e -> {
            loginbtn.setCursor(Cursor.HAND);
            loginbtn.setEffect(new Bloom(0.85));
        });
        loginbtn.setOnMouseExited(e -> {
            loginbtn.setCursor(Cursor.DEFAULT);
            loginbtn.setEffect(new Bloom(1));
        });

        forgotpassword.setOnMouseReleased(event -> showDialog());
        register.setOnMouseReleased(event -> showRegisterDialog());
        moveWindow(mainloginpane);
        userfield.setOnKeyPressed(event -> fieldListners(event));
        passfield.setOnKeyPressed(event -> fieldListners(event));
    }
    
    /**
     *  Shows a JFXDialog  whenever the forgot password label is clicked.
     *  Simply displays a message and includes a close btn for closing dialog.
     */
    
    public void showRegisterDialog() {
        Text title = new Text("Register");
        title.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 13));
        
        JFXTextField name = new JFXTextField();
        name.setPromptText("Name");
        name.setLabelFloat(true);
        name.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        
        JFXTextField surename = new JFXTextField();
        surename.setPromptText("Surename");
        surename.setLabelFloat(true);
        surename.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        
        JFXTextField emailField = new JFXTextField();
        emailField.setPromptText("Email");
        emailField.setLabelFloat(true);
        emailField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        
        JFXTextField address = new JFXTextField();
        address.setPromptText("Address");
        address.setLabelFloat(true);
        address.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C; -fx-padding: 20 0 0 0;");
        
        
        Label label = new Label();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(name, surename, emailField, address, label);
        
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(title);
        dialogContent.setPrefWidth(280);
        dialogContent.setBody(vbox);
        
        JFXButton register = new JFXButton("Register");
        register.setButtonType(JFXButton.ButtonType.RAISED);
        register.setStyle("-fx-background-color: #00b59c; -fx-text-fill: white");
        register.setOnAction(event -> {
            userRegister(name, surename, address, emailField, label);
        });
        
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #FF9A00; -fx-text-fill: white");
        
        dialogContent.setActions(register, close);
        JFXDialog dialog = new JFXDialog(stackpane, dialogContent, JFXDialog.DialogTransition.TOP);
        dialog.setOverlayClose(false);
        close.setOnAction(event -> {
            dialog.close();
        });
        dialog.show();

        dialog.setOnDialogOpened(event -> mainloginpane.setEffect(new GaussianBlur(5d)));
        dialog.setOnDialogClosed(event -> mainloginpane.setEffect(new GaussianBlur(0d)));
    }
    
    public boolean userRegister(JFXTextField name, JFXTextField surename, JFXTextField address, JFXTextField email, Label label){
        ConsumatorRepository consumator = new ConsumatorRepository();
        if(!(isValidEmailAddress(email.getText()))) {
            label.setText("Invalid email address.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        } else {
            try {
                Consumator c = consumator.findByClientEmail(email.getText());
                if(c != null){
                    label.setText("Email already taken.");
                    label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
                    return false;
                }
            }catch(CinemaException io){
                io.printStackTrace();
            }
        }
        if(address.getText() == null || address.getText().isEmpty()){
            label.setText("Address can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        if(name.getText() == null || name.getText().isEmpty()){
            label.setText("Name can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        if(surename.getText() == null || surename.getText().isEmpty()){
            label.setText("Lastname can't be empty.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            return false;
        }
        try{
            Consumator c = new Consumator();
            c.setClientname(name.getText());
            c.setClientsurname(surename.getText());
            c.setClientrole('0');
            c.setClientaddres(address.getText());
            c.setClientemail(email.getText());
            String password = getRandomString(8);
            c.setClientpassword(password);
            consumator.create(c);
            
            String msg = "Dear, "+c.getClientname()+" "+c.getClientsurname()+"\n\n"
                        + "Your new Movie booking system account has been created. \n\n"
                        + "From now on, please log in to your account using these credentials.\n"
                        + "Email: "+c.getClientemail()+"\n"
                        + "Password: "+password+"\n\n"
                        + "Thank you";
            sendNewEmail(email.getText(), "enisabdullahu069@gmail.com", "Movie booking system account registration", msg);
        }catch(CinemaException io){
            label.setText("Couln't register, try again.");
            label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;"); 
            return false;
        }
        label.setText("Registered successfully, check email.");
        label.setStyle("-fx-text-fill: #68AE00; -fx-font-size: 12px; -fx-padding: 10 0 0 0;"); 
        return true;
    }
    
    public void showDialog(){
        Text title = new Text("Reset your account password");
        title.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 13));
        
        JFXTextField emailField = new JFXTextField();
        emailField.setPromptText("Email");
        emailField.setLabelFloat(true);
        emailField.setStyle("-fx-text-fill: #00043C; -fx-prompt-text-fill: #00043C;");
        Label label = new Label();
        
        VBox vbox = new VBox();
        vbox.getChildren().addAll(emailField, label);
        
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(title);
        dialogContent.setPrefWidth(280);
        dialogContent.setBody(vbox);
        
        JFXButton reset = new JFXButton("Reset");
        reset.setButtonType(JFXButton.ButtonType.RAISED);
        reset.setStyle("-fx-background-color: #00b59c; -fx-text-fill: white");
        reset.setOnAction(event -> {
            resetPassword(emailField, label);
        });
        
        JFXButton close = new JFXButton("Close");
        close.setButtonType(JFXButton.ButtonType.RAISED);
        close.setStyle("-fx-background-color: #FF9A00; -fx-text-fill: white");
        
        dialogContent.setActions(reset, close);
        JFXDialog dialog = new JFXDialog(stackpane, dialogContent, JFXDialog.DialogTransition.TOP);
        dialog.setOverlayClose(false);
        close.setOnAction(event -> {
            dialog.close();
        });
        dialog.show();

        dialog.setOnDialogOpened(event -> mainloginpane.setEffect(new GaussianBlur(5d)));
        dialog.setOnDialogClosed(event -> mainloginpane.setEffect(new GaussianBlur(0d)));
    }
    
    public void resetPassword(JFXTextField email, Label label) {
        ConsumatorRepository consumator = new ConsumatorRepository();
        try{
            Consumator c = consumator.findByEmail(email.getText());
            if(c != null){
                String passwd = getRandomString(8);
                c.setClientpassword(passwd);
                consumator.edit(c);
                label.setText("Your password changed, check email.");
                label.setStyle("-fx-text-fill: #68AE00; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
                String msg = "Hello, "+c.getClientname()+" "+c.getClientsurname()+"\n\n"
                        + "We have approved your request of reseting password, your new password is: "+passwd+"\n\n"
                        + "Thank you";
                sendNewEmail(email.getText(), "enisabdullahu069@gmail.com", "Movie booking system reset password", msg);
            } else {
                email.setText("");
                label.setText("Email doesn't exist.");
                label.setStyle("-fx-text-fill: #da0202; -fx-font-size: 12px; -fx-padding: 10 0 0 0;");
            }
        }catch(CinemaException ec){
            ec.printStackTrace();
        }
    }

    public void staffLogin(ActionEvent event){
        Timeline timeline = animateLogin("user", loginbtn);
        timeline.play();
        timeline.setOnFinished(e -> {
            if(loginsuccess == true){
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                CommonMethods.consumator = costumer;
                loginsuccess = false;
            } else {
                rotateButton(loginbtn);
                Timeline timeline2 = new Timeline();
                timeline2.setCycleCount(1);
                timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(300),
                        new KeyValue (progressbar.translateYProperty(), 0)));
                timeline2.getKeyFrames().add(new KeyFrame(Duration.millis(200),
                        new KeyValue(loginbtn.translateYProperty(), 0)));
                timeline2.play();

                timeline2.setOnFinished(event1 -> {
                    errorlabel.setVisible(true);
                    vanished=false;
                    errorlabel.setOpacity(0);
                    FadeTransition ft=new FadeTransition(Duration.millis(500),errorlabel);
                    ft.setFromValue(0);
                    ft.setToValue(1);
                    ft.play();
                    progressbar.setVisible(false);
                    CommonMethods.disablelogin=true;
                });    
            }
        });
    }
    
    private Timeline animateLogin(String type, JFXButton button){
        CommonMethods.disablelogin=false;
        progressbar.setVisible(true);
        errorlabel.setVisible(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(300),
                new KeyValue (progressbar.translateYProperty(), -70)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
                new KeyValue(loginbtn.translateYProperty(), -30)));
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(2400)));

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                loginsuccess = checkUser();
            }
        });
        t.start();
        return timeline;
    }
    
    public boolean checkData(String username, String password){
        if(!isValidEmailAddress(username)){
            platformRunLater("Error! Email addres isn't valid.");
            return false;
        }
        if(password.isEmpty()){
            platformRunLater("Error! Password can't be empty.");
            return false;
        }
        return true;
    }
    
    private boolean checkUser(){
        String username=userfield.getText();
        String password=passfield.getText();
        if(!checkData(username, password)){
            return false;
        }
        ConsumatorRepository consumator = new ConsumatorRepository();
        try{
            costumer = consumator.findByUsernameAndPassword(username, password);
            if(costumer != null){
                return true;
            }else{
                platformRunLater("Error! Incorrect email or password.");
            }
        }catch(CinemaException e){
            e.printStackTrace();
        }
        return false;
    }

    public void platformRunLater(String message){
        Platform.runLater(new Runnable() {
            @Override
             public void run() {
                errorlabel.setText(message);
             }
        });
    }
    
    private void fieldListners(KeyEvent event){
        FadeTransition ft=new FadeTransition(Duration.millis(500),errorlabel);
        if(event.getCode()== KeyCode.ENTER){
            loginbtn.fire();
        }else if(errorlabel.isVisible() && vanished==false){
            vanished=true;
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.play();
            ft.setCycleCount(1);
            ft.setOnFinished(event1 -> {
                errorlabel.setVisible(false);
            });
        }
    }
}
