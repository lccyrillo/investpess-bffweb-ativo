package com.cyrillo.bff.investpessbffwebativo.core.entidade;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "log_processamento")
public class LogProcessamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigoprocessamento")
    private long codigoProcessamento;

    @Column(name = "descricaologprocessamento")
    private String descricaoLogProcessamento;

    @Column(name = "datalogprocessamento")
    private Date dataLogProcessamento;


    public long getCodigoProcessamento() {
        return codigoProcessamento;
    }

    public void setCodigoProcessamento(long codigoProcessamento) {
        this.codigoProcessamento = codigoProcessamento;
    }

    public String getDescricaoLogProcessamento() {
        return descricaoLogProcessamento;
    }

    public void setDescricaoLogProcessamento(String descricaoLogProcessamento) {
        this.descricaoLogProcessamento = descricaoLogProcessamento;
    }

    public Date getDataLogProcessamento() {
        return dataLogProcessamento;
    }

    public void setDataLogProcessamento(Date dataLogProcessamento) {
        this.dataLogProcessamento = dataLogProcessamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogProcessamento)) return false;
        LogProcessamento that = (LogProcessamento) o;
        return getCodigoProcessamento() == that.getCodigoProcessamento() && getDescricaoLogProcessamento().equals(that.getDescricaoLogProcessamento()) && getDataLogProcessamento().equals(that.getDataLogProcessamento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodigoProcessamento(), getDescricaoLogProcessamento(), getDataLogProcessamento());
    }
}

