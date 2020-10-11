package org.wendex;

public class CsvTools {
    public static String[] read(String s) {
        String[] arr = s.replaceAll("\"\"", "\"").split(",");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].charAt(0) == '\"') {
                arr[i] = arr[i].substring(1, arr[i].length() - 1);
            }
        }
    }

    public static write
}
