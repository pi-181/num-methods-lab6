package com.demkom58.nmlab6.regression;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class LsQuadraticRegression implements Regression {

    @Override
    public String calculate(double searched, double start, double end, int n, DoubleUnaryOperator function) throws Exception {
        validate(searched, start, end, n);

        var points = arrayPoints(start, end, n, function);
        var xs = points.getKey();
        var ys = points.getValue();

        var length = points.getKey().length;

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
        return "Результат: " + y.applyAsDouble(searched);
    }

}
