package bootstrapper;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.game.*;

import java.util.ArrayList;


public class Main extends Application {

    private PlayerObject PO1 = new PlayerObject(new Point(960, 900),"Player1", Color.BLACK);
    private PlayerObject PO2 = new PlayerObject(new Point(860, 900),"Player2", Color.BLACK);
    private ArrayList<ObstacleObject> obstacleObjects = new ArrayList<>();
    private Image playerImage = new Image("characters/character_black_blue.png");
    private Image obstacleImage = new Image("objects/barrel_red_down.png");
    private ArrayList<ImageView> playerImageView = new ArrayList<>();
    private ArrayList<ImageView> obstacleImageView = new ArrayList<>();
    private Game game = new Game(new ArrayList<>());

    //This is ugly because we have 4 buttons to press now.
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // background scroller
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/Background.fxml") );
        Parent p = fxmlLoader.load();

        BackgroundController c = fxmlLoader.getController();
        Scene scene = new Scene(p);

        primaryStage.setTitle( "Game" );
        primaryStage.setOnShown( (evt) -> c.startAmination() );
        Pane gamePane = (Pane) p.lookup("#gamePane");

        // player movement
        playerImageView.add(addPlayerImageView());
        playerImageView.add(addPlayerImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());

        for(ImageView player : playerImageView)
        {
            gamePane.getChildren().add(player);
        }

        for(ImageView obstacle : obstacleImageView)
        {
            gamePane.getChildren().add(obstacle);
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
                        playerImageView.get(0).setRotate(PO1.getCurrentRotation());
                        playerImageView.get(0).setX(PO1.getAnchor().getX());
                        playerImageView.get(0).setY(PO1.getAnchor().getY());
                        leftPressed = true;
                    }
                    break;
                case RIGHT:
                    if (!rightPressed) {
                        PO1 = game.moveCharacter("Player1", Direction.RIGHT);
                        playerImageView.get(0).setRotate(PO1.getCurrentRotation());
                        playerImageView.get(0).setX(PO1.getAnchor().getX());
                        playerImageView.get(0).setY(PO1.getAnchor().getY());
                        rightPressed = true;
                    }
                    break;
                case A:
                    if (!aPressed) {
                        PO2 = game.moveCharacter("Player2", Direction.A);
                        playerImageView.get(1).setRotate(PO2.getCurrentRotation());
                        playerImageView.get(1).setX(PO2.getAnchor().getX());
                        playerImageView.get(1).setY(PO2.getAnchor().getY());
                        aPressed = true;
                    }
                    break;
                case D:
                    if (!dPressed) {
                        PO2 = game.moveCharacter("Player2", Direction.D);
                        playerImageView.get(1).setRotate(PO2.getCurrentRotation());
                        playerImageView.get(1).setX(PO2.getAnchor().getX());
                        playerImageView.get(1).setY(PO2.getAnchor().getY());
                        dPressed = true;
                    }
                    break;
            }
        });
        primaryStage.setWidth(1200);
        primaryStage.setHeight(1000);
        primaryStage.setScene(scene);

        //Initialize first frame
        PO1 = game.moveCharacter("Player1", Direction.RIGHT);
        PO2 = game.moveCharacter("Player2", Direction.D);
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));

        //Initiate timer for map scroll.
        AnimationTimer aTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update();
                playerImageView.get(1).setX(PO2.getAnchor().getX());
                playerImageView.get(1).setY(PO2.getAnchor().getY());
                playerImageView.get(0).setX(PO1.getAnchor().getX());
                playerImageView.get(0).setY(PO1.getAnchor().getY());

                for (GameObject GO : game.getGameObjects()) {
                }

                obstacleObjects = game.returnObstacleObjects();

                for (int i = 0; i < obstacleObjects.size(); i++) {
                    obstacleImageView.get(i).setX(obstacleObjects.get(i).getAnchor().getX());
                    obstacleImageView.get(i).setY(obstacleObjects.get(i).getAnchor().getY());
                }
            }
        };
        aTimer.start();
        primaryStage.show();
    }

    private ImageView addPlayerImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(playerImage);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setRotate(180d);
        return imageView;
    }

    private ImageView addObstacleImageView() {
        ImageView imageView = new ImageView();
        imageView.setImage(obstacleImage);
        imageView.setFitWidth(70);
        imageView.setFitHeight(48);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }

    public static void main(String[] args) {
        //Repository repo = new Repository(new DatabaseContext());
        //System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        //repo.closeConnection();
    }
}
