/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matheus
 */
public class OperacoesAFND {

    public static int contadorEstados;

    /**
     * Cria um automato mais simples que reconheça palavras que contenham o
     * símbolo s.
     *
     * @param s Um símbolo do alfabeto do automato.
     * @return Um automato finito não determinístico.
     */
    public static AutomatoFinitoNaoDeterministico aFdeSimbolo(Simbolo s) {
//        cria estado inicial e final
        EstadoInicial einicial = new EstadoInicial("q" + contadorEstados);
        ++contadorEstados;
        EstadoFinal efinal = new EstadoFinal("q" + contadorEstados);
        ++contadorEstados;

//        cria um set de estados e adiciona o inicial e o final.
        Set<Estado> estados = new LinkedHashSet<>();
        estados.add(einicial);
        estados.add(efinal);

//        cria set de estados e adicio estado final, será usado para criar a transicao.
        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(efinal);
        Estados proxEstados = new Estados(prox);

//        cria o set de estados aceitacao e adiciona estado final.
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.add(efinal);

//        cria um set de simbolos e adiciona au alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.add(s);
        Alfabeto alfa = new Alfabeto(simbolos);

//        cria uma entrada,composta por estado de saida da transicao e o simbolo pelo qual transita
        Entrada entrada = new Entrada(einicial, s);

//        cria um map de transicoes, cada transicao é composta por uma entrada e os estados para qual a transicao 
//        pelo simbolo em questao leva.
        Map<Entrada, Estados> transicoes = new HashMap<>();
        transicoes.put(entrada, proxEstados);

//        gera o automato.
        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                einicial, estadosAceitacao, transicoes);

        return afnd;
    }

    /**
     * Cria o fecho de um automato. Cria uma transição de cada estado final para
     * o inicial por epsilon.
     *
     * @param af Um automato finito não determinístico.
     * @return Um automato finito não determinsitico.
     */
    public static AutomatoFinitoNaoDeterministico fechoDeAF(AutomatoFinitoNaoDeterministico af) {
        AutomatoFinitoNaoDeterministico afnd;
        afnd = concatenaParaFecho(aFPalavraVazia(), af);
        
        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(afnd.getEstadoInicial());
        Estados estados = new Estados(prox);
        
        Set<EstadoFinal> apoio = new LinkedHashSet<>();
        apoio.addAll(af.getEstadosAceitacao());

//        cria uma transição por epsilon de cada estado final para o inicial.
        for (EstadoFinal efinal : apoio) {
            Entrada entrada = new Entrada(efinal, Simbolo.EPSILON);

            afnd.addTransicao(entrada, estados);
        }

        return afnd;
    }

    /**
     * Cria a operação ou entre dois automatos. Cria um estado inicial novo e
     * transições que saem desse novo estado e vai para os estados iniciais de
     * cada automato por epsilon.
     *
     * @param af1 Um automato finíto não determinsitico.
     * @param af2 Um automato finíto não determinsitico.
     * @return Um automato finíto não determinsitico.
     */
    public static AutomatoFinitoNaoDeterministico ouEntreAFNDs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
//        Cria novo estado inicial.
        EstadoInicial novoInicial = new EstadoInicial("q" + contadorEstados);
        ++contadorEstados;

//        Substitui estados iniciais de af1 e af2 por estados normais
        Estado ei_af1 = af1.removeEstadoInicial();
        Estado ei_af2 = af2.removeEstadoInicial();

//        criando transicao nao deterministica do inicial para cada um dos iniciais dos afnd's
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(ei_af2);
        estadosProx.add(ei_af1);
        Estados proxEstados = new Estados(estadosProx);
        Entrada entrada = new Entrada(novoInicial, Simbolo.EPSILON);

//        Junção de estados
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());
        estados.add(novoInicial);

//        Junção de estados finais
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.addAll(af1.getEstadosAceitacao());
        estadosAceitacao.addAll(af2.getEstadosAceitacao());

//        Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

//        Junção de transições
        Map<Entrada, Estados> transicoes = new HashMap<>();
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());
        transicoes.put(entrada, proxEstados);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                novoInicial, estadosAceitacao, transicoes);

        return afnd;
    }
    

    /**
     * Concatena dois automatos. Cria uma transição por epsilon do(s) estado(s)
     * final(is) de um automato para o inicial do outro automato.
     *
     * @param af1 Um automato finíto não determinsitico.
     * @param af2 Um automato finíto não determinsitico.
     * @return Um automato finíto não determinsitico.
     */
    public static AutomatoFinitoNaoDeterministico concatenaAFs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
        Map<Entrada, Estados> transicoes = new HashMap<>();

//        Cria estado estado para subtiruir estado inicial de af2
        Estado eaf2 = af2.removeEstadoInicial();

//        Cria uma transição de cada estado final de af1 para o estado inicial de af2
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(eaf2);
        Estados proxEstados = new Estados(estadosProx);

        Set<EstadoFinal> apoio = new LinkedHashSet<>();
        apoio.addAll(af1.getEstadosAceitacao());

//        Junção de transições
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());

        for (EstadoFinal e : apoio) {
            Estado eaf1 = af1.removeEstadoFinal(e);
            Entrada entrada = new Entrada(eaf1, Simbolo.EPSILON);

//            Se a entrada ja existe, adiciona o estado à transiçao.
            if (transicoes.containsKey(entrada)) {
                transicoes.get(entrada).addEstado(eaf2);
            } else {
                transicoes.put(entrada, proxEstados);
            }
        }

//        Junção de estados.
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());

//        Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                af1.getEstadoInicial(), af2.getEstadosAceitacao(),
                transicoes);

        return afnd;
    }
    
    

    /**
     * Cria um automato que reconheça a linguagem vazia. Automato que contem
     * apenas estado inicial, não contem estados finais.
     *
     * @return Um automato finíto não determinsitico.
     */
    public static AutomatoFinitoNaoDeterministico aFLingVazia() {

//        cria um automato que tem apenas o estado inicial, todo o resto é nulo.
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

    /**
     * Cria automato que reconheça a palavra vazia. Contem uma transição por
     * epsilon de inicial para final.
     *
     * @return Um automato finíto não determinsitico.
     */
    public static AutomatoFinitoNaoDeterministico aFPalavraVazia() {
//        cria um automato que transita do estado inical ao final por epsilon.
        return aFdeSimbolo(Simbolo.EPSILON);
    }
    
    public static AutomatoFinitoNaoDeterministico concatenaParaFecho(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2){
         Map<Entrada, Estados> transicoes = new HashMap<>();

//        Cria estado estado para subtiruir estado inicial de af2
        Estado eaf2 = af2.removeEstadoInicial();

//        Cria uma transição de cada estado final de af1 para o estado inicial de af2
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(eaf2);
        Estados proxEstados = new Estados(estadosProx);

        Set<EstadoFinal> apoio = new LinkedHashSet<>();
        apoio.addAll(af1.getEstadosAceitacao());

//        Junção de transições
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());

        for (EstadoFinal e : apoio) {
            Entrada entrada = new Entrada(e, Simbolo.EPSILON);

//            Se a entrada ja existe, adiciona o estado à transiçao.
            if (transicoes.containsKey(entrada)) {
                transicoes.get(entrada).addEstado(eaf2);
            } else {
                transicoes.put(entrada, proxEstados);
            }
        }

//        Junção de estados.
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());
        
//        Junção de estados finais
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.addAll(af1.getEstadosAceitacao());
        estadosAceitacao.addAll(af2.getEstadosAceitacao());

//        Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                af1.getEstadoInicial(), estadosAceitacao,
                transicoes);

        return afnd;
    }
}
