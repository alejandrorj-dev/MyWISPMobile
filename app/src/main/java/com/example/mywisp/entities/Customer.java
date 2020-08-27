package com.example.mywisp.entities;

import java.io.Serializable;

public class Customer implements Serializable {

    //Attributes
    private Integer id;
    private String fullname;
    private String municipality;
    private String department;
    private String address;
    private String phone;
    private String email;
    private String status;
    private String remarks;

    //Constructor method
    public Customer(Integer pid, String pfullname, String pmunicipality, String pdepartment, String paddress, String pphone, String pemail, String pstatus, String premarks)
    {
        this.id = pid;
        this.fullname = pfullname;
        this.municipality = pmunicipality;
        this.department = pdepartment;
        this.address = paddress;
        this.phone = pphone;
        this.email = pemail;
        this.status = pstatus;
        this.remarks = premarks;
    }

    //Gets and sets methods
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getFullname()
    {
        return fullname;
    }

    public void setFullname(String fullname)
    {
        this.fullname = fullname;
    }

    public String getMunicipality()
    {
        return municipality;
    }

    public void setMunicipality(String municipality)
    {
        this.municipality = municipality;
    }

    public String getDepartment()
    {
        return department;
    }

    public void setDepartment(String department)
    {
        this.department = department;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }



}
