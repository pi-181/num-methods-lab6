package com.demkom58.nmlab6.regression;

import com.demkom58.lab.visual.MatrixTable;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class ExponentialRegression implements Regression {
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
