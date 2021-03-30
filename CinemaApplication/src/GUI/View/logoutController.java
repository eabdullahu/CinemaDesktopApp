/*******************************************************************************
            Controller class and logic implementation for logout.fxml
 ******************************************************************************/
package GUI.View;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class logoutController extends CommonMethods implements Initializable{
    @FXML
    private JFXButton yesbtn, nobtn, closebtn;
    @FXML
    private AnchorPane logoutpane;

    /**
     *  Initialise method required for implementing initializable and,
     *  sets up and applies all effects and animations to nodes in logout.fxml
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        page="Log Out";
        disableAllFocus(logoutpane);
        moveWindow(logoutpane);
    }

    public void handleConfirmation(ActionEvent event){
        JFXButton btn=(JFXButton)event.getSource();
        TranslateTransition t1=new TranslateTransition(Duration.millis(270),btn);
        t1.setToY(-17d);
        PauseTransition p1=new PauseTransition(Duration.millis(40));
        TranslateTransition t2=new TranslateTransition(Duration.millis(270),btn);
        t2.setToY(0d);

        SequentialTransition d=new SequentialTransition(btn, t1,p1,t2);
        d.play();
        d.setOnFinished(event1 -> {
            if(btn.getId().equals("yesbtn")){
                CommonMethods.confirmed=true;
                handleClose(event);
            }else if(btn.getId().equals("nobtn")){
                CommonMethods.confirmed=false;
                handleClose(event);
            }
        });
    }
}


