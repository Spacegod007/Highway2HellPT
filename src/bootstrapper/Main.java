package bootstrapper;

import database.Contexts.DatabaseContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.Gamerule;
import logic.game.Direction;
import logic.game.Game;
import logic.game.Point;

import java.util.ArrayList;

public class Main extends Application {

    private Point point;
    private Image image = new Image("characters/character_black_blue.png");
    private ImageView imageView = new ImageView();
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Game game = new Game(new ArrayList<>());

    @Override
    public void start(Stage primaryStage) throws Exception{
        root.getChildren().add(imageView);
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    point = game.moveCharacter("Player1", Direction.LEFT);
                    break;
                case RIGHT:
                    point = game.moveCharacter("Player1", Direction.RIGHT);
                    break;
                case A:
                    point = game.moveCharacter("Player2", Direction.A);
                    break;
                case D:
                    point = game.moveCharacter("Player2", Direction.D);
                    break;
            }
            imageView.setX(point.getX());
            imageView.setY(point.getY());
        });
        loadImage();
        primaryStage.setTitle("Game");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadImage() {
        imageView.setImage(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setRotate(180d);
        imageView.setX(960 - imageView.getFitWidth());
        imageView.setY(900);
    }

    public static void main(String[] args) {
        Repository repo = new Repository(new DatabaseContext());
        System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        repo.closeConnection();
    }
}
