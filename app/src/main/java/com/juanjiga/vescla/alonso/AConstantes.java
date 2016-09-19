package com.juanjiga.vescla.alonso;

// basado en Codigo Alonso
// para funcionar junto a la clase ADataBaseClaves

public class AConstantes {

    //Nombre de la base de datos y versión
    public static final String DB_NAME = "vescladb.db";
    public static final int DB_VERSION = 1;

    //Nombre de la tabla, "claves" y nombres de los campos (columnas)
    public static final String T_CLAVES = "claves";

    public static final String C_ID = "_id";
    public static final String C_NOMBRE = "nombre";
    public static final String C_USUARIO = "usuario";
    public static final String C_PASSWORD = "password";

    //Sentencia SQL para la creación de la tabla
    public static final String DATABASE_CREATE =
            "CREATE TABLE  " + T_CLAVES + "(" +
                    C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    C_NOMBRE + " TEXT NOT NULL," +
                    C_USUARIO + " TEXT," +
                    C_PASSWORD + " TEXT);" ;

    //Otras tablas si las hubiera...

}
