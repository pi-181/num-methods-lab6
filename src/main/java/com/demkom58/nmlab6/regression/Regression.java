package com.demkom58.nmlab6.regression;

import com.demkom58.divine.math.PointD;
import org.apache.commons.math3.util.Pair;

import java.util.function.DoubleUnaryOperator;
import java.util.function.ToDoubleFunction;

public interface Regression {

    String calculate(double x, double a, double b, int n, DoubleUnaryOperator function) throws Exception;

    default void validate(double x, double a, double b, double n) throws Exception {
        if (n == 0)
            throw new Exception("Вказано 0 кроків.");

        if (a >= b)
            throw new Exception("Значення 'a' більше або дорівнює 'b'");
    }

    default PointD[] points(double a, double b, int n, DoubleUnaryOperator function) {
        var points = new PointD[n + 1];

        var step = (b - a) / n;
        var currentX = a;

        for (int i = 0; i <= n; i++) {
            points[i] = new PointD(currentX, function.applyAsDouble(currentX));
            currentX += step;
        }

        return points;
    }

    default Pair<double[], double[]> arrayPoints(double a, double b, int n, DoubleUnaryOperator function) {
        var keys = new double[n + 1];
        var values = new double[n + 1];

        var step = (b - a) / n;
        var currentX = a;

        for (int i = 0; i <= n; i++) {
            keys[i] = currentX;
            values[i] = function.applyAsDouble(currentX);
            currentX += step;
        }

        return new Pair<>(keys, values);
    }

    default PointD[] findClosest(PointD[] points, ToDoubleFunction<PointD> extractor, double value) throws Exception {
        PointD lower = null;
        PointD higher = null;

        for (int i = 0; i < points.length; i++) {
            var current = extractor.applyAsDouble(points[i]);

            if (lower == null || (current < value && closest(current, value, extractor.applyAsDouble(lower))))
                lower = points[i];

            if (higher == null || (current > value && closest(current, value, extractor.applyAsDouble(higher))))
                higher = points[i];
        }

        if (lower == null || higher == null)
            throw new Exception("Невдалось знайти найближчі точки.");

        return new PointD[]{lower, higher};
    }

    default boolean closest(double target, double to, double then) {
        return Math.abs(to - target) < Math.abs(to - then);
    }

}
