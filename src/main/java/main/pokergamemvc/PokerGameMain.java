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


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.PlayerCopy;

public class PokerGameMain extends Application {

    private PlayerCopy player;

    @Override
    public void init() throws Exception {
        super.init();
        player = new PlayerCopy(1);
        player.addChips(50);
        System.out.println("init() called");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/PokerGameView.fxml"));

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/PokerGameView.fxml"));
        Parent root = loader.load();

        // Retrieve the controller from the FXML
        PokerGameController controller = loader.getController();

        // Set the model up for the controller
        controller.setPlayer(player);

        primaryStage.setTitle("Poker: Texas Holdem");
        primaryStage.setScene(new Scene(root, 902, 486));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
