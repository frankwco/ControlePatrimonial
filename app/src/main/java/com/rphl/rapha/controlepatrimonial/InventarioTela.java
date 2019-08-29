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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.rphl.rapha.controlepatrimonial.adapter.AdapterInventario;
import com.rphl.rapha.controlepatrimonial.modelo.Inventario;
import com.rphl.rapha.controlepatrimonial.ws.AbrirDialog;
import com.rphl.rapha.controlepatrimonial.ws.Aguarde;
import com.rphl.rapha.controlepatrimonial.ws.Caminho;
import com.rphl.rapha.controlepatrimonial.ws.InventariosWS;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InventarioTela extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private long idServidor;
    ListView listaInventario;
    private AdapterInventario adapterINventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);


        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
       // schemaGenerator.deleteTables(new SugarDb(this).getDB());




        Intent itl = getIntent();
        Bundle extras = itl.getExtras();
        if (extras != null) {
            idServidor = extras.getLong("id");
        }

        listaInventario = (ListView) findViewById(R.id.lvInventario);
        listaInventario.setOnItemClickListener(this);

       

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu2,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();




        if(id == R.id.sair2){

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


        if(id == R.id.limpaDados){



            final Context context = this;

            new AlertDialog.Builder(this)
                    .setTitle("Atenção")
                    .setMessage("Todos os dados serão excluidos, tem certeza que deseja continuar ?")
                    .setPositiveButton("sim",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    SugarContext.init(getApplicationContext());
                                    SchemaGenerator schemaGenerator = new SchemaGenerator(context);
                                    schemaGenerator.deleteTables(new SugarDb(context).getDB());


                                    SharedPreferences sharedPreferences = getSharedPreferences("chaveEmail", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putLong("chaveEmail", 0);
                                    editor.apply();

                                    SharedPreferences sharedPreferencesCheck = getSharedPreferences("chaveCheck", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorCheck = sharedPreferencesCheck.edit();
                                    editorCheck.putBoolean("chaveCheck", false);

                                    editorCheck.apply();

                                    Intent itl = new Intent(context, Login.class);
                                    startActivity(itl);

                                }
                            })
                    .setNegativeButton("não", null)
                    .show();



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        carregarLista();
    }




    public void atualizarDados(View view) {



        try {

        InventariosWS buscarInventarios = new InventariosWS(this);

        buscarInventarios.execute(Caminho.Caminho()+"/inventarios");
       //buscarInventarios.execute("http://172.21.150.124:8080/cronos/rest/service/inventarios");

        Gson gson = new Gson();
        List<Inventario> listaInventariosWeb = gson.fromJson(buscarInventarios.get(), new TypeToken<List<Inventario>>(){}.getType());

        for(Inventario i : listaInventariosWeb) {

             i.save();
        }

        List<Inventario> listaInventarioLocal = Inventario.listAll(Inventario.class);

        for(Inventario i : listaInventarioLocal){

            boolean encontrou = true;
            for(Inventario web : listaInventariosWeb){
                if(web.getId() == i.getId())
                    encontrou = false;
            }
           if(encontrou)
               i.delete();
        }




        carregarLista();


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public void carregarLista(){

        List<Inventario>  listaInventariosLocal = Inventario.listAll(Inventario.class);

        for(Inventario o : listaInventariosLocal){
            Log.i("invent id :: ", String.valueOf(o.getId()));
        }

        adapterINventario = new AdapterInventario(this, listaInventariosLocal);
        listaInventario.setAdapter(adapterINventario);

    }








    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Inventario inventario  = (Inventario) parent.getAdapter().getItem(position);

        Intent itl = new Intent(this, LocaisInventarioTela.class);

        itl.putExtra("idServidor",idServidor);
        itl.putExtra("idInventario",inventario.getId());
        //passar a lista de receitas da categoria

        startActivity(itl);
    }
}
