package com.example.james.planificador.GUI;

import android.content.Context;
import android.media.ImageWriter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;

import java.util.ArrayList;
import java.util.jar.Manifest;


public class VerEventos extends Fragment {

    private Spinner eventosSp;
    private TextView mostrarEvento;
    //private ImageButton colorIconoCat;
    String num;
    String eventoPlano = "Titulo: ";

    ArrayList<ArrayList<String>> categorias = new ArrayList<ArrayList<String>>();
    ArrayList<String> listaNombreEventos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_eventos, container, false);

        //INICIALIZAR LOS VALORES
        eventosSp = (Spinner)view.findViewById(R.id.spinnerTitulosCons);
        mostrarEvento = (TextView)view.findViewById(R.id.textViewInfoEvento);
        //colorIconoCat = (ImageButton) view.findViewById(R.id.iconoColor);


        //CARGAR CATEGORIAS
        cargarCategrias();


        eventosSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"Posicion: "+i,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    x++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,listaNombreEventos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eventosSp.setAdapter(adapter);//las categorias => eventosSp
        }
        else{
            Toast.makeText(getActivity(),"Agregue un contacto",Toast.LENGTH_SHORT).show();
        }
    }


    //***********************************************       BUSCAR Y MOSTRAR EL EVENTO      *************************************************


}
