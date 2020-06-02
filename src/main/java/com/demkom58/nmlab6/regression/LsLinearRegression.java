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

        DoubleUnaryOperator y = (double x) -> slope * x + intercept;
        return "Результат: " + y.applyAsDouble(searched);
    }

}
