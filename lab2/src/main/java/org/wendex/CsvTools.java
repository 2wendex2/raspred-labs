package org.wendex;

public class CsvTools {
    static final int MAX_ROWS = 23;

    private static int parseRow(String s, int i, StringBuilder sb) {
        boolean inMark = false;
        if (s.charAt(i) == '\"') {
            inMark = true;
            i++;
        }

        for (; i < s.length(); i++) {
            if (s.charAt(i) == ',' && !inMark) {
                return i + 1;
            } else if (s.charAt(i) == '\"') {
                inMark = false;
            } else {
                sb.append(s.charAt(i));
            }
        }
        return i;
    }

    public static String[] read(String s) {
        String[] arr = new String[MAX_ROWS];
        int n = 0;

        int i = 0;
        for (int k = 0; i < s.length(); k++) {
            StringBuilder sb = new StringBuilder();
            i = parseRow(s, i, sb);
            arr[k] = sb.toString();
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
    }
}
