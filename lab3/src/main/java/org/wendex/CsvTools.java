package org.wendex;

public class CsvTools {
    static final int MAX_ROWS = 23;
    static final char QUOTE_CHAR = '"';
    static final String QUOTE_STR = "\"";
    static final char COMMA_CHAR = ',';
    static final String COMMA_STR = ",";

    private static int parseCell(String s, int i, StringBuilder sb) {
        boolean inMark = false;
        if (s.charAt(i) == QUOTE_CHAR) {
            inMark = true;
            i++;
        }

        for (; i < s.length(); i++) {
            if (s.charAt(i) == COMMA_CHAR && !inMark) {
                return i + 1;
            } else if (s.charAt(i) == QUOTE_CHAR) {
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
            i = parseCell(s, i, sb);
            arr[k] = sb.toString();
        }

        return arr;
    }
}
