/*******************************************************************************
 Main class that loads up the initial login.fxml and starts up application.
 ******************************************************************************/
package GUI.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setResizable(false);
            Scene scene=new Scene(root, 1200,700);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
            CommonMethods.primaryStage = primaryStage;
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}