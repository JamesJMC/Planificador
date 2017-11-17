package com.example.james.planificador.LogicaDB;

import android.provider.BaseColumns;

/**
 * Created by James on 07/09/2017.
 */

public class EventoContract
{

    private EventoContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns
    {
        //nombre de la tabla
        public static final String TABLE_NAME = "Eventos";

        //nombre de cada una de las columnas de la tabla
        public static final String NAME_EVENT = "Evento";
        public static final String DATE_SINCE = "FechaInicial";
        public static final String DATE_UNTIL = "FechaFinal";
        public static final String HOUR_SINCE = "HoraInicial";
        public static final String HOUR_UNTIL = "HoraFinal";
        public static final String LOCATION = "Ubicacion";
        public static final String DESCRIPTION = "Descripcion";
        public static final String PARTICIPANT = "Participante";
        //public static final String STATE = "Estado";

        //Por definir
        //privacidad
        //añadir recordatorio
        //public static final String REPETITION = "";



    }

    public static class tablaContactos implements BaseColumns
    {
        public static final String TABLE_NAME = "TablaContactos";
        public static final String CONTACT_NAME = "Nombre";
        public static final String NUMBER = "Numero";
    }

    public static class tablaCategoria implements BaseColumns{
        public static final String TABLE_NAME = "CategoriaSitio";
        public static final String DESCRIP = "descripcion";
        public static final String ICONO_COLOR = "Icono";


    }

    public static class NombreCarpeta implements BaseColumns
    {
        public static final String TABLE_NAME = "NombresCarpeta";
        public static final String NAME_FOLDER = "Nombre";
    }

    public static class SitiosCreados implements BaseColumns{
        public static final String TABLE_NAME = "Sitios";
        public static final String NOMBRE = "Nombre";
        public static final String LONGI = "Longitud";
        public static final String LATI = "Latitud";
        public static final String FK_DESCRIP_CATEGORIA = "categoria";
        public static final String TEL = "telefono";
    }

    public static class Fotografias implements BaseColumns{
        public static final String TABLE_NAME = "Fotos";
        public static final String NOMBRE = "NombreFoto";
        public static final String FK_Sitio = "Sitio";
    }
}
