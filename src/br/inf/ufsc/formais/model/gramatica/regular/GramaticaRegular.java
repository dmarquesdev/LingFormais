/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.gramatica.regular;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.util.Set;

/**
 * Classe que define uma gramatica.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class GramaticaRegular {

    private Set<SimboloNaoTerminal> simbolosNaoTerminais;
    private Set<SimboloTerminal> simbolosTerminais;
    private Set<RegraProducao> regrasDeProducao;
    private SimboloNaoTerminal simboloInicial;

    public GramaticaRegular(Set<SimboloNaoTerminal> simbolosNaoTerminais, Set<SimboloTerminal> simbolosTerminais, Set<RegraProducao> regrasDeProducao, SimboloNaoTerminal simboloInicial) {
        this.simbolosNaoTerminais = simbolosNaoTerminais;
        this.simbolosTerminais = simbolosTerminais;
        this.regrasDeProducao = regrasDeProducao;
        this.simboloInicial = simboloInicial;
    }

    public Set<SimboloNaoTerminal> getSimbolosNaoTerminais() {
        return simbolosNaoTerminais;
    }

    public void setSimbolosNaoTerminais(Set<SimboloNaoTerminal> simbolosNaoTerminais) {
        this.simbolosNaoTerminais = simbolosNaoTerminais;
    }

    public Set<SimboloTerminal> getSimbolosTerminais() {
        return simbolosTerminais;
    }

    public void setSimbolosTerminais(Set<SimboloTerminal> simbolosTerminais) {
        this.simbolosTerminais = simbolosTerminais;
    }

    public Set<RegraProducao> getRegrasDeProducao() {
        return regrasDeProducao;
    }

    public void setRegrasDeProducao(Set<RegraProducao> regrasDeProducao) {
        this.regrasDeProducao = regrasDeProducao;
    }

    public SimboloNaoTerminal getSimboloInicial() {
        return simboloInicial;
    }

    public void setSimboloInicial(SimboloNaoTerminal simboloInicial) {
        this.simboloInicial = simboloInicial;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        for (RegraProducao regra : regrasDeProducao) {
            out.append(regra.toString()).append("\n");
        }

        return out.toString();
    }
}
