package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

/**
 * Created by aluno on 09/07/18.
 */

public class Servidor extends SugarRecord{




    private String nome;

    private String siape;

    public Servidor(){

    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }
}
