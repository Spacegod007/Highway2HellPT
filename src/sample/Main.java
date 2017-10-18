package sample;

import database.Contexts.LocalContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import logic.administration.Administration;
import logic.administration.Lobby;
import logic.administration.User;

import java.io.IOException;
import java.util.*;

public class Main extends Application{

    private Administration administration;
    private Button btnHostLobby = new Button();
    private Button btnJoinLobby = new Button();
    private Button btnKickPlayer = new Button();
    private Button btnStartGame = new Button();
    private ListView<Lobby> listvwLobby = new ListView<>();
    private ListView<User> listvwPlayers = new ListView<User>();
    private TextField text = new TextField();
    private Button btnRefresh = new Button();
    private Parent root; //niet converten naar local nog plz
    private Scanner input = new Scanner(System.in);

    @Override
    public void start(Stage primaryStage) throws Exception{
        try
        {
            System.out.println("Welcome!");
            System.out.print("Enter username: ");
            administration = new Administration(input.nextLine());
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
        listvwLobby.refresh();
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
        listvwLobby.setItems(administration.getLobbies());
        listvwPlayers.setLayoutX(300);
        listvwPlayers.setLayoutY(150);
        text.setLayoutX(300);
        text.setLayoutY(0);
    }

    private void hostLobby(){
        try{
            System.out.print("Enter name for lobby: ");
            administration.hostLobby(input.nextLine());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

//    private void hostLobby(){
//        try{
//            text.setText("Host lobby");
//            //code for new window
//            administration.hostLobby();
//            listvwPlayers.setItems(administration.getLobby().getPlayers());
//
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//            text.setText(e.toString());
//        }
//
//    }
//    private void joinLobby(){
//        try{
//            text.setText("Join lobby");
//            //code for new window
//            Lobby l = listvwLobby.getSelectionModel().getSelectedItem();
//            if(l != null){administration.joinLobby(l);}
//            else{text.setText("Nothing selected");}
//            listvwLobby.setDisable(true);
//            listvwPlayers.setItems(administration.getLobby().getPlayers());
//        }
//        catch(Exception e)//IOException e
//        {
//            e.printStackTrace();
//            text.setText(e.toString());
//        }
//    }
    private void joinLobby()
    {
        try
        {
            System.out.print("Enter name for user: ");
            administration.setUser(new User(input.nextLine()));
            Lobby lobby = listvwLobby.getSelectionModel().getSelectedItem();
            if(lobby != null)
            {
                if(administration.joinLobby(lobby))
                {
                    //success join lobby
                    listvwLobby.refresh();
                }
                else
                {
                    //fail join lobby
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
        //
    }
    private void startGame(){
        text.setText("Startgame");
    }

    public static void main(String[] args) {
        Repository repo = new Repository(new LocalContext());
        System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        repo.closeConnection();
    }

}
