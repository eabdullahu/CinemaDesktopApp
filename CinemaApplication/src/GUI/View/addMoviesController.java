package GUI.View;

import BLL.Cinema;
import BLL.Movie;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.MovieRepository;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class addMoviesController extends CommonMethods implements Initializable {

    @FXML
    private AnchorPane mainhomepane, leftpane, toppane;

    @FXML
    private JFXButton homebtn, addbtn, moviescreensbtn, uploadIcon, uploadImage;

    @FXML
    private JFXButton editProfileBtn;

    @FXML
    private JFXTextField MovieNameField, durationField, ratingField;

    @FXML
    private JFXRadioButton showOnSlide, comingSoon;

    @FXML
    private JFXDatePicker showFrom, showTo;

    @FXML
    private CheckComboBox<String> movieCategory;

    @FXML
    private JFXTreeTableView<Movie> treeView;

    @FXML
    private JFXComboBox<Cinema> CinemasBox;

    @FXML
    private JFXTextField selectedMovie;


    private Movie selectedMovieInstance;
    private List<Cinema> cinemas;
    private Map<Integer,String> cinemaMap = new HashMap<>();

    private File iconFile;
    private File imageFile;
    private List<String> filmCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        leftpanecolor = leftpane.getStyle().substring(22, 29);
        page = "Movie";

        moveWindow(leftpane);
        moveWindow(toppane);

        disableAllFocus(mainhomepane);
        generateTable();

        try {
            cinemas = new CinemaRepository().findAll();
        } catch (CinemaException e) {
            e.printStackTrace();
        }

        if(cinemas.size() > 0){
            for (Cinema cinema : cinemas) {
                cinemaMap.put(cinema.getCinemaid(), cinema.getCinemaname());
            }
        }

        ObservableList<Cinema> jobsOList = FXCollections.observableArrayList(cinemas);

        CinemasBox.setItems(jobsOList);
        CinemasBox.setPromptText("Select Cinema");

        filmCategory = Arrays.asList("Action", "Animated", "Comedy", "Drama", "Fantasy", "Horror", "SC-FI", "Sports");
        ObservableList<String> filmCategoryList = FXCollections.observableArrayList(filmCategory);

        movieCategory.getItems().addAll(filmCategoryList);

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);

    }

    private void generateTable() {

        JFXTreeTableColumn<Movie, String> movieId = new JFXTreeTableColumn<>("ID");
        movieId.setPrefWidth(150);
        movieId.setCellValueFactory(param -> new SimpleStringProperty(String.valueOf(param.getValue().getValue().getMovieCinemaid())));

        JFXTreeTableColumn<Movie, String> movieCategory = new JFXTreeTableColumn<>("Category");
        movieCategory.setPrefWidth(150);
        movieCategory.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMoviecategory()));


        JFXTreeTableColumn<Movie, String> movieAwards = new JFXTreeTableColumn<>("Awards");
        movieAwards.setPrefWidth(175);
        movieAwards.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getValue().getMovieawards()));

        JFXTreeTableColumn<Movie, String> movieName = new JFXTreeTableColumn<>("Name");
        movieName.setPrefWidth(175);
        movieName.setCellValueFactory(param -> new SimpleStringProperty("" + param.getValue().getValue().getMoviename()));

        ObservableList<Movie> movies = FXCollections.observableArrayList();

        List<Movie> movieList = null;
        try {
            movieList = new MovieRepository().findAll();
        } catch (CinemaException e) {
            e.printStackTrace();
        }
        assert movieList != null;
        movies.addAll(movieList);

        final TreeItem<Movie> root = new RecursiveTreeItem<Movie>(movies, RecursiveTreeObject::getChildren);

        treeView.getColumns().setAll(movieName, movieId,movieCategory,movieAwards);

        treeView.setRoot(root);
        treeView.setShowRoot(false);

    }

    public void updateFields(MouseEvent mouseEvent) throws FileNotFoundException {
        if(treeView.getSelectionModel().getSelectedItem() != null){
            selectedMovieInstance = treeView.getSelectionModel().getSelectedItem().getValue();
           selectedMovie.setText("" + selectedMovieInstance.getMovieid());

            MovieNameField.setText(selectedMovieInstance.getMoviename());
            CinemasBox.getSelectionModel().select(selectedMovieInstance.getMovieCinemaid());
            selectMovieCategorys(selectedMovieInstance.getMoviecategory());
            durationField.setText(selectedMovieInstance.getMovieduration());
            ratingField.setText(selectedMovieInstance.getMovierating() + "");

            if (selectedMovieInstance.getMovieshowToSlide().equals('1')) {
                showOnSlide.setSelected(true);
            } else {
                showOnSlide.setSelected(false);
            }

            if (selectedMovieInstance.getMovieStatus().equals("comingsoon")) {
                comingSoon.setSelected(true);
            } else {
                comingSoon.setSelected(false);
            }

            showFrom.setValue(convertToLocalDateViaInstant(selectedMovieInstance.getMovieshowingFromDate()));
            showTo.setValue(convertToLocalDateViaInstant(selectedMovieInstance.getMovieshowingToDate()));


            iconFile = new File(System.getProperty("user.dir")+ "\\images\\movies\\"+ selectedMovieInstance.getMovieicon());
            imageFile = new File(System.getProperty("user.dir")+ "\\images\\movies\\"+ selectedMovieInstance.getMovieimage());
        }
    }

    public void deleteCinema(ActionEvent actionEvent) throws CinemaException {

        new MovieRepository().delete(treeView.getSelectionModel().getSelectedItem().getValue());
        generateTable();
        clearFields();

    }
    @FXML
    public void pickIcon(ActionEvent actionEvent) {
        iconFile = getFile();
    }
    @FXML
    public void removeSelected(ActionEvent actionEvent) {
        clearFields();
    }

    @FXML
    public void pickImage(ActionEvent actionEvent) {
        imageFile = getFile();
    }

    @FXML
    void edit() throws CinemaException, IOException {
        // validate the  input fields
        if(!validateFields()){
            return;
        }
        Movie movie = null;
        if(selectedMovieInstance == null) {
            //Create e new Movie instance
             movie = new Movie();
        }else{
             movie = selectedMovieInstance;
        }

        movie.setMoviename(MovieNameField.getText());
        movie.setMovieCinemaid(CinemasBox.getValue());
        movie.setMoviecategory(movieCategory.getCheckModel().getCheckedItems().toString().replace('[',' ').replace(']',' ').trim());
        movie.setMovieduration(durationField.getText());
        movie.setMovierating(new BigDecimal(ratingField.getText()));
        movie.setMovieshowToSlide((showOnSlide.isSelected() ? '1' : '0'));
        movie.setMovieStatus((comingSoon.isSelected() ? "comingsoon" : "playing"));

        movie.setMovieshowingFromDate(getData(showFrom));
        movie.setMovieshowingToDate(getData(showTo));

        if(!iconFile.getName().equals(selectedMovieInstance.getMovieicon())) {
            //setting  the movie icon & image file
            movie.setMovieicon(iconFile.getName());

            //saving the img files to the destination..
            copyFile(iconFile.getPath(), System.getProperty("user.dir")+ "\\images\\movies\\"+iconFile.getName());
        }
       if(!imageFile.getName().equals(selectedMovieInstance.getMovieimage())) {
            //setting  the movie icon & image file
            movie.setMovieimage(imageFile.getName());

            //saving the img files to the destination..
           copyFile(imageFile.getPath(), System.getProperty("user.dir")+ "\\images\\movies\\"+imageFile.getName());
        }

        //updating to database the movie
        new MovieRepository().edit(movie);

        //Refreshing the tables
        generateTable();

    }


    public void copyFile(String from, String to) throws IOException{
        Path src = Paths.get(from);
        Path dest = Paths.get(to);
        copy(src.toFile(), dest.toFile());
    }

    public void copy(File src, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);

            // buffer size 1K
            byte[] buf = new byte[1024];

            int bytesRead;
            while ((bytesRead = is.read(buf)) > 0) {
                os.write(buf, 0, bytesRead);
            }
        } finally {
            is.close();
            os.close();
        }
    }

