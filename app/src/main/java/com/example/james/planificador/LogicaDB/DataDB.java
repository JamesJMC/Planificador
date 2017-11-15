package com.example.james.planificador.LogicaDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by James on 07/09/2017.
 */

public class DataDB
{


    //*********************************         INSERTAR, ELIMINAR Y OBTENER DATOS DE LOS EVENTOS       **************************************

    public static void InsertData(Context context, String evento, String fechai, String fechaf,
                                  String horai, String horaf, String ubicacion, String descrip, String parti)
    {//85721039
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(EventoContract.FeedEntry.NAME_EVENT, evento);
        values.put(EventoContract.FeedEntry.DATE_SINCE, fechai);
        values.put(EventoContract.FeedEntry.DATE_UNTIL, fechaf);
        values.put(EventoContract.FeedEntry.HOUR_SINCE, horai);
        values.put(EventoContract.FeedEntry.HOUR_UNTIL, horaf);
        values.put(EventoContract.FeedEntry.LOCATION, ubicacion);
        values.put(EventoContract.FeedEntry.DESCRIPTION, descrip);
        values.put(EventoContract.FeedEntry.DESCRIPTION, parti);
        //values.put(EventoContract.FeedEntry.PARTICIPANT, estado);
        // Insert the new row, returning the primary key value of the new row
        db.insert(EventoContract.FeedEntry.TABLE_NAME, null, values);
    }

    public static ArrayList<ArrayList<String>> getDataEvento(Context context)//
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String query = "SELECT * FROM " + EventoContract.FeedEntry.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<ArrayList<String>> listaEventos = new ArrayList<ArrayList<String>>();
        int size = cursor.getCount();
        if(cursor.moveToFirst())
        {
            do {
                ArrayList<String> evento = new ArrayList<String>();
                evento.add(cursor.getString(1));
                /*evento.add(cursor.getString(1));
                evento.add(cursor.getString(2));
                evento.add(cursor.getString(3));
                evento.add(cursor.getString(4));
                evento.add(cursor.getString(5));
                evento.add(cursor.getString(6));
                evento.add(cursor.getString(7));*/
                listaEventos.add(evento);
            }while (cursor.moveToNext());
            return listaEventos;
        }
        return null;
    }

    public static ArrayList<String> getDataEventoFila(Context context, String filtro)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] args = new String[] {filtro};
        Cursor cursor = db.rawQuery(" SELECT * FROM "+EventoContract.FeedEntry.TABLE_NAME+" WHERE "+
                EventoContract.FeedEntry.NAME_EVENT+"="+"'"+filtro+"'", null);
        ArrayList<String> listaEventos = new ArrayList<String>();
        int size = cursor.getCount();
        if(cursor.moveToFirst())
        {
            do {
                listaEventos.add(cursor.getString(1));
                listaEventos.add(cursor.getString(2));
                listaEventos.add(cursor.getString(3));
                listaEventos.add(cursor.getString(4));
                listaEventos.add(cursor.getString(5));
                listaEventos.add(cursor.getString(6));
                listaEventos.add(cursor.getString(7));
                listaEventos.add(cursor.getString(8));
            }while (cursor.moveToNext());
            return listaEventos;
        }
        return null;
    }


    //******************************************        AGREGAR, OBTENER Y ELIMINAR CONTACTO ****************************************

    public static void agregarContacto(Context context, String nombre, String numero)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //agregando los datos a la base de datos
        ContentValues values = new ContentValues();
        values.put(EventoContract.tablaContactos.CONTACT_NAME,nombre);
        values.put(EventoContract.tablaContactos.NUMBER,numero);
        db.insert(EventoContract.tablaContactos.TABLE_NAME, null, values);
    }

    public static ArrayList<ArrayList<String>> getContactos(Context context)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT "+EventoContract.tablaContactos.CONTACT_NAME+ "," + EventoContract.tablaContactos.NUMBER + " FROM " + EventoContract.tablaContactos.TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        ArrayList<ArrayList<String>> listaContactos = new ArrayList<ArrayList<String>>();
        int size = cursor.getCount();
        if(cursor.moveToFirst())
        {
            do {
                ArrayList<String> contacto = new ArrayList<String>();
                contacto.add(cursor.getString(0));
                contacto.add(cursor.getString(1));
                listaContactos.add(contacto);
            }while (cursor.moveToNext());
            return listaContactos;
        }
        return null;
    }

    public static String getDataContactoFila(Context context, String filtro)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT "+EventoContract.tablaContactos.NUMBER+" FROM " +
                EventoContract.tablaContactos.TABLE_NAME+ " WHERE "+EventoContract.tablaContactos.CONTACT_NAME+" = "+"'"+filtro+"'";
        Cursor cursor = db.rawQuery(query,null);
        String numContacto;
        int size = cursor.getCount();
        if(cursor.moveToFirst())
        {
            do {
                numContacto = cursor.getString(0);
            }while (cursor.moveToNext());
            return numContacto;
        }
        return null;
    }

    public static void eliminarContacto(Context context, String filtro)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        //Eliminando el contacto
        String selection = EventoContract.tablaContactos.CONTACT_NAME + " LIKE ?";
        String[] selectionArgs = {filtro};
        db.delete(EventoContract.tablaContactos.TABLE_NAME, selection, selectionArgs);
    }

    //******************************************************************************************************************************





    public static void DeleteData(Context context, String filtro)
    {
        EventosDBHelper mDbHelper = new EventosDBHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        // Define 'where' part of query.
        String selection = EventoContract.FeedEntry.NAME_EVENT + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { filtro };
        // Issue SQL statement.
        db.delete(EventoContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
    }
}
