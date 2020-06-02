package com.demkom58.nmlab6.regression;

import com.demkom58.divine.util.DoublePair;
import com.demkom58.lab.visual.MatrixTable;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class ExponentialRegression implements Regression {
    @Override
    public String calculate(double searched, MatrixTable table, int n) throws Exception {
        var xys = table.toSortedByFirst2DVec();
        DoubleUnaryOperator y = find(searched, xys, n);
        var calculated = y.applyAsDouble(searched);

        double correlation = 0;
        {
            var sqSum = Arrays.stream(xys)
                    .mapToDouble(v -> Math.pow(v.getD2() - y.applyAsDouble(v.getD1()), 2))
                    .sum();

            var sqCalcSum = Arrays.stream(xys)
                    .mapToDouble(v -> Math.pow(v.getD2() - calculated, 2))
                    .sum();

            correlation = Math.sqrt(1 - (sqSum / sqCalcSum));
        }

        double determination = Math.pow(correlation, 2);

        return "Результат: " + calculated + "\n" +
                "Індекс корреляції: " + correlation + "\n" +
                "Індекс детермінації: " + determination;
    }

    private DoubleUnaryOperator find(double searched, DoublePair[] xys, int values) {
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

        return (double x) -> a * (Math.pow(Math.E, b * x));
    }
}
