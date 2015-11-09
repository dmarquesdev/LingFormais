/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.exception;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.Estados;

/**
 *
 * @author Diego
 */
public class EstadosInalcancaveisException extends AnaliseLexicaException {

    private Estados estados;
    private Simbolo simbolo;

    public EstadosInalcancaveisException(Estados estado, Simbolo simbolo) {
        this.estados = estado;
        this.simbolo = simbolo;
    }

    public Estados getEstados() {
        return estados;
    }

    public Simbolo getSimbolo() {
        return simbolo;
    }

}
