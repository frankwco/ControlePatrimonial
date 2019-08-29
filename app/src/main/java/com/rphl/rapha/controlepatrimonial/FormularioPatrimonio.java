package com.rphl.rapha.controlepatrimonial;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rphl.rapha.controlepatrimonial.modelo.EquipamentoInventario;
import com.rphl.rapha.controlepatrimonial.modelo.LocalInventario;
import com.rphl.rapha.controlepatrimonial.modelo.Tombamento;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FormularioPatrimonio extends AppCompatActivity {

    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    static final int RC_EDIT = 10;
    static final int RC_SAVE = 11;
    static final int RS_SUCCESS = 1;
    static final int RS_CANCEL = 0;

    Tombamento tombamento;
    EquipamentoInventario equipamentoInventario;
    TextView objQr;
    EditText objDesc;
    EditText objCodPat;

    EditText objEsp;
    EditText objCor;
    EditText objQtd;
    EditText objMarca;
    EditText objMaterial;
    EditText objSerie;
    EditText objObservacoes;
    EditText objDim;
    RadioGroup objEtiqueta;
    RadioGroup rd_group;

    long idLocaisInventario;

    boolean editFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_patrimonio);

        objQr = (TextView) findViewById(R.id.tvQR);
        objDesc = (EditText) findViewById(R.id.etDescricao);
        objCodPat = (EditText) findViewById(R.id.etCodPat);
        objMaterial = (EditText) findViewById(R.id.etMaterial);
        objEsp = (EditText) findViewById(R.id.etEsp);
        objCor = (EditText) findViewById(R.id.etCor);
        objMarca = (EditText) findViewById(R.id.etMarca);
        objSerie = (EditText) findViewById(R.id.etNSerie);
        objDim = (EditText) findViewById(R.id.etDim);
        objQtd = (EditText) findViewById(R.id.edQTD);
        objObservacoes = (EditText) findViewById(R.id.etObservacao);
        objEtiqueta = (RadioGroup) findViewById(R.id.rbGrupoEtiqueta);
        rd_group = (RadioGroup) findViewById(R.id.rbGrupo);

        try{
            carregarConteudo(getIntent().getExtras());
        }catch(Exception e){
            finish();
            e.printStackTrace();
        }

    }

    private void carregarConteudo(Bundle bundle) throws Exception{
        if(bundle == null)
            throw new Exception("Bundle = Null. Unable to load the Activity");

        switch (bundle.getInt("REQUEST_CODE")){
            case RC_SAVE:
                idLocaisInventario = bundle.getLong("idLocaisInventario");
                tombamento = new Tombamento();
                equipamentoInventario = new EquipamentoInventario();
                editFlag = false;
                break;

            case RC_EDIT:
                long id = bundle.getLong("EQUIPAMENTO_INVENTARIO_ID");
                equipamentoInventario = EquipamentoInventario.findById(EquipamentoInventario.class, id);

                if(equipamentoInventario == null)
                    throw new Exception("Result not found!");

                tombamento = equipamentoInventario.getTombamento();

                objCodPat.setText(tombamento.getCodigo());
                objCor.setText(tombamento.getCor());
                objDim.setText(tombamento.getDimensoes());
                objEsp.setText(tombamento.getEspecificacao());
                objMarca.setText(tombamento.getMarca());
                objMaterial.setText(tombamento.getMaterial());
                objSerie.setText(tombamento.getNumeroSerie());
                objDesc.setText(tombamento.getDescricao());

                objObservacoes.setText(equipamentoInventario.getObservacoes());
                objQtd.setText(String.valueOf(equipamentoInventario.getQuantidade()));

                switch (equipamentoInventario.getEstado()){
                    case "BOM":
                        rd_group.check(R.id.rbBom);
                        break;
                    case "OCIOSO":
                        rd_group.check(R.id.rbOcioso);
                        break;
                    case "INSERVIVEL":
                        rd_group.check(R.id.rbInservivel);
                        break;
                }

                if(equipamentoInventario.getTrocarEtiqueta().equals("sim")){
                    objEtiqueta.check(R.id.rbSim);
                }else{
                    objEtiqueta.check(R.id.rbNao);
                }

                editFlag = true;

                break;

            default:
                throw new Exception("unknown request!");
        }

    }

    public void cadastrarPatrimonio(View view){

        LocalInventario localConferir = equipamentoInventario.getLocalInventario();

        if(localConferir == null)
            localConferir = LocalInventario.findById(LocalInventario.class, idLocaisInventario);

        if(localConferir != null) {

            if (objCodPat.getText().toString().length() > 1) {

                if (!editFlag && EquipamentoInventario.findWithQuery(EquipamentoInventario.class,
                        "Select * from EQUIPAMENTO_INVENTARIO e inner join tombamento t on e.tombamento = t.id  inner join LocaL_Inventario l on e.local_Inventario = l.id  where t.codigo = ? and l.id = ? ",
                        objCodPat.getText().toString().trim(),
                        String.valueOf(localConferir.getId())).size() > 0) {
                    Toast.makeText(this, "Patrimonio já inserido", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    if (objCodPat.getText().toString().length() == 10) {
                        salva(localConferir);
                    } else {
                        Toast.makeText(this, "O número de tombamento deve conter 10 números", Toast.LENGTH_SHORT).show();
                    }
                }

            } else {

                if(objSerie.getText().toString().length() == 0) {
                    Toast.makeText(this, "Informe o número de série", Toast.LENGTH_SHORT).show();
                }else {

                    if (objQtd.getText().toString().length() == 0)
                        Toast.makeText(this, "Informe a quantidade", Toast.LENGTH_SHORT).show();
                    else
                        salva(localConferir);
                }

            }

        }else{
            Toast.makeText(this, "Não foi possível buscar o Local onde se deseja realizar a conferência", Toast.LENGTH_SHORT).show();
        }

    }


    public void salva(LocalInventario localConferir){

        int etiqueta = objEtiqueta.getCheckedRadioButtonId();
        int conservado = rd_group.getCheckedRadioButtonId();

        if (etiqueta > 2) {
            if(conservado > 2) {

                tombamento.setCodigo(objCodPat.getText().toString());
                tombamento.setStatus(true);
                tombamento.setCor(objCor.getText().toString());
                tombamento.setDimensoes(objDim.getText().toString());
                tombamento.setEspecificacao(objEsp.getText().toString());
                tombamento.setMarca(objMarca.getText().toString());
                tombamento.setMaterial(objMaterial.getText().toString());
                tombamento.setNumeroSerie(objSerie.getText().toString());
                tombamento.setDescricao(objDesc.getText().toString());

                tombamento.save();

                Date data = new Date();
                DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                formatDate.format(data);

                equipamentoInventario.setDataConferenciaFormatada(String.valueOf(formatDate.format(data)));


                switch (rd_group.getCheckedRadioButtonId()) {
                    case R.id.rbBom:
                        equipamentoInventario.setEstado("BOM");
                        break;
                    case R.id.rbOcioso:
                        equipamentoInventario.setEstado("OCIOSO");
                        break;
                    case R.id.rbInservivel:
                        equipamentoInventario.setEstado("INSERVIVEL");
                        break;

                }

                equipamentoInventario.setLocalInventario(localConferir);
                if(!objQtd.getText().toString().equals(""))
                    equipamentoInventario.setQuantidade(Integer.parseInt(objQtd.getText().toString()));
                else
                    equipamentoInventario.setQuantidade(0);
                equipamentoInventario.setStatus(true);
                equipamentoInventario.setObservacoes(objObservacoes.getText().toString());
                equipamentoInventario.setTombamento(tombamento);

                switch (objEtiqueta.getCheckedRadioButtonId()) {
                    case R.id.rbSim:
                        equipamentoInventario.setTrocarEtiqueta("sim");
                        break;
                    case R.id.rbNao:
                        equipamentoInventario.setTrocarEtiqueta("nao");
                        break;

                }


                equipamentoInventario.save();

                finish();

            }else{
                Toast.makeText(this, "Informe o estado de conservação", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Informe se é necessário a troca da etiqueta", Toast.LENGTH_SHORT).show();
        }

    }

    //product qr code mode
    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dial
            showDialog(this, "No Scanner Found", "Download a scanner coogde activity?", "Yes", "No").show();
        }
    }

    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final AppCompatActivity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Log.i("FormularioPatrimonio", format);
                Log.i("FormularioPatrimonio", contents);
                Toast toast = Toast.makeText(this, "Leitura:" + contents + " Formato:" + format, Toast.LENGTH_LONG);


                objQr.setText(contents);
                objDesc.setText(contents.split("\n")[1]);
                objCodPat.setText(contents.split("\n")[0]);
                objEsp.setText("o que é? "+contents.split("\n")[2]);
               //toast.show();
            }
        }
    }


}
