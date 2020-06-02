package com.demkom58.nmlab6.regression;

import com.demkom58.divine.math.PointD;
import com.demkom58.divine.util.Pair;
import com.demkom58.lab.visual.MatrixTable;

import java.util.function.DoubleUnaryOperator;
import java.util.function.ToDoubleFunction;

public interface Regression {

    Result calculate(double x, MatrixTable table, int n) throws Exception;

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
