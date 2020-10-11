package org.wendex;

public class CsvTools {
    public static String[] read(String s) {
        String[] arr = s.replaceAll("\"\"", "\"").split(",");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].charAt(0) == '\"') {
                arr[i] = arr[i].substring(1, arr[i].length() - 1);
            }
        }
        return arr;
    }

    public static StringBuilder writeFirst(String s) {
        s.replaceAll("\"", "\"\"");
        StringBuilder sb = new StringBuilder("\"");
        sb.append(s);
        sb.append("\"");
        return sb;
    }

    public static void writeNext(StringBuilder sb, String s) {
        s.replaceAll("\"", "\"\"");
        sb.append(",");
        sb.append(s);
        sb.append("\"");
        return sb;
    }
}
