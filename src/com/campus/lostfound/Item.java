package com.campus.lostfound;

import java.sql.Date;

/**
 * Item is a Plain Old Java Object (POJO) / Model class representing
 * a lost or found item record in the 'items' database table.
 */
public class Item {

    private int id;
    private String itemName;
    private String category;
    private String description;
    private String location;
    private String reportType;     // "LOST" or "FOUND"
    private String reportedBy;
    private String contactNumber;
    private Date reportedDate;
    private String status;         // "LOST", "FOUND", or "RETURNED"

    // Default constructor
    public Item() {
    }

    // Constructor without ID (used when reporting/creating a new item)
    public Item(String itemName, String category, String description, String location,
                String reportType, String reportedBy, String contactNumber, Date reportedDate, String status) {
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.reportType = reportType;
        this.reportedBy = reportedBy;
        this.contactNumber = contactNumber;
        this.reportedDate = reportedDate;
        this.status = status;
    }

    // Full constructor including ID (used when fetching records from DB)
    public Item(int id, String itemName, String category, String description, String location,
                String reportType, String reportedBy, String contactNumber, Date reportedDate, String status) {
        this.id = id;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.reportType = reportType;
        this.reportedBy = reportedBy;
        this.contactNumber = contactNumber;
        this.reportedDate = reportedDate;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Item [ID=" + id + ", Name=" + itemName + ", Category=" + category +
                ", Location=" + location + ", Type=" + reportType +
                ", Status=" + status + ", Reported By=" + reportedBy +
                ", Date=" + reportedDate + "]";
    }
}
