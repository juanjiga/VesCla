package com.juanjiga.vescla;

public class Constantes {

    //Nombre de la base de datos y versión
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    //Tabla Claves y campos
    public static final String TABLA_CLAVES = "claves";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String USUARIO = "usuario";
    public static final String PASSWORD = "password";

    public static final String TABLA_CLIENTES_SQL =
            "CREATE TABLE  " + TABLA_CLAVES + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    NOMBRE + " TEXT NOT NULL," +
                    USUARIO   + " TEXT," +
                    PASSWORD   + " TEXT);" ;





}
