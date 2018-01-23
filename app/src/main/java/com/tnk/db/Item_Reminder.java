package com.tnk.db;

/**
 * Created by Tom on 2017-12-29.
 */

public class Item_Reminder {

    // fields
    private int rem_id;
    private String rem_title;
    private String rem_body;
    private String rem_date;
    private String rem_time;

    // constructors
    public Item_Reminder() {
    }

    public Item_Reminder(int id,
                         String title,
                         String body,
                         String date,
                         String time
                    ) {
        this.rem_id = id;
        this.rem_title = title;
        this.rem_body = body;
        this.rem_date = date;
        this.rem_time = time;
    }

    // #SET/#GET      Properties of the Class        HERE
    public void setID(int id) {this.rem_id = id;}
    public int getID() {return this.rem_id;}
    public void setRem_title(String type) {this.rem_title = type;}
    public String getRem_title() {return this.rem_title;}
    public void setRem_body(String name) {this.rem_body = name;}
    public String getRem_body() {return this.rem_body;}
    public void setRem_date(String brand) {this.rem_date = brand;}
    public String getRem_date() {return this.rem_date;}
    public void setRem_time(String quantity) {this.rem_time = quantity;}
    public String getRem_time() {return this.rem_time;}

}