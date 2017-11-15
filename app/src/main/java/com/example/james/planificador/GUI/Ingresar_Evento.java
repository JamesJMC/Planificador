package com.example.james.planificador.GUI;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;

import java.util.Calendar;

import static android.R.style.Theme_Holo_Light_Dialog_MinWidth;

public class Ingresar_Evento extends AppCompatActivity {

    private EditText nombreE, ubicacion,descripcion, participante;
    private Spinner fechaDesde, horaDesde, fechaHasta, horaHasta;
    private Button btnAgregarE;
    private String[] datoFecha = {"dd/mm/yy"};
    private String[] datoHora = {"hh/mm"};
    private DatePickerDialog.OnDateSetListener fecha;
    private DatePickerDialog.OnDateSetListener fecha1;
    private TimePickerDialog.OnTimeSetListener hora;
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar__evento);

        //incluir valores en los spinners
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.spinnerFecha , android.R.layout.simple_spinner_item);
        ArrayAdapter spinner_adapter1 = ArrayAdapter.createFromResource( this, R.array.spinnerHora , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fechaDesde = (Spinner)findViewById(R.id.fechaDesde);
        fechaHasta = (Spinner)findViewById(R.id.fechaHasta);
        horaDesde = (Spinner)findViewById(R.id.horaDesde);
        horaHasta = (Spinner)findViewById(R.id.horaHasta);

        fechaDesde.setAdapter(spinner_adapter);
        fechaHasta.setAdapter(spinner_adapter);
        horaDesde.setAdapter(spinner_adapter1);
        horaHasta.setAdapter(spinner_adapter1);


        //inicializar los componentes
        nombreE = (EditText)findViewById(R.id.nombreEvento);
        ubicacion = (EditText)findViewById(R.id.ubicacion);
        descripcion = (EditText)findViewById(R.id.descripcion);
        //participante = (EditText)findViewById(R.id.participante);

        //btnAgregarE = (Button)findViewById(R.id.btnAgregarEv);

        //Dar funcion a los componentes

        fechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarFechaDesde();
            }
        });

        fechaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colocarFechaHasta();
            }
        });

        horaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        horaHasta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*btnAgregarE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInformationEvent();
            }
        });*/

    }


    ////////////////////////////////////        M E T O D O S       //////////////////////////////////////////////

    private void colocarFechaDesde()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Ingresar_Evento.this,
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
                //fechaHasta.setText(date);
                colocarInfoFechaDesde(date);

            }
        };
    }

    private void colocarInfoFechaDesde(String date)
    {
        datoFecha = new String[]{date};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datoFecha);
        fechaDesde.setAdapter(adapter);
    }

    private void colocarFechaHasta()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Ingresar_Evento.this,
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
                //fechaHasta.setText(date);
                colocarInfoFechaHasta(date);

            }
        };
    }

    private void colocarInfoFechaHasta(String date)
    {
        datoFecha = new String[]{date};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,datoFecha);
        fechaHasta.setAdapter(adapter);
    }

    private void getInformationEvent()
    {
        /*String nombreEvento = nombreE.getText().toString();
        String fechaD = fechaDesde.getText().toString();
        String fechaH = fechaHasta.getText().toString();
        String horaD = horaDesde.getText().toString();
        String horaH = horaHasta.getText().toString();
        String ubicacionE = ubicacion.getText().toString();
        String descrip = descripcion.getText().toString();*/

        //DataDB.InsertData();
    }
}
