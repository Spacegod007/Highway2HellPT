package sample;

import database.Contexts.DatabaseContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.Gamerule;
import logic.game.Direction;
import logic.game.Game;

import java.util.ArrayList;

public class Main extends Application {

    private ImageView imageView = new ImageView();
    private Group root = new Group();
    Scene scene = new Scene(root);
    private Game game = new Game(new ArrayList<Gamerule>());

    @Override
    public void start(Stage primaryStage) throws Exception{
        root.getChildren().add(imageView);

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    System.out.println("Left key pressed");
                    game.moveCharacter("Player1", Direction.LEFT);
                    break;
                case RIGHT:
                    System.out.println("Right key pressed");
                    game.moveCharacter("Player1", Direction.RIGHT);
                    break;
            }
        });

        primaryStage.setTitle("Game");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Repository repo = new Repository(new DatabaseContext());
        System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        repo.closeConnection();

    }
}
