package com.mohammed.tcmc.ComerBem.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

    private String key;
    private String content;
    private String nome = "nome";
    private String userEmail = "unknown";
    private String imgurl = "imgurl";
    private String image = "image";

    private Long timestamp;

    public String toString() { return "{key:" + this.getKey() + ", content:"
            + this.getContent() + ", nome:" + this.getUserName()
            + ", email:" + this.getUserEmail() + ", imgurl:" + this.getImgurl()+ ", image:" + this.getImage() ; }

    public Message(){ }

    public Message(String content, String nome, String userEmail,String userImgurl,String image) {
        this.content = content;
        this.nome   = (nome == null) ? "unknown" : nome;
        this.userEmail = (userEmail == null) ? "unknown" : userEmail;
        this.imgurl = (userImgurl == null) ? "unknown" : userImgurl;
        this.image = (image == null) ? "unknown" : image;
        this.timestamp = System.currentTimeMillis(); }


    public String getDate() {SimpleDateFormat formatter = new SimpleDateFormat("dd/MM HH:mm");
    return formatter.format(new Date(timestamp)); }

    public String getKey() { return this.key; }
    public void setKey(String key) { this.key = key; }


    public String getContent() { return content; }
    public void setContent(String content) { this.content = (content == null) ? "empty message" : content; }

    public String getUserName() { return nome; }
    public void setUserName(String nome) {this.nome = (nome == null) ? "nome" : nome; }


    public String getUserEmail() { return this.userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = (userEmail == null) ? "unknown" : userEmail; }


    public Long getTimestamp() {return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = (timestamp == null)
            ? System.currentTimeMillis() : timestamp;}


    public String getImgurl() { return imgurl; }
    public void setImgurl(String imgurl) { this.imgurl = (imgurl == null) ? "imgurl" : imgurl; }


    public String getImage() { return image; }
    public void setImage(String image) { this.image = (image == null) ? "image" : image; }

}

