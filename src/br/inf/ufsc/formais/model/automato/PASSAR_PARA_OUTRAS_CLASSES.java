/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.model.gramatica.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class PASSAR_PARA_OUTRAS_CLASSES implements Cloneable {

    protected Set<Estado> estados;
    protected Alfabeto alfabeto;
    protected EstadoInicial estadoInicial;
    protected Set<EstadoFinal> estadosAceitacao;
    protected Map<Entrada, Estado> transicoes;
    private boolean generalizado;

    public PASSAR_PARA_OUTRAS_CLASSES(Set<Estado> estados, Alfabeto alfabeto, Map<Entrada, Estado> transicoes,
            EstadoInicial estadoInicial, Set<EstadoFinal> estadosAceitacao) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.transicoes = transicoes;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
    }

    public Set<Estado> getEstados() {
        return estados;
    }

    public void setEstados(Set<Estado> estados) {
        this.estados = estados;
    }

    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    public EstadoInicial getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(EstadoInicial estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public Set<EstadoFinal> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    public void setEstadosAceitacao(Set<EstadoFinal> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }

    public Map<Entrada, Estado> getTransicoes() {
        return transicoes;
    }

    public void setTransicoes(Map<Entrada, Estado> transicoes) {
        this.transicoes = transicoes;
    }

    public void generalizar() {
        EstadoInicial novoEstadoInicial = new EstadoInicial("NS");
        EpsilonTransicao transicaoInicio = new EpsilonTransicao(novoEstadoInicial, estadoInicial);

        EstadoFinal novoFinal = new EstadoFinal("NF");
        Set<EpsilonTransicao> transToFim = new LinkedHashSet<>();
        Set<Estado> antigosFinais = new LinkedHashSet<>();

        for (EstadoFinal estado : estadosAceitacao) {
            EpsilonTransicao et = new EpsilonTransicao(estado, novoFinal);
            Estado est = new Estado(estado.getId());

            for (Transicao tr : transicoes) {
                if (tr.getEstadoAtual().equals(estado)) {
                    tr.setEstadoAtual(est);
                }

                if (tr.getProximoEstado().equals(estado)) {
                    tr.setProximoEstado(est);
                }
            }

            antigosFinais.add(est);
            transToFim.add(et);
        }

        estados.removeAll(estadosAceitacao);
        estados.addAll(antigosFinais);

        for (Estado estado : estados) {
            for (Estado est : estados) {
                EpsilonTransicao et = new EpsilonTransicao(estado, est);
                transicoes.add(et);
            }
        }

        estadosAceitacao = new LinkedHashSet<>();
        estadosAceitacao.add(novoFinal);

        estadoInicial = novoEstadoInicial;
        transicoes.addAll(transToFim);
        transicoes.add(transicaoInicio);

        estados.add(novoEstadoInicial);
        estados.add(novoFinal);

        generalizado = true;
    }

    public boolean isGeneralizado() {
        return generalizado;
    }

    public EstadoFinal getEstadoFinal() {
        if (generalizado) {
            return estadosAceitacao.toArray(new EstadoFinal[1])[0];
        }

        return null;
    }

    public Transicao getTransicao(Estado estadoA, Estado estadoB) {
        for (Transicao t : transicoes) {
            if (t.getEstadoAtual().equals(estadoA) && t.getProximoEstado().equals(estadoB)) {
                return t;
            }
        }

        return null;
    }
}
