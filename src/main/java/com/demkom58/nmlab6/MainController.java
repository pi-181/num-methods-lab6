package com.demkom58.nmlab6;

import com.demkom58.divine.chart.ExtendedLineChart;
import com.demkom58.divine.gui.GuiController;
import com.demkom58.divine.util.AlertUtil;
import com.demkom58.divine.util.Pair;
import com.demkom58.lab.visual.CellUpdateCallback;
import com.demkom58.lab.visual.MatrixTable;
import com.demkom58.nmlab6.regression.ExponentialRegression;
import com.demkom58.nmlab6.regression.LsLinearRegression;
import com.demkom58.nmlab6.regression.LsQuadraticRegression;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class MainController extends GuiController {
    @FXML private GridPane matrixGrid;
    @FXML private TextField stepsInput;
    @FXML private TextField xInput;
    @FXML private ExtendedLineChart<Double, Double> lineChart;

    private int steps;
    private double x;

    private XYChart.Series<Double, Double> functionSeries =
            new XYChart.Series<>("Функція", FXCollections.observableArrayList());

    private MatrixTable matrixTable;

    @Override
    public void init() {
        super.init();

        this.matrixTable = new MatrixTable(
                (event, x, y) -> Platform.runLater(this::fillFunctionSeries),
                matrixGrid,
                40,
                true,
                "0",
                null
        );

        this.matrixTable.init(getSteps(), 2);
        lineChart.getData().add(functionSeries);
        read();
    }

    @FXML
    public void onUpdate(ActionEvent event) {
        matrixTable.setMatrix(new double[getSteps()][2]);
    }

    @FXML
    public void linear(MouseEvent event) {
        try {
            check();
            var result = new LsLinearRegression().calculate(x, matrixTable, steps);
            showResult("Linear", result);
        } catch (Exception e) {
            AlertUtil.showErrorMessage(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void quadratic(MouseEvent event) {
        try {
            check();
            var result = new LsQuadraticRegression().calculate(x, matrixTable, steps);
            showResult("Quadratic", result);
        } catch (Exception e) {
            AlertUtil.showErrorMessage(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void lagrange(MouseEvent event) {
        try {
            check();
            var result = new ExponentialRegression().calculate(x, matrixTable, steps);
            showResult("Exp", result);
        } catch (Exception e) {
            AlertUtil.showErrorMessage(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void onChanged(KeyEvent event) {
        read();
    }

    private void showResult(String method, String result) {
        AlertUtil.showInfoMessage("Method " + method, result);
        read();
    }

    private void check() throws IllegalStateException {

    }

    private void fillFunctionSeries() {
        lineChart.getData().clear();
        lineChart.getData().add(functionSeries);
        lineChart.removeHorizontalValueMarkers();
        lineChart.removeVerticalValueMarkers();
        functionSeries.setData(FXCollections.observableArrayList());

        var height = matrixTable.getHeight();

        var xys = matrixTable.toSortedByFirst2DVec();

        for (int i = 0; i < height; i++) {
            var point = xys[i];
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(point.getD1(), point.getD2());
            functionSeries.getData().add(data);
        }
    }

    private void read() {
        if (!lineChart.getData().isEmpty())
            lineChart.setData(FXCollections.observableArrayList());

        steps = getSteps();
        try {
            x = Double.parseDouble(xInput.getText());
        } catch (Exception e) {
            x = Double.parseDouble(xInput.getPromptText());
        }
        fillFunctionSeries();
    }

    public int getSteps() {
        String text = stepsInput.getText();
        if (text.isBlank())
            return Integer.parseInt(stepsInput.getPromptText());

        var expression = new Expression(text);
        if (!expression.checkSyntax())
            return Integer.MIN_VALUE;

        return (int) expression.calculate();
    }

}
