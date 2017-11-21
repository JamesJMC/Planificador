package com.example.james.planificador.GUI;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by James on 20/11/2017.
 */

public class Global {

    public LatLng latLng;
    public String tituloSitio, categoria, telefono;


    public Global(LatLng lat_lng, String sitio, String cat, String tel)
    {
        latLng = lat_lng;
        tituloSitio = sitio;
        categoria = cat;
        telefono = tel;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTituloSitio() {
        return tituloSitio;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTelefono() {
        return telefono;
    }

}
