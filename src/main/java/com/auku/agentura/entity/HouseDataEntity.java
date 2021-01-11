package com.auku.agentura.entity;

public class HouseDataEntity {
    private String address;
    private double price;
    private String ownerName;
    private String ownerSurname;
    private String agentSurname;

    public HouseDataEntity() {

    }

    public HouseDataEntity(String address, double price, String ownerName, String ownerSurname, String agentSurname) {
        this.address = address;
        this.price = price;
        this.ownerName = ownerName;
        this.ownerSurname = ownerSurname;
        this.agentSurname = agentSurname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerSurname() {
        return ownerSurname;
    }

    public void setOwnerSurname(String ownerSurname) {
        this.ownerSurname = ownerSurname;
    }

    public String getAgentSurname() {
        return agentSurname;
    }

    public void setAgentSurname(String agentSurname) {
        this.agentSurname = agentSurname;
    }
}
