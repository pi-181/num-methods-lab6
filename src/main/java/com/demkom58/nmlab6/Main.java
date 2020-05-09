package com.demkom58.nmlab6;


import com.demkom58.divine.gui.GuiManager;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GuiManager guiManager = new GuiManager(primaryStage);

        primaryStage.setTitle("Чисельні методи: Лабараторна робота №6");
        primaryStage.setResizable(false);

        guiManager.registerGui(MainController.class, getClass().getResource("/style/main.fxml"), primaryStage);

        final MainController mainController = Objects.requireNonNull(guiManager.getGuiPack(MainController.class)).getThird();
        guiManager.setGui(MainController.class);

        final FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.seconds(0.25));
        transition.setFromValue(0);
        transition.setToValue(100);
        transition.setNode(mainController.getPane());

        primaryStage.show();
        transition.play();
    }
}
