package com.juanjiga.vescla;

// Clase "POJO" Plan O.... Java Object

public class Clave {
    private int id;
    private String nombre;
    private String usuario;
    private String password;
    private int imagen;

    public Clave(int id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

