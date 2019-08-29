package com.rphl.rapha.controlepatrimonial.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rphl.rapha.controlepatrimonial.modelo.Local;
import com.rphl.rapha.controlepatrimonial.R;

import java.util.List;

/**
 * Created by rapha on 30/05/2017.
 */

public class AdapterLocal extends BaseAdapter {
    private Activity activity;
    private List<Local> listaLocais;

    public AdapterLocal(Activity activity, List<Local> listaLocais) {
        this.activity = activity;
        this.listaLocais = listaLocais;
    }

    @Override
    public int getCount() {
        return listaLocais.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLocais.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaLocais.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_list_view_local, parent, false);

        Local local = (Local) getItem(position);

        TextView objDesc = (TextView) view.findViewById(R.id.tvDescricao);

        objDesc.setText(local.getDescricao());

        return view;
    }
}
