/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.er;

import br.inf.ufsc.formais.model.Simbolo;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class ExpressaoRegular {

    private List<Simbolo> simbolos;

    public ExpressaoRegular(List<Simbolo> simbolos) {
        this.simbolos = simbolos;
    }

    public ExpressaoRegular(Simbolo simbolo) {
        this.simbolos = new ArrayList<>();
        simbolos.add(simbolo);
    }

    public ExpressaoRegular() {
        this.simbolos = new ArrayList<>();
    }

    public void concatenar(Simbolo simbolo) {
        simbolos.add(simbolo);
    }

    public void concatenar(ExpressaoRegular er) {
        simbolos.add(SimboloOperacional.ABRE_GRUPO);
        List<Simbolo> simbolosOutraER = new ArrayList<>();
        simbolosOutraER.addAll(er.simbolos);
        for (Simbolo simbolo : simbolosOutraER) {
            simbolos.add(simbolo);
        }
        simbolos.add(SimboloOperacional.FECHA_GRUPO);
    }

    private void agrupar(ExpressaoRegular er) {
        er.simbolos.add(0, SimboloOperacional.ABRE_GRUPO);
        er.simbolos.add(SimboloOperacional.FECHA_GRUPO);
    }

    public void alternancia(Simbolo simbolo) {
        agrupar(this);
        concatenar(SimboloOperacional.ALTERNANCIA);
        concatenar(simbolo);
    }

    public void alternancia(ExpressaoRegular er) {
        agrupar(this);
        agrupar(er);
        concatenar(SimboloOperacional.ALTERNANCIA);
        concatenar(er);
    }

    public void concatenarSimboloFecho(Simbolo simbolo) {
        concatenar(simbolo);
        concatenar(SimboloOperacional.FECHO);
    }

    public void concatenarERFecho(ExpressaoRegular er) {
        agrupar(er);
        concatenar(er);
        concatenar(SimboloOperacional.FECHO);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.simbolos);
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
        final ExpressaoRegular other = (ExpressaoRegular) obj;
        if (!toString().equals(other.toString())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Simbolo simbolo : simbolos) {
            out.append(simbolo.getReferencia());
        }

        return out.toString();
    }
}
