package com.rphl.rapha.controlepatrimonial.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rphl.rapha.controlepatrimonial.R;
import com.rphl.rapha.controlepatrimonial.modelo.EquipamentoInventario;

import java.util.List;

/**
 * Created by rapha on 30/05/2017.
 */

public class AdapterPatrimonio extends BaseAdapter {
    private Activity activity;
    private List<EquipamentoInventario> listaPatrimonios;

    public AdapterPatrimonio(Activity activity, List<EquipamentoInventario> listaPatrimonios) {
        this.activity = activity;
        this.listaPatrimonios = listaPatrimonios;
    }

    @Override
    public int getCount() {
        return listaPatrimonios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPatrimonios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaPatrimonios.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_list_view_patrimonios, parent, false);

        EquipamentoInventario patrimonio = (EquipamentoInventario) getItem(position);

        TextView objDesc = (TextView) view.findViewById(R.id.tvPatDescri);
        //TextView objNum = (TextView) view.findViewById(R.id.tvPatNum);

        objDesc.setText(patrimonio.getDataConferencia()+"-");
        //objNum.setText(patrimonio.getCodigoPat());

        return view;
    }
}
