package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

/**
 * Created by aluno on 06/07/18.
 */

public class Localidades extends SugarRecord{


    private String descricao;

    private String codigoLocalidade;


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigoLocalidade() {
        return codigoLocalidade;
    }

    public void setCodigoLocalidade(String codigoLocalidade) {
        this.codigoLocalidade = codigoLocalidade;
    }
}
