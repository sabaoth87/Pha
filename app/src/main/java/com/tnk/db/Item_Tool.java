package com.tnk.db;

/**
 * Created by Tom on 2017-12-29.
 */

public class Item_Tool {

    // fields
    private int toolID;
    private String toolType;
    private String toolName;
    private String toolBrand;
    private String toolQuantity;
    private String toolQuality;
    private String toolLocation;
    private String toolNote;
    private String toolLink;
    private String toolPic;
    private String toolSize;
    private String toolUses;
    private String toolAmmo;
    private String toolCat;
    private String toolStatus;

    // constructors
    public Item_Tool() {
    }

    public Item_Tool(int id,
                     String toolType,
                     String toolName,
                     String toolBrand,
                     String toolQuantity,
                     String toolQuality,
                     String toolLocation,
                     String toolNote,
                     String toolLink,
                     String toolPic,
                     String toolSize,
                     String toolUses,
                     String toolAmmo,
                     String toolCat,
                     String toolStatus
                    ) {
        this.toolID = id;
        this.toolType = toolType;
        this.toolName = toolName;
        this.toolBrand = toolBrand;
        this.toolQuantity = toolQuantity;
        this.toolQuality = toolQuality;
        this.toolLocation = toolLocation;
        this.toolNote = toolNote;
        this.toolLink = toolLink;
        this.toolPic = toolPic;
        this.toolSize = toolSize;
        this.toolUses = toolUses;
        this.toolAmmo = toolAmmo;
        this.toolCat = toolCat;
        this.toolStatus = toolStatus;
    }

    // #SET/#GET      Properties of the Class        HERE
    public void setID(int id) {this.toolID = id;}
    public int getID() {return this.toolID;}
    public void setToolType(String type) {this.toolName = type;}
    public String getToolType() {return this.toolType;}
    public void setToolName(String name) {this.toolName = name;}
    public String getToolName() {return this.toolName;}
    public void setToolBrand(String brand) {this.toolBrand = brand;}
    public String getToolBrand() {return this.toolBrand;}
    public void setToolQuantity(String quantity) {this.toolQuantity = quantity;}
    public String getToolQuantity() {return this.toolQuantity;}
    public void setToolQuality(String quality) {this.toolQuality = quality;}
    public String getToolQuality() {return this.toolQuality;}
    public void setToolLocation(String location) {this.toolLocation = location;}
    public String getToolLocation() {return this.toolLocation;}
    public void setToolNote(String note) {this.toolNote = note;}
    public String getToolNote() {return this.toolNote;}
    public void setToolLink(String link) {this.toolLink = link;}
    public String getToolLink() {return this.toolLink;}
    public void setToolPic(String pic) {this.toolPic = pic;}
    public String getToolPic() {return this.toolPic;}
    public void setToolSize(String size) {this.toolSize = size;}
    public String getToolSize() {return this.toolSize;}
    public void setToolUses(String uses) {this.toolUses = uses;}
    public String getToolUses() {return this.toolUses;}
    public void setToolAmmo(String ammo) {this.toolAmmo = ammo;}
    public String getToolAmmo() {return this.toolAmmo;}
    public void setToolCat(String category) {this.toolCat = category;}
    public String getToolCat() {return this.toolCat;}
    public void setToolStatus(String status) {this.toolStatus = status;}
    public String getToolStatus() {return this.toolStatus;}

}