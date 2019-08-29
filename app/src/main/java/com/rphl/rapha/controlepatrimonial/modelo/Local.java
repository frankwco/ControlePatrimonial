package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

/**
 * Created by rapha on 20/08/2017.
 */

public class Local extends SugarRecord{



    private String descricao;

    



    public Local() {
    }

    public Local(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
