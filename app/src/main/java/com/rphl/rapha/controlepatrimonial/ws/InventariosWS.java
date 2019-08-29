package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rphl.rapha.controlepatrimonial.R;
import com.rphl.rapha.controlepatrimonial.modelo.Inventario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by aluno on 05/07/18.
 */

public class InventariosWS extends AsyncTask<String, Integer, String> {

    Activity activity;
    ProgressBar progressBar;

    private ProgressDialog mProgressBar;



    public InventariosWS(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);


      /*  Gson gson = new Gson();
        List<Inventario> listaInventariosWeb = gson.fromJson(s, new TypeToken<List<Inventario>>(){}.getType());

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
        }*/



    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBarInventario);
        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    protected String doInBackground(String... strings) {
        String jsonRetorno = null;
        try{
            URL url = new URL(strings[0]);
            InputStream stream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String linha;
            while( (linha = bufferedReader.readLine()) != null)
            {
                builder.append(linha);
            }
            jsonRetorno = builder.toString();

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return jsonRetorno;
    }
}
