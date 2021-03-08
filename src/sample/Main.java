package sample;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;


/**
 * authors: Richard Pena (10074424), Samih Ejel (...)
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        

        
        primaryStage.setTitle("Assignment 1");
        //first page
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Scene scene1;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Button nextButton= new Button("Next");
        Button dir1Button= new Button("Choose dir1");
        Button dir2Button= new Button("Choose dir2");
        Button dir3Button= new Button("Choose dir3");
        nextButton.setOnAction(e -> primaryStage.setScene(new Scene(root, 600, 500)));
        dir1Button.setOnAction(e -> {
            File dir1 = directoryChooser.showDialog(primaryStage);
        });
        dir2Button.setOnAction(e -> {
            File dir2 = directoryChooser.showDialog(primaryStage);
        });   
        dir3Button.setOnAction(e -> {
            File dir3 = directoryChooser.showDialog(primaryStage);
        });     

        VBox layout1 = new VBox(20);     
        layout1.getChildren().addAll(nextButton,dir1Button,dir2Button,dir3Button);
        scene1= new Scene(layout1, 600, 500);

        
        primaryStage.setScene(scene1);
        primaryStage.show();
        //File selectedDirectory = directoryChooser.showDialog(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
