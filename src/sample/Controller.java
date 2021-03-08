package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
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
    private ObservableList<TestFile> testData;

    @FXML
    public void initialize() {
        // Initialize UI components
        fileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        // Initialize TreeMaps and ObservableList
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();
        spamProbabilityMap = new TreeMap<>();
        testData = FXCollections.observableArrayList();

        // Parse all files and create train maps
        try {
            parseFile(new File("src\\sample\\data\\train\\ham"), trainHamFreq);
            parseFile(new File("src\\sample\\data\\train\\ham2"), trainHamFreq);
            parseFile(new File("src\\sample\\data\\train\\spam"), trainSpamFreq);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TEST CODE
//        System.out.println(trainHamFreq.get("about"));
//        System.out.println(trainSpamFreq.get("about"));
//        System.out.println(trainHamFreq.get("a"));
//        System.out.println(trainSpamFreq.get("a"));
//        System.out.println(trainHamFreq.get("you"));
//        System.out.println(trainSpamFreq.get("you"));
//        System.out.println(trainHamFreq.get("your"));
//        System.out.println(trainSpamFreq.get("your"));
//        System.out.println(numHam);
//        System.out.println(numSpam);

        // Calculate probabilities for spam probability map
        for (Map.Entry<String, Integer> entry : trainSpamFreq.entrySet()) {
            String key = entry.getKey();
            double wis = (double) trainSpamFreq.get(key)/numSpam;
            double wih;
            if (trainHamFreq.containsKey(key)) {
                wih = (double) trainHamFreq.get(key)/numHam;
            } else {
                wih = 0;
            }
            spamProbabilityMap.put(key, wis/(wis+wih));
        }

        // MORE TEST CODE
//        System.out.println("about: " + spamProbabilityMap.get("about"));
//        System.out.println("you: " + spamProbabilityMap.get("you"));
//        System.out.println("your: " + spamProbabilityMap.get("your"));

        // baba booey
        // Take in all the ham files in test/ham and test/spam
        try {
            testFile(new File("src\\sample\\data\\test\\ham"), "ham");
            testFile(new File("src\\sample\\data\\test\\spam"), "spam");
        } catch (IOException e) {
            e.printStackTrace();
        }

        table.setItems(testData);
    }

    /**
     * This method parses all the files in a directory and stores the number of times a word appears in map (parameter)
     * @param file input file/directory
     * @param map which train map to
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
     * This method tests all files in a specified directory and calculates the probability that it will be a spam
     * e-mail, and records that probability as a testFile object, which is then stored in the ObservableList testData
     * @param file Input file/directory
     * @param actualClass the type of mail this is
     * @throws IOException
     */
    private void testFile(File file, String actualClass) throws IOException {
        if (file.isDirectory()) {
            System.out.println(file.getAbsolutePath());
            File[] content = file.listFiles();
            for (File current : content) {
                testFile(current, actualClass);
            }
        } else {
            Scanner scanner = new Scanner(file);
            double sum = 0;
            while (scanner.hasNext()) {
                String token = scanner.next();
                if (isValidWord(token)) {
                    if (spamProbabilityMap.containsKey(token)) {
                        sum += Math.log(1-spamProbabilityMap.get(token))-Math.log(spamProbabilityMap.get(token));
                    }
                }
            }
            testData.add(new TestFile(file.getName(), 1/(1+Math.pow(Math.E, sum)), actualClass));
        }
    }

    /**
     * Checks if a word contains only letters
     * @param word String to check
     * @return true if the word contains only letters, false otherwise
     */
    private boolean isValidWord(String word) {
        String allLetters = "^[a-zA-Z]+$";
        return word.matches(allLetters);
    }

    /**
     * Counts a word into a map
     * @param token word to count
     * @param map map to count the word in
     */
    private void countWord(String token, TreeMap<String, Integer> map) {
        if (map.containsKey(token)) {
            map.put(token, map.get(token)+1);
        } else {
            map.put(token, 1);
        }
    }

}
