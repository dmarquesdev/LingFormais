package br.inf.ufsc.formais.exception;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.Estado;

/**
 * Exceção lançada quando uma palavra não é reconhecida por um AFD
 * 
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class EstadoInalcancavelException extends Exception {

    private Estado estado;
    private Simbolo simbolo;

    /**
     * Contrutor. Inicializa todos os atributos da classe.
     * @param estado estado em que findou a computação
     * @param simbolo ultimo simbolo que foi computado.
     */
    public EstadoInalcancavelException(Estado estado, Simbolo simbolo) {
        this.estado = estado;
        this.simbolo = simbolo;
    }

    /**
     * Retorna o estado em que findou a computação
     * @return estado em que findou a computação 
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Retorna o ultimo simbolo que foi computado
     * @return ultimo simbolo que foi computado
     */
    public Simbolo getSimbolo() {
        return simbolo;
    }

}
