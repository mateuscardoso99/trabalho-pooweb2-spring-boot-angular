package com.trabalho.api.request;

import com.trabalho.api.model.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CadastroEndereco {
    @NotBlank(message = "cidade inválida")
    @Size(max = 255)
    private String cidade;

    @NotBlank(message = "uf inválida")
    @Size(min = 2, max = 2)
    private String uf;

    @NotBlank(message = "bairro inválido")
    @Size(max = 255)
    private String bairro;

    @NotBlank(message = "rua inválida")
    @Size(max = 255)
    private String rua;

    @NotBlank(message = "numero inválido")
    @Size(max = 20)
    private String numero;

    @Size(max = 50, message = "tamanho complemento <= 50")
    private String complemento;

    @Size(max = 100, message = "tamanho latitude <= 20")
    private String latitude;

    @Size(max = 100, message = "tamanho longitude <= 20")
    private String longitude;
    
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getUf() {
        return uf;
    }
    public void setUf(String uf) {
        this.uf = uf;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getRua() {
        return rua;
    }
    public void setRua(String rua) {
        this.rua = rua;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Endereco toEndereco(){
        Endereco e = new Endereco();
        e.setBairro(this.getBairro());
        e.setCidade(this.getCidade());
        e.setComplemento(this.getComplemento());
        e.setLatitude(this.getLatitude());
        e.setLongitude(this.getLongitude());
        e.setNumero(this.getNumero());
        e.setRua(this.getRua());
        e.setUf(this.getUf());
        return e;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cidade == null) ? 0 : cidade.hashCode());
        result = prime * result + ((uf == null) ? 0 : uf.hashCode());
        result = prime * result + ((bairro == null) ? 0 : bairro.hashCode());
        result = prime * result + ((rua == null) ? 0 : rua.hashCode());
        result = prime * result + ((numero == null) ? 0 : numero.hashCode());
        result = prime * result + ((complemento == null) ? 0 : complemento.hashCode());
        result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
        result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CadastroEndereco other = (CadastroEndereco) obj;
        if (cidade == null) {
            if (other.cidade != null)
                return false;
        } else if (!cidade.equals(other.cidade))
            return false;
        if (uf == null) {
            if (other.uf != null)
                return false;
        } else if (!uf.equals(other.uf))
            return false;
        if (bairro == null) {
            if (other.bairro != null)
                return false;
        } else if (!bairro.equals(other.bairro))
            return false;
        if (rua == null) {
            if (other.rua != null)
                return false;
        } else if (!rua.equals(other.rua))
            return false;
        if (numero == null) {
            if (other.numero != null)
                return false;
        } else if (!numero.equals(other.numero))
            return false;
        if (complemento == null) {
            if (other.complemento != null)
                return false;
        } else if (!complemento.equals(other.complemento))
            return false;
        if (latitude == null) {
            if (other.latitude != null)
                return false;
        } else if (!latitude.equals(other.latitude))
            return false;
        if (longitude == null) {
            if (other.longitude != null)
                return false;
        } else if (!longitude.equals(other.longitude))
            return false;
        return true;
    }

    
}
