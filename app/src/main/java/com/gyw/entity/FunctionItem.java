package com.gyw.entity;

public class FunctionItem {
    private String id;
    private String functionItem;

    public FunctionItem() {
    }

    public FunctionItem(String id, String functionItem) {
        this.id = id;
        this.functionItem = functionItem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFunctionItem() {
        return functionItem;
    }

    public void setFunctionItem(String functionItem) {
        this.functionItem = functionItem;
    }

    @Override
    public String toString() {
        return "FunctionItem{" +
                "id='" + id + '\'' +
                ", functionItem='" + functionItem + '\'' +
                '}';
    }
}
