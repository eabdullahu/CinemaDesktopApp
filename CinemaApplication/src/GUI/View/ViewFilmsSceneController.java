package GUI.View;

import BLL.Cinema;
import BLL.Movie;
import DAL.CinemaException;
import DAL.CinemaRepository;
import DAL.MovieRepository;
import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
/**
 * The controller for the View Films Scene.
 */
public class ViewFilmsSceneController extends CommonMethods implements Initializable {
    @FXML
    private JFXButton contactbtn, logoutbtn, searchBtn, editProfileBtn, cinemabtn, hallsbtn, moviesbtn, ticketsbtn, usersbtn;
    @FXML
    private Label userlabel, slideShowFilmTitle, slideShowFilmCategory;
    @FXML
    private AnchorPane mainhomepane, leftpane, toppane;
    @FXML
    private StackPane paginationPane;
    @FXML
    private ComboBox cinemaCombo, movieCombo, dateCombo;
    @FXML
    private HBox hb = new HBox();
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane grid, slideShow;
    @FXML
    private ImageView pic, imageView;
    @FXML
    private Image image;
    @FXML
    private Image images[] = null;
    private int j = 0;
    private List<File> slideShowFinal = new ArrayList<File>();
    private List<Movie> movieFinal = new ArrayList<Movie>();
    private ArrayList<File> fileList = new ArrayList<File>();
    private List<Movie> lista = new ArrayList<Movie>();
    
    private Pagination pagination;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        page="Home";
        leftpanecolor=leftpane.getStyle().substring(22,29);
        
        dateCombo.setDisable(true);
        dateCombo.setStyle("-fx-opacity: 1.0; -fx-text-fill: black;");
        displayUserName(userlabel);
        moveWindow(toppane);
        moveWindow(leftpane);
        addCinema();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        movieCombo.getSelectionModel().select(0);
        searchBtn.setCursor(Cursor.HAND);
        
        try {
            movieFinal = new MovieRepository().showToSlideShow('1');
            String executionPath = System.getProperty("user.dir");
            for(Movie m : movieFinal){
                slideShowFinal.add(new File(executionPath+"\\images\\movies\\"+m.getMovieimage()));
            }
            
            slideShow.setAlignment(Pos.CENTER);
            
            images = new Image[slideShowFinal.size()];
            for (int i = 0; i < slideShowFinal.size(); i++) {
                images[i] = new Image(slideShowFinal.get(i).toURI().toString());
            }

            imageView = new ImageView(images[j]);
            
            imageView.setFitHeight(300);
            imageView.setFitWidth(950);
            
            VBox vbox = new VBox();
            vbox.setPadding(new Insets(10));
            vbox.setStyle("-fx-background-color: black; -fx-opacity: 0.5;");
            slideShowFilmTitle = new Label(movieFinal.get(j).getMoviename().trim());
            slideShowFilmTitle.setStyle("-fx-padding: 5 5 5 5; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
            slideShowFilmTitle.setWrapText(false);
            slideShowFilmCategory = new Label(movieFinal.get(j).getMoviecategory()+" - "+movieFinal.get(j).getMovieduration() 
                + "\nFrom: " +new SimpleDateFormat("dd-MM-yyyy").format(movieFinal.get(j).getMovieshowingFromDate())+
                    " to: "+new SimpleDateFormat("dd-MM-yyyy").format(movieFinal.get(j).getMovieshowingToDate()));
       
            slideShowFilmCategory.setStyle("-fx-padding: 5 5 5 5; -fx-text-fill: white; -fx-font-size: 16px;");
            slideShowFilmCategory.setWrapText(false);
            vbox.getChildren().addAll(slideShowFilmTitle, slideShowFilmCategory);
            vbox.setAlignment(Pos.CENTER_LEFT);
            
            StackPane root = new StackPane();
            root.getChildren().addAll(imageView, vbox);
            
            HBox hBox = new HBox();
            hBox.getChildren().add(root);

            slideShow.add(hBox, 0, 0);
            
            /*Change image every five seconds*/
            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    nextImage();
                }
            }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
            createPagination("all", null, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        animate();
    }
    
    public void createPagination(String type, Cinema cinema, String category) throws CinemaException {
        double allMovie = 0;
        if(type == "search"){
            allMovie = Math.ceil(new MovieRepository().countAllByLocAndCategory(cinema, category) / 10.0);
        }else {
            allMovie = Math.ceil(new MovieRepository().countAll() / 10.0);
        }
        pagination = new Pagination((int)allMovie, 0);
        /* me ndrru nese mundemi !!! */
        pagination.setPageFactory(new Callback<Integer, Node>() {
            @Override
            public Node call(Integer pageIndex) {
                return createPage(pageIndex, type);
            }
        });
        paginationPane.getChildren().addAll(pagination);
        paginationPane.setAlignment(Pos.CENTER);
    }
 
