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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Gamerule;
import logic.game.Direction;
import logic.game.Game;
import logic.game.PlayerObject;
import logic.game.Point;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    private PlayerObject PO1 = new PlayerObject(new Point(960, 900),"Player1", Color.BLACK);
    private PlayerObject PO2 = new PlayerObject(new Point(860, 900),"Player2", Color.BLACK);
    private Image image = new Image("characters/character_black_blue.png");
    private ArrayList<ImageView> playerImage = new ArrayList<>();
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private Game game = new Game(new ArrayList<>());

    //This is ugly because we have 4 buttons to press now.
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        playerImage.add(addImageView());
        playerImage.add(addImageView());

        for(ImageView player : playerImage)
        {
            root.getChildren().add(player);
        }

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    leftPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
                    break;
                case A:
                    aPressed = false;
                    break;
                case D:
                    dPressed = false;
                    break;
            }
        });

        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    if(!leftPressed)
                    {
                        PO1 = game.moveCharacter("Player1", Direction.LEFT);
                        playerImage.get(0).setRotate(PO1.getCurrentRotation());
                        playerImage.get(0).setX(PO1.getAnchor().getX());
                        playerImage.get(0).setY(PO1.getAnchor().getY());
                        leftPressed = true;
                    }
                    break;
                case RIGHT:
                    if(!rightPressed)
                    {
                        PO1 = game.moveCharacter("Player1", Direction.RIGHT);
                        playerImage.get(0).setRotate(PO1.getCurrentRotation());
                        playerImage.get(0).setX(PO1.getAnchor().getX());
                        playerImage.get(0).setY(PO1.getAnchor().getY());
                        rightPressed = true;
                    }
                    break;
                case A:
                    if(!aPressed)
                    {
                        PO2 = game.moveCharacter("Player2", Direction.A);
                        playerImage.get(1).setRotate(PO2.getCurrentRotation());
                        playerImage.get(1).setX(PO2.getAnchor().getX());
                        playerImage.get(1).setY(PO2.getAnchor().getY());
                        aPressed = true;
                    }
                    break;
                case D:
                    if(!dPressed)
                    {
                        PO2 = game.moveCharacter("Player2", Direction.D);
                        playerImage.get(1).setRotate(PO2.getCurrentRotation());
                        playerImage.get(1).setX(PO2.getAnchor().getX());
                        playerImage.get(1).setY(PO2.getAnchor().getY());
                        dPressed = true;
                    }
                    break;
            }
        });
        primaryStage.setTitle("Game");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1080);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);

        //Initialize first frame
        PO1 = game.moveCharacter("Player1", Direction.RIGHT);
        PO2 = game.moveCharacter("Player2", Direction.D);


        //Initiate timer for map scroll.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                game.update();
                playerImage.get(1).setX(PO2.getAnchor().getX());
                playerImage.get(1).setY(PO2.getAnchor().getY());
                playerImage.get(0).setX(PO1.getAnchor().getX());
                playerImage.get(0).setY(PO1.getAnchor().getY());
            }
        }, 0, 33);

        primaryStage.show();
    }

    private ImageView addImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setRotate(180d);
        //imageView.setX(960);
        //imageView.setY(900);
        return imageView;
    }

    public static void main(String[] args) {
        //Repository repo = new Repository(new DatabaseContext());
        //System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        //repo.closeConnection();
    }
}
