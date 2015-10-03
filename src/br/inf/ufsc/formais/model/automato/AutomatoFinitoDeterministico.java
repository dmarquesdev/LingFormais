/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;

/**
 *
 * @author Diego
 */
public class AutomatoFinitoDeterministico implements AutomatoFinito {
    
    protected Set<Estado> estados;
    protected Alfabeto alfabeto;
    protected EstadoInicial estadoInicial;
    protected Set<EstadoFinal> estadosAceitacao;
    protected Map<Entrada, Estado> transicoes;
    
    public AutomatoFinitoDeterministico(Set<Estado> estados, Alfabeto alfabeto, EstadoInicial estadoInicial, Set<EstadoFinal> estadosAceitacao, Map<Entrada, Estado> transicoes) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.transicoes = transicoes;
    }
    
    public AutomatoFinitoDeterministico() {
        estados = new LinkedHashSet<>();
        estadosAceitacao = new LinkedHashSet<>();
        transicoes = new HashMap<>();
    }
    
    @Override
    public void addEstado(Estado estado) {
        estados.add(estado);
    }
    
    @Override
    public Set<Estado> getEstados() {
        return estados;
    }
    
    @Override
    public void setEstados(Set<Estado> estados) {
        this.estados = estados;
    }
    
    @Override
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }
    
    @Override
    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }
    
    @Override
    public EstadoInicial getEstadoInicial() {
        return estadoInicial;
    }
    
    @Override
    public void setEstadoInicial(EstadoInicial estadoInicial) {
        this.estadoInicial = estadoInicial;
    }
    
    @Override
    public Set<EstadoFinal> getEstadosAceitacao() {
        return estadosAceitacao;
    }
    
    @Override
    public void setEstadosAceitacao(Set<EstadoFinal> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }
    
    @Override
    public void addEstadoFinal(EstadoFinal estado) {
        estadosAceitacao.add(estado);
    }
    
    @Override
    public Estado getEstadoTransicao(Entrada entrada) {
        return transicoes.get(entrada);
    }
    
    @Override
    public void addTransicao(Entrada entrada, Estado destino) {
        transicoes.put(entrada, destino);
    }
    
    @Override
    public void removeEstado(Estado estado) {
        // TODO
    }
    
    public Map<Entrada, Estado> getTransicoes() {
        return transicoes;
    }
    
    @Override
    public boolean existeTransicao(Estado de, Estado para) {
        for (Simbolo s : alfabeto.getSimbolos()) {
            Entrada ent = new Entrada(de, s);
            if (transicoes.get(ent) != null) {
                return true;
            }
        }
        
        return false;
    }
    
    @Override
    public boolean existeTransicao(Estado de, Simbolo entrada, Estado para) {
        Entrada ent = new Entrada(de, entrada);
        return (transicoes.get(ent) != null && transicoes.get(ent).equals(para));
    }
    
    @Override
    public Estado removeEstadoFinal(EstadoFinal estado) {
        Estado novo = new Estado(estado.getId());
        for (Entrada ent : transicoes.keySet()) {
            if (ent.getEstado().equals(estado)) {
                ent.setEstado(novo);
            } 
            
            if (transicoes.get(ent).equals(estado)) {
                //transicoes.replace(ent, novo);
            }
        }
        estadosAceitacao.remove(estado);
        estados.remove(estado);
        estados.add(novo);
        return novo;
    }
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("M = (E,A,T,I,F)\n");
        
        out.append("E = {");
        for (Estado estado : estados) {
            out.append(estado.getId()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("}\n");
        
        out.append(alfabeto.toString()).append("\n");
        
        for (Entrada ent : transicoes.keySet()) {
            out.append("T(").append(ent.toString())
                    .append(") -> ")                 
                    .append(transicoes.get(ent).toString())
                    .append("\n");
        }
        
        out.append("\n");
        
        out.append("I = ").append(estadoInicial.getId()).append("\n");
        
        out.append("F = {");
        for (EstadoFinal estAceita : estadosAceitacao) {
            out.append(estAceita.getId()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("}\n");
        
        return out.toString();
    }
    
}
