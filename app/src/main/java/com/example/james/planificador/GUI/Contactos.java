package com.example.james.planificador.GUI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.james.planificador.LogicaDB.DataDB;
import com.example.james.planificador.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.jar.Manifest;


public class Contactos extends Fragment {

    private ExpandableListView listView;
    private ListaExpandible listAdapter;
    private List<String> listaCategorias;
    private HashMap<String,List<String>> listHashMap;
    private String nombreC,numeroC;

    Dialog dialogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contactos, container, false);

        listView = (ExpandableListView) view.findViewById(R.id.listaContactos);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, final int i, long l) {
                PopupMenu popUpMenu = new PopupMenu(getActivity(),listView);
                popUpMenu.getMenuInflater().inflate(R.menu.popup_menu, popUpMenu.getMenu());

                popUpMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        String nombreCo = listView.getItemAtPosition(i).toString();
                        DataDB.eliminaCategoria(getContext(),nombreCo);
                        Toast.makeText(getActivity(),"La categoría "+" '"+nombreCo+"' se ha eliminado",Toast.LENGTH_SHORT).show();
                        iniitData();
                        setAdaptador();
                        return true;
                    }
                });
                popUpMenu.show();
                return false;
            }
        });
        iniitData();
        setAdaptador();




        return view;
    }


    // AGREGAR CONTACTO A LA BASE DE DATOS
    public void agregarContactoDB(String name, String num)
    {
        Toast.makeText(getActivity(),name+"   +   "+num,Toast.LENGTH_SHORT).show();
        DataDB.agregarContacto(getContext(),name, num);
        iniitData();
        setAdaptador();
    }


    //HACER LA LISTA DE LOS CONTACTOS
    private void setAdaptador()
    {
        if(!listaCategorias.isEmpty())
        {
            listAdapter = new ListaExpandible(getContext(),listaCategorias,listHashMap);
            listView.setAdapter(listAdapter);
        }
    }

    private void mandar()
    {
        Toast.makeText(getActivity(),"Se esta ejecutando",Toast.LENGTH_SHORT).show();
    }

    //CARGAR LOS DATOS EN UN ARREGLO
    private void iniitData()
    {
        //arreglo con todos los contactos y los numeros [ [ [] , [] ], ...]
        ArrayList<ArrayList<String>> categorias = DataDB.getCategoria(getContext());
        listaCategorias = new ArrayList<>();
        listHashMap = new HashMap<>();

        int x = 0;

        //obtener nombres y numeros de la base de datos
        if (!(categorias == null))
        {
            if (!categorias.isEmpty())
            {
                int i = categorias.size();
                while (x < i) {
                    listaCategorias.add(categorias.get(x).get(0));
                    List<String> numeros = new ArrayList<String>();
                    numeros.add(categorias.get(x).get(1));
                    listHashMap.put(listaCategorias.get(x), numeros);
                    x++;
                }
            }
        }
        else{
            Toast.makeText(getActivity(),"No hay contactos agregados",Toast.LENGTH_SHORT).show();
        }
    }

}
