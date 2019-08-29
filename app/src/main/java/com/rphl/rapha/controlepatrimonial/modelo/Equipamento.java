package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

/**
 * Created by aluno on 10/07/18.
 */

public class Equipamento extends SugarRecord{

    private String descricao;

    private boolean status;


    public Equipamento(){

    }





    public String getDescricao() {
        return descricao;
    }



    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }



    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
