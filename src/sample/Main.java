package sample;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;


/**
 * authors: Richard Pena (10074424), Samih Ejel (...)
 */


public class Main extends Application {

    public static File dir1;
    public static Button confirmButton= new Button("Train using data folder at chosen directory");
    @Override
    public void start(Stage primaryStage) throws Exception{
        

        
        primaryStage.setTitle("Assignment 1");
        //first page
        Text directoryLabel = new Text("Chosen Directory: ");
        Text directory = new Text("");
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Scene scene1;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Button dir1Button= new Button("Choose directory of data folder");
        Button nextButton= new Button("View Table using trained data");
        nextButton.setOnAction(e -> {
            primaryStage.setScene(new Scene(root, 600, 500));            
        });
        dir1Button.setOnAction(e -> {
            dir1 = directoryChooser.showDialog(primaryStage);
            directory.setText(""+dir1);
        });   

        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);     
        layout1.getChildren().addAll(nextButton,confirmButton,dir1Button,directoryLabel,directory);
        scene1= new Scene(layout1, 600, 500);

        
        primaryStage.setScene(scene1);
        primaryStage.show();
        //File selectedDirectory = directoryChooser.showDialog(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
