package com.mohammed.tcmc.ComerBem.model;

import java.io.Serializable;



public class FoodItem implements Serializable {
    private String nome_da_receita,category,imgurl, description,fid;
    private double ingredientes;
    private int modo = 0, count;


    public FoodItem(String fid, String nome_da_receita, String category, double price, String imgurl, String description, int count) {
        this.nome_da_receita = nome_da_receita;
        this.category = category;
        this.ingredientes = price;
        this.imgurl = imgurl;
        this.description = description;
        this.fid = fid;
        this.count = count;
    }

    public FoodItem(String fid, String nome_da_receita, String category, int modo){
        this.nome_da_receita = nome_da_receita;
        this.fid = fid;
        this.category = category;
        this.modo = modo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public String getNome_da_receita() {
        return nome_da_receita;
    }

    public void setNome_da_receita(String nome_da_receita) {
        this.nome_da_receita = nome_da_receita;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(double price) {
        this.ingredientes = price;
    }
}
