/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class Estados extends Estado {
    private Set<Estado> estados;

    public Estados(Set<Estado> estados) {
        super("");
        this.estados = estados;
    }

    public Estados() {
        super("");
        this.estados = new LinkedHashSet<>();
    }
    
    
    
    public void addEstado(Estado estado) {
        estados.add(estado);
    }
    
    public Set<Estado> get() {
        return estados;
    }
    
    public Estado[] getEstados() {
        return estados.toArray(new Estado[estados.size()]);
    }
    
    public void removeEstado(Estado estado) {
        estados.remove(estado);
    }
    
    public int size() {
        return estados.size();
    }

    @Override
    public String getId() {
        StringBuilder out = new StringBuilder();
        for (Estado estado : estados) {
            out.append(estado.getId()).append(", ");
        }
        
        out.delete(out.length() - 2, out.length());
        
        return out.toString();
    }

    @Override
    public String toString() {
        return getId();
    }
}
