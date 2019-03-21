package com.mohammed.tcmc.ComerBem.model;

import com.mohammed.tcmc.ComerBem.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.mohammed.tcmc.ComerBem.edit_activities.Product;

import java.util.List;


public class Usuario {
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String imgurl;
    private String telefone;

    private List<Product> productList;

    private Usuario(String nome,String telefone,String idUsuario ,String email ,String imgurl) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.idUsuario = idUsuario;
        this.imgurl = imgurl;
    }

    public Usuario() {



    }

    public void salvar() {
        DatabaseReference firebase = ConfiguracaoFirebase.getFirebaseDatabase();
        firebase.child("usuarios")
                .child(this.idUsuario)
                .setValue(this);
    }

    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
