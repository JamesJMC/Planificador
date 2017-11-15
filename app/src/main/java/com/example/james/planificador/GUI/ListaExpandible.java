package com.example.james.planificador.GUI;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.james.planificador.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by James on 08/11/2017.
 */

public class ListaExpandible extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listaNombreContactos;
    private HashMap<String,List<String>> listHashMap;

    public ListaExpandible(Context context, List<String> listaNombreContactos, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listaNombreContactos = listaNombreContactos;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listaNombreContactos.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listaNombreContactos.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listaNombreContactos.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listaNombreContactos.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String tituloP = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.grouplista,null);
        }
        TextView lbllistaNombreContactos = (TextView)view.findViewById(R.id.textViewGrupoLista);
        lbllistaNombreContactos.setTypeface(null, Typeface.BOLD);
        lbllistaNombreContactos.setText(tituloP);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.itemlista,null);
        }
        TextView lblChildText = (TextView)view.findViewById(R.id.textViewItemLista);
        lblChildText.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
