/*******************************************************************************
 Controller class and logic implementation for checkout.fxml
 ******************************************************************************/
package GUI.View;

import BLL.Cinema;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.MovieRepository;
import DAL.MovieTicketsRepository;
import com.jfoenix.controls.*;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.*;
import java.text.*;
import java.io.IOException;
import java.net.URL;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class checkoutController extends CommonMethods implements Initializable{
    //Inisilisation of javafx objects and variables used.
    @FXML
    private JFXButton contactbtn, logoutbtn, searchBtn, selectSeats, editProfileBtn, cinemabtn, hallsbtn, moviesbtn, checkoutbtn, ticketsbtn, usersbtn;
    @FXML
    private JFXComboBox chooseTickets;
    @FXML
    private ImageView movieImage;
    @FXML
    private AnchorPane mainpane, leftpane, toppane, selectPane, moviePane;
    @FXML
    private Label ticketPriceLabel,timelabel,datelabel,movieLabel,userlabel, ticketPrice, discountLabel,cinemalabel;
    @FXML
    public static Label seatsLabel;
    @FXML
    private Label errorlabel;
    
    private MovieTicketsRepository movieTicketsRep = new MovieTicketsRepository();
    private MovieRepository movieRep = new MovieRepository();
    private CinemaRepository cinemaRep = new CinemaRepository(); 
    
    public static double totalprice = 0.00;
    public static int selectedtickets = 0;
    public static double ticketPriceDB = 0;
    public static double pricePerTicket = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommonMethods.choosenMovieTicket = null;
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
            mainpane.getChildren().add(stackPane);
            editProfileBtn.setOnAction(event -> {
                editProfile(stackPane, mainpane);
            });
            
            AnchorPane.setTopAnchor(stackPane, (mainpane.getPrefHeight() - 280) / 2);
            AnchorPane.setLeftAnchor(stackPane, (mainpane.getPrefWidth() - 280) / 2);
        }
        returnAction(logoutbtn);
        totalprice = 0;
        displayUserName(userlabel);
        selectSeats.setVisible(false);
        
        leftpanecolor = leftpane.getStyle().substring(22, 29);
        moveWindow(leftpane);
        moveWindow(toppane);
        disableAllFocus(mainpane);
        popNode(selectPane);
        popNode(moviePane);
        
        selectedTicket();
        checkTickets ();
    }
    
    public void continuePayment(){
        popButton(checkoutbtn);
        if(consumator == null){
            errorlabel.setText("You have to be logged in to continue.");
        }else{
            selectedtickets = Integer.parseInt((String) chooseTickets.getValue());
            if(selectedtickets != 0 && seatsController.numberOfSelectedSeats == selectedtickets){
                showPopUp("card-payment");
            }else{
                errorlabel.setText("Please select seats.");
            }
        }
    }
    
    public void selectedTicket(){
        chooseTickets.getSelectionModel().select(0);
        ticketPriceLabel.setText(String.format("%.2f", totalprice)+"\u20AC");
        try {
            MovieTickets ticket = movieTicketsRep.findById(CommonMethods.choosenTicket);
            CommonMethods.choosenMovieTicket = ticket;
            Movie movie = CommonMethods.choosenMovie;
            Cinema cinema = CommonMethods.movieCinema;
            movieLabel.setText("Name:     " + movie.getMoviename());
            cinemalabel.setText("Cinema:   " + cinema.getCinemalocation() + " - " + cinema.getCinemaname());
            datelabel.setText("Date:       " + new SimpleDateFormat("MM-dd-yyyy").format(ticket.getTicketplaydate()));
            timelabel.setText("Time:       " + new SimpleDateFormat("HH:mm:ss").format(ticket.getTicketplayhour()));
            ticketPrice.setText(ticket.getTicketprice()+"\u20AC");
            ticketPriceDB = ticket.getTicketprice().doubleValue();;
            String executionPath = System.getProperty("user.dir");
            File movieImg = new File(executionPath+"\\images\\movies\\"+movie.getMovieicon());
            movieImage.setImage(new Image(movieImg.toURI().toString()));
        } catch(CinemaException ex){
            ex.printStackTrace();
        }
    }
    
    public void checkTickets (){
        if(CommonMethods.choosenMovieTicket.getTicketsavailable() < 10){
            for(int i = 10; i > CommonMethods.choosenMovieTicket.getTicketsavailable(); i--){
                chooseTickets.getItems().remove(i);
            }
        }
    }
   
    public void showPopUp(String page){
        Stage stage = (Stage) mainpane.getScene().getWindow();
        mainpane.setEffect(new ColorAdjust(0,0,-0.25,0));
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(page+".fxml"));
            Stage popup = new Stage();
            Scene scene=new Scene(parent);
            scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

            popup.initOwner(stage);
            popup.setScene(scene);
            popup.initStyle(StageStyle.UNDECORATED);
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.showAndWait();
            mainpane.setEffect(new ColorAdjust(0,0,0,0));
            popup.close();
            page = "Add";
        } catch (IOException ex) {
            System.out.println("Error in showing pop up");
            ex.printStackTrace();
        }
    }

    public void handleChoice(ActionEvent e) {
        selectedtickets = Integer.parseInt((String) chooseTickets.getValue());
        
        if(selectedtickets != 0){
            if(selectedtickets >= 5){
                discountLabel.setText("- 5%");
                totalprice = (ticketPriceDB * 0.95) * selectedtickets;
                pricePerTicket = (ticketPriceDB * 0.95);
            }else{
                discountLabel.setText("- 0%");
                totalprice = (ticketPriceDB * selectedtickets);
                pricePerTicket = ticketPriceDB;
            }
            selectSeats.setVisible(true);
        } else {
            selectSeats.setVisible(false);
            totalprice = 0;
        }
        ticketPriceLabel.setText(String.format("%.2f", totalprice)+"\u20AC");
    }
    
    public void seatsBtn(ActionEvent e) {
        showPopUp("seats");
    }
}