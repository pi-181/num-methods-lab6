package com.demkom58.nmlab6.regression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class LsLinearRegression implements Regression {

    @Override
    public String calculate(double searched, double start, double end, int n, DoubleUnaryOperator function) throws Exception {
        validate(searched, start, end, n);

        var points = arrayPoints(start, end, n, function);
        var xs = points.getFirst();
        var ys = points.getValue();

        var length = points.getKey().length;

        var xMid = Arrays.stream(xs).average().orElse(Double.NaN);
        var yMid = Arrays.stream(ys).average().orElse(Double.NaN);

        var xx = 0.0;
        var xy = 0.0;

        for (int i = 0; i < length; i++) {
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
