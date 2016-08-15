package com.juanjiga.vescla;

// Clase "POJO" Plain Old Java Object

public class Clave {
    private int id;
    private String nombre;
    private String usuario;
    private String password;

    public Clave(String nombre, String usuario, String password) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
    }

    public Clave(int id, String nombre, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

