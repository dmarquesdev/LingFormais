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
import br.inf.ufsc.formais.model.gramatica.regular.GramaticaRegular;
import br.inf.ufsc.formais.model.gramatica.regular.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

/**
 * Converte um automato finito deterministico em uma gramatica.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFD2Gramatica {

    public static GramaticaRegular converterParaGramatica(AutomatoFinitoDeterministico afd) {
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

            if (afd.getEstadoTransicao(entrada) != null) {
                SimboloNaoTerminal prox = new SimboloNaoTerminal(afd.getEstadoTransicao(entrada).getId());

                for (SimboloNaoTerminal snt : naoTerminais) {
                    if (snt.equals(prox)) {
                        prox = snt;
                    }
                }

                regra.getCadeiaProduzida().setSimboloNaoTerminal(prox);
            }

            regras.add(regra);
        }

        return new GramaticaRegular(naoTerminais, terminais, regras, simboloInicial);
    }
}
