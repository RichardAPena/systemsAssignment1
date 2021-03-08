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
        fileName.setCellValueFactory(new PropertyValueFactory<>("filename"));
        actualClass.setCellValueFactory(new PropertyValueFactory<>("actualClass"));
        spamProbability.setCellValueFactory(new PropertyValueFactory<>("spamProbability"));

        // Initialize TreeMaps
        trainHamFreq = new TreeMap<>();
        trainSpamFreq = new TreeMap<>();

        // Input stream
        /*try {
            FileInputStream ham = new FileInputStream(new File("data\\train\\ham"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/

        // FILE IO data/train/ham2
        // FILE IO data/train/spam

        File directoryPath1 = new File("sample\\data\\train\\ham");
        File directoryPath2 = new File("sample\\data\\train\\ham2");
        File directoryPath3 = new File("sample\\data\\train\\spam");

        File hamList[] = directoryPath1.listFiles();
        File ham2List[] = directoryPath2.listFiles();
        File spamList[] = directoryPath3.listFiles();

        System.out.println(directoryPath1.getAbsolutePath());
        //System.out.println(hamList[0].getAbsolutePath());

        /*
        try {
            for (File file : hamList) {
                parseFile(file, trainHamFreq);
            }
            for (File file : ham2List) {
                parseFile(file, trainHamFreq);
            }
            for (File file : spamList) {
                parseFile(file, trainSpamFreq);
            }

            //parseFile(new File("data\\train\\ham"), trainHamFreq);
            //parseFile(new File("data\\train\\ham2"), trainHamFreq);
            //parseFile(new File("data\\train\\spam"), trainSpamFreq);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

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

    private void parseFile(File file, TreeMap<String, Integer> map) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String token = scanner.next();
            token = token.toLowerCase();
            if (isValidWord(token)) {
                countWord(token, map);
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
