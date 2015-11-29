/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.gramatica.regular;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;

/**
 * Regras de produção de uma gramatica.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class RegraProducao {

    private SimboloNaoTerminal simboloProducao;
    private Cadeia cadeiaProduzida;

    public RegraProducao(SimboloNaoTerminal simboloProducao, Cadeia cadeiaProduzida) {
        this.simboloProducao = simboloProducao;
        this.cadeiaProduzida = cadeiaProduzida;
    }

    public RegraProducao() {
        this.cadeiaProduzida = new Cadeia(null);
    }

    public SimboloNaoTerminal getSimboloProducao() {
        return simboloProducao;
    }

    public void setSimboloProducao(SimboloNaoTerminal simboloProducao) {
        this.simboloProducao = simboloProducao;
    }

    public Cadeia getCadeiaProduzida() {
        return cadeiaProduzida;
    }

    public void setCadeiaProduzida(Cadeia cadeiaProduzida) {
        this.cadeiaProduzida = cadeiaProduzida;
    }

    @Override
    public String toString() {
        return simboloProducao.getReferencia() + " -> " + cadeiaProduzida.toString();
    }
}
