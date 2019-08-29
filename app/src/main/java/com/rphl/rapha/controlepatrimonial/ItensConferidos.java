package com.rphl.rapha.controlepatrimonial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rphl.rapha.controlepatrimonial.adapter.AdapterItensConferidos;
import com.rphl.rapha.controlepatrimonial.modelo.EquipamentoInventario;
import com.rphl.rapha.controlepatrimonial.ws.AbrirDialog;
import com.rphl.rapha.controlepatrimonial.ws.Aguarde;
import com.rphl.rapha.controlepatrimonial.ws.TransmitirWS;

import java.util.ArrayList;
import java.util.List;

public class ItensConferidos extends AppCompatActivity  implements AdapterView.OnItemLongClickListener {

    ListView listaItensConferidos;
    private AdapterItensConferidos adapterItensConferidos;
    long idLocaisConferir;
    private ProgressDialog mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itens_conferidos);

        Intent itl = getIntent();

        Bundle extras = itl.getExtras();
        if (extras != null) {
            idLocaisConferir = extras.getLong("idLocaisInventario");
        }

        Log.i("local para conferir ir ", String.valueOf(idLocaisConferir));

        listaItensConferidos = (ListView) findViewById(R.id.lvItensConferidos);
        listaItensConferidos.setOnItemLongClickListener(this);
        listaItensConferidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EquipamentoInventario ei = (EquipamentoInventario) adapterItensConferidos.getItem(i);
                if(ei != null){
                    Intent in = new Intent(ItensConferidos.this, FormularioPatrimonio.class);
                    in.putExtra("EQUIPAMENTO_INVENTARIO_ID", ei.getId());
                    in.putExtra("REQUEST_CODE", FormularioPatrimonio.RC_EDIT);
                    startActivityForResult(in, FormularioPatrimonio.RC_EDIT);
                }
            }
        });

        List<EquipamentoInventario> ll = EquipamentoInventario.listAll(EquipamentoInventario.class);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        carregarLista();
    }


    public void carregarLista(){

        adapterItensConferidos = new AdapterItensConferidos(this,
                EquipamentoInventario.findWithQuery(EquipamentoInventario.class,
                        "Select * from EQUIPAMENTO_INVENTARIO where local_Inventario = ? ",
                        String.valueOf(idLocaisConferir)));
        listaItensConferidos.setAdapter(adapterItensConferidos);

    }

    public void iniciarConferencia(View view){
        Intent itl = new Intent(this, FormularioPatrimonio.class);
        itl.putExtra("idLocaisInventario",idLocaisConferir);
        itl.putExtra("REQUEST_CODE", FormularioPatrimonio.RC_SAVE);
        startActivity(itl);
    }


    public void transmitir(View view){

        mProgressBar = new ProgressDialog(this);
        AbrirDialog.abrirDialogTrasmitir(this, mProgressBar);

        TransmitirWS enviar = new TransmitirWS(this);
        List<EquipamentoInventario> lista = new ArrayList<>();
        lista =  EquipamentoInventario.findWithQuery(EquipamentoInventario.class, "Select * from EQUIPAMENTO_INVENTARIO where local_Inventario = ? ", String.valueOf(idLocaisConferir));

        for(EquipamentoInventario e : lista){
            Log.i("equuuu ", String.valueOf(e.getDataConferencia()));
        }

        enviar.execute(lista);
        Aguarde p = new Aguarde(this, mProgressBar);
        p.execute(1);

        Log.i("no salvvvvvv ","sdsds");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id == R.id.sair){

            SharedPreferences sharedPreferences = getSharedPreferences("chaveEmail", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putLong("chaveEmail", 0);
            editor.apply();

            SharedPreferences sharedPreferencesCheck = getSharedPreferences("chaveCheck", Context.MODE_PRIVATE);
            SharedPreferences.Editor editorCheck = sharedPreferencesCheck.edit();
            editorCheck.putBoolean("chaveCheck", false);

            editorCheck.apply();

            Intent itl = new Intent(this, Login.class);
            startActivity(itl);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        final EquipamentoInventario equipamento  = (EquipamentoInventario) parent.getAdapter().getItem(position);
        new AlertDialog.Builder(this)
                .setTitle("Deletando Tombamento")
                .setMessage("Tem certeza que deseja deletar esse tombamento?")
                .setPositiveButton("sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                adapterItensConferidos.notifyDataSetChanged();
                                equipamento.delete();
                                carregarLista();
                            }
                        })
                .setNegativeButton("não", null)
                .show();
        return true;
    }

}

