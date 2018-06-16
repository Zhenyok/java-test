package com.itnetsoft.hibernate.entities;

import com.itnetsoft.enums.FieldAccess;
import com.itnetsoft.enums.FieldType;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "settings")
public class SettingsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lob
    @Column(name="field_name", columnDefinition = "text",  unique = true,nullable = false)
    private String fieldName;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "value", nullable = false, columnDefinition = "longtext")
    private String value;

    @Column(name="field_type", nullable=false)
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name="field_access", nullable=false)
    @Enumerated(EnumType.STRING)
    private FieldAccess fieldAccess;

    public FieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public void setFieldAccess(FieldAccess fieldAccess) {
        this.fieldAccess = fieldAccess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SettingsEntity that = (SettingsEntity) o;

        if (id != that.id) return false;
        if (!fieldName.equals(that.fieldName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + fieldName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SettingsEntity{" +
                "id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", value='" + value + '\'' +
                ", fieldType=" + fieldType +
                ", fieldAccess=" + fieldAccess +
                '}';
    }
}
