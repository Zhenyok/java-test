package com.itnetsoft.dto;


import com.itnetsoft.enums.FieldAccess;
import com.itnetsoft.enums.FieldType;

import java.io.Serializable;

public class SettingsDTO implements Serializable{
    public SettingsDTO(){}
    private int id;
    private String fieldName;
    private FieldType fieldType;
    private String value;
    private FieldAccess fieldAccess;

    public FieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public void setFieldAccess(FieldAccess fieldAccess) {
        this.fieldAccess = fieldAccess;
    }

    public String getFieldName() {
        return fieldName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "SettingsDTO:{id:"+id+",\n" +
                "fieldName:"+fieldName+",\n" +
                "fieldType:"+fieldType+",\n" +
                "fieldAccess:"+fieldAccess+",\n" +
                "value:"+value+"\n};";
    }
}
