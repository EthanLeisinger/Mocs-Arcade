package edu.utc.arcade;

import com.google.gson.JsonObject;
import com.sun.javafx.collections.ObservableListWrapper;
import edu.utc.arcade.game.Game;
import edu.utc.arcade.game.GameLibrary;
import edu.utc.arcade.gui.GameListViewCell;
import edu.utc.arcade.logging.Log;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    private JsonObject settings;
    private ScrollPane root;
    private ListView<Game> listView;
    private GameLibrary library = new GameLibrary();

    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        setupLayout();
        loadLibrary(false);
        primaryStage.setTitle("Mocs Arcade");
        primaryStage.setScene(new Scene(root, 1980, 1080));
        primaryStage.show();
        Log.i("Height: " + (listView != null ? listView.getHeight() : "NULL"));
    }

    private void loadSettings() throws IOException {
        File settingsFile = new File("./settings.json");

        //If settings file does not exist make a new one
        if (!settingsFile.exists())
            Log.i("Settings file created: " + settingsFile.createNewFile());
    }

    private void setupLayout() {
        root = new ScrollPane();
        root.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        root.fitToHeightProperty().setValue(true);
        root.fitToWidthProperty().setValue(true);


        listView = new ListView<>();


        root.setContent(listView);
    }

    private void loadLibrary(boolean localOnly) {
        ObservableList<Game> observableList = new ObservableListWrapper<Game>(library.getLibraryList(localOnly));
        listView.setCellFactory(new Callback<ListView<Game>, ListCell<Game>>() {
            @Override
            public ListCell<Game> call(ListView<Game> param) {
                return new GameListViewCell();
            }
        });

        listView.setItems(observableList);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
