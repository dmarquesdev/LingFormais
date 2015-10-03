/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.model.er.SimboloOperacional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matheus
 */
public class ER2AFND {

    private static int contadorEstados = 0;
    
    public static AutomatoFinitoNaoDeterministico converterParaAutomato(ExpressaoRegular er) {
        
        AutomatoFinitoNaoDeterministico ultimo = null;
        
        //laço responsavel por analizar cada simbolo da expressão regular
        for (int i = 0; i < er.getSimbolos().size(); i++) {

            if (er.getSimbolos().get(i).getReferencia().equals("(")) {
                //quando encontra um abre grupo entra num laço gerando uma sub er ate o fecha grupo correpondente.
                //calcula essa sub er e retorna.
                List<Simbolo> subSimbolos = new ArrayList<>();
                int abregrupo = 1;
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
                ExpressaoRegular subEr = new ExpressaoRegular(subSimbolos);
                i = j-1;//pula para simbolo imediatamente após o fecha grupo.

                //analiza se existe um automato anterior para ser concatenado.
                //se não existir cria af, se o prox simbolo for fecho, cria o fecho desse af
                //se existir automato para concatenar, concatena com atual(com ou sem fecho).
                if (ultimo == null) {
                    if (i < er.getSimbolos().size() - 1) {
                        if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                            ultimo = fechoDeAF(converterParaAutomato(subEr));
                            ++i;
                        } else {
                            ultimo = converterParaAutomato(subEr);
                        }
                    } else {
                        ultimo = converterParaAutomato(subEr);
                    }
                } else {
                    if (i < er.getSimbolos().size() - 1) {
                        if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                            ultimo = concatenaAFs(ultimo, fechoDeAF(converterParaAutomato(subEr)));
                            ++i;
                        } else {
                            ultimo = concatenaAFs(ultimo, converterParaAutomato(subEr));
                        }
                    } else {
                        ultimo = concatenaAFs(ultimo, converterParaAutomato(subEr));
                    }
                }
            } else if (er.getSimbolos().get(i).getReferencia().equals("*")) {
                //gera o fecho do ultimo automato
                ultimo = fechoDeAF(ultimo);

            } else if (er.getSimbolos().get(i).getReferencia().equals("|")) {
                //cria automato depois do | e opera com o automato em espera
                List<Simbolo> subSimbolos;
                subSimbolos = er.getSimbolos().subList(i+1, er.getSimbolos().size());
                ExpressaoRegular subEr = new ExpressaoRegular(subSimbolos);
                ultimo = ouEntreAFs(ultimo, converterParaAutomato(subEr));
                ++i;
            } else if (er.getSimbolos().get(i) == SimboloOperacional.CONJUNTO_VAZIO) {
                ultimo = aFLingVazia();

            } else { 
                //se for um simbolo é gerado um automato que reconheça esse simbolo, gera o seu fecho se o proximo
                //simbolo for fecho, concatena com o automato anterior se existir.
                if (ultimo == null) {
        
                    if (er.getSimbolos().get(i) == SimboloOperacional.EPSILON) {
                        ultimo = aFPalavraVazia();
                    } else {
                        if (i < er.getSimbolos().size() - 1) {
                            if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                                ultimo = fechoDeAF(aFdeSimbolo(er.getSimbolos().get(i)));
                                i++;
                            }else{
                                ultimo = aFdeSimbolo(er.getSimbolos().get(i));
                            }
                        } else {
                            ultimo = aFdeSimbolo(er.getSimbolos().get(i));
                        }

                    }

                } else {

                    if (er.getSimbolos().get(i) == SimboloOperacional.EPSILON) {
                        ultimo = concatenaAFs(ultimo, aFPalavraVazia());
                    } else {
                        if (i < er.getSimbolos().size() - 1) {
                            if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                                ultimo = concatenaAFs(ultimo, fechoDeAF(aFdeSimbolo(er.getSimbolos().get(i))));
                                ++i;
                            }else{
                                ultimo = concatenaAFs(ultimo, aFdeSimbolo(er.getSimbolos().get(i)));
                            }
                        } else {
                            ultimo = concatenaAFs(ultimo, aFdeSimbolo(er.getSimbolos().get(i)));
                        }

                    }
                }

            }
        }

        return ultimo;
    }

    /*
    cria um automato que reconeça palavras com o simbolo do alfabeto passado como parametro
    */
    public static AutomatoFinitoNaoDeterministico aFdeSimbolo(Simbolo s) {
        //cria estado inicial e final
        EstadoInicial einicial = new EstadoInicial("q" + contadorEstados);
        ++contadorEstados;
        EstadoFinal efinal = new EstadoFinal("q" + contadorEstados);
        ++contadorEstados;
        
        //cria um set de estados e adiciona o inicial e o final.
        Set<Estado> estados = new LinkedHashSet<>();
        estados.add(einicial);
        estados.add(efinal);

        //cria set de estados e adicio estado final, será usado para criar a transicao.
        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(efinal);
        Estados proxEstados = new Estados(prox);

        //cria o set de estados aceitacao e adiciona estado final.
        Set<EstadoFinal> estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.add(efinal);

        //cria um set de simbolos e adiciona au alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.add(s);
        Alfabeto alfa = new Alfabeto(simbolos);

        //cria uma entrada,composta por estado de saida da transicao e o simbolo pelo qual transita
        Entrada entrada = new Entrada(einicial, s);

        //cria um map de transicoes, cada transicao é composta por uma entrada e os estados para qual a transicao 
        //pelo simbolo em questao leva.
        Map<Entrada, Estados> transicoes = new HashMap<>();
        transicoes.put(entrada, proxEstados);

        //gera o automato.
        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                einicial, estadosAceitacao, transicoes);

        return afnd;
    }

    public static AutomatoFinitoNaoDeterministico fechoDeAF(AutomatoFinitoNaoDeterministico af) {
        AutomatoFinitoNaoDeterministico afnd;
        afnd = af;

        Set<Estado> prox = new LinkedHashSet<>();
        prox.add(afnd.getEstadoInicial());
        Estados estados = new Estados(prox);

        //cria uma transição por epsilon de cada estado final para o inicial.
        for (EstadoFinal efinal : af.getEstadosAceitacao()) {
            Entrada entrada = new Entrada(efinal, Simbolo.EPSILON);

            afnd.addTransicao(entrada, estados);
        }

        return afnd;
    }

    public static AutomatoFinitoNaoDeterministico ouEntreAFs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
        //Cria novo estado inicial.
        EstadoInicial novoInicial = new EstadoInicial("q" + contadorEstados);
        ++contadorEstados;
        
        //Substitui estados iniciais de af1 e af2 por estados normais
        Estado ei_af1 = af1.removeEstadoInicial();
        Estado ei_af2 = af2.removeEstadoInicial();

        //criando transicao nao deterministica do inicial para cada um dos iniciais dos afnd's
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(ei_af2);
        estadosProx.add(ei_af1);
        Estados proxEstados = new Estados(estadosProx);
        Entrada entrada = new Entrada(novoInicial, Simbolo.EPSILON);

        //Junção de estados
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

    public static AutomatoFinitoNaoDeterministico concatenaAFs(AutomatoFinitoNaoDeterministico af1, AutomatoFinitoNaoDeterministico af2) {
        Map<Entrada, Estados> transicoes = new HashMap<>();

        //Cria estado estado para subtiruir estado inicial de af2
        Estado eaf2 = af2.removeEstadoInicial();

        //Cria uma transição de cada estado final de af1 para o estado inicial de af2
        Set<Estado> estadosProx = new LinkedHashSet<>();
        estadosProx.add(eaf2);
        Estados proxEstados = new Estados(estadosProx);
        
        Set<EstadoFinal> apoio =  new LinkedHashSet<>();
        apoio.addAll(af1.getEstadosAceitacao());
        
        //Junção de transições
        transicoes.putAll(af1.getTransicoes());
        transicoes.putAll(af2.getTransicoes());
        
        for (EstadoFinal e : apoio) {
            Estado eaf1 = af1.removeEstadoFinal(e);
            Entrada entrada = new Entrada(eaf1, Simbolo.EPSILON);
            
            //Se a entrada ja existe, adiciona o estado à transiçao.
            if(transicoes.containsKey(entrada)){
                transicoes.get(entrada).addEstado(eaf2);
            }else{
                transicoes.put(entrada, proxEstados);
            }
        }
        
        //Junção de estados.
        Set<Estado> estados = new LinkedHashSet<>();
        estados.addAll(af1.getEstados());
        estados.addAll(af2.getEstados());

        //Junção de Alfabeto
        Set<Simbolo> simbolos = new LinkedHashSet<>();
        simbolos.addAll(af1.getAlfabeto().getSimbolos());
        simbolos.addAll(af2.getAlfabeto().getSimbolos());
        Alfabeto alfa = new Alfabeto(simbolos);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico(estados, alfa,
                af1.getEstadoInicial(), af2.getEstadosAceitacao(),
                transicoes);

        return afnd;
    }

    public static AutomatoFinitoNaoDeterministico aFLingVazia() {
        
        //cria um automato que tem apenas o estado inicial, todo o resto é nulo.
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

    public static AutomatoFinitoNaoDeterministico aFPalavraVazia() {
        //cria um automato que transita do estado inical ao final por epsilon.
        return aFdeSimbolo(Simbolo.EPSILON);
    }
}
