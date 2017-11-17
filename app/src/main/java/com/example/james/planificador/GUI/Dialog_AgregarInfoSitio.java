package com.example.james.planificador.GUI;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.james.planificador.R;

public class Dialog_AgregarInfoSitio extends Fragment {

    private TextView titulo;
    private Spinner categorias;
    private EditText telefonoSitio;
    private ImageButton foto, video;
    private GridView gridViewGaleria;
    View view;
    String nombreSitio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_grid_view_imagenesmarca, container, false);

        titulo = (TextView)view.findViewById(R.id.nameSitio);
        categorias = (Spinner)view.findViewById(R.id.spinCategoria);
        telefonoSitio = (EditText)view.findViewById(R.id.telSitio);
        foto = (ImageButton)view.findViewById(R.id.tomarFoto);
        video = (ImageButton)view.findViewById(R.id.tomarVideo);
        gridViewGaleria = (GridView)view.findViewById(R.id.gridviewImagenes);

        titulo.setText(nombreSitio);

        nombreSitio = Main.identificarMarca;
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mandar();
            }
        });
        mandar();
        return view;
    }

    private void mandar() {
        Toast.makeText(getContext(),"nombre del sitio: "+nombreSitio,Toast.LENGTH_SHORT);
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
