package com.aiden.recipesearch.util;


import java.util.Locale;

public class StringUtils {
    public static String toTitleCase(String str){
        str = str.toLowerCase(Locale.getDefault());
        String[] strings = str.split(" ");
        for(int i = 0; i < strings.length; i++){
            String s = strings[i];
            String firstChar = s.substring(0,1);
            String rest = s.substring(1);
            firstChar = firstChar.toUpperCase();
            strings[i] = firstChar + rest;
        }

        StringBuilder output = new StringBuilder();
        for(String s : strings){
            output.append(s).append(" ");
        }
        return output.toString().trim();
    }
}
