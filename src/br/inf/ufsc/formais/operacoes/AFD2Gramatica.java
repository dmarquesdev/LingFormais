/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import java.util.LinkedHashSet;
import java.util.Set;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.model.gramatica.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

/**
 *
 * @author Diego
 */
public class AFD2Gramatica {
    public static Gramatica converterParaGramatica(AutomatoFinitoDeterministico afd) {
        SimboloNaoTerminal simboloInicial = new SimboloNaoTerminal(afd.getEstadoInicial().getId());
        Set<SimboloNaoTerminal> naoTerminais = new LinkedHashSet<>();
        Set<SimboloTerminal> terminais = new LinkedHashSet<>();
        Set<RegraProducao> regras = new LinkedHashSet<>();

        for (Estado estado : afd.getEstados()) {
            if (estado instanceof EstadoFinal) {
                SimboloTerminal term = new SimboloTerminal(estado.getId());
                terminais.add(term);
            } else {
                SimboloNaoTerminal nterm = new SimboloNaoTerminal(estado.getId());
                naoTerminais.add(nterm);
            }
        }

        for (Entrada entrada : afd.getTransicoes().keySet()) {
            RegraProducao regra = new RegraProducao();
            SimboloNaoTerminal producao = new SimboloNaoTerminal(entrada.getEstado().getId());
            regra.setSimboloProducao(producao);

            SimboloTerminal term = new SimboloTerminal(entrada.getSimbolo().getReferencia());
            regra.getCadeiaProduzida().setSimboloTerminal(term);

            if (afd.getEstadoTransicao(entrada) != null
                    && !afd.getEstadoTransicao(entrada).equals(entrada.getEstado())) {
                SimboloNaoTerminal prox = new SimboloNaoTerminal(afd.getEstadoTransicao(entrada).getId());
                regra.getCadeiaProduzida().setSimboloNaoTerminal(prox);
            } else if (afd.getEstadoTransicao(entrada) != null
                    && afd.getEstadoTransicao(entrada).equals(entrada.getEstado())) {
                RegraProducao loop = new RegraProducao();
                loop.setSimboloProducao(producao);
                loop.getCadeiaProduzida().setSimboloTerminal(term);
                loop.getCadeiaProduzida().setSimboloNaoTerminal(
                        new SimboloNaoTerminal(afd.getEstadoTransicao(entrada).getId()));
                regras.add(loop);
            }

            regras.add(regra);
        }

        return new Gramatica(naoTerminais, terminais, regras, simboloInicial);
    }
}
