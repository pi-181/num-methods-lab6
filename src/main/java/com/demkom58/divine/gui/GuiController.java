package com.demkom58.divine.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public abstract class GuiController {

    private Stage stage;
    private FXMLLoader loader;
    private Scene scene;
    private AnchorPane pane;
    private GuiManager guiManager;

    public void init() { }

    @NotNull
    public Scene getScene() {
        return scene;
    }

    public void setScene(@NotNull final Scene scene) {
        this.scene = scene;
    }

    @NotNull
    public Stage getStage() {
        return stage;
    }

    public void setStage(@NotNull final Stage stage) {
        this.stage = stage;
    }

    @NotNull
    public FXMLLoader getLoader() {
        return loader;
    }

    public void setLoader(@NotNull final FXMLLoader loader) {
        this.loader = loader;
    }

    @NotNull
    public AnchorPane getPane() {
        return pane;
    }

    public void setPane(@NotNull final AnchorPane pane) {
        this.pane = pane;
    }

    @NotNull
    public GuiManager getGuiManager() {
        return guiManager;
    }

    public void setGuiManager(@NotNull final GuiManager guiManager) {
        this.guiManager = guiManager;
    }

}
