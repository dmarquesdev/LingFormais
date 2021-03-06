/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.model.gramatica.regular.GramaticaRegular;
import br.inf.ufsc.formais.model.gramatica.regular.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;

/**
 *
 * Operações que transformam uma gramatica em um automato finito não deterministico.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Gramatica2AFND {

    public static AutomatoFinitoNaoDeterministico converterParaAFND(GramaticaRegular g) {
        Set<Simbolo> simbAlfa = new LinkedHashSet<>();
        simbAlfa.addAll(g.getSimbolosTerminais());
        Alfabeto alfa = new Alfabeto(simbAlfa);

        EstadoInicial estadoInicial = new EstadoInicial(g.getSimboloInicial().getReferencia());

        Set<Estado> estados = new LinkedHashSet<>();
        for (SimboloNaoTerminal snt : g.getSimbolosNaoTerminais()) {
            Estado est = new Estado(snt.getReferencia());
            estados.add(est);
        }

        Set<EstadoFinal> finais = new LinkedHashSet<>();
        EstadoFinal fim = new EstadoFinal("T");
        finais.add(fim);

        Map<Entrada, Estados> transicoes = new HashMap<>();
        for (RegraProducao regra : g.getRegrasDeProducao()) {
            Estado atual = new Estado(regra.getSimboloProducao().getReferencia());
            Estado prox = null;
            
            for (Estado estado : estados) {
                if(regra.getSimboloProducao().getReferencia().equals(estado.getId())) {
                    atual = estado;
                }
            }

            Simbolo entrada = regra.getCadeiaProduzida().getSimboloTerminal();
            if (regra.getCadeiaProduzida().isTerminal()) {
                prox = fim;
            } else {
                for (Estado estado : estados) {
                    if (regra.getCadeiaProduzida().getSimboloNaoTerminal()
                            .getReferencia().equals(estado.getId())) {
                        prox = estado;
                    }
                }
            }

            Entrada ent = new Entrada(atual, entrada);
            Estados ests = transicoes.get(ent);
            
            if(ests == null) {
                ests = new Estados();
            }
            
            ests.addEstado(prox);
            
            transicoes.put(ent, ests);
        }

        return new AutomatoFinitoNaoDeterministico(estados, alfa, estadoInicial, finais, transicoes);
    }
}
