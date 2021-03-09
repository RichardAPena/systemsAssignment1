package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Controller {

    @FXML private  TableView<TestFile> table;
    @FXML private TableColumn<TestFile, String> fileName;
    @FXML private TableColumn<TestFile, String> actualClass;
    @FXML private TableColumn<TestFile, String> spamProbability;
    @FXML private  TextField accuracyField;
    @FXML private  TextField precisionField;
    private  TreeMap<String, Integer> trainHamFreq;
    private  TreeMap<String, Integer> trainSpamFreq;
    private  TreeMap<String, Double> spamProbabilityMap;
    private  int numHam = 0;
    private  int numSpam = 0;
    private  ObservableList<TestFile> testData;
    private  double numCorrectGuesses;
    private  double numGuesses;
    private  double truePositives;
    private  double falsePositives;
    private  double accuracy;
    private  double precision;

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
        
        numCorrectGuesses = 0;
        numGuesses = 0;

        Main.confirmButton.setOnAction(e ->{
            buttonPress();
        });

        
    }

    public void buttonPress(){
        System.out.println(Main.dir1);
        // Parse all files and create train maps
        try {
            parseFile(new File(Main.dir1 + "\\train\\ham"), trainHamFreq);
            parseFile(new File(Main.dir1 + "\\train\\ham2"), trainHamFreq);
            parseFile(new File(Main.dir1 + "\\train\\spam"), trainSpamFreq);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        // Take in all the ham files in test/ham and test/spam
        try {
            testFile(new File(Main.dir1 + "\\test\\ham"), "ham");
            testFile(new File(Main.dir1 + "\\test\\spam"), "spam");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Set data into fields and table
        table.setItems(testData);
        accuracy = numCorrectGuesses/numGuesses;
        precision = truePositives/(truePositives+falsePositives);
        accuracyField.setText(String.valueOf(accuracy));
        precisionField.setText(String.valueOf(precision));
    }

    /**
     * This method parses all the files in a directory and stores the number of times a word appears in map (parameter)
     * @param file input file/directory
     * @param map which train map to
     * @throws IOException
     */
    private  void parseFile(File file, TreeMap<String, Integer> map) throws IOException {
        if (file.isDirectory()) {
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
    private  void testFile(File file, String actualClass) throws IOException {
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
                        sum += Math.log(1-spamProbabilityMap.get(token)) - Math.log(spamProbabilityMap.get(token));
                    }
                }
            }
           
            TestFile entry = new TestFile(file.getName(), (1/(1+Math.pow(Math.E, sum))), actualClass);
            testData.add(entry);
            if (entry.thisIsSpam() == entry.getActualClass()){
                numGuesses +=1;
                numCorrectGuesses+=1;
                if((entry.thisIsSpam() == "spam")&&(entry.getActualClass() == "spam")){
                    truePositives += 1;
                }
            }
            else{
                numGuesses+=1;
                if ((entry.thisIsSpam() == "spam")&&(entry.getActualClass() == "ham")){
                    falsePositives += 1;
                }
            }

        }
    }

    /**
     * Checks if a word contains only letters
     * @param word String to check
     * @return true if the word contains only letters, false otherwise
     */
    private static boolean isValidWord(String word) {
        String allLetters = "^[a-zA-Z]+$";
        return word.matches(allLetters);
    }

    /**
     * Counts a word into a map
     * @param token word to count
     * @param map map to count the word in
     */
    private static void countWord(String token, TreeMap<String, Integer> map) {
        if (map.containsKey(token)) {
            map.put(token, map.get(token)+1);
        } else {
            map.put(token, 1);
        }
    }

}
