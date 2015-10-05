package br.inf.ufsc.formais.model;

import java.util.Set;

/**
 * Representação de um Alfabeto para uma Linguagem Regulares.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Alfabeto {
    /**
     * Conjunto de símbolos do alfabeto.
     */
    private Set<Simbolo> simbolos;

    /**
     * Cria um alfabeto
     * @param simbolos Conjunto de símbolos do alfabeto.
     */
    public Alfabeto(Set<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    /**
     * Retorna o conjunto de símbolos do alfabeto.
     * @return Conjunto de símbolos.
     */
    public Set<Simbolo> getSimbolos() {
        return simbolos;
    }

    /**
     * Atribui um conjunto de símbolos ao alfabeto
     * @param simbolos Conjunto de símbolos.
     */
    public void setSimbolos(Set<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    /**
     * Escreve o objeto em forma de String.
     * @return Alfabeto em formato de String
     */
    @Override
    public String toString() {
         StringBuilder out = new StringBuilder("A = {");

        for (Simbolo simb : simbolos) {
            out.append(simb.getReferencia()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("}\n");
        
        return out.toString();
    }
    
}
