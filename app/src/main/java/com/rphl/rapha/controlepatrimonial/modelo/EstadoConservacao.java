package com.rphl.rapha.controlepatrimonial.modelo;

/**
 * Created by aluno on 10/07/18.
 */

public enum EstadoConservacao {

    BOM("BOM"), RUIM("RUIM"), INSERVIVEL("INSERVIVEL");


    private String friendlyName;

    private EstadoConservacao(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override public String toString(){
        return friendlyName;
    }

}


