package com.example.james.planificador.GUI;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;


public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    // Declare variables


    private GoogleMap mMap;
    static int contadorMapa = 0;
    double lat, lon = 0;
    private Marker marcador;
    private ImageView imageView;

    //carpeta de imagenes de todo el proyecto
    public static String identificarMarca;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //prepareing list
        /*taxtVlue=gridViewValues.taxtVlue();
        imageValue=gridViewValues.imageValue();

        metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // prepared arraylist and passed it to the Adapter class

        mAdapter = new GridviewAdapter(this,taxtVlue, imageValue, metrics );*/

        //CARPETA DEL PROYECTO
        crearAlbumPATH("Planificador");
        //AGREGAR MARCADORES DE SITIOS


        //cargarSitiosCreados();
    }

    private void crearAlbumPATH(String planificador)
    {
        File album = new File(Environment.getExternalStorageDirectory(), planificador);
        if(!album.exists())
        {
            album.mkdirs();
        }
        else
        {
            return;
        }
    }

    //private void cargarSitiosCreados(){ }

    private void agregarEvento() {
        //Intent eventoNuevo = new Intent(Main.this, Ingresar_Evento.class);
        //startActivity(eventoNuevo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            switch (contadorMapa) {
                case 0:
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    Toast.makeText(this, "Tipo de mapa Híbrido", Toast.LENGTH_SHORT).show();
                    contadorMapa += 1;
                    break;
                case 1:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                    Toast.makeText(this, "Tipo de mapa Ninguno", Toast.LENGTH_SHORT).show();
                    contadorMapa += 1;
                    break;
                case 2:
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    Toast.makeText(this, "Tipo de mapa Normal", Toast.LENGTH_SHORT).show();
                    contadorMapa += 1;
                    break;
                case 3:
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    Toast.makeText(this, "Tipo de mapa Satélite", Toast.LENGTH_SHORT).show();
                    contadorMapa += 1;
                    break;
                case 4:
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    Toast.makeText(this, "Tipo de mapa Terreno", Toast.LENGTH_SHORT).show();
                    contadorMapa = 0;
                    break;
                default:
                    break;
            }
            return true;
        }
        if(id == R.id.infoSistema)
        {
            Intent info = new Intent(Main.this, VerInfoSistema.class);
            startActivity(info);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_event) {
            Intent menuPrincipal = new Intent(Main.this, MenuPrincipal.class);
            startActivity(menuPrincipal);
        } else if (id == R.id.list_events) {
            Intent mapa = new Intent(Main.this, Actividades_Mapa.class);
            startActivity(mapa);
        } else if (id == R.id.invitation) {
            //Enviar evento por mensaje
        } else if (id == R.id.contacts) {
            Intent mapa = new Intent(Main.this, Actividades_Mapa.class);
            startActivity(mapa);
        } else if (id == R.id.info) {
            Intent clima = new Intent(Main.this, DatosClimaticos.class);
            startActivity(clima);

        } else if (id == R.id.cerrar) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /***
     * Metodos
     */

    private void cambiarEstiloMapa(View view) {
        Toast.makeText(this, "Cambiar estilo del mapa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.addMarker(new MarkerOptions()
                .position(new LatLng(10, 10))
                .title("Hello world"));*/
    }

    //un cambio

    @Override
    public void onClick(View view) {

    }

    private void irUbicacionActual() {
        LatLng coordenadas = new LatLng(lat, lon);
        CameraUpdate miubicacion = CameraUpdateFactory.newLatLngZoom(coordenadas, 16);
        if (marcador != null) {
            marcador.remove();
        }
        //mMap.setMyLocationEnabled(true);
        marcador = mMap.addMarker(new MarkerOptions().position(coordenadas).title("Mi ubicación actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.amarillo)));
        mMap.animateCamera(miubicacion);
    }

    @Override
    public void onMapLongClick(final LatLng latLng) {
        /*if (marcador != null) {
            marcador.remove();
        }*/
        //mMap.setMyLocationEnabled(true);
        final String[] cadenaName = new String[1];
        final AlertDialog.Builder alert = new AlertDialog.Builder(Main.this);
        alert.setTitle("Agregar sitio");
        alert.setMessage("Por favor rellene todos los espacios");

        // Set an EditText view to get user input
        Context context = Main.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setBackgroundColor(Color.TRANSPARENT);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputName = new EditText(context);
        inputName.setHint("Nombre");
        layout.addView(inputName);

        alert.setView(layout);
        alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                cadenaName[0] = inputName.getText().toString();
                int n = 1;
                if(!cadenaName[0].isEmpty())
                {
                    crearAlbumSecundario(latLng, cadenaName[0], n);
                }
                else{ Toast.makeText(Main.this, "Debe ingresar un nombre!", Toast.LENGTH_SHORT).show(); }
            }
        });

        alert.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
        alert.show();
    }

    private void crearAlbumSecundario(LatLng latLng, String cadena, int n)
    {
        String NameSec = cadena;
        boolean var = true;
        if(crearAlbum(cadena))
        {
            marcador = mMap.addMarker(new MarkerOptions().position(latLng).title(cadena)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.amarillo)));
            Toast.makeText(Main.this, "Haz creado un marcador con el nombre: "+cadena, Toast.LENGTH_SHORT).show();
            identificarMarca = cadena;
            return;
        }
        else
        {
            while (var)
            {
                if (crearAlbum(cadena+"("+n+")"))
                {
                    NameSec = cadena+"("+n+")";
                    var = false;
                    identificarMarca = NameSec;
                    marcador = mMap.addMarker(new MarkerOptions().position(latLng).title(cadena)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.amarillo)));
                    Toast.makeText(Main.this, "Haz creado un marcador con el nombre: "+cadena+"("+n+")", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    n += 1;
                }
            }
        }
        DataDB.guardarNombreCarpetaPorTituloMarca(Main.this, NameSec);
    }

    @Override
    public void onMapClick(LatLng latLng)
    {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        //**************************        CREACION DEL DIALOG       *****************************
        String jsnf = identificarMarca;
        View view = new Dialog_AgregarInfoSitio().getView();
        final Dialog alert = new Dialog(Main.this);
        alert.setTitle("Agregar datos al sitio");
        alert.setContentView(R.layout.fragment_grid_view_imagenesmarca);
        alert.show();
        return false;
    }


    /*Crear album en almacenamiento interno*/
    private boolean crearAlbum(String alb)
    {
        File album = new File(Environment.getExternalStorageDirectory()+"/Planificador", alb);
        if(!album.exists())
        {
            album.mkdirs();
            return true;
        }
        else
        {
            return false;
        }
    }
}
