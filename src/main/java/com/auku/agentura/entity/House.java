package com.auku.agentura.entity;

import java.util.Objects;

public class House {

    private String address;
    private int agentId;
    private int ownerId;
    private double size;
    private double price;
    private int buildYear;

    public House() {

    }

    public House(String address, int agentId, int ownerId, double size, double price, int buildYear) {
        this.address = address;
        this.agentId = agentId;
        this.ownerId = ownerId;
        this.size = size;
        this.price = price;
        this.buildYear = buildYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(int buildYear) {
        this.buildYear = buildYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Objects.equals(address, house.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }

    @Override
    public String toString() {
        return "House{" +
                "address='" + address + '\'' +
                ", ownerId=" + ownerId +
                ", price=" + price +
                '}';
    }
}
