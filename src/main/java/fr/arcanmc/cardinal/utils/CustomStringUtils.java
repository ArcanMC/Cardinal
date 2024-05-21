package fr.arcanmc.cardinal.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomStringUtils {

    public static boolean arrayContains(String compare, String[] args, boolean ignoreCase) {
        return ignoreCase ? Arrays.stream(args).anyMatch(each -> each.equalsIgnoreCase(compare)) : Arrays.asList(args).contains(compare);
    }

    public static boolean arrayContains(String compare, String[] args) {
        return arrayContains(compare, args, true);
    }

    public static String[] splitStringIntoWords(String str) {
        List<String> words = splitIntoWords(str);
        return words.toArray(new String[0]);
    }

    public static int getIndexOfWordAtPosition(String str, int position) {
        List<String> words = splitIntoWords(str);
        return position < words.size() ? words.get(position).length() : -1;
    }

    public static String[] splitStringToArgs(String str) {
        List<String> tokens = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();

        boolean insideQuote = false;

        for (char c : str.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ' ' && !insideQuote) {
                if (!sb.isEmpty()) {
                    tokens.add(sb.toString());
                }
                sb.delete(0, sb.length());
            } else {
                sb.append(c);
            }
        }
        tokens.add(sb.toString());

        return tokens.toArray(new String[0]);
    }

    private static List<String> splitIntoWords(String str) {
        List<String> words = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean insideQuote = false;

        for (char c : str.toCharArray()) {
            if (c == '"') {
                insideQuote = !insideQuote;
            } else if (c == ' ' && !insideQuote) {
                if (!sb.isEmpty()) {
                    words.add(sb.toString());
                    sb.delete(0, sb.length());
                }
            } else {
                sb.append(c);
            }
        }

        if (!sb.isEmpty()) {
            words.add(sb.toString());
        }
        return words;
    }
}
