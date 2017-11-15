package com.example.james.planificador.GUI;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
    private ImageButton mostrarEV, enviarMsjs;
    String num;
    String eventoPlano = "Titulo: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_eventos, container, false);

        //INICIALIZAR LOS VALORES
        eventosSp = (Spinner)view.findViewById(R.id.spinnerTitulosCons);
        mostrarEvento = (TextView)view.findViewById(R.id.textViewInfoEvento);
        mostrarEV = (ImageButton)view.findViewById(R.id.ver);
        enviarMsjs = (ImageButton)view.findViewById(R.id.envMSjs);

        //CARGAR EVENTOS
        cargarEventos();

        //BOTON MPSTRAR
        mostrarEV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarInfoEvento();
            }
        });

        enviarMsjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(num, null, eventoPlano, null, null);
                Toast.makeText(getActivity(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    //**********************************************        AGREGAR EVENTOS AL SPINNER      *************************************************
    private void cargarEventos()
    {
        ArrayList<ArrayList<String>> contacts = DataDB.getDataEvento(getContext());
        ArrayList<String> listaNombreEventos = new ArrayList<>();
        int x = 0;

        //obtener solo nombres de la base de datos
        if (!(contacts == null))
        {
            if (!contacts.isEmpty())
            {
                int i = contacts.size();
                while (x < i) {
                    listaNombreEventos.add(contacts.get(x).get(0));
                    x++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,listaNombreEventos);
            eventosSp.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(),"Agregue un contacto",Toast.LENGTH_SHORT).show();
        }
    }


    //***********************************************       BUSCAR Y MOSTRAR EL EVENTO      *************************************************

    public void buscarInfoEvento()
    {
        ArrayList<String> fila = DataDB.getDataEventoFila(getContext(),eventosSp.getSelectedItem().toString());
        if (fila == null)
        {
            Toast.makeText(getActivity(),"No se encontró ningún evento con este nombre",Toast.LENGTH_LONG).show();
            return;
        }
        int c = fila.size();
        int i = 0;

        while (i<c)
        {
            switch (i)
            {
                case 0:
                    eventoPlano+=fila.get(i)+"\n"+"Fecha de inicio: ";
                    break;
                case 1:
                    eventoPlano+=fila.get(i)+"\n"+"Fecha final: ";
                    break;
                case 2:
                    eventoPlano+=fila.get(i)+"\n"+"Hora de inicio: ";
                    break;
                case 3:
                    eventoPlano+=fila.get(i)+"\n"+"Hora final: ";
                    break;
                case 4:
                    eventoPlano+=fila.get(i)+"\n"+"Ubicación: ";
                    break;
                case 5:
                    eventoPlano+=fila.get(i)+"\n"+"Descripción: ";
                    break;
                case 6:
                    eventoPlano+=fila.get(i)+"\n"+"Participante: ";
                    break;
                case 7:
                    num = DataDB.getDataContactoFila(getContext(),fila.get(i-1));
                    eventoPlano+=fila.get(i)+"\n"+"Número de telefono: "+num;
                    break;
            }
            i++;
        }
        mostrarEvento.setText(eventoPlano);
    }
}
