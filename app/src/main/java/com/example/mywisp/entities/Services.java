package com.example.mywisp.entities;

import java.io.Serializable;

public class Services implements Serializable {
    //Attributes
    private String Id;
    private String name;
    private String price;
    private String description;

    //Construct method
    public Services(String pId, String pname, String pprice, String pdescription)
    {
        this.Id = pId;
        this.name = pname;
        this.price = pprice;
        this.description = pdescription;
    }

    //Gets and sets methods
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
