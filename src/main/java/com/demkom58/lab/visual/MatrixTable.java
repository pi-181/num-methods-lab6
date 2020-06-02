package com.demkom58.lab.visual;

import com.demkom58.divine.util.DoublePair;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MatrixTable {
    private final CellUpdateCallback updateCallback;

    private final GridPane root;
    private final int cellSize;
    private final boolean editable;
    private final String defaultValue;

    private TextField[][] fields;

    private int width;
    private int height;

    private final Supplier<TextFormatter<?>> formatterSupplier;

    public MatrixTable(@NotNull final GridPane root,
                       @NotNull String defaultValue,
                       @Nullable Supplier<TextFormatter<?>> formatterSupplier) {
        this(root, 30, defaultValue, formatterSupplier);
    }

    public MatrixTable(@NotNull final GridPane root,
                       int cellSize,
                       @NotNull String defaultValue,
                       @Nullable Supplier<TextFormatter<?>> formatterSupplier) {
        this(null, root, cellSize, true, defaultValue, formatterSupplier);
    }

    public MatrixTable(@Nullable final CellUpdateCallback updateCallback,
                       @NotNull final GridPane root,
                       int cellSize,
                       boolean editable,
                       @NotNull String defaultValue,
                       @Nullable Supplier<TextFormatter<?>> formatterSupplier) {
        this.updateCallback = updateCallback;
        this.root = root;
        this.cellSize = cellSize;
        this.editable = editable;
        this.defaultValue = defaultValue;

        this.fields = null;
        this.width = 0;
        this.height = 0;

        this.formatterSupplier = formatterSupplier;
    }

    public void setMatrix(byte[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public void setMatrix(short[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public void setMatrix(int[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public void setMatrix(long[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public void setMatrix(float[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public void setMatrix(double[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++)
            for (int x = 0; x < matrix[y].length; x++)
                fields[y][x].setText(String.valueOf(matrix[y][x]));
    }

    public double[][] getDoubleMatrix() {
        double[][] matrix = new double[height][width];

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                String text = fields[y][x].getText();
                matrix[y][x] = text.isBlank() ? 0 : Double.parseDouble(text);
            }

        return matrix;
    }

    public void setMatrix(Object[][] matrix) {
        if (matrix.length == 0) {
            init(0, 0);
            return;
        }

        if (matrix == null || matrix[0] == null)
            throw new IllegalArgumentException();

        init(matrix.length, matrix[0].length);

        for (int y = 0; y < matrix.length; y++) {
            for (int x = 0; x < matrix[y].length; x++) {
                final Object o = matrix[y][x];
                fields[y][x].setText(o == null ? "null" : o.toString());
            }
        }
    }

    public void init(int height, int width) {
        this.fields = new TextField[height][width];
        this.height = height;
        this.width = width;

        root.getChildren().clear();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Create a new TextField in each Iteration
                TextField cell = createCell(x, y);
                // Iterate the Index using the loops
                root.getChildren().add(cell);
                GridPane.setRowIndex(cell, y);
                GridPane.setColumnIndex(cell, x);

                fields[y][x] = cell;
            }
        }

        root.setPrefSize(cellSize * width, Math.max(cellSize * height, 90));
    }

    private TextField createCell(final int x, final int y) {
        final TextField cell = new TextField();

        if (updateCallback != null)
            cell.setOnKeyTyped(action -> this.updateCallback.onCellUpdate(action, x, y));

        if (formatterSupplier != null)
            cell.setTextFormatter(formatterSupplier.get());

        cell.setPrefSize(cellSize, cellSize);
        cell.setAlignment(Pos.CENTER);
        cell.setEditable(editable);
        cell.setText(defaultValue);
        return cell;
    }

    public TextField getCell(int y, int x) {
        if (this.fields == null)
            return null;

        if (x >= width || y >= height)
            throw new IllegalArgumentException();

        return this.fields[y][x];
    }

    public void setAll(String val) {
        if (fields == null)
            return;

        for (TextField[] xFields : fields)
            for (TextField field : xFields)
                field.setText(val);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                builder.append(fields[y][x].getText());

                if (x != (width - 1)) {
                    builder.append(';');
                }
            }

            if (y != (height - 1))
                builder.append('\n');
        }

        return builder.toString();
    }

    public boolean isInitialized() {
        return fields != null;
    }

    public boolean isEmpty() {
        return !isInitialized() || width == 0 || height == 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCellSize() {
        return cellSize;
    }

    public DoublePair[] to2DVec() {
        if (width != 2)
            throw new RuntimeException("Width of matrix should be 2");

        var pairs = new DoublePair[getHeight()];

        for (int i = 0; i < getHeight(); i++) {
            double x = 0;
            double y = 0;

            try {
                x = Double.parseDouble(getCell(i, 0).getText());
            } catch (NumberFormatException ignored) {}

            try {
                y = Double.parseDouble(getCell(i, 1).getText());
            } catch (NumberFormatException ignored) {}

            pairs[i] = new DoublePair(x, y);
        }

        return pairs;
    }

    public DoublePair[] toSortedByFirst2DVec() {
        return Arrays.stream(to2DVec())
                .sorted(Comparator.comparingDouble(DoublePair::getD1))
                .toArray(DoublePair[]::new);
    }
}
