package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Controller {

    @FXML private TableView<TestFile> table;
    @FXML private TableColumn<TestFile, String> fileName;
    @FXML private TableColumn<TestFile, String> actualClass;
    @FXML private TableColumn<TestFile, Double> spamProbability;
    private TreeMap<String, Integer> trainHamFreq;
    private TreeMap<String, Integer> trainSpamFreq;
    private TreeMap<String, Double> spamProbabilityMap;
    private int numHam = 0;
    private int numSpam = 0;

    @FXML
    public void initialize() {
        // Initialize UI components
        fileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        // Initialize TreeMaps
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        spamProbabilityMap = new TreeMap<>();

//        File directoryPath1 = new File(""); // sample\data\train\ham
//        File directoryPath2 = new File("src\\sample\\data\\train\\ham2"); // sample\data\train\ham
//        File directoryPath3 = new File("src\\sample\\data\\train\\spam"); // sample\data\train\ham

        try {
            parseFile(new File("src\\sample\\data\\train\\ham"), trainHamFreq);
            parseFile(new File("src\\sample\\data\\train\\ham2"), trainHamFreq);
            parseFile(new File("src\\sample\\data\\train\\spam"), trainSpamFreq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TEST CODE
        System.out.println(trainHamFreq.get("about"));
        System.out.println(trainSpamFreq.get("about"));
        System.out.println(trainHamFreq.get("a"));
        System.out.println(trainSpamFreq.get("a"));
        System.out.println(trainHamFreq.get("you"));
        System.out.println(trainSpamFreq.get("you"));
        System.out.println(trainHamFreq.get("your"));
        System.out.println(trainSpamFreq.get("your"));
        System.out.println(numHam);
        System.out.println(numSpam);

        // Calculate probabilities for spam probability map
        for (Map.Entry<String, Integer> entry : trainSpamFreq.entrySet()) {
            // TODO
            String key = entry.getKey();
            //int value = entry.getValue();
            double wis = (double) trainSpamFreq.get(key)/numSpam;
            double wih;
            if (trainHamFreq.containsKey(key)) {
                wih = (double) trainHamFreq.get(key)/numHam;
            } else {
                wih = 0;
            }
            spamProbabilityMap.put(key, wis/(wis+wih));
        }

//        for (Map.Entry<String, Integer> entry : trainSpamFreq.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
//        }

        // MORE TEST CODE
        System.out.println("about: " + spamProbabilityMap.get("about"));
        System.out.println("you: " + spamProbabilityMap.get("you"));
        System.out.println("your: " + spamProbabilityMap.get("your"));

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
            if (map == trainHamFreq) numHam++;
            if (map == trainSpamFreq) numSpam++;
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
