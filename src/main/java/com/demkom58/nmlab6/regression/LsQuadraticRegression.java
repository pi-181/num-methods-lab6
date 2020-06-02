package com.demkom58.nmlab6.regression;

import com.demkom58.lab.visual.MatrixTable;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class LsQuadraticRegression implements Regression {

    @Override
    public String calculate(double searched, MatrixTable table, int n) throws Exception {
        var values = table.getHeight();
        var xys = table.toSortedByFirst2DVec();
        var xs = new double[values];
        var ys = new double[values];

        for (int i = 0; i < values; i++) {
            xs[i] = xys[i].getD1();
            ys[i] = xys[i].getD2();
        }

        var length = xs.length;

        double xAvg = Arrays.stream(xs).average().orElse(Double.NaN);
        double yAvg = Arrays.stream(ys).average().orElse(Double.NaN);

        double x2Avg = Arrays.stream(xs).map(a -> a * a).average().orElse(Double.NaN);
        double x3Avg = Arrays.stream(xs).map(a -> a * a * a).average().orElse(Double.NaN);
        double x4Avg = Arrays.stream(xs).map(a -> a * a * a * a).average().orElse(Double.NaN);

        double xyAvg = 0.0;
        for (int i = 0; i < length; ++i)
            xyAvg += xs[i] * ys[i];
        xyAvg /= length;

        double x2yAvg = 0.0;
        for (int i = 0; i < length; ++i)
            x2yAvg += xs[i] * xs[i] * ys[i];
        x2yAvg /= length;

        double sxx = x2Avg - xAvg * xAvg;
        double sxy = xyAvg - xAvg * yAvg;
        double sxx2 = x3Avg - xAvg * x2Avg;
        double sx2x2 = x4Avg - x2Avg * x2Avg;
        double sx2y = x2yAvg - x2Avg * yAvg;

        double b = (sxy * sx2x2 - sx2y * sxx2) / (sxx * sx2x2 - sxx2 * sxx2);
        double c = (sx2y * sxx - sxy * sxx2) / (sxx * sx2x2 - sxx2 * sxx2);
        double a = yAvg - b * xAvg - c * x2Avg;

        DoubleUnaryOperator y = (double x) -> a + b * x + c * x * x;
        final double calculated = y.applyAsDouble(searched);
        return "Результат: " + calculated;
    }

}
