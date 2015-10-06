package br.inf.ufsc.formais.model;

import java.util.Objects;

/**
 * Representação de um Símbolo para Linguagens Regulares.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Simbolo {

    /**
     * Representação da Palavra Vazia (Epsilon).
     */
    public static final Simbolo EPSILON = new Simbolo("ε");
    /**
     * Representação do Vazio (Conjunto Vazio).
     */
    public static final Simbolo CONJUNTO_VAZIO = new Simbolo("∅");

    /**
     * Atributo responsável por armazenar o símbolo de forma que
     * possa ser trabalhado computacionalmente.
     */
    private String referencia;

    /**
     * Cria um símbolo.
     * @param referencia String de referência ao símbolo, como por exemplo "a".
     */
    public Simbolo(String referencia) {
        this.referencia = referencia;
    }

    /**
     * Retorna a referência do símbolo em String.
     * @return Referência do símbolo.
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * Atribui um valor a referência do símbolo.
     * @param referencia Valor a ser atribuído para a referência.
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.referencia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Simbolo other = (Simbolo) obj;
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return referencia;
    }
}
