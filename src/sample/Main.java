package sample;

import database.Contexts.LocalContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.administration.Administration;
import logic.administration.Lobby;
import logic.administration.User;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application{

    private Scanner input = new Scanner(System.in);
    private static Administration administration;
    private Button btnHostLobby = new Button();
    private Button btnJoinLobby = new Button();
    private Button btnKickPlayer = new Button();
    private Button btnStartGame = new Button();
    private ListView<Lobby> listvwLobby = new ListView<>();
    private ListView<User> listvwPlayers = new ListView<User>();
    private TextField text = new TextField();
    private Button btnRefresh = new Button();
    private Parent root; //niet converten naar local nog plz

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
            Repository repo = new Repository(new LocalContext());
            System.out.println("Connection to database: " + Boolean.toString(repo.testConnection()));
            administration.setUser(new User("David"));
            root = FXMLLoader.load(getClass().getResource("main.fxml"));
            primaryStage.setTitle("Hello World");
            Canvas canvas = new Canvas(600, 600);
            ((GridPane)root).getChildren().add(canvas);
            setUpControls();
            ((GridPane)root).getChildren().add(new Pane(btnHostLobby, btnJoinLobby, btnKickPlayer, btnStartGame, text, listvwLobby, listvwPlayers, btnRefresh));
            primaryStage.setScene(new Scene(root, 600, 600));
            primaryStage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void refreshLobbies()
    {
        listvwLobby.setItems(administration.refresh());
        viewLobby();
    }

    private void setUpControls()
    {
        btnHostLobby.setLayoutX(0);
        btnHostLobby.setLayoutY(0);
        btnHostLobby.setPrefWidth(150);
        btnHostLobby.setText("Host lobby");
        btnHostLobby.setOnAction(event -> hostLobby());

        btnJoinLobby.setLayoutX(150);
        btnJoinLobby.setLayoutY(0);
        btnJoinLobby.setPrefWidth(150);
        btnJoinLobby.setText("Join lobby");
        btnJoinLobby.setOnAction(event -> joinLobby());

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
        btnRefresh.setOnAction(event -> refreshLobbies());

        listvwLobby.setLayoutX(50);
        listvwLobby.setLayoutY(150);
        listvwLobby.getSelectionModel().selectedItemProperty().addListener(event -> viewLobby());
        listvwLobby.setItems(administration.refresh());

        listvwPlayers.setLayoutX(300);
        listvwPlayers.setLayoutY(150);

        text.setLayoutX(300);
        text.setLayoutY(0);
    }

    private void viewLobby(){
        Lobby lobby = listvwLobby.getSelectionModel().getSelectedItem();
        if(lobby != null){
            System.out.println(lobby.getId());
            listvwPlayers.setItems(lobby.getPlayers());
        }
    }
    private void hostLobby(){
        try{
            administration.hostLobby("testlobby");
            listvwLobby.setItems(administration.refresh());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void joinLobby()
    {
        try
        {
            Lobby lobby = listvwLobby.getSelectionModel().getSelectedItem();
            if(lobby != null)
            {
                int i = listvwLobby.getSelectionModel().getSelectedIndex();
                if(administration.joinLobby(lobby))
                {
                    //success join lobby
                    System.out.println("Successful join");
                    listvwLobby.setItems(administration.refresh());
                    listvwLobby.getSelectionModel().select(i);
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
    private void addTestPlayers(){
        text.setText("Addtestplayer");
    }

    private void kickPlayer()
    {
        try
        {
            User player = listvwPlayers.getSelectionModel().getSelectedItem();
            if(player != null)
            {
                int l = listvwLobby.getSelectionModel().getSelectedIndex();
                int index = listvwPlayers.getSelectionModel().getSelectedIndex();
                administration.kickPlayer(l, index);
                viewLobby();
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
}
