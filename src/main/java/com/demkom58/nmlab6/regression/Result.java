package com.demkom58.nmlab6.regression;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Result implements Comparable<Result> {
    private final double result;
    private final double correlation;
    private final double determination;
    private final DoubleUnaryOperator function;

    public Result(double result, double correlation, double determination, DoubleUnaryOperator function) {
        this.result = result;
        this.correlation = correlation;
        this.determination = determination;
        this.function = function;
    }

    public double getResult() {
        return result;
    }

    public double getCorrelation() {
        return correlation;
    }

    public double getDetermination() {
        return determination;
    }

    public DoubleUnaryOperator getFunction() {
        return function;
    }

    @Override
    public int compareTo(@NotNull Result o) {
        return Double.compare(determination, o.determination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result1 = (Result) o;
        return Double.compare(result1.result, result) == 0 &&
                Double.compare(result1.correlation, correlation) == 0 &&
                Double.compare(result1.determination, determination) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, correlation, determination);
    }

    @Override
    public String toString() {
        return "Результат: " + result + "\n" +
                "Індекс корреляції: " + correlation + "\n" +
                "Індекс детермінації: " + determination;
    }
}
