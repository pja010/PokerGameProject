/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Per Ästrom, Lindsay Knupp, Callie Valenti, Guillermo Torres
 * Section: 02-8:50 am and 01-11:30 am
 * Date: 11/2/20
 * Time: 11:46 AM
 *
 * Project: csci205FinalProject
 * Package: main.PokerGameVisuals
 * Class: PokerGameController
 *
 * Description:
 *
 * ****************************************
 */
package main.pokergamemvc;


import com.sun.javafx.iio.ImageStorage;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PokerGameMain extends Application {

    public static void main(String[] args) {
        Application.launch(args); }


    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        VBox vbox = new VBox(5);
        //root.setPrefWidth(800);
        //root.setPrefHeight(950);
        vbox.setPadding(new Insets(10, 5, 10, 5));

        Group root = new Group();

        HBox rootLayout = new HBox(10);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));

        //StackPane leftPane = new StackPane();
        Rectangle leftRect = new Rectangle(0, 0, 900, 800);
        leftRect.setStroke(Color.BLACK);
        leftRect.setArcWidth(50);
        leftRect.setArcHeight(50);
        leftRect.setFill(Color.GREEN);
        //leftPane.getChildren().add(leftRect);

        Rectangle rightRect = new Rectangle(900,0,300, 950);
        rightRect.setStroke(Color.BLACK);
        rightRect.setArcWidth(50);
        rightRect.setArcHeight(50);
        rightRect.setFill(Color.DARKOLIVEGREEN);

        StackPane bottomPane = new StackPane();
        Rectangle bottomRect = new Rectangle(0, 800, 900, 150);
        bottomRect.setStroke(Color.BLACK);
        bottomRect.setArcWidth(50);
        bottomRect.setArcHeight(50);
        bottomRect.setFill(Color.DARKSEAGREEN);

        VBox leftVBox = new VBox(50);
        leftVBox.setAlignment(Pos.CENTER);

        VBox rightVBox = new VBox(20);
        rightVBox.setAlignment(Pos.CENTER);

        VBox bottomVBox = new VBox(20);
        bottomVBox.setAlignment(Pos.CENTER);

        ToggleGroup toggle = new ToggleGroup();

        ToggleButton betBtn = new ToggleButton("Bet");
        betBtn.setAlignment(Pos.CENTER);
        betBtn.setToggleGroup(toggle);

        ToggleButton foldBtn = new ToggleButton("Fold");
        foldBtn.setAlignment(Pos.CENTER_RIGHT);
        foldBtn.setToggleGroup(toggle);

        ToggleButton checkBtn = new ToggleButton("Check");
        checkBtn.setAlignment(Pos.CENTER_LEFT);
        checkBtn.setToggleGroup(toggle);

        toggle.selectToggle(checkBtn);

        checkBtn.setUserData(bottomPane);
        betBtn.setUserData(bottomPane);
        foldBtn.setUserData(bottomPane);

        //HBox hbox = new HBox();
        //hbox.getChildren().addAll(checkBtn, betBtn, foldBtn);
        //hbox.setPadding(new Insets(10, 5, 10, 5));

        //vbox.getChildren().addAll(hbox);

        VBox bottomVbox = new VBox();


        root.getChildren().addAll(leftRect, rightRect, bottomRect);

        //Group group = new Group(leftPane, rightPane, bottomPane);

        Scene scene = new Scene(root, 1200, 950, Color.BLACK);

        primaryStage.setTitle("Poker: Texas Holdem");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }
}
