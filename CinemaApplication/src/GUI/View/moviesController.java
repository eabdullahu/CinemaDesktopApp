/*******************************************************************************
            Controller class and logic implementation for movies.fxml
 ******************************************************************************/
package GUI.View;

import BLL.Hall;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.CinemaException;
import DAL.MovieRepository;
import DAL.MovieTicketsRepository;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;

public class moviesController extends CommonMethods implements Initializable{
    @FXML
    private JFXButton contactbtn, logoutbtn, searchBtn, selectSeats, editProfileBtn, cinemabtn, hallsbtn, moviesbtn, checkoutbtn, ticketsbtn, usersbtn;
    @FXML
    private AnchorPane mainmoviespane, leftpane, toppane, moviepane, tablepane,
            DATE1, DATE2, DATE3, DATE4;
    @FXML
    private ImageView movieimage;
    @FXML
    private Label movietitle, movierating, movieduration, moviecategory, movieshowingtodate,userlabel,
            date1Label, date2Label, date3Label, date4Label;
    @FXML
    private JFXButton movieStatus, movieCinema, movieTrailer;

    @FXML
    private JFXComboBox hallCombo;
    
    private List<MovieTickets> movieTickets = new ArrayList<MovieTickets>();
    private List<SlotsController> slots = new ArrayList<SlotsController>();
    private MovieRepository movieRep = new MovieRepository();
    private MovieTicketsRepository movieTicketsRep = new MovieTicketsRepository();
    private LinkedHashMap<String, String> st = new LinkedHashMap<String, String>();

    private List test = new ArrayList<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CommonMethods.choosenMovie = null;
        try {
            setDefaultMovie();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            mainmoviespane.getChildren().add(stackPane);
            editProfileBtn.setOnAction(event -> {
                editProfile(stackPane, mainmoviespane);
            });
            
            AnchorPane.setTopAnchor(stackPane, (mainmoviespane.getPrefHeight() - 280) / 2);
            AnchorPane.setLeftAnchor(stackPane, (mainmoviespane.getPrefWidth() - 280) / 2);
        }
        returnAction(logoutbtn);
        
