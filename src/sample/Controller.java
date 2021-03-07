package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {

    @FXML private TableView<TestFile> table;
    @FXML private TableColumn<TestFile, String> fileName;
    @FXML private TableColumn<TestFile, String> actualClass;
    @FXML private TableColumn<TestFile, Double> spamProbability;

    @FXML
    public void initialize() {
        fileName.setCellValueFactory(new PropertyValueFactory<TestFile, String>("fileName"));
        actualClass.setCellValueFactory(new PropertyValueFactory<TestFile, String>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<TestFile, Double>("spamProbability"));
        table.setItems(DataSource.getData());
    }
}
