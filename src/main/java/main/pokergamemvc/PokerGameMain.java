/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2020
 * Instructor: Prof. Brian King
 *
 * Name: Per Astrom, Lindsay Knupp, Callie Valenti, Guillermo Torres
 * Section: 02-8:50 am and 01-11:30 am
 * Date: 11/2/20
 * Time: 11:46 AM
 *
 * Project: csci205FinalProject
 * Package: main.PokerGameVisuals
 * Class: PokerGameController
 *
 * Description: Main class for the Poker game,
 * excluding networking.
 *
 * ****************************************
 */
package main.pokergamemvc;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.GUIPlayer;
import main.Table;

/**
 * Main class to run poker game application with GUI
 * for a single player. Not to be used in networking version.
 */
public class PokerGameMain extends Application {

    /**
     * The GUI models in the game.
     */
    private GUIPlayer player;
    private Table table;

    /**
     * Initiates the game.
     * @throws Exception an exception in the initial setup.
     */
    @Override
    public void init() throws Exception {
        super.init();
        table = new Table();
        player = new GUIPlayer(4);
        player.addChips(50);
        System.out.println("init() called");
    }

    /**
     * Start the game by setting the view, the controller with the models
     * and the stage.
     * @param primaryStage the stage of the game GUI.
     * @throws Exception an exception in the star setup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/PokerGameView.fxml"));
        Parent root = loader.load();

        // Retrieve the controller from the FXML view file
        PokerGameController controller = loader.getController();

        // Set the models up for the controller
        controller.setPlayer(player);
        controller.setTable(table);

        primaryStage.setTitle("Poker: Texas Hold'em");
        primaryStage.setScene(new Scene(root, 1570, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
