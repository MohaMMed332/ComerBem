package com.mohammed.tcmc.ComerBem.edit_activities;


import android.support.design.widget.TextInputEditText;

import com.mohammed.tcmc.ComerBem.model.Comment;

import java.util.List;

public class Product {
    private String id;
    private String imgurl;
    private String nome_da_receita;
    private String ingredientes;
    private String ingredientes2;
    private String ingredientes3;
    private String modo;
    private String modo2;
    private String modo3;
    private String categoria;
    private List<Comment> commentList;

    public Product( String imgurl, String nome_da_receita, String ingredientes, String ingredientes2,
                    String ingredientes3,String modo,String modo2,String modo3 ) {
        this.nome_da_receita = nome_da_receita;
        this.ingredientes = ingredientes;
        this.ingredientes2 = ingredientes2;
        this.ingredientes3 = ingredientes3;
        this.modo = modo;
        this.modo2 = modo2;
        this.modo3 = modo3;

        this.imgurl = imgurl; }


    public Product() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getImgurl() { return imgurl; }
    public void setImgurl(String imgurl) { this.imgurl = imgurl; }




    public String getNome_da_Receita() { return nome_da_receita; }
    public void setNome_da_Receita(String name) { this.nome_da_receita = name; }


    public String getIngredientes() { return ingredientes; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }



    public String getIngredientes2() { return ingredientes2; }
    public void setIngredientes2(String ingredientes2) { this.ingredientes2 = ingredientes2; }



    public String getIngredientes3() { return ingredientes3; }
    public void setIngredientes3(String ingredientes3) { this.ingredientes3 = ingredientes3; }






    public String getModo() { return modo; }
    public void setModo(String modo) { this.modo = modo; }






    public String getModo2() { return modo2; }
    public void setModo2(String modo2) { this.modo2 = modo2; }


    public String getModo3() { return modo3; }
    public void setModo3(String modo3) { this.modo3 = modo3; }





    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List <Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List <Comment> commentList) {
        this.commentList = commentList;
    }
}
