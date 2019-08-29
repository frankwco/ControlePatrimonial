package com.rphl.rapha.controlepatrimonial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.rphl.rapha.controlepatrimonial.modelo.LoginUser;
import com.rphl.rapha.controlepatrimonial.ws.AbrirDialog;
import com.rphl.rapha.controlepatrimonial.ws.Aguarde;
import com.rphl.rapha.controlepatrimonial.ws.BuscarTimesWS;
import com.rphl.rapha.controlepatrimonial.ws.LoginWS;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    private SharedPreferences pref;

    public static final String PREFS_NAME = "MyPrefsFile";


    EditText usuario, senha;
    TextView savlo;
    CheckBox continuaLogado;


   // private ProgressDialog mProgressBar;

    Activity activity;
    ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        SugarContext.init(getApplicationContext());
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());
      //  schemaGenerator.deleteTables(new SugarDb(this).getDB());


        usuario = (EditText) findViewById(R.id.edEmail);
        senha = (EditText) findViewById(R.id.edSenha);

        continuaLogado = (CheckBox) findViewById(R.id.ckConectado);


        SharedPreferences sharedPreferences = getSharedPreferences("chaveEmail", Context.MODE_PRIVATE);
        Long id = sharedPreferences.getLong("chaveEmail", 0L);

        SharedPreferences sharedPreferencesCheck = getSharedPreferences("chaveCheck", Context.MODE_PRIVATE);
        Boolean continUal = sharedPreferencesCheck.getBoolean("chaveCheck", false);


        if (continUal) {

            Intent itl = new Intent(this, InventarioTela.class);
            itl.putExtra("id", id);
            startActivity(itl);

        }

           // BuscarTimesWS buscarTimesWS = new BuscarTimesWS(this);
            //http://200.17.98.122:8080/cronos/rest/service/listaTimesSP
           //https://200.17.98.122:8443/cronos/rest/service/listaTimesSP
          //  buscarTimesWS.execute("https://200.17.98.122:8443/cronos/rest/service/listaTimesSP");


    }



    @Override
    protected void onStop() {
        super.onStop();


    }






    public void logar(View view) {





        String email = ((EditText) findViewById(R.id.edEmail)).getText().toString();
        String senha = ((EditText) findViewById(R.id.edSenha)).getText().toString();

        LoginUser login = new LoginUser();
        login.setLogin(email);
        login.setSenha(senha);

            LoginWS logar = new LoginWS(this);
            logar.execute(login);

    }
}
