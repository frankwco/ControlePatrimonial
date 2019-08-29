package com.rphl.rapha.controlepatrimonial.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rphl.rapha.controlepatrimonial.R;
import com.rphl.rapha.controlepatrimonial.modelo.LocalInventario;

import java.util.List;

/**
 * Created by rapha on 30/05/2017.
 */

public class AdapterLocaisInventario extends BaseAdapter {
    private Activity activity;
    private List<LocalInventario> listaLocaisInventario;

    public AdapterLocaisInventario(Activity activity, List<LocalInventario> listaLocaisInventario) {
        this.activity = activity;
        this.listaLocaisInventario = listaLocaisInventario;
    }

    @Override
    public int getCount() {
        return listaLocaisInventario.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLocaisInventario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_list_view_locais_inventario, parent, false);

        LocalInventario locaisInventario = (LocalInventario) getItem(position);
        TextView objDesc = (TextView) view.findViewById(R.id.tvDescricaoLocaisInventario);

        objDesc.setText(locaisInventario.getLocal().getDescricao());

        return view;
    }
}
