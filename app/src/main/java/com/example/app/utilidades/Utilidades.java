package com.example.app.utilidades;

public class Utilidades {
    public static final String  TABLA_USUARIO ="usuario";
    public static final String  CAMPO_ID ="id";
    public static final String  CAMPO_NOMBRE ="nombre";
    public static final String  CAMPO_EMAIL ="email";
    public static final String  CAMPO_TELEFONO ="telefono";
    public static final String  CAMPO_EDAD ="edad";
    public static final String  CAMPO_SEXO ="sexo";

   public static final String CREAR_TABLA_USUARIO="CREATE TABLE "+TABLA_USUARIO+" ("+CAMPO_ID+"id INTEGER, "+
           CAMPO_NOMBRE+"nombre TEXT," +
           ""+CAMPO_EMAIL+" email TEXT, " +" "+CAMPO_TELEFONO+
           "telefono TEXT,"+CAMPO_EDAD+"edad TEXT,"+CAMPO_SEXO+" sexo TEXT)";
}
