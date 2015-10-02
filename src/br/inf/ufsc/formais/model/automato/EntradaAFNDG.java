/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import java.util.Objects;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;

/**
 *
 * @author Diego
 */
public class EntradaAFNDG extends Entrada {

    private ExpressaoRegular expressaoRegular;

    public EntradaAFNDG(Estado estado, ExpressaoRegular er) {
        super(estado, null);
        this.expressaoRegular = er;
    }

    public EntradaAFNDG(Estado estado, Simbolo simbolo) {
        super(estado, simbolo);
        expressaoRegular = new ExpressaoRegular(simbolo);
    }

    public ExpressaoRegular getExpressaoRegular() {
        return expressaoRegular;
    }

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
        if(this == obj) {
            return true;
        }
        if(!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntradaAFNDG other = (EntradaAFNDG) obj;
        if (!Objects.equals(this.expressaoRegular, other.expressaoRegular)) {
            return false;
        }
        return true;
    }

}
