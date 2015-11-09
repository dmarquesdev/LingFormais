package br.inf.ufsc.formais.exception;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.Estados;

/**
 * Exceção lançada quando uma palavra não é reconhecida por um AFND
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class EstadosInalcancaveisException extends Exception {

    private Estados estados;
    private Simbolo simbolo;

    /**
     * Contrutor. Inicializa todos os atributos da classe.
     *
     * @param estado estados em que findou a computação
     * @param simbolo ultimo simbolo que foi computado.
     */
    public EstadosInalcancaveisException(Estados estado, Simbolo simbolo) {
        this.estados = estado;
        this.simbolo = simbolo;
    }

    /**
     * Retorna os estadso em que findou a computação
     *
     * @return estados em que findou a computação
     */
    public Estados getEstados() {
        return estados;
    }

    /**
     * Retorna o ultimo simbolo que foi computado
     *
     * @return ultimo simbolo que foi computado
     */
    public Simbolo getSimbolo() {
        return simbolo;
    }

}
