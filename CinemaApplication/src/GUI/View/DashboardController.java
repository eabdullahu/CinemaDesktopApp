/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.View;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import DAL.BoughtTicketsRepository;
import DAL.CinemaException;
import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.awt.MouseInfo;

/**
 * FXML Controller class
 *
 * @author enisa
 */
public class DashboardController extends CommonMethods implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane mainhomepane, leftpane,toppane;
    @FXML
    private GridPane comboBoxes;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private JFXButton editProfileBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        leftpanecolor = leftpane.getStyle().substring(22, 29);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        page = "Home";
        moveWindow(leftpane);
        moveWindow(toppane);

        StackPane stackPane = new StackPane();
        mainhomepane.getChildren().add(stackPane);
        editProfileBtn.setOnAction(event -> {
            editProfile(stackPane, mainhomepane);
        });

        AnchorPane.setTopAnchor(stackPane, (mainhomepane.getPrefHeight() - 280) / 2);
        AnchorPane.setLeftAnchor(stackPane, (mainhomepane.getPrefWidth() - 280) / 2);

        List<PieChart.Data> list = new ArrayList<>();
        try {
            List l = new BoughtTicketsRepository().sold();

            Long all = new BoughtTicketsRepository().countSold();

            Iterator it = l.iterator();
            while(it.hasNext()) {
                Object rows[] = (Object[])it.next();
                double value = Double.parseDouble(rows[0].toString()) / all * 100;
                value = Double.parseDouble(new DecimalFormat("##.##").format(value));
                String name = rows[1].toString() + " " + value + "%";
                list.add(new PieChart.Data(name, value));
            }
        }catch(CinemaException ec){
            ec.printStackTrace();

        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(list);
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Shikueshmeria e filmave ne perqindje");

        GridPane.setConstraints(chart, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        comboBoxes.getChildren().add(chart);

        barChart();
    }

    public void barChart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Fitimet e 10 diteve te fundit");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        try {
            List l = new BoughtTicketsRepository().soldPrice();
            Iterator it = l.iterator();
            while(it.hasNext()) {
                Object rows[] = (Object[])it.next();
                series1.getData().add(new XYChart.Data(rows[1].toString(), Double.parseDouble(rows[0].toString())));
            }
        }catch(CinemaException ec){
            ec.printStackTrace();
        }

        bc.getData().add(series1);
        bc.setLegendVisible(false);
        GridPane.setConstraints(bc, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
        comboBoxes.getChildren().add(bc);
    }
}
