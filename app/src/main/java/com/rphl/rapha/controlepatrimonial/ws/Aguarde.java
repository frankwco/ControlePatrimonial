package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * Created by aluno on 18/07/18.
 */

public class Aguarde extends AsyncTask<Integer, String, String> {


    Activity activity;
    ProgressDialog mProgressBar;

    public Aguarde(Activity activity, ProgressDialog progressDialog){
        this.activity = activity;
        this.mProgressBar = progressDialog;

    }



    @Override
    protected String doInBackground(Integer... integers) {

        int progress = 0;
        int total = integers[0];

        while (progress <= total) {

            try {

                Thread.sleep(200); // 2 segundos

            } catch(InterruptedException e) {

            }

            // String m = progress % 2 == 0 ? "dados usuário" : "contatos";

            // exibimos o progresso
            this.publishProgress(String.valueOf(progress), String.valueOf(total), "Aguarde..." );

            progress++;
        }

        return "DONE";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

        Float progress = Float.valueOf(values[0]);
        Float total = Float.valueOf(values[1]);

        String message = values[2];

        mProgressBar.setProgress((int) ((progress / total) * 100));
        mProgressBar.setMessage(message);

        // se os valores são iguais, termianos nosso processamento
        if (values[0].equals(values[1])) {
            // removemos a dialog
            mProgressBar.cancel();
        }
    }
}
