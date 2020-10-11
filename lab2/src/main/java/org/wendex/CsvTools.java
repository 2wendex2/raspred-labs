package org.wendex;

public class CsvTools {
    private String parseRow(String s, int index) {
        StringBuilder sb = new StringBuilder();
        boolean inMark = false;
        if (s.charAt(index) == '\"') {
            inMark = true;
            index++;
        }

        for (int i = index; i < s.length(); i++) {
            if (s.charAt(i) == ',' && !inMark) {
                return sb.toString();
            } else if (s.charAt(i) == '\"') {
                inMark = false;
            } else {
                sb.append(s.charAt(i));
            }
        }
    }

    public static String[] read(String s) {
        String[] arr = new String[23];
        int n = 0;
        arr[0] = new String();
        bool begi
        for (int i = 0; i < s.length(); i++) {
            if
        }
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
