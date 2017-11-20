package com.example.james.planificador.LogicaDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.SimpleTimeZone;

/**
 * Created by James on 07/09/2017.
 */

public class EventosDBHelper extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";


    /*#########################################    CREAR #############################*/

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + EventoContract.FeedEntry.TABLE_NAME + " (" +
                    EventoContract.FeedEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP +
                    EventoContract.FeedEntry.NAME_EVENT + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.DATE_SINCE + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.DATE_UNTIL + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.HOUR_SINCE + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.HOUR_UNTIL + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.LOCATION + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    EventoContract.FeedEntry.PARTICIPANT + TEXT_TYPE +  " )";
                    //EventoContract.FeedEntry.STATE + BOLEAN


    private static final String SQL_CREATE_TABLE_FOTOGRAFIA =
            "CREATE TABLE " + EventoContract.Fotografias.TABLE_NAME + " ("+
                    EventoContract.Fotografias._ID + " INTEGER PRIMARY KEY," +
                    EventoContract.Fotografias.NOMBRE + TEXT_TYPE +
                    EventoContract.Fotografias.FK_Sitio+ TEXT_TYPE + " )";

    private static final String SQL_CREATE_TABLE_SITIOSCREADOS =
            "CREATE TABLE " + EventoContract.SitiosCreados.TABLE_NAME + " ("+
                    EventoContract.SitiosCreados._ID + " INTEGER PRIMARY KEY," +
                    EventoContract.SitiosCreados.NOMBRE + TEXT_TYPE + COMMA_SEP +
                    EventoContract.SitiosCreados.LATI + TEXT_TYPE + COMMA_SEP +
                    EventoContract.SitiosCreados.LONGI + TEXT_TYPE + COMMA_SEP +
                    EventoContract.SitiosCreados.FK_DESCRIP_CATEGORIA + TEXT_TYPE + " )";

    private static final String SQL_CREATE_TABLE_CONTACTOS =
            "CREATE TABLE "+EventoContract.tablaContactos.TABLE_NAME + " ("+
                    EventoContract.tablaContactos._ID + " INTEGER PRIMARY KEY," +
                    EventoContract.tablaContactos.CONTACT_NAME + TEXT_TYPE + COMMA_SEP +
                    EventoContract.tablaContactos.NUMBER + TEXT_TYPE + " )";

    private static final String SQL_CREATE_TABLE_NAMES_FOLDER =
            "CREATE TABLE "+EventoContract.NombreCarpeta.TABLE_NAME + " ("+
                    EventoContract.NombreCarpeta._ID + " INTEGER PRIMARY KEY," +
                    EventoContract.NombreCarpeta.NAME_FOLDER + TEXT_TYPE + " )";

    private static final String SQL_CREATE_TABLE_CATEGORIAS =
            "CREATE TABLE "+EventoContract.tablaCategoria.TABLE_NAME + " ("+
                    EventoContract.tablaCategoria._ID + " INTEGER PRIMARY KEY," +
                    EventoContract.tablaCategoria.DESCRIP  + TEXT_TYPE + COMMA_SEP +
                    EventoContract.tablaCategoria.ICONO_COLOR + TEXT_TYPE + " )";


    /*#########################################    BORRAR #############################*/
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EventoContract.FeedEntry.TABLE_NAME;

    private static final String SQL_DELETE_TABLA_FOTOS =
            "DROP TABLE IF EXISTS " + EventoContract.Fotografias.TABLE_NAME;

    private static final String SQL_DELETE_TABLA_SITIOSCREADOS =
            "DROP TABLE IF EXISTS " + EventoContract.SitiosCreados.TABLE_NAME;

    private static final String SQL_DELETE_TABLA_CONTACTOS =
            "DROP TABLE IF EXISTS " + EventoContract.tablaContactos.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_NAMES_FOLDER =
            "DROP TABLE IF EXISTS " + EventoContract.NombreCarpeta.TABLE_NAME;

    private static final String SQL_DELETE_TABLE_CATEGORIAS =
            "DROP TABLE IF EXISTS " + EventoContract.tablaCategoria.TABLE_NAME;

    //Para acceder a la base de datos, crea una instancia de la subclase de SQLiteOpenHelper:
    //  EventosDBHelper mDbHelper = new EventosDBHelper(getContext());

    public EventosDBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_TABLE_FOTOGRAFIA);
        db.execSQL(SQL_CREATE_TABLE_SITIOSCREADOS);
        db.execSQL(SQL_CREATE_TABLE_CONTACTOS);
        db.execSQL(SQL_CREATE_TABLE_NAMES_FOLDER);
        db.execSQL(SQL_CREATE_TABLE_CATEGORIAS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_TABLA_FOTOS);
        db.execSQL(SQL_DELETE_TABLA_SITIOSCREADOS);
        db.execSQL(SQL_DELETE_TABLA_CONTACTOS);
        db.execSQL(SQL_DELETE_TABLE_NAMES_FOLDER);
        db.execSQL(SQL_DELETE_TABLE_CATEGORIAS);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
