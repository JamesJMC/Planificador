package com.example.james.planificador.GUI;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class Dialog_AgregarInfoSitio extends Fragment implements View.OnClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_OK = 1;

    private TextView titulo;
    private Spinner categorias;
    private EditText telefonoSitio;
    private ImageButton foto, video;
    View view = getView();
    String nombreSitio;
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    private GridViewAdapter mAdapter;
    private ArrayList<String> taxtVlue;
    private ArrayList<Integer> imageValue;
    private DisplayMetrics metrics;

    private GridView gridViewGaleria;
    private ImageView imageView;
    public TextView txtViewTitle;
    public Typeface tp;

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
        nombreSitio = Main.identificarMarca;
        titulo.setText(nombreSitio);


        foto.setOnClickListener(this);
        return view;
    }

    private void mandar() {
        Toast.makeText(getContext(),"nombre del sitio: "+nombreSitio,Toast.LENGTH_SHORT);
    }

    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.tomarFoto:
                mandar();
                break;
            case R.id.tomarVideo:
                break;
            default:
                break;
        }
    }

    private List<String> cargarImagenes(String path)
    {
        File directory = new File(path);
        List<String> lista = null;
        if(directory.exists())
        {

            listFile = directory.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];

            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
                //obtener el nombre del archivo
                lista.add(listFile[i].getName());
            }
            return lista;
        }
        return null;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            if (requestCode == REQUEST_IMAGE_CAPTURE)
            {
                Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(cameraImage);
            }
        }
    }
}