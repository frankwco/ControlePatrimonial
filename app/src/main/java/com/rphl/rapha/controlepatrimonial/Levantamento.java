package com.rphl.rapha.controlepatrimonial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.rphl.rapha.controlepatrimonial.adapter.AdapterPatrimonio;
import com.rphl.rapha.controlepatrimonial.modelo.Local;

import org.w3c.dom.Text;

import java.util.List;

public class Levantamento extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AdapterPatrimonio adapterPatrimonio;
    ListView listaPatrimonios;
    private String local;
    private Local clocal;
    TextView localObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levantamento);

        Intent itl = getIntent();

        Bundle extras = itl.getExtras();
        if (extras != null) {
            local = extras.getString("local");
        }

        localObj = (TextView) findViewById(R.id.tvNomeLocal);
        clocal = (Local) Local.findById(Local.class, Long.valueOf(local));

        localObj.setText(clocal.getDescricao());

        listaPatrimonios = (ListView) findViewById(R.id.lvPatrimonios);
        listaPatrimonios.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
     //   preencheListaPatrimonios();
    }

    public void adicionaPatrimonio(View view) {
        Intent itPatrimonioCad = new Intent(this, FormularioPatrimonio.class);
        itPatrimonioCad.putExtra("local", localObj.getText().toString());
        startActivity(itPatrimonioCad);
    }

    /*private void preencheListaPatrimonios() {
        adapterPatrimonio = new AdapterPatrimonio(this, getListaPatrimonios());
        listaPatrimonios.setAdapter(adapterPatrimonio);
    }*/

   /* private List<Patrimonio> getListaPatrimonios() {
        List<Patrimonio> li = Patrimonio.findWithQuery(Patrimonio.class, "Select *from Patrimonio where local LIKE '%"+clocal.getDescricao()+"%'");
        return li;
    }*/

}
