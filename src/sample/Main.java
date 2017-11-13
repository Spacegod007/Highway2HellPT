package sample;

import database.Contexts.LocalContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.administration.Administration;
import logic.administration.Lobby;
import logic.administration.User;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application{

    private static Administration administration;
    private Button btnHostLobby = new Button();
    private Button btnJoinLobby = new Button();
    private Button btnKickPlayer = new Button();
    private Button btnStartGame = new Button();
    private Button btnLeaveLobby = new Button();
    private ListView<Lobby> listvwLobby = new ListView<>();
    private ListView<User> listvwPlayers = new ListView<>();
    private TextField text = new TextField();
    private Button btnRefresh = new Button();
    //private Parent root; //niet converten naar local nog plz
    private TextField txtEnterName = new TextField();
    private Button btnLaunchLobbyScreen = new Button();
    private FlowPane lobbyScreen;
    private FlowPane titleScreen;
    private Scene titleScene;
    private Scene lobbyScene;
    private Stage stage;


    public static void launchView(String[] args, Administration admin)
    {
        administration = admin;
        System.out.println("launching");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        try
        {
            stage = primaryStage;

            Repository repo = new Repository(new LocalContext());
            System.out.println("Connection to database: " + Boolean.toString(repo.testConnection()));

            //root = FXMLLoader.load(getClass().getResource("main.fxml"));
            //deze dingen moeten zoals 'root' allebei uit een fxml komen

            setUpControls();
            titleScreen = new FlowPane();
            lobbyScreen = new FlowPane();

            titleScreen.getChildren().addAll(txtEnterName, btnLaunchLobbyScreen);
            lobbyScreen.getChildren().addAll(btnHostLobby, btnJoinLobby, btnKickPlayer, btnStartGame, btnLeaveLobby, text, listvwLobby, listvwPlayers, btnRefresh);


            titleScene = new Scene(titleScreen, 600, 600);
            lobbyScene = new Scene(lobbyScreen, 600, 600);

            primaryStage.setTitle("Hello World");
            primaryStage.setScene(titleScene);
            primaryStage.show();

            administration.setMain(this);

        }
        catch(Exception e /*IOException*/)
        {
            e.printStackTrace();
        }
    }

    private void setUpControls()
    {
        btnHostLobby.setLayoutX(150);
        btnHostLobby.setLayoutY(0);
        btnHostLobby.setPrefWidth(150);
        btnHostLobby.setText("Host lobby");
        btnHostLobby.setOnAction(event -> hostLobby());

        btnJoinLobby.setLayoutX(300);
        btnJoinLobby.setLayoutY(0);
        btnJoinLobby.setPrefWidth(150);
        btnJoinLobby.setText("Join lobby");
        btnJoinLobby.setOnAction(event -> joinLobby());

        btnLeaveLobby.setLayoutX(450);
        btnLeaveLobby.setLayoutY(0);
        btnLeaveLobby.setPrefWidth(150);
        btnLeaveLobby.setText("Leave lobby");
        btnLeaveLobby.setOnAction(event -> leaveLobby());

        btnKickPlayer.setLayoutX(0);
        btnKickPlayer.setLayoutY(550);
        btnKickPlayer.setPrefWidth(100);
        btnKickPlayer.setText("Kick player");
        btnKickPlayer.setOnAction(event -> kickPlayer());

        btnStartGame.setLayoutX(100);
        btnStartGame.setLayoutY(550);
        btnStartGame.setPrefWidth(100);
        btnStartGame.setText("Start game");
        btnStartGame.setOnAction(event -> startGame());

        btnRefresh.setLayoutX(0);
        btnRefresh.setLayoutY(50);
        btnRefresh.setText("Refresh");
        btnRefresh.setPrefWidth(100);

        listvwLobby.setLayoutX(50);
        listvwLobby.setLayoutY(150);
        listvwLobby.getSelectionModel().selectedItemProperty().addListener(event -> viewLobby(null));

        listvwPlayers.setLayoutX(300);
        listvwPlayers.setLayoutY(150);

        text.setLayoutX(0);
        text.setLayoutY(0);

        btnLaunchLobbyScreen.setOnAction(event -> launchLobbyScreen());
    }

    private void launchLobbyScreen()
    {
        stage.setScene(lobbyScene);
    }

    private void viewLobby(User player){
        Lobby lobby = listvwLobby.getSelectionModel().getSelectedItem();
        if(lobby != null){
            listvwPlayers.setItems(lobby.getPlayers());
            if(player != null)
            {
                for(User p : listvwPlayers.getItems())
                {
                    if(p.getID() == player.getID()){
                        listvwPlayers.getSelectionModel().select(p);
                    }
                }
            }
        }
    }

    private void hostLobby(){
        if(!administration.inLobby())
        {
            if((text.getText()).trim().length()>=4)
            {
                try
                {
                    administration.hostLobby(text.getText());
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else
            {
                System.out.println("Enter a name of at least 4 characters");
            }
        }
        else
            {
                System.out.println("Please leave your current lobby before hosting a new one");
            }
    }

    private void joinLobby()
    {
        try
        {
            Lobby lobby = listvwLobby.getSelectionModel().getSelectedItem();
            if(lobby != null)
            {
                if(administration.inLobby())
                {
                    administration.leaveLobby();
                }
                if(administration.joinLobby(lobby))
                {
                    System.out.println("Successful join");
                }
                else
                {
                    //fail join lobby
                    System.out.println("Failed join");
                }
            }
            else
            {
                System.out.println("No lobby selected");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void leaveLobby()
    {
        try
        {
            administration.leaveLobby();
        }
        catch(Exception e)
        {

        }
    }

    private void kickPlayer()
    {
        try
        {
            User player = listvwPlayers.getSelectionModel().getSelectedItem();
            if(player != null)
            {
                int id = listvwPlayers.getSelectionModel().getSelectedItem().getID();
                if(id != administration.getUser().getID())
                {
                    administration.leaveLobby(id);
                    viewLobby(null); //hier nog naar kijken
                }
                else
                {
                    System.out.println("Can't kick self");
                }
            }
            else
            {
                System.out.println("No player selected");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    private void startGame(){
        text.setText("Startgame");
    }

    public void setListvwLobby(ObservableList<Lobby> lobbys)
    {
        Lobby selectLobby = null;
        if(listvwLobby.getSelectionModel().getSelectedItem() != null)
        {
            int selectedId = listvwLobby.getSelectionModel().getSelectedItem().getId();

            for (Lobby lobby : lobbys)
            {
                if (lobby.getId() == selectedId)
                {
                    selectLobby = lobby;
                    break;
                }
            }
        }
        Lobby finalSelectLobby = selectLobby;

        Platform.runLater(() ->
        {
            listvwLobby.setItems(lobbys);
            if(finalSelectLobby != null)
            {
                listvwLobby.getSelectionModel().select(finalSelectLobby);
                listvwPlayers.setItems(finalSelectLobby.getPlayers());
            }

        });
    }
}
