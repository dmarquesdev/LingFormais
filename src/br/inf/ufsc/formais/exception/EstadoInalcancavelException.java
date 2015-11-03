/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.exception;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.Estado;

/**
 *
 * @author Diego
 */
public class EstadoInalcancavelException extends Exception {

    private Estado estado;
    private Simbolo simbolo;

    public EstadoInalcancavelException(Estado estado, Simbolo simbolo) {
        this.estado = estado;
        this.simbolo = simbolo;
    }

    public Estado getEstado() {
        return estado;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

}
