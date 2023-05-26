package src.utils;

import java.util.Arrays;

public class StringParsers {
    public static int[] parseIntArray(String str) {
        return Arrays.stream(str.split(",")).map((el) -> Integer.parseInt(el.replaceAll("(\\[|\\])", "").trim()))
                .mapToInt((el) -> el).toArray();
    }
}
