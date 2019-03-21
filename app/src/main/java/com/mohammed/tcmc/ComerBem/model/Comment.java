package com.mohammed.tcmc.ComerBem.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Comment {

    private String key;
    private String idProduct;
    private String content;
    private String nome = "nome";
    private String userEmail = "unknown";
    private String userimg = "imgurl";

    private Long timestamp;

    public String toString() {
        return "{key:" + this.getKey() + ", content:" + this.getContent() + ", nome:" + this.getUserName() + ", email:" + this.getUserEmail() + ", idProduct:"
                + this.getIdProduct() + ", userimage:" + this.getUserimg();
    }

    public Comment(){
    }

    public Comment(String content, String nome, String userEmail, String idProduct, String imgurl) {
        this.content = content;
        this.nome   = (nome == null) ? "unknown" : nome;
        this.userEmail = (userEmail == null) ? "unknown" : userEmail;
        this.idProduct = (idProduct == null) ? "unknown" : idProduct;
        this.timestamp = System.currentTimeMillis();
        this.userimg   = (imgurl == null) ? "unknown" : imgurl;

    }

    public String getDate() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
        return formatter.format(new Date(timestamp));
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = (content == null) ? "empty message" : content;
    }

    public String getUserName() {
        return nome;
    }

    public void setUserName(String nome) {
        this.nome = (nome == null) ? "nome" : nome;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = (userEmail == null) ? "unknown" : userEmail;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = (timestamp == null) ? System.currentTimeMillis() : timestamp;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUserimg() { return userimg; }
    public void setUserimg(String userimg) { this.userimg = userimg; }
}

