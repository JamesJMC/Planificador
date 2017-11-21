package com.example.james.planificador.GUI;

import android.content.Context;
import android.media.ImageWriter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;

import java.util.ArrayList;
import java.util.jar.Manifest;


public class VerEventos extends Fragment {

    private Spinner eventosSp, colorSpin;
    private TextView colorCambio;
    EditText descCat;
    private Button saveChanges;
    //private ImageButton colorIconoCat;
    String num;
    String eventoPlano = "Titulo: ";

    String colors[] = {"ROJO","VERDE","AMARILLO","BLANCO","MAGENTA","CIAN","AZUL","NARANJA","NEGRO"};
    String des;
    String colocito;

    boolean col1, col2; //variables que verifican si hubo un cambi en los datos de las categorias

    ArrayList<ArrayList<String>> categorias = new ArrayList<ArrayList<String>>();
    ArrayList<String> listaNombreEventos = new ArrayList<>();
    ArrayList<String> listaColores = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_eventos, container, false);

        //INICIALIZAR LOS VALORES
        eventosSp = (Spinner)view.findViewById(R.id.spinnerTitulosCons);
        colorSpin = (Spinner)view.findViewById(R.id.spinnerColorCat);
        descCat = (EditText)view.findViewById(R.id.descripcionCat);
        saveChanges = (Button)view.findViewById(R.id.guardarCambios);
        colorCambio = (TextView) view.findViewById(R.id.colorCategoria);
        //colorIconoCat = (ImageButton) view.findViewById(R.id.iconoColor);


        //CARGAR CATEGORIAS
        cargarCategrias();


        eventosSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Posicion: "+i,Toast.LENGTH_SHORT).show();

                des = categorias.get(i).get(0);
                descCat.setText(des);
                colocito = categorias.get(i).get(1);
                colorCambio.setText(colocito);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item,colors);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                colorSpin.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        colorSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                colorCambio.setText(colorSpin.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                colorCambio.setText(colocito);
            }
        });


        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataDB.editarCategoria(getContext(),descCat.getText().toString(),colorCambio.getText().toString(),des);
                Toast.makeText(getActivity(),"Los cambios se han hecho exitosamente!",Toast.LENGTH_SHORT).show();
                cargarCategrias();
            }
        });



        return view;
    }


    //**********************************************        AGREGAR EVENTOS AL SPINNER      *************************************************
    private void cargarCategrias()
    {
        categorias = DataDB.getCategoria(getContext());
        int x = 0;

        //obtener solo nombres de la base de datos
        if (!(categorias == null))
        {
            if (!categorias.isEmpty())
            {
                int i = categorias.size();
                while (x < i) {
                    listaNombreEventos.add(categorias.get(x).get(0));
                    listaColores.add(categorias.get(x).get(1));
                    x++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item,listaNombreEventos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eventosSp.setAdapter(adapter);//las categorias => eventosSp
        }
        else{
            Toast.makeText(getActivity(),"Agregue una categoría",Toast.LENGTH_SHORT).show();
        }
    }


    //***********************************************       BUSCAR Y MOSTRAR EL EVENTO      *************************************************


}