    public VBox createPage(int pageIndex, String type) {
        if(pageIndex > 0){
            grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != 0);
        }
        if(type.equals("all")){
            try{
                lista = new MovieRepository().findAll(pageIndex * 10, 10);
            }catch (CinemaException ex){
                ex.printStackTrace();
            }
        }
        createGrid();
        if(lista.size() > 5){
            paginationPane.setLayoutY(920);
        }else {
            paginationPane.setLayoutY(660);
        }
        return new VBox();
    }
    
    /*next image  */
    public void nextImage(){
        j = j + 1;
        if (j == slideShowFinal.size()) {
            j = 0;
        }
        imageView.setImage(images[j]);
        slideShowFilmTitle.setText(movieFinal.get(j).getMoviename());
        slideShowFilmCategory.setText(movieFinal.get(j).getMoviecategory()+" - "+movieFinal.get(j).getMovieduration() 
                + "\nFrom: " +new SimpleDateFormat("MM-dd-yyyy").format(movieFinal.get(j).getMovieshowingFromDate())+
                " to: "+new SimpleDateFormat("MM-dd-yyyy").format(movieFinal.get(j).getMovieshowingToDate()));
        FadeTransition ft = new FadeTransition(Duration.millis(500), imageView);
        ft.setFromValue(0);
        ft.setToValue(10);
        ft.play();
    }
    
    /*previous image just in case if wee add buttons */
    public void previousImage(){
        j = j - 1;
        if (j == 0 || j > slideShowFinal.size() + 1 || j == -1) {
            j = slideShowFinal.size() - 1;
        }
        imageView.setImage(images[j]);
        slideShowFilmTitle.setText(movieFinal.get(j).getMoviename());
        slideShowFilmCategory.setText(movieFinal.get(j).getMoviecategory()+" - "+movieFinal.get(j).getMovieduration());
    }
    
    public void createGrid(){
        fileList = new ArrayList<>();
        String executionPath = System.getProperty("user.dir");
        for(Movie m : lista){
            fileList.add(new File(executionPath+"\\images\\movies\\"+m.getMovieicon()));
        }

        grid.setPadding(new Insets(10,10,10,10));
        grid.setHgap(22);
        grid.setVgap(22);

        int rows = (fileList.size() / 5) + 2;
        int columns = 5;
        int imageIndex = 0;
        for (int i = 1 ; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (imageIndex < fileList.size()) {
                    addImage(imageIndex, j, i);
                    imageIndex++;
                }
            }
        }
    }
    
    public void searchDb(){
        String cinema = (String) cinemaCombo.getValue();
        String category = (String) movieCombo.getValue();
        Integer cinemaId = null;
        Cinema c = null;
        
        try{
            if(!cinema.equals("All locations")){
                cinemaId = Integer.parseInt(cinema.substring(0, cinema.indexOf(".")));
                c = new CinemaRepository().findById(cinemaId);
            }
            
            lista = new MovieRepository().findAllByLocAndCategory(c, category, 0, 10);
            if(!lista.isEmpty()){
                /*fshije grid rows perpos 0 ku gjendet Label MOVIES */
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != 0);
                createPagination("search", c, category);
            } else {
                /*fshije grid rows perpos 0 ku gjendet Label MOVIES */
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) != 0);
                Label l = new Label("Nothing found on database.");
                hb.getChildren().add(l);
                GridPane.setConstraints(l, 15, 1, 1, 1, HPos.CENTER, VPos.CENTER);
                grid.getChildren().addAll(l);
            }
        }catch(CinemaException ce) {
            ce.printStackTrace();
        }
    }
    
    public void addCinema(){
        try {
            List<Cinema> cinemaList = new CinemaRepository().findAll();
            ObservableList<String> options = FXCollections.observableArrayList("All locations");
            for(Cinema c : cinemaList){
                options.add(c.getCinemaid()+". "+c.getCinemalocation());
            }
            cinemaCombo.getItems().addAll(options);
            cinemaCombo.getSelectionModel().select(0);
        }catch (CinemaException ex){
            ex.printStackTrace();
        }
    }
    
    private void addImage(int index, int colIndex, int rowIndex) {
        String id = lista.get(index).getMovieid()+"";
        image = new Image(fileList.get(index).toURI().toString());
        pic = new ImageView();
        pic.setFitWidth(160);
        pic.setFitHeight(220);
        pic.setImage(image);
        pic.setId(id);
        pic.setCursor(Cursor.HAND);
        pic.setOnMouseClicked(e -> {
            try {
                // storing the selected film to customise the newly created scene
                CommonMethods.selectedFilmId = lista.get(index).getMovieid();
                CommonMethods.movieCinema = lista.get(index).getMovieCinemaid();
                launchScene("movies.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        /*Add text ne fund te image ne anen e majte*/
        Label label = new Label(lista.get(index).getMoviename().trim());
        label.setStyle("-fx-background-color: white; -fx-padding: 5;");
        label.setEllipsisString("...");
        label.setWrapText(false);
        label.setTextOverrun(OverrunStyle.ELLIPSIS);
        
        StackPane root = new StackPane();
        root.setMaxWidth(160);
        root.getChildren().addAll(pic, label);
        root.setAlignment(Pos.BASELINE_LEFT);
        
        hb.getChildren().add(root);
        GridPane.setConstraints(root, colIndex, rowIndex, 1, 1, HPos.CENTER, VPos.CENTER);
        grid.getChildren().addAll(root);
    }

    public void animate(){
        popNodeNew(scrollPane);
    }
}