/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministicoGeneralizado;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.AFD2AFNDG;
import br.inf.ufsc.formais.operacoes.AFNDG2ER;

/**
 *
 * @author Diego
 */
public class AutomatoFinitoDeterministicoTeste {

    public void runTest() {
        Set<Estado> estados = new LinkedHashSet<>();
        Set<EstadoFinal> finais = new LinkedHashSet<>();
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        Map<Entrada, Estado> transicoes = new HashMap<>();
        
        EstadoInicial q0 = new EstadoInicial("q0");
        EstadoFinal q1 = new EstadoFinal("q1");
        EstadoFinal q2 = new EstadoFinal("q2");
        
        estados.add(q0);
        estados.add(q1);
        estados.add(q2);
        
        finais.add(q1);
        finais.add(q2);
        
        
        Simbolo a = new Simbolo("a");
        Simbolo b = new Simbolo("b");
        
        simbolos.add(a);
        simbolos.add(b);
        
        Alfabeto alfa = new Alfabeto(simbolos);
        
        Entrada e1 = new Entrada(q0, a);
        Entrada e2 = new Entrada(q0, b);
        Entrada e3 = new Entrada(q1, a);
        Entrada e4 = new Entrada(q1, b);
        Entrada e5 = new Entrada(q2, a);
        Entrada e6 = new Entrada(q2, b);
        
        transicoes.put(e1, q1);
        transicoes.put(e2, q2);
        transicoes.put(e3, q1);
        transicoes.put(e4, q2);
        transicoes.put(e5, q1);
        transicoes.put(e6, q2);
        
        AutomatoFinitoDeterministico dfa = new AutomatoFinitoDeterministico(estados, alfa, q0, finais, transicoes);
        
        System.out.println(dfa.toString());
        
//        Gramatica gr = AFD2Gramatica.converterParaGramatica(dfa);
//        
//        System.out.println(gr.toString());
        
        AutomatoFinitoNaoDeterministicoGeneralizado afndg = AFD2AFNDG.converterParaAFDNG(dfa);
        
        System.out.println(afndg.toString());
        
        ExpressaoRegular er = AFNDG2ER.converterParaER(afndg);
        
        System.out.println(er.toString());
    }
}
