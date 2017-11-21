package com.example.james.planificador.GUI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.james.planificador.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AgregarInfoSitio extends AppCompatActivity implements View.OnClickListener{

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int RESULT_OK = 1;

    private TextView titulo;
    private Spinner categorias;
    private EditText telefonoSitio;
    private ImageButton foto, video;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_info_sitio);

        titulo = (TextView)findViewById(R.id.nameSitio);
        categorias = (Spinner)findViewById(R.id.spinCategoria);
        telefonoSitio = (EditText)findViewById(R.id.telSitio);
        foto = (ImageButton)findViewById(R.id.tomarFoto);
        video = (ImageButton)findViewById(R.id.tomarVideo);
        gridViewGaleria = (GridView)findViewById(R.id.gridviewImagenes);
        nombreSitio = Main.identificarMarca;
        titulo.setText(nombreSitio);


        foto.setOnClickListener(this);
        video.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.tomarFoto:
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
        if (takePictureIntent.resolveActivity(this.getPackageManager()) != null) {
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
