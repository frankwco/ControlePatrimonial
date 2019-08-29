package com.rphl.rapha.controlepatrimonial.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.rphl.rapha.controlepatrimonial.modelo.Inventario;

import com.rphl.rapha.controlepatrimonial.R;

import java.util.List;



public class AdapterInventario extends BaseAdapter {
    private Activity activity;
    private List<Inventario> listaInventario;

    public AdapterInventario(Activity activity, List<Inventario> listaInventario) {
        this.activity = activity;
        this.listaInventario = listaInventario;
    }

    @Override
    public int getCount() {
        return listaInventario.size();
    }

    @Override
    public Object getItem(int position) {
        return listaInventario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaInventario.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_list_view_inventariol, parent, false);

        Inventario inventario = (Inventario) getItem(position);
        TextView objDesc = (TextView) view.findViewById(R.id.tvDescricaoInventario);

        objDesc.setText(inventario.getDescricao());

        return view;
    }
}
