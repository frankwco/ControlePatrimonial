package com.rphl.rapha.controlepatrimonial.ws;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rphl.rapha.controlepatrimonial.modelo.Equipamento;
import com.rphl.rapha.controlepatrimonial.modelo.EquipamentoInventario;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aluno on 11/07/18.
 */

public class TransmitirWS extends AsyncTask<List<EquipamentoInventario>, Integer, String> {


    Context context;

    public TransmitirWS(Context context) {
        this.context = context;
    }

    // já executa no principal
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show(); // aqui vai ter um retorno automático
    }

    // executa em uma thed alternativa, fora do fluxo de execução principal.
    // a entrada tem q ser o tipo que vai enviar
    @Override
    protected String doInBackground(List<EquipamentoInventario>... equipamento) {
        URL url = null;
        String retorno = "Erro ao Transmitir os dados";
        try {


           url = new URL(Caminho.Caminho()+"/listaConferencia");
         //   url = new URL("http://172.21.150.124:8080/cronos/rest/service/listaConferencia");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());


            Gson gson = new Gson();
            os.writeBytes(gson.toJson(equipamento[0]));
            Log.i("data ", gson.toJson(equipamento[0]));

            os.flush();
            os.close();


            if(conn.getResponseCode() == 200)
                retorno = "Dados Transmitidos com Sucesso !!! ";
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

        return retorno;
    }

}