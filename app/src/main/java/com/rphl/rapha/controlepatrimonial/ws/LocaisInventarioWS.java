package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.rphl.rapha.controlepatrimonial.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by aluno on 05/07/18.
 */

public class LocaisInventarioWS extends AsyncTask<String, Integer, String> {

    Activity activity;
    ProgressBar progressBar;

    public LocaisInventarioWS(Activity activity){
        this.activity = activity;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBarLocalInventario);
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
