package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataSource {

    public static ObservableList<TestFile> getData() {
        ObservableList<TestFile> data =
                FXCollections.observableArrayList();
        // TODO
        data.add(new TestFile("test", 0.25, "none"));
        return data;
    }
}
