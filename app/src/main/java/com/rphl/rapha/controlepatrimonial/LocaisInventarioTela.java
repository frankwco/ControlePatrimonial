package com.rphl.rapha.controlepatrimonial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.rphl.rapha.controlepatrimonial.adapter.AdapterLocaisInventario;
import com.rphl.rapha.controlepatrimonial.modelo.LocalInventario;
import com.rphl.rapha.controlepatrimonial.modelo.Servidor;
import com.rphl.rapha.controlepatrimonial.ws.Aguarde;
import com.rphl.rapha.controlepatrimonial.ws.Caminho;
import com.rphl.rapha.controlepatrimonial.ws.LocaisInventarioWS;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LocaisInventarioTela extends AppCompatActivity  implements AdapterView.OnItemClickListener{

    AdapterLocaisInventario adapterLocaisInventario;
    ListView listaLocaisInventario;
    long idServidor;
    long idInventario;
    private ProgressDialog mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais_inventario_tela);

        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
        //schemaGenerator.deleteTables(new SugarDb(this).getDB());

        Intent itl = getIntent();
        Bundle extras = itl.getExtras();
        if (extras != null) {
            idServidor = extras.getLong("idServidor");
            idInventario = extras.getLong("idInventario");
        }


        listaLocaisInventario  = (ListView) findViewById(R.id.lvTelaLocaisInventario);
        listaLocaisInventario.setOnItemClickListener(this);


    }

    public void preencherAdapter(){

        List<LocalInventario> l = LocalInventario.findWithQuery(LocalInventario.class, "Select * from LOCAL_INVENTARIO where inventario = ? and servidor_Conferencia = ? ", String.valueOf(idInventario), String.valueOf(idServidor));

        Log.i("tamanho ", String.valueOf(l.size()));

        adapterLocaisInventario = new AdapterLocaisInventario(this, l);
        listaLocaisInventario.setAdapter(adapterLocaisInventario);
    }


    public void atualizarLocaisInventario(View view){



        try {




            LocaisInventarioWS buscaLocaisInventario= new LocaisInventarioWS(this);
            //http://172.21.150.124:8080
         //   buscaLocaisInventario.execute("http://200.17.98.122:8080/cronos/rest/service/locaisInventario/"+idInventario+"/"+idServidor);
          //  buscaLocaisInventario.execute("http://172.21.150.124:8080/cronos/rest/service/locaisInventario/"+idInventario+"/"+idServidor);
            buscaLocaisInventario.execute(Caminho.Caminho()+"/locaisInventario/"+idInventario+"/"+idServidor);


            Gson gson = new Gson();
            List<LocalInventario> listaInventarios = gson.fromJson(buscaLocaisInventario.get(), new TypeToken<List<LocalInventario>>(){}.getType());




            Servidor servidor = new Servidor();

            for(LocalInventario l : listaInventarios){
              servidor =  Servidor.findById(Servidor.class, idServidor);

               if(servidor == null){
                   l.getServidorConferencia().setId((long) idServidor);
                   l.getServidorConferencia().save();

               }else {
                   l.setServidorConferencia(servidor);

               }
                l.getLocal().save();
                l.save();
           }

           List<LocalInventario> locais = LocalInventario.listAll(LocalInventario.class);

           for(LocalInventario l : locais){
               boolean verifica = false;
                for(LocalInventario localWeb : listaInventarios){
                    if(l.getId() == localWeb.getId()){
                        verifica = true;
                    }
                }
                if(!verifica)
                    l.delete();
           }

           preencherAdapter();



      } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (ExecutionException e) {
            e.printStackTrace();

        }



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
    protected void onPostResume() {
        super.onPostResume();
        preencherAdapter();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LocalInventario locaisInventario  = (LocalInventario) parent.getAdapter().getItem(position);

        Intent itl = new Intent(this, ItensConferidos.class);

        itl.putExtra("idLocaisInventario",locaisInventario.getId());

        startActivity(itl);
    }




}
