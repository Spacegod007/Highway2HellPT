package bootstrapper;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import logic.Score;
import logic.game.*;

import java.util.ArrayList;


public class Main extends Application {

    private PlayerObject PO1 = new PlayerObject(new Point(960, 900),"Player1", Color.BLACK);
    //private PlayerObject PO2 = new PlayerObject(new Point(860, 900),"Player2", Color.BLACK);
    private ArrayList<ObstacleObject> obstacleObjects = new ArrayList<>();

    //Playerimages for creating characters for later versions that use sockets.
    private Image playerImage = new Image("characters/character_black_blue.png");
    private Image playerImage2 = new Image("characters/character_blonde_red.png");

    private Image obstacleImage = new Image("objects/barrel_red_down.png");
    private ArrayList<ImageView> playerImageView = new ArrayList<>();
    private ArrayList<ImageView> obstacleImageView = new ArrayList<>();
    private Game game = new Game(new ArrayList<>());
    private Scene scoreboardScene;

    //Failsafe for if someone decides to hold in one of the buttons.
    private boolean leftPressed = false;
    private boolean rightPressed = false;

    private int playersDead = 0;
    private int players = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{

        // background scroller
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getResource("/Background.fxml") );
        Parent p = fxmlLoader.load();
        BackgroundController c = fxmlLoader.getController();
        Scene scene = new Scene(p);

        fxmlLoader = new FXMLLoader(getClass().getResource("/views/Scoreboard.fxml"));
        Parent root = fxmlLoader.load();
        scoreboardController sbc = fxmlLoader.getController();
        scoreboardScene = new Scene(root);

        primaryStage.setTitle( "Game" );
        primaryStage.setOnShown( (evt) -> c.startAmination() );
        Pane gamePane = (Pane) p.lookup("#gamePane");

        // player movement
        playerImageView.add(addPlayerImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());
        obstacleImageView.add(addObstacleImageView());
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

        for(GameObject GO : game.getGameObjects())
        {
            if(GO.getClass() == PlayerObject.class)
            {
                players++;
            }
        }

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case LEFT:
                    leftPressed = false;
                    break;
                case RIGHT:
                    rightPressed = false;
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
            }
        });
        primaryStage.setWidth(1200);
        primaryStage.setHeight(1000);
        primaryStage.setScene(scene);

        //Initialize first frame
        PO1 = game.moveCharacter("Player1", Direction.RIGHT);
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));
        obstacleObjects.add(new ObstacleObject(70, 48));

        //Initiate timer for map scroll.
        AnimationTimer aTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                game.update();
                for(ImageView PI : playerImageView)
                {
                    PI.setX(PO1.getAnchor().getX());
                    PI.setY(PO1.getAnchor().getY());
                }

                for (GameObject GO : game.getGameObjects()) {
                    //Check if the game is allowed to end.
                    if(GO.getClass() == PlayerObject.class)
                    {
                        if(((PlayerObject) GO).getisDead())
                        {
                            playersDead++;
                            if(playersDead == players)
                            {
                                ArrayList<Score> scores = new ArrayList<>();
                                for(GameObject tempGO : game.getGameObjects())
                                {
                                    if(tempGO.getClass() == PlayerObject.class)
                                    {
                                        PlayerObject PO = (PlayerObject)GO;
                                        scores.add(new Score(PO.getName(), (double)PO.getDistance()));
                                    }
                                }
                                sbc.setScore(scores);
                                game.endGame(scoreboardScene, primaryStage);
                                scores.clear();
                            }
                        }
                    }
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
        imageView.setFitWidth(78);
        imageView.setFitHeight(54);
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
