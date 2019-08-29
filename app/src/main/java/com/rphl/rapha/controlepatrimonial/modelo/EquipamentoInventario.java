package com.rphl.rapha.controlepatrimonial.modelo;

import android.widget.Spinner;

import com.orm.SugarRecord;

import java.util.Date;


public class EquipamentoInventario extends SugarRecord {


    private Date dataConferencia;

    private String dataConferenciaFormatada;


    private boolean status;

    private String estado;

    private String observacoes;

    private String trocarEtiqueta;

    private LocalInventario localInventario;

    private Tombamento tombamento;

    private int quantidade;

    public EquipamentoInventario() {
    }


    public Date getDataConferencia() {
        return dataConferencia;
    }

    public void setDataConferencia(Date dataConferencia) {
        this.dataConferencia = dataConferencia;
    }






    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }



    public LocalInventario getLocalInventario() {
        return localInventario;
    }

    public void setLocalInventario(LocalInventario localInventario) {
        this.localInventario = localInventario;
    }

    public Tombamento getTombamento() {
        return tombamento;
    }

    public void setTombamento(Tombamento tombamento) {
        this.tombamento = tombamento;
    }

    public void setEstado(Spinner objEstadoConservacao) {
    }

    public String getDataConferenciaFormatada() {
        return dataConferenciaFormatada;
    }

    public void setDataConferenciaFormatada(String dataConferenciaFormatada) {
        this.dataConferenciaFormatada = dataConferenciaFormatada;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getTrocarEtiqueta() {
        return trocarEtiqueta;
    }

    public void setTrocarEtiqueta(String trocarEtiqueta) {
        this.trocarEtiqueta = trocarEtiqueta;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
