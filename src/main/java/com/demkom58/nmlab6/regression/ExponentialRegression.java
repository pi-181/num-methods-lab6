package com.demkom58.nmlab6.regression;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class ExponentialRegression implements Regression {
    @Override
    public String calculate(double searched, double start, double end, int n, DoubleUnaryOperator function) throws Exception {
        validate(searched, start, end, n);

        var points = arrayPoints(start, end, n, function);
        var xs = points.getKey();
        var ys = points.getValue();

        int length = xs.length;

        double sumX = Arrays.stream(xs).sum();
        double sumX2 = Arrays.stream(xs).map(x -> Math.pow(x, 2)).sum();
        double sumY = Arrays.stream(ys).map(Math::log).sum();

        double sumXY = 0.00;
        for (int i = 0; i < length; i++)
            sumXY = sumXY + (xs[i] * (Math.log(ys[i])));

        double b = ((length * sumXY) - (sumX * sumY)) / (length * sumX2 - (sumX * sumX));
        double a = Math.pow(Math.E, (sumY - (b * sumX)) / length);

        DoubleUnaryOperator y = (double x) -> a * (Math.pow(Math.E, b * x));
        return "Результат: " + y.applyAsDouble(searched);
    }
}
