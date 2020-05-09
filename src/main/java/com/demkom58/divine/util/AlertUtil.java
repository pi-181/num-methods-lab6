package com.demkom58.divine.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.jetbrains.annotations.NotNull;

public final class AlertUtil {
    public static void showErrorMessage(@NotNull final Exception exception) {
        showErrorMessage(exception.getMessage());
    }

    public static void showErrorMessage(@NotNull final String error) {
        alert(Alert.AlertType.ERROR, "Помилка", error, ButtonType.OK);
    }

    public static void showInfoMessage(@NotNull final String header, @NotNull final String info) {
        alert(Alert.AlertType.INFORMATION, header, info, ButtonType.OK);
    }

    public static void alert(Alert.AlertType type, String header, String message, ButtonType... types) {
        Alert alert = new Alert(type, message, types);
        alert.setHeaderText(header);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

}
