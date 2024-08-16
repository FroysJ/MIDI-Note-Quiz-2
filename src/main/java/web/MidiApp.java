package web;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class MidiApp extends Application {

    private static final String SERVER_URL = "http://localhost:8080";

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        Storage.store(primaryStage);
        stage = primaryStage;

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(SERVER_URL + "/index.html");

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("app", this);
            } else {
                System.out.println("failed");
            }
        });

        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MIDI Note Quiz");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void closeWindow() {
        stage.close();
    }

    public static class Storage {
        public static Stage stage;
        private Storage(Stage stage) {
            Storage.stage = stage;
        }
        public static void store(Stage input) {
            if (stage == null) {
                stage = input;
            }
        }
        public static void close() {
            stage.close();
            System.out.println("Stage closed.");
        }
    }
}
