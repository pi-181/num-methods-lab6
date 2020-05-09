package com.demkom58.divine.gui;

import com.demkom58.divine.util.Triple;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GuiManager {
    @NotNull
    private final Stage stage;
    public double xOffset;
    public double yOffset;
    public boolean dragging;
    private Map<Class<? extends GuiController>, Triple<AnchorPane, Scene, ? extends GuiController>> guiMap = new HashMap<>();

    public GuiManager(@NotNull final Stage stage) {
        this.stage = stage;
    }

    public <T extends GuiController> @NotNull Triple<AnchorPane, Scene, T> registerGui(@NotNull final Class<T> controllerClass,
                                                                                       @NotNull final URL loader,
                                                                                       @NotNull final Stage stage) throws Exception {
        return this.registerGui(controllerClass, new FXMLLoader(loader), stage);
    }

    public <T extends GuiController> @NotNull Triple<AnchorPane, Scene, T> registerGui(@NotNull final Class<T> controllerClass,
                                                                                       @NotNull final FXMLLoader loader,
                                                                                       @NotNull final Stage stage) throws Exception {
        final AnchorPane pane = loader.load();
        final T controller = loader.getController();
        final Scene scene = new Scene(loader.getRoot());

        controller.setScene(scene);
        controller.setLoader(loader);
        controller.setStage(stage);
        controller.setPane(pane);
        controller.setGuiManager(this);

        final Triple<AnchorPane, Scene, T> triple = new Triple<>(pane, scene, controller);
        guiMap.put(controllerClass, triple);

        controller.init();

        return triple;
    }

    public @Nullable <T extends GuiController> Triple<AnchorPane, Scene, T> getGuiPack(Class<T> controllerClass) {
        return (Triple<AnchorPane, Scene, T>) guiMap.get(controllerClass);
    }

    public @Nullable <T extends GuiController> T getController(Class<T> controllerClass) {
        final Triple<AnchorPane, Scene, T> guiPack = getGuiPack(controllerClass);

        if (guiPack == null)
            return null;

        return guiPack.getThird();
    }

    public @Nullable <T extends GuiController> Scene getScene(Class<T> controllerClass) {
        final Triple<AnchorPane, Scene, T> guiPack = getGuiPack(controllerClass);

        if (guiPack == null)
            return null;

        return guiPack.getSecond();
    }

    public @Nullable <T extends GuiController> AnchorPane getAnchorPane(Class<T> controllerClass) {
        final Triple<AnchorPane, Scene, T> guiPack = getGuiPack(controllerClass);

        if (guiPack == null)
            return null;

        return guiPack.getFirst();
    }

    public void setGui(@NotNull final Class<? extends GuiController> controller) {
        stage.setScene(Objects.requireNonNull(getScene(controller), "Scene can't be null!"));
    }

    public void setGui(GuiController controller) {
        stage.setScene(controller.getScene());
    }

    public @NotNull Map<Class<? extends GuiController>, Triple<AnchorPane, Scene, ? extends GuiController>> getGuiMap() {
        return guiMap;
    }

    public @NotNull Stage getStage() {
        return stage;
    }

    public void setAllDraggable() {
        guiMap.values().forEach(t -> {
            final Scene scene = t.getSecond();

            scene.setOnMousePressed(event -> {
                dragging = true;
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });

            scene.setOnMouseDragged(event -> {
                if (!dragging)
                    return;

                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            });

            scene.setOnMouseReleased(event -> dragging = false);
        });

    }

    public void setDraggable(Region dragElement) {

        final double maxX = dragElement.getPrefWidth();
        final double maxY = dragElement.getPrefHeight();
        final double minX = dragElement.getLayoutX();
        final double minY = dragElement.getLayoutY();

        guiMap.values().forEach(t -> {
            final Scene scene = t.getSecond();

            scene.setOnMousePressed(event -> {
                double x = event.getSceneX(), y = event.getSceneY();
                if (x < minX || x > maxX || y < minY || y > maxY)
                    return;

                dragging = true;
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            });

            scene.setOnMouseDragged(event -> {
                if (!dragging)
                    return;

                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            });

            scene.setOnMouseReleased(event -> dragging = false);

        });

    }

    public void setAllTransparent() {
        guiMap.values().forEach(objects -> objects.getSecond().setFill(Color.TRANSPARENT));
    }

    public void close() {
        stage.close();
        System.exit(0);
    }

    public boolean isDragging() {
        return dragging;
    }
}
