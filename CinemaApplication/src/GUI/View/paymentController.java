/*******************************************************************************
 Controller class and logic implementation for payment.fxml
 ******************************************************************************/
package GUI.View;

import BLL.BoughtTickets;
import BLL.Consumator;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.BoughtTicketsRepository;
import DAL.CinemaException;
import DAL.MovieTicketsRepository;
import com.jfoenix.controls.*;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class paymentController extends CommonMethods implements Initializable {
    //Inisilisation of javafx objects and variables used.
    @FXML
    private JFXButton closebtn;
    @FXML
    private AnchorPane mainpane, toppane, cardpayment;
    @FXML
    private StackPane stackpane;
    @FXML
    public JFXComboBox expmonthbox, expyearbox;
    @FXML
    private ImageView selectedimage;
    @FXML
    private JFXTextField firstnamefield, lastnamefield, emailfield, cardfield,
            cvvfield;
    @FXML
    private Label errorlabel, movieLabel, totalPrice, datelabel, timelabel;
    private boolean printing = false;

    private BoughtTicketsRepository boughtTicketRep = new BoughtTicketsRepository();
    private MovieTicketsRepository movieTicketRep = new MovieTicketsRepository();
    
    private MovieTickets ticket = CommonMethods.choosenMovieTicket;
    private Movie movie = CommonMethods.choosenMovie;
    private Consumator consumator = CommonMethods.consumator;
    
    private Thread thread = null;
    private boolean threadRunning = true;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toppane.toFront();
        closebtn.toFront();
        moveWindow(toppane);
        setNumericFieldValidation(cvvfield, 3);
        setNumericFieldValidation(cardfield, 16);
        setUpCardPage();
    }

    private void setUpCardPage() {
        List<Integer> list = new ArrayList<>();
        ObservableList<Integer> o1 = FXCollections.observableList(list);
        o1.addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        expmonthbox.setItems(o1);

        ObservableList<Integer> o2 = FXCollections.observableList(new ArrayList<>());
        for (int i = 20; i < 25; i++) {
            o2.add(new Integer(i));
        }
        
        firstnamefield.setText(consumator.getClientname());
        lastnamefield.setText(consumator.getClientsurname());
        emailfield.setText(consumator.getClientemail());
        firstnamefield.setDisable(true);
        lastnamefield.setDisable(true);
        emailfield.setDisable(true);
        
        expyearbox.setItems(o2);
        movieLabel.setText("Name:     " + movie.getMoviename());
        totalPrice.setText("Total:      " + String.format("%.2f", checkoutController.totalprice)+"\u20AC");
        datelabel.setText("Date:       " + new SimpleDateFormat("MM-dd-yyyy").format(ticket.getTicketplaydate()));
        timelabel.setText("Time:       " + new SimpleDateFormat("HH:mm:ss").format(ticket.getTicketplayhour()));
        String executionPath = System.getProperty("user.dir");
        File movieImg = new File(executionPath+"\\images\\movies\\"+movie.getMovieicon());
        selectedimage.setImage(new javafx.scene.image.Image(movieImg.toURI().toString()));
    }
    
    private void printReceipt(String msg) {
        if (printing) {
            return;
        }
        printing = true;
        Text title = new Text(msg);
        title.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
        title.setFill(Paint.valueOf("#00204A"));
        JFXDialogLayout dialogContent = new JFXDialogLayout();
        dialogContent.setHeading(title);
        dialogContent.setPrefWidth(300);
        ProgressIndicator spinner = new ProgressIndicator();
        spinner.setStyle("-fx-progress-color: #3A0088");
        dialogContent.setBody(spinner);
        stackpane = new StackPane();
        cardpayment.getChildren().add(stackpane);

        stackpane.setPrefWidth(800d);
        stackpane.setPrefHeight(500d);
        showDialog(stackpane, dialogContent);
    }

    private void showDialog(StackPane stackPane, JFXDialogLayout dialogContent) {
        JFXDialog dialog = new JFXDialog(stackpane, dialogContent, JFXDialog.DialogTransition.TOP);
        dialog.setOverlayClose(false);
        dialog.show();
        PauseTransition p = new PauseTransition(Duration.millis(3000));
        thread = new Thread(){
            @Override
            public void run() {
                while (threadRunning) {
                    insertIntoDb();
                }
            }
        };
        thread.start();
        p.play();
        p.setOnFinished(event -> {
            Text msg = new Text("Transaction Complete");
            msg.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 18));
            msg.setFill(Paint.valueOf("#00204A"));
            dialogContent.setHeading(msg);
            PauseTransition p2 = new PauseTransition(Duration.millis(5000));
            p2.play();
            p2.setOnFinished(event1 -> {
                printing = false;
                try {
                    launchScene("home.fxml");
                } catch (IOException ex) {
                    Logger.getLogger(paymentController.class.getName()).log(Level.SEVERE, null, ex);
                }
                cardpayment.getChildren().remove(stackpane);
                dialog.close();
                closebtn.fire();
            });
        });
    }
    
    public void insertIntoDb(){
        CommonMethods.ticketsNames = new ArrayList<>();
        HashMap<Short, List<Short>> selectedSeatsLocal = (HashMap<Short, List<Short>>) seatsController.selectedSeats.clone();
        try {
            Integer avail = choosenMovieTicket.getTicketsavailable() - seatsController.numberOfSelectedSeats;
            Integer sold = choosenMovieTicket.getTicketssold() + seatsController.numberOfSelectedSeats;
            choosenMovieTicket.setTicketsavailable(avail.shortValue());
            choosenMovieTicket.setTicketssold(sold.shortValue());
            movieTicketRep.edit(choosenMovieTicket);
            for(Short s : selectedSeatsLocal.keySet()){
                List<Short> lista = selectedSeatsLocal.get(s);
                if(lista != null){
                    for(Short seat : lista){
                        BoughtTickets bTicket = new BoughtTickets();
                        bTicket.setBTicketclientid(CommonMethods.consumator);
                        bTicket.setBTicketcinemaid(CommonMethods.movieCinema);
                        bTicket.setBTickethallid(CommonMethods.movieHall);
                        bTicket.setBTicketmovieid(choosenMovie);
                        bTicket.setBTicketmovieticketid(choosenMovieTicket);
                        bTicket.setBTicktestatus("paid");
                        bTicket.setBTicketgeneratedtoken("asdasdasdasdasd");
                        bTicket.setBTicketPrice(BigDecimal.valueOf(checkoutController.pricePerTicket));
                        bTicket.setBTicketrow(s);
                        bTicket.setBTicketseatnumber(seat);
                        bTicket.setBTicketdate(new java.sql.Date(System.currentTimeMillis()));
                        boughtTicketRep.create(bTicket);
                        CommonMethods.ticketsNames.add(bTicket.getBTicketid()+""+bTicket.getBTicketrow()+""+bTicket.getBTicketseatnumber()+"");
                        convertToPdf(bTicket, seatsController.numberOfSelectedSeats, checkoutController.pricePerTicket);
                    }
                }
            }
            sendEmailWithAttachments(consumator.getClientemail());
        } catch(CinemaException ex){
            ex.printStackTrace();
        }
        threadRunning = false;
    }

    public void handleButtons(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
        st.setOnFinished(event1 -> {
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), btn);
            st2.setToX(1);
            st2.setToY(1);
            st2.setRate(1.5);
            st2.setCycleCount(1);
            st2.play();
            if (btn.getId().equals("placeorderbtn")) {
                if (validateCardPage()) {
                    printReceipt("Finishing transaction");
                }
            } else if (btn.getId().equals("Processing Order")) {
                cancelOrder(event);
            } else if (btn.getId().equals("cancelbtn")) {
                cancelOrder(event);
            }
        });
    }

    private boolean validateCardPage() {
        if (firstnamefield.getText().isEmpty() || lastnamefield.getText().isEmpty() ||
                emailfield.getText().isEmpty() ||
                cardfield.getText().isEmpty() || cvvfield.getText().isEmpty() ||
                expmonthbox.getSelectionModel().isEmpty() ||
                expyearbox.getSelectionModel().isEmpty()) {
            errorlabel.setText("All fields need to be filled.");
            return false;
        } else if (!(isValidEmailAddress(emailfield.getText()))) {
            errorlabel.setText("Invalid Email Address");
            return false;
        } else if (cardfield.getText().length() != 16) {
            errorlabel.setText("Invalid Bank Card Length");
            return false;
        } else if (cvvfield.getText().length() != 3) {
            errorlabel.setText("Invalid CVV Length");
            return false;
        }
        return true;
    }

    private void setNumericFieldValidation(JFXTextField field, int limit) {
        field.lengthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (field.getText().length() > limit) {
                    field.setText(field.getText().substring(0, limit));
                } else if (!field.getText().matches("\\d*")) {
                    field.setText(field.getText().replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    public void cancelOrder(ActionEvent event) {
        handleClose(event);
    }
}
