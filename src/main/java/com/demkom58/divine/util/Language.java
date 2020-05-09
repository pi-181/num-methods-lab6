package com.demkom58.divine.util;

public final class Language {
    public static String plural(int n, String one, String two, String five) {
        n = Math.abs(n);

        n %= 100;
        if (n >= 5 && n <= 20)
            return five;

        n %= 10;
        if (n == 1)
            return one;

        if (n >= 2 && n <= 4)
            return two;

        return five;
    }
}
