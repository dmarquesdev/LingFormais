/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinito;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Matheus
 */
public class ER2AFND {

    /*
     public AutomatoFinitoNaoDeterministico converterParaAutomato(ExpressaoRegular er) {
     List<Simbolo> subSimbolos = new ArrayList<>();
     ExpressaoRegular subEr = new ExpressaoRegular(subSimbolos);

     int abregrupo = 0;
     for (int i = 0; i < er.getSimbolos().size(); i++) {
     if (er.getSimbolos().get(i).getReferencia().equals("(")) {
     abregrupo++;
     int j = i + 1;
     while (abregrupo > 0) {
     if (er.getSimbolos().get(j).getReferencia().equals("(")) {
     abregrupo++;
     } else if (er.getSimbolos().get(j).getReferencia().equals(")")) {
     abregrupo--;
     }
     if (abregrupo > 0) {
     subSimbolos.add(er.getSimbolos().get(j));
     }
     j++;
     }
     subEr.toAutomato();

     } else if (exp.charAt(i) == '*') {

     } else if (exp.charAt(i) == '|') {

     }
     }

     Matcher matcher1 = Pattern.compile(("([a-z|0-9])")).matcher(exp); //er com um simbolo
     Matcher matcher2 = Pattern.compile(("([a-z|0-9])")).matcher(exp);
     Matcher matcher3 = Pattern.compile(("([a-z|0-9]\\|[a-z|0-9])")).matcher(exp); // a|b
     if (matcher1.matches() && matcher1.groupCount() == 1) {
     System.out.println("ER:= a \n");
     }
     return null;
     }
     */
    public AutomatoFinitoNaoDeterministico AFdeSimbolo(Simbolo s) {

        EstadoInicial einicial = new EstadoInicial("->S");
        EstadoFinal efinal = new EstadoFinal("F*");

        Set<Estado> estados = new LinkedHashSet<>();
        estados.add(einicial);
        estados.add(efinal);

        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(efinal);
        Estados proxEstados = new Estados(prox);

        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.add(efinal);

        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.add(s);
        Alfabeto alfa = new Alfabeto(simbolos);

        Entrada entrada = new Entrada(einicial, s);

        Map<Entrada, Estados> transicoes = new HashMap<>();
        transicoes.put(entrada, proxEstados);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                einicial, estadosAceitacao, transicoes);

        return afnd;
    }

    public AutomatoFinitoNaoDeterministico FechoDeAF(AutomatoFinitoNaoDeterministico af) {
        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico();
        afnd = af;

        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(afnd.getEstadoInicial());
        Estados estados = new Estados(prox);

        for (EstadoFinal efinal : af.getEstadosAceitacao()) {
            Entrada entrada = new Entrada(efinal, Simbolo.EPSILON);

            afnd.addTransicao(entrada, estados);
        }

        return afnd;
    }

    public AutomatoFinitoNaoDeterministico OuEntreAFs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
        EstadoInicial novoInicial = new EstadoInicial("S");

        //Criando estados que serão substituidos pelos iniciais de af1 e af2
        Estado ei_af1 = af1.removeEstadoInicial();
        Estado ei_af2 = af2.removeEstadoInicial();

        //criando transicao nao deterministica do inicial para cada um dos iniciais dos afnd's
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(ei_af2);
        estadosProx.add(ei_af1);
        Estados proxEstados = new Estados(estadosProx);
        Entrada entrada = new Entrada(novoInicial, Simbolo.EPSILON);

       
        //Junção de estados
        //precisamos alterar o id dos estados que possivelmente terão ids iguais
        for (Estado e : af1.getEstados()) {
            e.setId("1." + e.getId());
        }
        for (Estado e : af2.getEstados()) {
            e.setId("2." + e.getId());
        }
        
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());
        estados.add(novoInicial);
        

        //Junção de estados finais
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.addAll(af1.getEstadosAceitacao());
        estadosAceitacao.addAll(af2.getEstadosAceitacao());

        //Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

        //Junção de transições
        Map<Entrada, Estados> transicoes = new HashMap<>();
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());
        transicoes.put(entrada, proxEstados);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                novoInicial, estadosAceitacao, transicoes);

        return afnd;
    }

    public AutomatoFinitoNaoDeterministico ConcatenaAFs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
        Map<Entrada, Estados> transicoes = new HashMap<>();

        //Cria estado estado para subtiruir estado inicial de af2
        Estado eaf2 = af2.removeEstadoInicial();
       
        //Cria uma transição de cada estado final de af1 para o estado inicial de af2
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(eaf2);
        Estados proxEstados = new Estados(estadosProx);
        
        for (EstadoFinal e : af1.getEstadosAceitacao()) {
            Estado eaf1 = af1.removeEstadoFinal(e);

            //cria a transicao do final de af1 para af2
            Entrada entrada = new Entrada(eaf1, Simbolo.EPSILON);
            transicoes.put(entrada, proxEstados);
            
        }

        //Junção de estados
        //precisamos alterar o id dos estados que possivelmente serão iguais
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());

        //Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

        //Junção de transições
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
            af1.getEstadoInicial(), af2.getEstadosAceitacao(),
            transicoes);

        return afnd;
    }

    public AutomatoFinitoNaoDeterministico AFLingVazia() {
        
        EstadoInicial einicial = new EstadoInicial("S");
        Set<Estado> estados = new LinkedHashSet<>();
        estados.add(einicial);
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        Alfabeto a = new Alfabeto(null);
        Map<Entrada, Estados> transicoes = new HashMap<>();
        Set<EstadoFinal> ef = new LinkedHashSet<>();

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, a,
            einicial, estadosAceitacao, transicoes);
        return afnd;
    }

    public AutomatoFinito AFPalavraVazia() {
        //não implementado
        return null;
    }
}
