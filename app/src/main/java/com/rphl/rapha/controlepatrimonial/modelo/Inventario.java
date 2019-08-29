package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

import java.util.Date;



public class Inventario extends SugarRecord {



    private Date dataInicio;

    private Date dataTermino;

    private String descricao;

    private boolean status;


    public Inventario() {
    }



    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(Date dataTermino) {
        this.dataTermino = dataTermino;
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
