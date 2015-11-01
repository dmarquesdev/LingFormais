package br.inf.ufsc.formais.model.automato;

import java.util.Objects;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;

/**
 * Clase que representa um Simbolo do Alfabeto.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class EntradaAFNDG extends Entrada {

    /**
     * Expressão regular da Entrada.
     */
    private ExpressaoRegular expressaoRegular;

    /**
     * Contrutor, responsável por inicializar todos os atributos classe.
     * @param estado Estado em que sera inicilizado o objeto.
     * @param er Expressão Regular em que sera inicilizado o objeto.
     */
    public EntradaAFNDG(Estado estado, ExpressaoRegular er) {
        super(estado, null);
        this.expressaoRegular = er;
    }

    /**
    * Contrutor, responsável por inicializar todos os atributos classe.
    * @param estado Estado em que sera inicilizado o objeto.
    * @param simbolo  Simbolo em que sera inicilizado o objeto.
    */
    public EntradaAFNDG(Estado estado, Simbolo simbolo) {
        super(estado, simbolo);
        expressaoRegular = new ExpressaoRegular(simbolo);
    }

    /**
     * Retorna a expressão regular da entrada.
     * @return Expressão regular da entrada.
     */
    public ExpressaoRegular getExpressaoRegular() {
        return expressaoRegular;
    }

    /**
     * Seta a expressão regular da Entrada.
     * @param expressaoRegular Expressão regular a ser setada.
     */
    public void setExpressaoRegular(ExpressaoRegular expressaoRegular) {
        this.expressaoRegular = expressaoRegular;
    }

    @Override
    public String toString() {
        return estado.toString() + ", " + expressaoRegular.toString();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.expressaoRegular);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntradaAFNDG other = (EntradaAFNDG) obj;
        if (!Objects.equals(this.estado, other.estado)) {
            return false;
        }
        if (!Objects.equals(this.expressaoRegular, other.expressaoRegular)) {
            return false;
        }
        return true;
    }

}
