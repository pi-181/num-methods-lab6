package com.demkom58.nmlab6.regression;

import com.demkom58.nmlab6.MainController;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.DoubleUnaryOperator;

public class Result implements Comparable<Result> {
    private final String name;
    private final double result;
    private final double correlation;
    private final String character;
    private final double determination;
    private final DoubleUnaryOperator function;

    public Result(String name, double result, double correlation, double determination, DoubleUnaryOperator function) {
        this.name = name;
        this.result = result;
        this.correlation = correlation;
        double absCorr = Math.abs(correlation) ;
        if (absCorr <= 0.3)
            character = "практично відсутній";
        else if (absCorr > 0.3 && absCorr <= 0.5)
            character = "слабкий";
        else if (absCorr > 0.5 && absCorr <= 0.7)
            character = "помітний";
        else if (absCorr > 0.7 && absCorr <= 0.9)
            character = "сильний";
        else if (absCorr > 0.9 && absCorr <= 1)
            character = "дуже сильний";
        else
            character = "?";

        this.determination = determination;
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
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
                Double.compare(result1.determination, determination) == 0 &&
                Objects.equals(name, result1.name) &&
                Objects.equals(function, result1.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, result, correlation, determination, function);
    }

    @Override
    public String toString() {
                return "Результат:" + result + "\n" +
                "Індекс корреляції: " + correlation + " (" + character + " звязок x i y)" + "\n" +
                "Індекс детермінації: " + determination;
    }
}
