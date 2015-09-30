/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.model.gramatica.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class Gramatica2AFD {
    public static AutomatoFinitoDeterministico converterParaAFD(Gramatica g) {
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

        Map<Entrada, Estado> transicoes = new HashMap<>();
        for (RegraProducao regra : g.getRegrasDeProducao()) {

            Estado atual = new Estado(regra.getSimboloProducao().getReferencia());
            Estado prox = null;

            Simbolo entrada = regra.getCadeiaProduzida().getSimboloTerminal();
            if (regra.getCadeiaProduzida().isTerminal()) {
                prox = fim;
            } else {
                prox = new Estado(regra.getCadeiaProduzida().getSimboloNaoTerminal().getReferencia());
            }
            
            Entrada ent = new Entrada(atual, entrada);
            transicoes.put(ent, prox);
        }

        return new AutomatoFinitoDeterministico(estados, alfa, estadoInicial, finais, transicoes);
    }
}
