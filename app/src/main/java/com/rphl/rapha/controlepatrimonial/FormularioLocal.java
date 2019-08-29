package com.rphl.rapha.controlepatrimonial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.rphl.rapha.controlepatrimonial.modelo.Local;

public class FormularioLocal extends AppCompatActivity {
    Local local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_local);
    }

    public void cadastrarLocal(View view){
        local = new Local();
        EditText objDesc = (EditText) findViewById(R.id.etLocalCadDesc);

        if (Local.find(Local.class, "descricao = ?", objDesc.getText().toString().trim()).size() > 0){
            Toast.makeText(this, "Local já inserido", Toast.LENGTH_SHORT).show();
        } else {
            local.setDescricao(objDesc.getText().toString());

            //insere no banco
            local.save();
        }

        finish();
    }
}
