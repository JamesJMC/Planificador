package com.example.james.planificador.GUI;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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


public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, View.OnClickListener,
        GoogleMap.OnMapLongClickListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    /*com.github.clans.fab.FloatingActionMenu btnMenu;
    com.github.clans.fab.FloatingActionButton btnCambiarEstilo;
    com.github.clans.fab.FloatingActionButton btnAgregarEvento;
    com.github.clans.fab.FloatingActionButton btnMiUbicacion;*/

    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

    private GoogleMap mMap;
    static int contadorMapa = 0;
    double lat, lon = 0;
    private Marker marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        //AGREGAR MARCADORES DE SITIOS


        //menu flotante
        /*btnMenu = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.fabPrincipal);
        btnMenu.setClosedOnTouchOutside(true);


        //Agregar eventos a los botones de los botones del menu flotante
        btnCambiarEstilo = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.cambiar_estiloMapa);
        btnAgregarEvento = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.agregarEvento);
        btnMiUbicacion = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.miUbicacion);

        btnCambiarEstilo.setOnClickListener(this);
        btnAgregarEvento.setOnClickListener(this);
        btnMiUbicacion.setOnClickListener(this);*/


        //cargarSitiosCreados();
    }

    //private void cargarSitiosCreados(){ }

    private void agregarEvento() {
        Intent eventoNuevo = new Intent(Main.this, Ingresar_Evento.class);
        startActivity(eventoNuevo);
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
            Intent enviarMensaje = new Intent(Main.this, Enviar_por_Mensaje.class);
            startActivity(enviarMensaje);
        } else if (id == R.id.contacts) {
            Intent mapa = new Intent(Main.this, Actividades_Mapa.class);
            startActivity(mapa);
        } else if (id == R.id.info) {

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

        // Add some markers to the map, and add a data object to each marker.
        mPerth = mMap.addMarker(new MarkerOptions().position(PERTH).title("Perth"));
        mPerth.setTag(0);

        mSydney = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .title("Sydney"));
        mSydney.setTag(0);

        mBrisbane = mMap.addMarker(new MarkerOptions()
                .position(BRISBANE)
                .title("Brisbane"));
        mBrisbane.setTag(0);

        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMapClickListener(this);
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
    public void onMapLongClick(LatLng latLng) {
        /*if (marcador != null) {
            marcador.remove();
        }*/
        //mMap.setMyLocationEnabled(true);
        marcador = mMap.addMarker(new MarkerOptions().position(latLng).title("Mi ubicación actual")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.amarillo)));

    }

    @Override
    public void onMapClick(LatLng latLng)
    {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(Main.this);

        alert.setTitle("Agregar sitio");
        alert.setMessage("Por favor rellene todos los espacios");

        // Set an EditText view to get user input
        Context context = Main.this;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputName = new EditText(context);
        inputName.setHint("Nombre");
        layout.addView(inputName);

        final Spinner categorias = new Spinner(Main.this);
        //añadir las categorias al spinner
        layout.addView(categorias);

        final EditText inputNumber = new EditText(context);
        inputNumber.setHint("Telefono");
        layout.addView(inputNumber);

        final GridView gridViewImagenes = new GridView(Main.this);


        final ImageButton imagenes = new ImageButton(Main.this);
        imagenes.setImageResource(R.drawable.ic_menu_camera);
        imagenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        layout.addView(imagenes);

        alert.setView(layout);

        alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Guardamos el valor digitado en una variable o lo mostramos en
                // un TextViews no se lo que se nos ocurra o lo que necesitemos
                // hacer.
                String cadenaName = inputName.getText().toString();
                String cadenaumber = inputNumber.getText().toString();
            }
        });

        alert.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
        alert.show();


        return false;
    }


    /**
     * Clase interna del mapa
     */

}
