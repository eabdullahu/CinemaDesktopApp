package GUI.View;

import BLL.Cinema;
import BLL.Hall;
import BLL.Movie;
import BLL.MovieTickets;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.MovieRepository;
import DAL.MovieTicketsRepository;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
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

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class addTicketController extends CommonMethods implements Initializable {
    @FXML
    private AnchorPane mainhomepane, leftpane, toppane;

    @FXML
    private JFXButton editProfileBtn ;

    @FXML
    private JFXTreeTableView<MovieTickets> treeView;

    @FXML
    private JFXComboBox<Cinema> CinemasBox;

    @FXML
    private JFXDatePicker showFrom;

    @FXML
    private JFXTextField selectedMovie, TicketsPrice;

    @FXML
    private JFXComboBox<Hall> hallController;

    @FXML
    private JFXComboBox<String> playingTime;

    @FXML
    private JFXComboBox<Movie> movieBox;

    private MovieTickets selectedTicket;
    private List<Cinema> cinemas;
    private List<Movie> movies;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftpanecolor = leftpane.getStyle().substring(22, 29);
        page = "Tickets";

        moveWindow(leftpane);
        moveWindow(toppane);

        generateTable();

        try {
            //cinemas = new CinemaRepository().findAll();
            movies = new MovieRepository().findAll();
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        //ObservableList<Cinema> cinemasObservableList = FXCollections.observableArrayList(cinemas);
        ObservableList<Movie> MoviesObservableList  = FXCollections.observableArrayList(movies);
        ObservableList<String> playingTimeItems  = FXCollections.observableArrayList(Arrays.asList("16:00","18:00", "19:45", "22:30"));

        movieBox.setItems(MoviesObservableList);
        //CinemasBox.setItems(cinemasObservableList);

        playingTime.setItems(playingTimeItems);
        playingTime.setPromptText("Pick time to play");

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);
    }

    private void generateTable() {
        JFXTreeTableColumn<MovieTickets, String> ticketId = new JFXTreeTableColumn<>("ID");
        ticketId.setPrefWidth(150);
        ticketId.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getTicketid())));

        JFXTreeTableColumn<MovieTickets, String> ticketMovieName = new JFXTreeTableColumn<>("Movie");
        ticketMovieName.setPrefWidth(150);
        ticketMovieName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMovieid().getMoviename()));


        JFXTreeTableColumn<MovieTickets, String> ticketHallName = new JFXTreeTableColumn<>("Hall");
        ticketHallName.setPrefWidth(175);
        ticketHallName.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getTickethallid().getHallname()));

        JFXTreeTableColumn<MovieTickets, String> ticketShowfromDate = new JFXTreeTableColumn<>("From");
        ticketShowfromDate.setPrefWidth(175);
        ticketShowfromDate.setCellValueFactory(param -> new SimpleStringProperty("" + convertToLocalDateViaInstant(param.getValue().getValue().getTicketplaydate())));

        JFXTreeTableColumn<MovieTickets, String> ticketShowtoDate = new JFXTreeTableColumn<>("Time");
        ticketShowtoDate.setPrefWidth(175);
        ticketShowtoDate.setCellValueFactory(param -> new SimpleStringProperty("" + getPlayingTimefrombs(param.getValue().getValue().getTicketplayhour())));

        ObservableList<MovieTickets> tickets = FXCollections.observableArrayList();

        List<MovieTickets> ticketsList = null;
        try {
            ticketsList = new MovieTicketsRepository().findAll();
        } catch (CinemaException e) {
            e.printStackTrace();
        }
        assert ticketsList != null;
        tickets.addAll(ticketsList);

        final TreeItem<MovieTickets> root = new RecursiveTreeItem<>(tickets, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll( ticketId, ticketMovieName,ticketHallName, ticketShowfromDate,ticketShowtoDate);

        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    @FXML
    void deleteCinema(ActionEvent event) throws CinemaException {
        if(treeView.getSelectionModel().getSelectedItem().getValue() != null){
            new MovieTicketsRepository().delete(treeView.getSelectionModel().getSelectedItem().getValue());
            generateTable();
            clearFields();
        }
    }

    @FXML
    void fillCinema(ActionEvent event){
        if(movieBox.getSelectionModel().getSelectedItem() != null) {
            Cinema c = movieBox.getSelectionModel().getSelectedItem().getMovieCinemaid();

            ObservableList<Cinema> cinemasObservableList = FXCollections.observableArrayList(c);
            CinemasBox.setItems(cinemasObservableList);
            CinemasBox.getSelectionModel().select(c);
            generateHalls(event);
        }
    }

    private void clearFields() {
        selectedMovie.setText("");

        CinemasBox.getSelectionModel().clearSelection();

        CinemasBox.setPromptText("Select Cinema");
        movieBox.getSelectionModel().clearSelection();
        movieBox.setPromptText("select Movie");

        hallController.getSelectionModel().clearSelection();
        hallController.setPromptText("Select Hall");
        playingTime.getSelectionModel().clearSelection();
        showFrom.setValue(null);
        TicketsPrice.setText("");

    }

    private boolean validate() {
        boolean valide = true;
        if(CinemasBox.getSelectionModel().isEmpty()){
            CinemasBox.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else CinemasBox.setStyle("-fx-border-color: transparent");

        if(movieBox.getSelectionModel().isEmpty()){
            movieBox.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else movieBox.setStyle("-fx-border-color: transparent");

        if(hallController.getSelectionModel().isEmpty()){
            hallController.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else hallController.setStyle("-fx-border-color: transparent");

        if(playingTime.getSelectionModel().isEmpty()){
            playingTime.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else playingTime.setStyle("-fx-border-color: transparent");

        if(showFrom.getValue() == null){
            showFrom.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else showFrom.setStyle("-fx-border-color: transparent");

        if(TicketsPrice.getText().isEmpty() && !isNumeric(TicketsPrice.getText())){
            TicketsPrice.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else TicketsPrice.setStyle("-fx-border-color: transparent");

        return valide;

    }

    @FXML
    void edit(ActionEvent event) throws ParseException, CinemaException {

        if(selectedTicket == null)
            selectedTicket = new MovieTickets();
            selectedTicket.setTicketssold((short) 0);
        if(!validate())
            return;


        selectedTicket.setMovieid(movieBox.getSelectionModel().getSelectedItem());
        selectedTicket.setTickethallid(hallController.getValue());
        selectedTicket.setTicketplaydate(getData(showFrom));


        DateFormat formatter = new SimpleDateFormat("HH:mm");
        
        Date date = formatter.parse(playingTime.getSelectionModel().getSelectedItem());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 1);

        selectedTicket.setTicketplayhour(calendar.getTime());

        selectedTicket.setTicketsavailable((short) (hallController.getValue().getHallcolumns() * hallController.getValue().getHallrow()));
        selectedTicket.setTicketprice(BigDecimal.valueOf(Double.parseDouble((TicketsPrice.getText()))));


        new MovieTicketsRepository().edit(selectedTicket);
        // Generate tables with the new data
        generateTable();

        //clear fields for the next ticket
        clearFields();

    }

    @FXML
    void removeSelected(ActionEvent event) {
        selectedTicket = null;
       clearFields();
    }
    /*
     * TODO:Move to commonMethods
     *  @params JFXDatePicker
     * returns the date from datepicker.
     * */
    Date getData(JFXDatePicker datePicker){
        LocalDate ld = datePicker.getValue();
        Calendar c =  Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
        return  c.getTime();
    }

    @FXML
    void updateFields(MouseEvent event) throws ParseException {
        if(treeView.getSelectionModel().getSelectedItem() != null) {
            selectedTicket = treeView.getSelectionModel().getSelectedItem().getValue();

            selectedMovie.setText("" + selectedTicket.getTicketid());
            movieBox.getSelectionModel().select(selectedTicket.getMovieid());

            CinemasBox.getSelectionModel().select(selectedTicket.getMovieid().getMovieCinemaid());

            List<Hall> hallsList = (List<Hall>) selectedTicket.getTickethallid().getHallCinemaid().getHallCollection();
            ObservableList<Hall> halls = FXCollections.observableArrayList();
            halls.addAll(hallsList);

            hallController.setItems(halls);
            hallController.getSelectionModel().select(hallsList.indexOf(selectedTicket.getTickethallid()));

            showFrom.setValue(convertToLocalDateViaInstant(selectedTicket.getTicketplaydate()));


            playingTime.getSelectionModel().select(getPlayingTimefrombs(selectedTicket.getTicketplayhour()));

            TicketsPrice.setText(selectedTicket.getTicketprice() + "");
        }
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void generateHalls(ActionEvent actionEvent) {
        if(!CinemasBox.getSelectionModel().isEmpty()) {
            List<Hall> hallsList = (List<Hall>) CinemasBox.getSelectionModel().getSelectedItem().getHallCollection();

            ObservableList<Hall> halls = FXCollections.observableArrayList();
            halls.addAll(hallsList);
            hallController.setItems(halls);
        }
    }

    String getPlayingTimefrombs(Date date){
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        Timestamp ts=new Timestamp(date.getTime());
        return  formatter.format(Timestamp.from(ts.toInstant().plus(-1, ChronoUnit.HOURS)));
    }

    @FXML
    public void goToDashboard(ActionEvent event){
        try {
            launchScene("Dashboard.fxml");
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    /*
    * TODO: 1.Validate fields
    *       2.fix bug in timeslot picker
    *
    * */
}
