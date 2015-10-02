/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import java.util.Objects;

import br.inf.ufsc.formais.model.Simbolo;

/**
 *
 * @author Diego
 */
public class Entrada {

    protected Estado estado;
    protected Simbolo simbolo;

    public Entrada(Estado estado, Simbolo simbolo) {
        this.estado = estado;
        this.simbolo = simbolo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.estado);
        hash = 53 * hash + Objects.hashCode(this.simbolo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.simbolo, other.simbolo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return estado.toString() + ", " + simbolo.toString();
    }
    
    

}
