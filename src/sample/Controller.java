package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.TreeMap;

public class Controller {

    @FXML private TableView<TestFile> table;
    @FXML private TableColumn<TestFile, String> fileName;
    @FXML private TableColumn<TestFile, String> actualClass;
    @FXML private TableColumn<TestFile, Double> spamProbability;
    private TreeMap<String, Integer> trainHamFreq;
    private TreeMap<String, Integer> trainSpamFreq;

    @FXML
    public void initialize() {
        // Initialize UI components
        fileName.setCellValueFactory(new PropertyValueFactory<TestFile, String>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<TestFile, String>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<TestFile, Double>("spamProbability"));

        // Initialize TreeMaps
        trainHamFreq = new TreeMap<String, Integer>();
        trainSpamFreq = new TreeMap<String, Integer>();

        // Input stream
        try {
            FileInputStream ham = new FileInputStream(new File("data\\train\\ham"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // FILE IO data/train/ham2
        // FILE IO data/train/spam

        trainHamFreq.put("AAAA", 1);
        trainSpamFreq.put("BBBB", 1);

        System.out.println(trainSpamFreq.containsKey("AAAA"));
        System.out.println(trainHamFreq.containsKey("AAAA"));
        System.out.println(trainSpamFreq.containsKey("BBBB"));
        System.out.println(trainHamFreq.containsKey("BBBB"));

        System.out.println(trainHamFreq.get("AAAA"));

        trainHamFreq.put("AAAA", 2);

        System.out.println(trainHamFreq.get("AAAA"));
        table.setItems(DataSource.getData());
    }

    private void parseFile(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String token = scanner.next();
            token = token.toLowerCase();
            if (isValidWord(token)) {
                countWord(token, trainHamFreq);
            }
        }
    }

    private boolean isValidWord(String word) {
        String allLetters = "^[a-zA-Z]+$";
        return word.matches(allLetters);
    }

    private void countWord(String token, TreeMap<String, Integer> map) {
        if (map.containsKey(token)) {
            map.put(token, map.get(token)+1);
        } else {
            map.put(token, 1);
        }
    }
}
