package com.juanjiga.vescla;

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
}