        leftpanecolor=leftpane.getStyle().substring(22,29);
        page="Movie Screens";
        disableAllFocus(mainmoviespane);
        displayUserName(userlabel);
        moveWindow(leftpane);
        moveWindow(toppane);
        animate();
    }
    
    public void getMovieSlots(Movie m, Hall h){
        try{
            if(h == null){
                movieTickets = null;
            } else {
                movieTickets = movieTicketsRep.findByMovieIdAndHallId(m, h);
            }
            tablepane.getChildren().removeAll(test);
            test.clear();
            if(movieTickets != null) {
                int i = 0;
                for(MovieTickets mT : movieTickets){
                    String s = new SimpleDateFormat("dd/MM/YYYY").format(mT.getTicketplaydate());
                    if(!st.containsKey(s)){
                        i++;
                        if(st.size() == 4){
                            break;
                        }
                        st.put(s, "DATE"+i);
                        returnLabelName(i).setText(s);
                    }
                    returnDate(i).setVisible(true);
                    slots.add(new SlotsController(mT.getTicketid(), s, new SimpleDateFormat("HH:mm:ss").format(mT.getTicketplayhour()), st.get(s), mT.getTicketsavailable()));
                }
                iterateSlots();
            }
            removePanes();
        } catch (CinemaException ex){
            ex.printStackTrace();
        }
    }
    
    public void removePanes() {
        Integer s = st.size()+1;
        for(int i = 4; i >= s; i--){
            returnDate(i).setVisible(false);
            StackPane pane = new StackPane();
            test.add(pane);
            tablepane.getChildren().add(pane);
            pane.setPrefWidth(180d);
            pane.setPrefHeight(67d);
            Timetable location=Timetable.valueOf("DATE"+i+"_SLOT2");
            pane.setLayoutX(location.getX());
            pane.setLayoutY(location.getY());
            Label info=new Label("No published tickets.");
            info.setLayoutX(10);
            info.setLayoutY(5);
            info.setStyle("-fx-text-fill: black; -fx-font-size: 14px");
            pane.getChildren().add(info);
            pane.setAlignment(Pos.CENTER);
        }
    }
    
    public AnchorPane returnDate(int i){
        switch(i){
            case 1:
                return DATE1;
            case 2:
                return DATE2;
            case 3:
                return DATE3;
            case 4:
                return DATE4;
        }
        return null;
    }
    
    public Label returnLabelName(int i){
        switch(i){
            case 1:
                return date1Label;
            case 2:
                return date2Label;
            case 3:
                return date3Label;
            case 4:
                return date4Label;
        }
        return null;
    }
    
    public void iterateSlots(){
        for(SlotsController s : slots){
            assignPane(s.getPlace()+"_"+s.getTime(), s.getTickets(), s.getPlace()+"_"+s.getTime(), s.getId());
        }
    }
    
    private void assignPane(String position, int availableseats, String id, int ticketId){
        AnchorPane pane=new AnchorPane();
        test.add(pane);
        tablepane.getChildren().add(pane);
        pane.setPrefWidth(180d);
        pane.setPrefHeight(67d);
        Timetable location=Timetable.valueOf(position);
        pane.setLayoutX(location.getX());
        pane.setLayoutY(location.getY());

        pane.getStyleClass().add("tiles");
        Label info=new Label("Available Seats");
        Label seats=new Label(String.valueOf(availableseats));
        info.setLayoutX(10);
        info.setLayoutY(5);
        info.setStyle("-fx-text-fill: white; -fx-font-size: 13px");
        seats.setLayoutX(10);
        seats.setLayoutY(20);
        seats.setStyle("-fx-text-fill: white; -fx-font-size: 29px");
        pane.getChildren().addAll(info,seats);
        setSubPane(pane,availableseats, ticketId);
        pane.setId(id);
        seats.setId("seat");
    }

    private void setSubPane(AnchorPane pane, int availableseats, int ticketId){
        AnchorPane subpane=new AnchorPane();
        test.add(subpane);
        pane.getChildren().add(subpane);
        subpane.setPrefWidth(67d);
        subpane.setPrefHeight(67d);
        subpane.setLayoutX(113d);
        subpane.setLayoutY(0.5d);

        if(availableseats >= 10){
            pane.setStyle("-fx-background-color:  #A2C11C");
            subpane.setStyle("-fx-background-color:  #68AE00");
            
            subpane.setOnMouseEntered(e -> {
                subpane.setCursor(Cursor.HAND);
                subpane.setStyle("-fx-background-color:  #487900");
                subpane.setEffect(new Bloom(0.85));
            });
            
            subpane.setOnMouseExited(e -> {
                subpane.setStyle("-fx-background-color:  #68AE00");
                subpane.setEffect(new Bloom(1));
            });
        }else if(availableseats < 10 && availableseats > 0){
            pane.setStyle("-fx-background-color: #FF9338");
            subpane.setStyle("-fx-background-color: #CC752C");
            subpane.setOnMouseEntered(e -> {
                subpane.setCursor(Cursor.HAND);
                subpane.setStyle("-fx-background-color:  #A35D23");
                subpane.setEffect(new Bloom(0.85));
            });
            
            subpane.setOnMouseExited(e -> {
                subpane.setStyle("-fx-background-color:  #CC752C");
                subpane.setEffect(new Bloom(1));
            });
        }else{
            pane.setStyle("-fx-background-color: #fc1919");
            subpane.setStyle("-fx-background-color: #da0202");
        }
        subpane.getStyleClass().add("tiles2");
        
        if(availableseats > 0){
            subpane.setOnMouseClicked(e -> {
                try {
                    CommonMethods.choosenTicket = ticketId;
                    launchScene("checkout.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
        
        FontAwesomeIconView cart=new FontAwesomeIconView();
        cart.setGlyphName("SHOPPING_CART");
        cart.setSize("30");
        subpane.getChildren().add(cart);
        cart.setLayoutX(18d);
        cart.setY(45d);
        cart.setStyle("-fx-fill: white");
    }

    private void setDefaultMovie(){
        try{
            Movie movie = movieRep.findById(CommonMethods.selectedFilmId);
            CommonMethods.choosenMovie = movie;

            ObservableList<Hall> halls = FXCollections.observableArrayList();
            List<Hall> hallList = movieTicketsRep.getMoviePlayHalls(movie);
            halls.addAll(hallList);
            hallCombo.setItems(halls);
            hallCombo.getSelectionModel().select(0);

            getMovieSlots(movie, (hallList.size() > 0 ? hallList.get(0) : null));

            String executionPath = System.getProperty("user.dir");
            File movieImage = new File(executionPath+"\\images\\movies\\"+movie.getMovieicon());
            movieimage.setImage(new Image(movieImage.toURI().toString()));
            movietitle.setText(movie.getMoviename());
            movierating.setText(movie.getMovierating()+"");
            movieduration.setText(movie.getMovieduration());
            moviecategory.setText(movie.getMoviecategory());
            movieshowingtodate.setText(new SimpleDateFormat("MM-dd-yyyy").format(movie.getMovieshowingFromDate()));
            movieCinema.setText(CommonMethods.movieCinema.getCinemalocation());
            if(movie.getMovieStatus().equals("playing")){
                movieStatus.setText("Playing");
                movieStatus.setStyle("-fx-background-color:  #68AE00; -fx-text-fill: #FFF;");
            } else {
                movieStatus.setText("Comingsoon");
                movieStatus.setStyle("-fx-background-color:  #CC752C; -fx-text-fill: #FFF;");
            }
            movieTrailer.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    try {
                        Desktop.getDesktop().browse(new URI(movie.getMovietrailer()));
                    }catch(Exception ec){
                        ec.printStackTrace();
                    }
                }
            });
        } catch (CinemaException ex){
            ex.printStackTrace();
        }
    }
    
    public void animate(){
        popNode(moviepane);
        popNode(tablepane);
    }

    public void loadMovieHall(ActionEvent e){
        if(!hallCombo.getSelectionModel().isEmpty()) {
            Hall h = (Hall) hallCombo.getSelectionModel().getSelectedItem();
            slots.clear();
            st.clear();
            getMovieSlots(CommonMethods.choosenMovie, h);
        }
    }
}
