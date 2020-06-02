package com.demkom58.nmlab6.regression;

import com.demkom58.lab.visual.MatrixTable;

import java.util.Arrays;
import java.util.function.DoubleUnaryOperator;

public class LsLinearRegression implements Regression {

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

        double correlation;
        {
            var mulSum = Arrays.stream(xys).mapToDouble(v -> v.getD1() * v.getD2()).sum();
            var xSum = Arrays.stream(xs).sum();
            var ySum = Arrays.stream(ys).sum();
            var xySumMul = xSum * ySum;
            var x2Sum = Arrays.stream(xs).map(x -> x * x).sum();
            var y2Sum = Arrays.stream(ys).map(y -> y * y).sum();

            correlation =
                    (n * mulSum - xySumMul)
                    /
                    Math.sqrt((n * x2Sum - Math.pow(xSum, 2)) * (n * y2Sum - Math.pow(ySum, 2)));

        }
        double determination = Math.pow(correlation, 2);

        DoubleUnaryOperator y = find(xs, ys);
        return "Результат: " + y.applyAsDouble(searched) + "\n" +
                "Індекс корреляції: " + correlation + "\n" +
                "Індекс детермінації: " + determination;
    }

    private DoubleUnaryOperator find(double[] xs, double[] ys) {
        var xMid = Arrays.stream(xs).average().orElse(Double.NaN);
        var yMid = Arrays.stream(ys).average().orElse(Double.NaN);

        var xx = 0.0;
        var xy = 0.0;

        for (int i = 0; i < xs.length; i++) {
            var dX = (xs[i] - xMid);

            xx += Math.pow(dX, 2);
            xy += dX * (ys[i] - yMid);
        }

        if (xx == 0)
            xx = Math.pow(1, -10);

        var slope = xy / xx;
        var intercept = yMid - slope * xMid;

        return (double x) -> slope * x + intercept;
    }

}
