/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministicoGeneralizado;
import br.inf.ufsc.formais.model.automato.EntradaAFNDG;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;

/**
 *
 * @author Diego
 */
public class AFD2AFNDG {

    public static AutomatoFinitoNaoDeterministicoGeneralizado
            converterParaAFDNG(AutomatoFinitoDeterministico afd) {
        EstadoInicial estadoInicial = new EstadoInicial("NS");
        EstadoFinal estadoFinal = new EstadoFinal("NF");

        Map<EntradaAFNDG, Estados> transicoes = new LinkedHashMap<>();
        
        EntradaAFNDG entradaNSNF = new EntradaAFNDG(estadoInicial, Simbolo.EPSILON);
        Estados estadosNSNF = new Estados();
        estadosNSNF.addEstado(estadoFinal);
        
        transicoes.put(entradaNSNF, estadosNSNF);
        
        Set<EstadoFinal> aceitacao = new LinkedHashSet<>();
        aceitacao.addAll(afd.getEstadosAceitacao());
        
        for (EstadoFinal ef : aceitacao) {
            afd.removeEstadoFinal(ef);
        }
        
        Set<Estado> estados = afd.getEstados();

        for (Estado i : estados) {
            EntradaAFNDG entradaEstadoInicial = new EntradaAFNDG(estadoInicial, Simbolo.EPSILON);
            Estados estadosEntradaEstadoInicial = transicoes.get(entradaEstadoInicial);
            if(estadosEntradaEstadoInicial == null) {
                estadosEntradaEstadoInicial = new Estados(new LinkedHashSet<Estado>());
                estadosEntradaEstadoInicial.addEstado(i);
                transicoes.put(entradaEstadoInicial, estadosEntradaEstadoInicial);
            } else {
                estadosEntradaEstadoInicial.addEstado(i);
            }
            
            EntradaAFNDG entradaEstadoFinal = new EntradaAFNDG(i, Simbolo.EPSILON);
            Estados estadosEntradaEstadoFinal = transicoes.get(entradaEstadoFinal);
            if(estadosEntradaEstadoFinal == null) {
                estadosEntradaEstadoFinal = new Estados(new LinkedHashSet<Estado>());
                estadosEntradaEstadoFinal.addEstado(estadoFinal);
                transicoes.put(entradaEstadoFinal, estadosEntradaEstadoFinal);
            } else {
                estadosEntradaEstadoFinal.addEstado(estadoFinal);
            }
            
            for (Estado j : estados) {
                EntradaAFNDG eTrans = new EntradaAFNDG(i, (i.equals(j)) ? Simbolo.EPSILON : Simbolo.CONJUNTO_VAZIO);
                for (Simbolo s : afd.getAlfabeto().getSimbolos()) {
                    if (afd.existeTransicao(i, s, j)) {
                        eTrans.getExpressaoRegular().alternancia(s);
                    }
                }
                Estados estTrans = transicoes.get(eTrans);
                if(estTrans != null) {
                    estTrans.addEstado(j);
                } else {
                    estTrans = new Estados(new LinkedHashSet<Estado>());
                    estTrans.addEstado(j);
                    transicoes.put(eTrans, estTrans);
                }
            }
        }

        estados.add(estadoInicial);
        estados.add(estadoFinal);

        aceitacao = new LinkedHashSet<>();
        aceitacao.add(estadoFinal);

        return new AutomatoFinitoNaoDeterministicoGeneralizado(estados, afd.getAlfabeto(), estadoInicial, aceitacao, transicoes);
    }
}
