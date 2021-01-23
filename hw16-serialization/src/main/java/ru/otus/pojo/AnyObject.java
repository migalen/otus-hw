package ru.otus.pojo;

import java.util.List;

public class AnyObject {
    private String string;
    private int pInt;
    private Integer oInteger;
    private boolean pBoolean;
    private Boolean oBoolean;
    private List<String> listObject;
    private Object[] arrayObject;
    private Float oFloat;

    public AnyObject(String string, int pInt, Integer oInteger, boolean pBoolean, Boolean oBoolean, List<String> listObject, Object[] arrayObject, Float oFloat) {
        this.string = string;
        this.pInt = pInt;
        this.oInteger = oInteger;
        this.pBoolean = pBoolean;
        this.oBoolean = oBoolean;
        this.listObject = listObject;
        this.arrayObject = arrayObject;
        this.oFloat = oFloat;
    }
}
