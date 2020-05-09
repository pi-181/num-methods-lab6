package com.demkom58.nmlab6;

import com.demkom58.divine.chart.ExtendedLineChart;
import com.demkom58.divine.gui.GuiController;
import com.demkom58.divine.util.AlertUtil;
import com.demkom58.nmlab6.regression.ExponentialRegression;
import com.demkom58.nmlab6.regression.LsLinearRegression;
import com.demkom58.nmlab6.regression.LsQuadraticRegression;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;

public class MainController extends GuiController {
    @FXML private TextField functionInput;
    @FXML private TextField fromAInput;
    @FXML private TextField toBInput;
    @FXML private TextField stepsInput;
    @FXML private TextField xInput;
    @FXML private ExtendedLineChart<Double, Double> lineChart;

    private Function function;

    private double start;
    private double end;

    private int steps;
    private double x;

    private XYChart.Series<Double, Double> functionSeries =
            new XYChart.Series<>("Функція", FXCollections.observableArrayList());

    @Override
    public void init() {
        super.init();
        lineChart.getData().add(functionSeries);
        read();
    }

    @FXML
    public void linear(MouseEvent event) {
        try {
            check();
            var result = new LsLinearRegression().calculate(x, start, end, steps, function::calculate);
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
            var result = new LsQuadraticRegression().calculate(x, start, end, steps, function::calculate);
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
            var result = new ExponentialRegression().calculate(x, start, end, steps, function::calculate);
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
        if (!function.checkSyntax())
            throw new IllegalStateException("Перевірте введену функцію.\n" + function.getErrorMessage());
    }

    private void fillFunctionSeries(Double intervalA, Double intervalB) {
        lineChart.getData().add(functionSeries);
        lineChart.removeHorizontalValueMarkers();
        lineChart.removeVerticalValueMarkers();
        functionSeries.setData(FXCollections.observableArrayList());

        double y, x;
        x = intervalA - 5.0;

        final double end = 2 * (intervalB * 10 - intervalA * 10) + 50;
        for (int i = 0; i < end; i++) {
            x = x + 0.1;
            y = function.calculate(x);
            final XYChart.Data<Double, Double> data = new XYChart.Data<>(x, y);

            if (i != 0 && i != end - 1) {
                final Rectangle rectangle = new Rectangle(0, 0);
                rectangle.setVisible(false);
                data.setNode(rectangle);
            }

            functionSeries.getData().add(data);
        }


        final XYChart.Series<Double, Double> intervalSeries =
                new XYChart.Series<>("Проміжок", FXCollections.observableArrayList());

        intervalSeries.getData().addAll(
                new XYChart.Data<>(intervalA, 0d),
                new XYChart.Data<>(intervalB, 0d)
        );

        lineChart.getData().add(intervalSeries);
    }

    private void read() {
        final var prefix = "f(x)=";

        if (!lineChart.getData().isEmpty())
            lineChart.setData(FXCollections.observableArrayList());

        String functionText = functionInput.getText().replace(" ", "");
        if (functionText.isBlank()) {
            final String promptText = functionInput.getPromptText().replace(" ", "");
            functionText = promptText.substring(promptText.indexOf(prefix));
        }

        if (!functionText.isEmpty() && !functionText.startsWith(prefix))
            return;
        function = new Function(functionText);

        start = getA();
        end = getB();
        steps = getSteps();
        x = getX();

        if (function.checkSyntax())
            fillFunctionSeries(start, end);
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

    public double getX() {
        String text = xInput.getText();
        if (text.isBlank())
            return Double.parseDouble(xInput.getPromptText());

        var expression = new Expression(text);
        if (!expression.checkSyntax())
            return Double.MIN_VALUE;

        return expression.calculate();
    }

    public double getA() {
        String text = fromAInput.getText();
        if (text.isBlank())
            return Double.parseDouble(fromAInput.getPromptText());

        var expression = new Expression(text);
        if (!expression.checkSyntax())
            return Double.MIN_VALUE;

        return expression.calculate();
    }

    public double getB() {
        String text = toBInput.getText();
        if (text.isBlank())
            return Double.parseDouble(toBInput.getPromptText());

        var expression = new Expression(text);
        if (!expression.checkSyntax())
            return Double.MIN_VALUE;

        return expression.calculate();
    }
}
