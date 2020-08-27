package com.example.mywisp.entities;

import java.io.Serializable;

public class Instalation implements Serializable {
    //Attributes
    private String Id;
    private String date;
    private String materials;
    private String place;
    private String idCustomer;
    private String idService;

    //Constructor method
    public Instalation(String pId, String pdate, String pmaterials, String pplace, String pidCustomer, String pidService)
    {
        this.Id = pId;
        this.date = pdate;
        this.materials = pmaterials;
        this.place = pplace;
        this.idCustomer = pidCustomer;
        this.idService = pidService;
    }

    //Gets and sets methods

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

}
