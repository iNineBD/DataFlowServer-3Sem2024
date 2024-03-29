package com.dataflow.apidomrock.services.utils;

import com.dataflow.apidomrock.entities.enums.TypeEnum;

public class InferenceTypes {
    public static TypeEnum inferTypeField(String[] fieldValues){
        TypeEnum typeEnumFinal = TypeEnum.TEXTO;
        for (String input : fieldValues){
            if (isInteger(input)) {
                typeEnumFinal =  TypeEnum.INTEIRO;
            } else if (isDouble(input)) {
                typeEnumFinal =  TypeEnum.DECIMAL;
            } else if (isBoolean(input)) {
                typeEnumFinal =  TypeEnum.BOOLEANO;
            } else if (DateInferenceType.isDate(input)) {
                typeEnumFinal =  TypeEnum.DATA;
            } else {
                typeEnumFinal =  TypeEnum.TEXTO;
            }
        }
        return typeEnumFinal;
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
