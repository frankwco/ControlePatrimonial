package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rphl.rapha.controlepatrimonial.InventarioTela;
import com.rphl.rapha.controlepatrimonial.R;
import com.rphl.rapha.controlepatrimonial.modelo.LoginUser;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

/**
 * Created by aluno on 04/07/18.
 */

public class LoginWS  extends AsyncTask <LoginUser, Integer, String>{




    Activity activity;
    ProgressBar progressBar;
    CheckBox continuaLogado;

    public LoginWS(Activity activity){
        this.activity = activity;
    }



    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.INVISIBLE);

        continuaLogado = (CheckBox)  activity.findViewById(R.id.ckConectado);

        long id;
        if (s.equals(""))
            id = 0;
        else
            id = Long.parseLong(s);

        try {
            if (id > 0) {
                Intent itl = new Intent(activity, InventarioTela.class);
                itl.putExtra("id", id);
               activity.startActivity(itl);


                SharedPreferences sharedPreferences = activity.getSharedPreferences("chaveEmail", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putLong("chaveEmail", id);
                editor.apply();

                SharedPreferences sharedPreferencesCheck = activity.getSharedPreferences("chaveCheck", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorCheck = sharedPreferencesCheck.edit();
                if (continuaLogado.isChecked())
                    editorCheck.putBoolean("chaveCheck", true);
                else
                    editorCheck.putBoolean("chaveCheck", false);

                editorCheck.apply();


            } else {
                Toast.makeText(activity, "Login ou senha incorretos", Toast.LENGTH_SHORT).show(); // aqui vai ter um retorno automático
            }
    } catch (Exception e) {
        e.printStackTrace();
    }





}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }



    @Override
    protected String doInBackground(LoginUser... loginUsers) {
        URL url = null;
        String response = "";
        try {
            LoginUser login = loginUsers[0];



          //  url = new URL("http://200.17.98.122:8080/cronos/rest/service/logar/"+login.getLogin()+"/"+login.getSenha());
          //  url = new URL("http://172.21.150.124:8080/cronos/rest/service/logar/"+login.getLogin()+"/"+login.getSenha());
            url = new URL(Caminho.Caminho()+"/logar/"+login.getLogin()+"/"+login.getSenha());


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.connect();

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());

            Gson gson = new Gson();
            os.writeBytes(gson.toJson(login));


            os.flush();
            os.close();



            int responseCode=conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }

            Log.i("STATUS", String.valueOf(conn.getResponseCode()));
            Log.i("MSG", conn.getResponseMessage());

            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }


    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/
}
