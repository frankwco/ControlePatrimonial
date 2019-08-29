package com.rphl.rapha.controlepatrimonial.ws;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by aluno on 18/07/18.
 */

public class AbrirDialog {

    public static void abrirDialogTrasmitir(Activity activity,ProgressDialog progressDialog){

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Aguarde");
        progressDialog.setMessage("Transmitindo dados");
        progressDialog.show();
    }

    public static void abrirDialogAguarde(Activity activity,ProgressDialog progressDialog){

        progressDialog.setCancelable(false);
        progressDialog.setTitle("Aguarde");
        progressDialog.setMessage("Processando...");
        progressDialog.show();
    }
}
