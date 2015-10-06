package br.inf.ufsc.formais.model.automato;

import java.util.Objects;

import br.inf.ufsc.formais.model.Simbolo;

/**
 * Clase que representa um Simbolo do Alfabeto.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Entrada {
    /**
     * Estado atual de uma transição
     */
    protected Estado estado;
    
    /**
     * Simbolo pelo qual a transição irá ocorrer
     */
    protected Simbolo simbolo;

    /**
     * Contrutor, inicializa todos os atributos da classe.
     * @param estado estado que será inicializado a Entrada.
     * @param simbolo simbolo que sera inicializado a Entrada.
     */
    public Entrada(Estado estado, Simbolo simbolo) {
        this.estado = estado;
        this.simbolo = simbolo;
    }
    
    /**
     * Obtem o estado da Entrada.
     * @return O estado da Entrada.
     */
    public Estado getEstado() {
        return estado;
    }
    
    /**
     * Seta o estado da Entrada.
     * @param estado Estado a ser setado na Entrada.
     */
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    /**
     * Obtem o Simbolo da Entrada.
     * @return 
     */
    public Simbolo getSimbolo() {
        return simbolo;
    }

    /**
     * Seta o Simbolo da Entrada.
     * @param simbolo simbolo a ser setado.
     */
    public void setSimbolo(Simbolo simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * Sobrescreve o metodo HashCode() para que possa ser usado como chave
     * em mapas e identificados em conjuntos.
     * @return novo HashCode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.estado);
        hash = 53 * hash + Objects.hashCode(this.simbolo);
        return hash;
    }
    /**
     * Verifica se a classe é igual ao Objeto recebido por paramêtro.
     * @param obj Objeto a ser comparado.
     * @return Verdadeiro se for igual, falso caso contrário.
     */
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
        final Entrada other = (Entrada) obj;
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.simbolo, other.simbolo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return estado.toString() + ", " + simbolo.toString();
    }
    
    

}
