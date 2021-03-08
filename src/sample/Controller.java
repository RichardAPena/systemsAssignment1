package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
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
        fileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        // Initialize TreeMaps
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();

        File directoryPath1 = new File("src\\sample\\data\\train\\ham"); // sample\data\train\ham
        File directoryPath2 = new File("src\\sample\\data\\train\\ham2"); // sample\data\train\ham
        File directoryPath3 = new File("src\\sample\\data\\train\\spam"); // sample\data\train\ham

        try {

            parseFile(directoryPath1, trainHamFreq);
            parseFile(directoryPath2, trainHamFreq);
            parseFile(directoryPath3, trainHamFreq);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // TEST CODE
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

    /**
     *
     * @param file
     * @param map
     * @throws IOException
     */
    private void parseFile(File file, TreeMap<String, Integer> map) throws IOException {
        if (file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            File[] content = file.listFiles();
            for (File current : content) {
                parseFile(current, map);
            }
        } else {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String token = scanner.next();
                token = token.toLowerCase();
                if (isValidWord(token)) {
                    countWord(token, map);
                }
            }
        }
    }

    /**
     *
     * @param word
     * @return
     */
    private boolean isValidWord(String word) {
        String allLetters = "^[a-zA-Z]+$";
        return word.matches(allLetters);
    }

    /**
     *
     * @param token
     * @param map
     */
    private void countWord(String token, TreeMap<String, Integer> map) {
        if (map.containsKey(token)) {
            map.put(token, map.get(token)+1);
        } else {
            map.put(token, 1);
        }
    }

    private void test() {
        // TODO: testing accuracy
    }
}
