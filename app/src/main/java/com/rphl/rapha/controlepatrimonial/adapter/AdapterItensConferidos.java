package com.rphl.rapha.controlepatrimonial.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rphl.rapha.controlepatrimonial.R;
import com.rphl.rapha.controlepatrimonial.modelo.EquipamentoInventario;
import com.rphl.rapha.controlepatrimonial.modelo.Inventario;

import java.util.List;


public class AdapterItensConferidos extends BaseAdapter {
    private Activity activity;
    private List<EquipamentoInventario> listaEquipamentoInventario;

    public AdapterItensConferidos(Activity activity, List<EquipamentoInventario> listaEquipamentoInventario) {
        this.activity = activity;
        this.listaEquipamentoInventario = listaEquipamentoInventario;
    }

    @Override
    public int getCount() {
        return listaEquipamentoInventario.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEquipamentoInventario.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listaEquipamentoInventario.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.linha_list_view_itens_conferidos, parent, false);

        EquipamentoInventario equipamento = (EquipamentoInventario) getItem(position);
        TextView objDesc = (TextView) view.findViewById(R.id.tvDescricaoLocaisInventario);

        objDesc.setText(String.valueOf(equipamento.getTombamento().getCodigo() +" - "+equipamento.getTombamento().getDescricao()));

        return view;
    }
}
