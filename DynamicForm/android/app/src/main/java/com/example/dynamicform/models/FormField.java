package com.example.dynamicform.models;

public class FormField {
    public static final String TYPE_TEXT = "text";
    public static final String TYPE_NUMBER = "number";
    public static final String TYPE_DATE = "date";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_GPS = "gps";
    public static final String TYPE_EMAIL = "email";
    public static final String TYPE_PHONE = "phone";

    private String type;
    private String label;
    private boolean required;
    private String value;
    private String imagePath;
    private double latitude;
    private double longitude;

    public FormField(String type, String label, boolean required) {
        this.type = type;
        this.label = label;
        this.required = required;
        this.value = "";
    }

    // Getters and setters
    public String getType() { return type; }
    public String getLabel() { return label; }
    public boolean isRequired() { return required; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}