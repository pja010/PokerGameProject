package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class PrototypeGUI extends Application {

    private PrototypeModel theModel;
    private PrototypeView theView;
    private PrototypeController theController;
    private Player player;

    @Override
    public void init() throws Exception {
        super.init();
        this.theModel = new PrototypeModel();
        this.theView = new PrototypeView(theModel);
//        player = Round.getPlayer(1);
        player = new Player(1);
        player.addChips(50);
    }

    @Override
    public void start(Stage primaryStage) {
        this.theController = new PrototypeController(theModel, theView, player);

        primaryStage.setScene(new Scene(theView.getRoot()));
        primaryStage.setTitle("Prototype Poker GUI");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
