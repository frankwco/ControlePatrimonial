package com.rphl.rapha.controlepatrimonial.modelo;

import com.orm.SugarRecord;

/**
 * Created by aluno on 05/07/18.
 */

public class LocalInventario extends SugarRecord{



    public LocalInventario()  {
    }
    
    private String situacao;

    private boolean status;

    private Localidades local;

    private Servidor servidorConferencia;

    private Inventario inventario;






    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Localidades getLocal() {
        return local;
    }

    public void setLocal(Localidades local) {
        this.local = local;
    }


    public Servidor getServidorConferencia() {
        return servidorConferencia;
    }

    public void setServidorConferencia(Servidor servidorConferencia) {
        this.servidorConferencia = servidorConferencia;
    }


}
