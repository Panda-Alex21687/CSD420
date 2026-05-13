package com.baldree.mod10;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FanApp extends Application {

    @Override
    public void start(Stage stage) {
        FanRepository repository = new JdbcFanRepository();
        FanController controller = new FanController(repository);

        Scene scene = new Scene(controller.getView(), 500, 350);

        stage.setTitle("Fan Database App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}