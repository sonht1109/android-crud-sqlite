package com.example.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id, img;
    private String category, title, date;
    private double price;

    public Item() {
    }

    public Item(String category, String title, String date, double price, int img) {
        this.category = category;
        this.title = title;
        this.date = date;
        this.price = price;
        this.img = img;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Item(int id, String category, String title, String date, double price, int img) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.date = date;
        this.price = price;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }
}
