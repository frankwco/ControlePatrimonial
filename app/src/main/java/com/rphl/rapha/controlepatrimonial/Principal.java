package com.rphl.rapha.controlepatrimonial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.rphl.rapha.controlepatrimonial.adapter.AdapterLocal;
import com.rphl.rapha.controlepatrimonial.modelo.Local;

import java.util.List;
//https://bitbucket.org/adriel1010/aplicativopatrimonio/
public class Principal extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private AdapterLocal adapterLocal;
    ListView listaLocais;
    private int idServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //https://stackoverflow.com/questions/33031570/android-sugar-orm-no-such-table-exception
        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());

       listaLocais = (ListView) findViewById(R.id.lvLocal);
        listaLocais.setOnItemClickListener(this);







    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Local local = (Local) parent.getAdapter().getItem(position);

        Intent itl = new Intent(this, Levantamento.class);

        itl.putExtra("local",String.valueOf(local.getId()));
        //passar a lista de receitas da categoria

        startActivity(itl);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        preencheListaLocal();
    }

//    Button bt = (Button) findViewById(R.id.btAddLocal);
//    bt.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            Intent itCadCateg = new Intent(getApplicationContext(), FormularioCategoria.class);
//            startActivity(itCadCateg);
//        }
//    });

    public void adicionaLocal(View view) {
        Intent itLocalCad = new Intent(this, FormularioLocal.class);
        startActivity(itLocalCad);
    }

    private void preencheListaLocal() {



        adapterLocal = new AdapterLocal(this, getListaLocais());
        listaLocais.setAdapter(adapterLocal);
    }

    private List<Local> getListaLocais() {



        List<Local> li = Local.listAll(Local.class);





        return li;
    }

}
