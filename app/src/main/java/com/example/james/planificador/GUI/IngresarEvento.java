package com.example.james.planificador.GUI;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;
import static android.content.ContentValues.TAG;


public class IngresarEvento extends Fragment {

    private EditText descripcion;
    private Spinner color;
    private ImageButton imageButtonADD;

    private DatePickerDialog.OnDateSetListener fecha1;
    private TimePickerDialog.OnTimeSetListener hora;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingresar_evento, container, false);

        descripcion = (EditText)view.findViewById(R.id.descripcion);
        color = (Spinner) view.findViewById(R.id.contactosSpinner);
        imageButtonADD = (ImageButton)view.findViewById(R.id.imageButtonAddC);
        agregarColores();

        cargarEventos();

        //inicializar los componentes



        //DAR FUNCION A LOS COMPONENTES



        //GUARDAR EL EVENTO EN LA BASE DE DATOS
        imageButtonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        return view;
    }

    private void agregarColores()
    {
        String colors[] = {"ROJO","VERDE","AMARILLO","BLANCO","MAGENTA","CIAN","AZUL","NARANJA","NEGRO"};
        // = ["ROJO","VERDE","AMARILLO","BLANCO","MAGENTA","CIAN","AZUL","NNARANJA","NEGRO"]

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,colors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        color.setAdapter(adapter);
    }


    // CARGAR PARTICIPANTES DE LA BASE DE DATOS


    //*************************************************         GUARDAR DATOS EN LA BASE DE DATOS ************************************
    private void guardarDatos()
    {
        //VERIFICAR QUE TODOS LOS ESPACIOS ESTÉN LLENOS
        if(descripcion.getText().toString() == "" || color.getSelectedItem().toString() == "")
        {
            Toast.makeText(getActivity(),"Por favor, rellene todos los espacios",Toast.LENGTH_LONG).show();
        }
        else
        {
            DataDB.setCategoria(getContext(),descripcion.getText().toString(), color.getSelectedItem().toString());
            Toast.makeText(getActivity(),"Los datos se han guardado exitosamente!",Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarEventos()
    {
        ArrayList<ArrayList<String>> categorias = DataDB.getCategoria(getContext());
        ArrayList<String> listaNombreCategoria = new ArrayList<>();
        int x = 0;

        //obtener solo nombres de la base de datos
        if (!(categorias == null))
        {
            if (!categorias.isEmpty())
            {
                int i = categorias.size();
                while (x < i) {
                    listaNombreCategoria.add(categorias.get(x).get(0));
                    x++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item,listaNombreCategoria);
            //categoria.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(),"Agregue una categoría",Toast.LENGTH_SHORT).show();
        }
    }
}
