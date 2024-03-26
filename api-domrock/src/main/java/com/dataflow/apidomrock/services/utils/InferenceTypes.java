package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.entities.enums.Type;

public class InferenceTypes {
    public static Type inferTypeField(String[] fieldValues){
        Type typeFinal = Type.TEXTO;
        for (String input : fieldValues){
            if (isInteger(input)) {
                typeFinal =  Type.INTEIRO;
            } else if (isDouble(input)) {
                typeFinal =  Type.DECIMAL;
            } else if (isBoolean(input)) {
                typeFinal =  Type.BOOLEANO;
            } else if (DateInferenceType.isDate(input)) {
                typeFinal =  Type.DATA;
            } else {
                typeFinal =  Type.TEXTO;
            }
        }
        return typeFinal;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static boolean isBoolean(String input) {
        return "true".equalsIgnoreCase(input) || "false".equalsIgnoreCase(input);
    }




}
