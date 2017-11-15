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
    private List<String> listaNombreContactos;
    private HashMap<String,List<String>> listHashMap;
    private ImageButton imageButtonAddContacto;
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
                        switch (id)
                        {
                            case R.id.llamar:
                                String numero = listView.getItemAtPosition(i).toString();
                                Intent llamarContacto = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+numero));
                                startActivity(llamarContacto);
                                Toast.makeText(getActivity(),"Numero"+ numero,Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.Eliminar:
                                String nombreCo = listView.getItemAtPosition(i).toString();
                                DataDB.eliminarContacto(getContext(),nombreCo);
                                Toast.makeText(getActivity(),"El contacto "+" '"+nombreCo+"' se ha eliminado",Toast.LENGTH_SHORT).show();
                                iniitData();
                                setAdaptador();//84508052 - 85967124 - 88025070
                                break;
                        }
                        return true;
                    }
                });
                popUpMenu.show();
                return false;
            }
        });
        iniitData();
        setAdaptador();



        //darle funcion al
        imageButtonAddContacto = (ImageButton)view.findViewById(R.id.addContact);
        imageButtonAddContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Agregar contacto");
                alert.setMessage("Por favor rellene todos los espacios");

                // Set an EditText view to get user input
                Context context = getContext();
                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText inputName = new EditText(context);
                inputName.setHint("Nombre");
                layout.addView(inputName);

                final EditText inputNumber = new EditText(context);
                inputNumber.setHint("Número");
                layout.addView(inputNumber);

                alert.setView(layout);

                alert.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Guardamos el valor digitado en una variable o lo mostramos en
                        // un TextViews no se lo que se nos ocurra o lo que necesitemos
                        // hacer.
                        String cadenaName = inputName.getText().toString();
                        String cadenaumber = inputNumber.getText().toString();
                        agregarContactoDB(cadenaName, cadenaumber);
                    }
                });

                alert.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Canceled.
                            }
                        });
                alert.show();
            }
        });


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
        if(!listaNombreContactos.isEmpty())
        {
            listAdapter = new ListaExpandible(getContext(),listaNombreContactos,listHashMap);
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
        ArrayList<ArrayList<String>> contacts = DataDB.getContactos(getContext());
        listaNombreContactos = new ArrayList<>();
        listHashMap = new HashMap<>();

        int x = 0;

        //obtener nombres y numeros de la base de datos
        if (!(contacts == null))
        {
            if (!contacts.isEmpty())
            {
                int i = contacts.size();
                while (x < i) {
                    listaNombreContactos.add(contacts.get(x).get(0));
                    List<String> numeros = new ArrayList<String>();
                    numeros.add(contacts.get(x).get(1));
                    listHashMap.put(listaNombreContactos.get(x), numeros);
                    x++;
                }
            }
        }
        else{
            Toast.makeText(getActivity(),"No hay contactos agregados",Toast.LENGTH_SHORT).show();
        }
    }

}
