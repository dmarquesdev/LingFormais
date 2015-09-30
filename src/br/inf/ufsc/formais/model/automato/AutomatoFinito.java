/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Diego
 */
public interface AutomatoFinito {
    public void addEstado(Estado estado);
    public Set<Estado> getEstados();
    public void setEstados(Set<Estado> estados);
    public void removeEstado(Estado estado);
    
    public Alfabeto getAlfabeto();
    public void setAlfabeto(Alfabeto alfabeto);
    
    public EstadoInicial getEstadoInicial();
    public void setEstadoInicial(EstadoInicial estadoInicial);
    
    public Set<EstadoFinal> getEstadosAceitacao();
    public void setEstadosAceitacao(Set<EstadoFinal> estadosAceitacao);
    public void addEstadoFinal(EstadoFinal estado);
    public Estado removeEstadoFinal(EstadoFinal estado);
    
    public Map<? extends Entrada, ? extends Estado> getTransicoes();
    public Estado getEstadoTransicao(Entrada entrada);
    public void addTransicao(Entrada entrada, Estado destino);
    public boolean existeTransicao(Estado de, Estado para);
    public boolean existeTransicao(Estado de, Simbolo entrada, Estado para);
    
}
