/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.gramatica;

import br.inf.ufsc.formais.model.Simbolo;

/**
 * Define um simbolo terminal de uma gramatica.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class SimboloTerminal extends Simbolo {

    public static final SimboloTerminal EPSILON = new SimboloTerminal("ε");

    public static final SimboloTerminal FINAL_SENTENCA = new SimboloTerminal("$");

    public SimboloTerminal(String referencia) {
        super(referencia);
    }

    @Override
    public void setReferencia(String referencia) {
        super.setReferencia(referencia.toLowerCase());
    }
}
