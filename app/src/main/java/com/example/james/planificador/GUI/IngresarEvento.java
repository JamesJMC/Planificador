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

    private EditText tituloE, fechaDE, fechaHE, horaDE, horaHE, ubicacion, descripcion;
    private Spinner participanteSp;
    private ImageButton imageButtonADD;

    private DatePickerDialog.OnDateSetListener fecha1;
    private TimePickerDialog.OnTimeSetListener hora;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingresar_evento, container, false);

        //inicializar los componentes
        tituloE = (EditText)view.findViewById(R.id.nombreEvento);
        fechaDE = (EditText)view.findViewById(R.id.fechaDesde);
        fechaHE = (EditText)view.findViewById(R.id.fechaHasta);
        horaDE = (EditText)view.findViewById(R.id.horaDesde);
        horaHE = (EditText)view.findViewById(R.id.horaHasta);
        ubicacion = (EditText)view.findViewById(R.id.ubicacion);
        descripcion = (EditText)view.findViewById(R.id.descripcion);
        participanteSp = (Spinner) view.findViewById(R.id.contactosSpinner);
        imageButtonADD = (ImageButton)view.findViewById(R.id.imageButtonAddC);


        //DAR FUNCION A LOS COMPONENTES
        fechaDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarFechaDesde();
            }
        });
        fechaHE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarFechaHasta();
            }
        });
        horaDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarHoraDesde();
            }
        });
        horaHE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarHoraHasta();
            }
        });

        //AGREGAR LOS PARTICIPANTES REGISTRADOS EN LA BASE DE DATOS
        cargarParticipantes();

        //GUARDAR EL EVENTO EN LA BASE DE DATOS
        imageButtonADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        return view;
    }



    // CARGAR PARTICIPANTES DE LA BASE DE DATOS

    private void cargarParticipantes()
    {
        ArrayList<ArrayList<String>> contacts = DataDB.getContactos(getContext());
        ArrayList<String> listaNombreContactos = new ArrayList<>();
        int x = 0;

        //obtener solo nombres de la base de datos
        if (!(contacts == null))
        {
            if (!contacts.isEmpty())
            {
                int i = contacts.size();
                while (x < i) {
                    listaNombreContactos.add(contacts.get(x).get(0));
                    x++;
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,listaNombreContactos);
            participanteSp.setAdapter(adapter);
        }
        else{
            Toast.makeText(getActivity(),"Agregue un contacto",Toast.LENGTH_SHORT).show();
        }
    }


    //**********************************************        PARA FECHAS Y HORAS     ******************************************************

    private void colocarFechaDesde()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                Theme_Holo_Light_Dialog_MinWidth,
                fecha1,
                year,
                month,
                day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        fecha1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: mm/dd/yy: " + year + "/" + month + "/" + dayOfMonth);
                String date = month + "/" + dayOfMonth + "/" + year;
                fechaDE.setText(date);

            }
        };
    }

    private void colocarFechaHasta()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                Theme_Holo_Light_Dialog_MinWidth,
                fecha1,
                year,
                month,
                day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        fecha1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                Log.d(TAG, "onDateSet: mm/dd/yy: " + year + "/" + month + "/" + dayOfMonth);
                String date = month + "/" + dayOfMonth + "/" + year;
                fechaHE.setText(date);

            }
        };
    }

    private void colocarHoraDesde()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        //int md = cal.get(Calendar.AM_PM);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, hora,hour, min, false);
        dialog.setTitle("Seleccione la hora");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        hora = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(TAG, "onDateSet: HH:mm:ss: " + hourOfDay + "/" + minute);
                String time = hourOfDay + ":" + minute;
                horaDE.setText(time);
            }
        };
    }

    private void colocarHoraHasta()
    {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        //int md = cal.get(Calendar.AM_PM);

        TimePickerDialog dialog = new TimePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, hora,hour, min, false);
        dialog.setTitle("Seleccione la hora");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        hora = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Log.d(TAG, "onDateSet: HH:mm:ss: " + hourOfDay + "/" + minute);
                String time = hourOfDay + ":" + minute;
                horaHE.setText(time);
            }
        };
    }


    //*************************************************         GUARDAR DATOS EN LA BASE DE DATOS ************************************
    private void guardarDatos()
    {
        //VERIFICAR QUE TODOS LOS ESPACIOS ESTÉN LLENOS
        if(tituloE.getText().toString() != "" & fechaDE.getText().toString() != "" & fechaHE.getText().toString() != ""
                & horaDE.getText().toString() != "" & horaHE.getText().toString() != "" & ubicacion.getText().toString() != ""
                & descripcion.getText().toString() != "" & participanteSp.getSelectedItem().toString() != "")
        {
            Toast.makeText(getActivity(),"Por favor, rellene todos los espacios",Toast.LENGTH_LONG).show();
        }
        else
        {
            DataDB.InsertData(getContext(),tituloE.getText().toString(),fechaDE.getText().toString(),fechaHE.getText().toString(),
                    horaDE.getText().toString(), horaHE.getText().toString(), ubicacion.getText().toString(), descripcion.getText().toString(),
                    participanteSp.getSelectedItem().toString());
            Toast.makeText(getActivity(),"Los datos se han guardado exitosamente!",Toast.LENGTH_SHORT).show();
        }
    }
}
