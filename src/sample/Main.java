package sample;

import database.Contexts.DatabaseContext;
import database.Repositories.Repository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Repository repo = new Repository(new DatabaseContext());
        System.out.println(Boolean.toString(repo.testConnection()));
        launch(args);
        repo.closeConnection();

    }
}
