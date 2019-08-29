package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rphl.rapha.controlepatrimonial.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by aluno on 15/06/18.
 */

public class BuscarTimesWS extends AsyncTask<String, Integer, String> {

    Activity activity;

    public BuscarTimesWS(Activity activity){
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


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
