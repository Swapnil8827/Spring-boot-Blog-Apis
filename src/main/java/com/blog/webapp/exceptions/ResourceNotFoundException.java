package com.blog.webapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    private String resourceName;
    private String fieldName;
    private String fieldType;
    private Integer fieldId;

    public ResourceNotFoundException( String resourceName, String fieldName, Integer fieldId) {
        super(resourceName + " not found with "+fieldName+" : "+fieldId);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldId = fieldId;
    }

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldType) {

        super(resourceName + " not found with "+fieldName+" : "+fieldType);
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldType = fieldType;

    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