//    This method checks for any possible mistake in the input.
    private boolean validateFields(){
        boolean valide = true;
        if(MovieNameField.getText().isEmpty()){
            MovieNameField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else MovieNameField.setStyle("-fx-border-color: transparent");

        if(durationField.getText().isEmpty()){
            durationField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else durationField.setStyle("-fx-border-color: transparent");

        if(ratingField.getText().isEmpty()){
            ratingField.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else ratingField.setStyle("-fx-border-color: transparent");

        if(showTo.getValue() == null){
            showTo.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else showTo.setStyle("-fx-border-color: transparent");

        if(showFrom.getValue() == null){
            showFrom.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else showFrom.setStyle("-fx-border-color: transparent");

        if(imageFile == null){
            uploadImage.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else uploadImage.setStyle("-fx-border-color: transparent");

        if(iconFile == null){
            uploadIcon.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else uploadIcon.setStyle("-fx-border-color: transparent");

        if(CinemasBox.getValue() == null){
            CinemasBox.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else CinemasBox.setStyle("-fx-border-color: transparent");

        if(movieCategory.getCheckModel().getCheckedItems().toString().replace("[","").replace("]","").isEmpty()){
            movieCategory.setStyle("-fx-border-color: #d21a1a");
            valide = false;
        }else movieCategory.setStyle("-fx-border-color: transparent");

        return valide;
    }

    private void clearFields(){

        selectedMovie.setText("");
        CinemasBox.getSelectionModel().clearSelection();
        movieCategory.getCheckModel().clearChecks();
        MovieNameField.setText("");
        durationField.setText("");
        ratingField.setText("");
        showOnSlide.setSelected(false);
        comingSoon.setSelected(false);

        showTo.setValue(null);
        showFrom.setValue(null);

        iconFile = null;
        imageFile = null;
    }

    Date getData(JFXDatePicker datePicker){
        LocalDate ld = datePicker.getValue();
        Calendar c =  Calendar.getInstance();
        c.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth());
        return  c.getTime();
    }

    void selectMovieCategorys(String category){
        movieCategory.getCheckModel().clearChecks();
        String [] categoryArray= category.split(", ");

        for (int i = 0; i < categoryArray.length; i++) {
            movieCategory.getCheckModel().check(filmCategory.indexOf(categoryArray[i]));
        }
    }

    File getFile(){
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Image", "*.jpg", "*.png", "*.jpeg", "*.webp")
        );

        return fileChooser.showOpenDialog(new Stage());
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
