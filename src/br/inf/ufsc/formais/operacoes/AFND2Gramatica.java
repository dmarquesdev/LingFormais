/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.model.gramatica.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class AFND2Gramatica {

    public static Gramatica converterParaGramatica(AutomatoFinitoNaoDeterministico afnd) {
        SimboloNaoTerminal simboloInicial = new SimboloNaoTerminal(afnd.getEstadoInicial().getId());
        Set<SimboloNaoTerminal> naoTerminais = new LinkedHashSet<>();
        Set<SimboloTerminal> terminais = new LinkedHashSet<>();
        Set<RegraProducao> regras = new LinkedHashSet<>();

        for (Estado estado : afnd.getEstados()) {
            if (estado instanceof EstadoFinal) {
                SimboloTerminal term = new SimboloTerminal(estado.getId());
                terminais.add(term);
            } else {
                SimboloNaoTerminal nterm = new SimboloNaoTerminal(estado.getId());
                naoTerminais.add(nterm);
            }
        }

        for (Entrada entrada : afnd.getTransicoes().keySet()) {
            for (Estado estado : afnd.getEstadosTransicao(entrada).get()) {
                RegraProducao regra = new RegraProducao();
                SimboloNaoTerminal producao = new SimboloNaoTerminal(entrada.getEstado().getId());
                for (SimboloNaoTerminal snt : naoTerminais) {
                    if (snt.equals(producao)) {
                        producao = snt;
                    }
                }

                regra.setSimboloProducao(producao);

                SimboloTerminal term = new SimboloTerminal(entrada.getSimbolo().getReferencia());
                for (SimboloTerminal st : terminais) {
                    if (term.equals(st)) {
                        term = st;
                    }
                }

                regra.getCadeiaProduzida().setSimboloTerminal(term);
                
                SimboloNaoTerminal prox = new SimboloNaoTerminal(estado.getId());

                for (SimboloNaoTerminal snt : naoTerminais) {
                    if (snt.equals(prox)) {
                        prox = snt;
                    }
                }

                regra.getCadeiaProduzida().setSimboloNaoTerminal(prox);

                regras.add(regra);
            }
        }

        return new Gramatica(naoTerminais, terminais, regras, simboloInicial);
    }
}
