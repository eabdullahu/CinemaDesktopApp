package GUI.View;

import BLL.BoughtTickets;
import BLL.Hall;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.BoughtTicketsRepository;
import DAL.CinemaException;
import com.jfoenix.controls.JFXButton;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class seatsController extends CommonMethods implements Initializable{
    @FXML
    private JFXButton cancelbtn, confirmbtn;
    @FXML
    private GridPane grid;
    @FXML
    private JFXButton seats;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private HBox hb = new HBox();
    @FXML
    private AnchorPane mainpane;

    private BoughtTicketsRepository bTicketRep = new BoughtTicketsRepository();
    public static Integer numberOfSelectedSeats = 0;
    private Integer maxSeats = checkoutController.selectedtickets;
    private Movie movie = CommonMethods.choosenMovie;
    private MovieTickets movieTicket = CommonMethods.choosenMovieTicket;
    public static HashMap<Short, List<Short>> selectedSeats = null;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedSeats = new HashMap<Short, List<Short>>();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        numberOfSelectedSeats = 0;
        moveWindow(mainpane);
        disableAllFocus(mainpane);
        seatsPlace();
    }
    
    public HashMap<Short, List<Short>> bTicketRep(){
        HashMap<Short, List<Short>> hash = new HashMap<>();
        try{
            List<BoughtTickets> bTickets = bTicketRep.findByMovieIdAndTicketid(movie, movieTicket);
            for(int i = 0; i < bTickets.size(); i++){
                if(!hash.containsKey(bTickets.get(i).getBTicketrow())){
                    hash.put(bTickets.get(i).getBTicketrow(), new ArrayList<>());
                }
                List<Short> asd = hash.get(bTickets.get(i).getBTicketrow());
                asd.add(bTickets.get(i).getBTicketseatnumber());
                hash.put(bTickets.get(i).getBTicketrow(), asd);
            }
        }catch (CinemaException ex){
            ex.printStackTrace();
        }
        return hash;
    }
    
    public void seatsPlace(){
        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(5);
        grid.setVgap(5);
        HashMap<Short, List<Short>> list = bTicketRep();
        Hall hall = movieTicket.getTickethallid();
        CommonMethods.movieHall = hall;
        char row = 'A';
        for(int i = 1; i < hall.getHallrow()+1 ; i++){
            List<Short> arrayList = list.get((short)i);
            for(int j = 1; j < hall.getHallcolumns()+1; j++){
                seats = new JFXButton();
                if(arrayList != null){
                    if(arrayList.contains((short)j)){
                        seats.setStyle("-fx-background-color: #C40018; -fx-text-fill: white;");
                    }else{
                        seatsSelected(seats);
                    }
                }else{
                    seatsSelected(seats);
                }

                seats.setText((char)(row) + "" + j);
                seats.setMinWidth(40);
                seats.setMinHeight(30);
                seats.setId(i+"_"+j);
                seats.setCursor(Cursor.HAND);

                hb.getChildren().add(seats);
                GridPane.setConstraints(seats, j, i, 1, 1, HPos.CENTER, VPos.CENTER);
                grid.getChildren().addAll(seats);
            }
            row += 1;
        }
    }
    
    private void popSeat(JFXButton btn){
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
        st.setOnFinished(event -> {
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), btn);
            st2.setToX(1);
            st2.setToY(1);
            st2.setRate(1.5);
            st2.setCycleCount(1);
            st2.play();
        });
    }
    
    public void seatsSelected(JFXButton seat){
        seat.setStyle("-fx-background-color: #8EA6B4; -fx-text-fill: white;");
        seat.setOnMouseClicked(e -> {
            String[] s2 = seat.getId().split("_");
            if(isSelected(Short.parseShort(s2[0]), Short.parseShort(s2[1]))){
                removeSelectedSeat(seat);
            }else if(numberOfSelectedSeats < maxSeats){
                userSeats(seat, Short.parseShort(s2[0]), Short.parseShort(s2[1]));
                numberOfSelectedSeats++;
                seat.setStyle("-fx-background-color: #4A772F; -fx-text-fill: white;");
                popSeat(seat);
            }
        });
    }
    
    public boolean isSelected(Short row, Short seat){
        List<Short> asd = selectedSeats.get(row);
        if(asd != null){
            if(asd.contains(seat)){
                asd.remove(seat);
                numberOfSelectedSeats--;
                return true;
            }else{
                return false;
            }
        }
        return false;
    }
    
    public void removeSelectedSeat(JFXButton seat){
        seat.setStyle("-fx-background-color: #8EA6B4; -fx-text-fill: white;");
    }
    
    public void userSeats(JFXButton seatBtn, Short row, Short seat){
        if(!selectedSeats.containsKey(row)){
            selectedSeats.put(row, new ArrayList<>());
        }
        List<Short> asd = selectedSeats.get(row);
        if(!asd.contains(seat)){
            asd.add(seat);
        }
        selectedSeats.put(row, asd);
    }
    
    private void popButon(JFXButton btn, ActionEvent event2){
        ScaleTransition st = new ScaleTransition(Duration.millis(200), btn);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setRate(1.5);
        st.setCycleCount(1);
        st.play();
        st.setOnFinished(event -> {
            ScaleTransition st2 = new ScaleTransition(Duration.millis(200), btn);
            st2.setToX(1);
            st2.setToY(1);
            st2.setRate(1.5);
            st2.setCycleCount(1);
            st2.play();
            handleClose(event2);
        });
    }
    
    public void handleCancellation(ActionEvent event){
        numberOfSelectedSeats = 0;
        popButon(cancelbtn, event);
    }

    public void handleConfirmation(ActionEvent event){
        if(numberOfSelectedSeats == maxSeats){
//            checkoutController.seatsLabel.setText("Yes");
            popButon(confirmbtn, event);
        }else {
            popSeat(confirmbtn);
            confirmbtn.setStyle("-fx-background-color: #C40018; -fx-background-radius: 2;");
        }
    }
}
