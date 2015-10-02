/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.er;

import br.inf.ufsc.formais.model.Simbolo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Diego
 */
public class ExpressaoRegular {

    private List<Simbolo> simbolos;

    public ExpressaoRegular(List<Simbolo> simbolos) {
        this.simbolos = new ArrayList<>();
        this.simbolos.addAll(simbolos);
    }

    public ExpressaoRegular(Simbolo simbolo) {
        this.simbolos = new ArrayList<>();
        simbolos.add(simbolo);
    }

    public ExpressaoRegular() {
        this.simbolos = new ArrayList<>();
    }

    public void concatenar(Simbolo simbolo) {
        ExpressaoRegular antes = null;
        simbolos.add(simbolo);
    }

    public void concatenar(ExpressaoRegular er) {
        List<Simbolo> simbolosOutraER = new ArrayList<>();
        simbolosOutraER.addAll(er.simbolos);

        ExpressaoRegular antes = null;

        if (er.somenteVazio() || this.somenteVazio()) {
            this.simbolos.removeAll(simbolos);
            this.simbolos.add(Simbolo.CONJUNTO_VAZIO);
        } else if (this.somenteEpsilon() && !er.somenteEpsilon()) {
            this.simbolos.remove(Simbolo.EPSILON);
            this.simbolos.addAll(simbolosOutraER);
        } else if (!this.somenteEpsilon() && !er.somenteEpsilon()) {
            simbolos.add(SimboloOperacional.ABRE_GRUPO);
            for (Simbolo simbolo : simbolosOutraER) {
                simbolos.add(simbolo);
            }
            simbolos.add(SimboloOperacional.FECHA_GRUPO);
        }

    }

    private void agrupar(ExpressaoRegular er) {
        ExpressaoRegular antes = null;
        if (!er.somenteEpsilon() && !er.somenteVazio() && simbolos.size() > 1) {
            er.simbolos.add(0, SimboloOperacional.ABRE_GRUPO);
            er.simbolos.add(SimboloOperacional.FECHA_GRUPO);
        }
    }

    public void alternancia(Simbolo simbolo) {
        if (this.somenteVazio() || this.somenteEpsilon()) {
            this.simbolos.remove(0);
            this.simbolos.add(simbolo);
        } else if (simbolos.size() > 1 || !simbolos.contains(simbolo)) {
            agrupar(this);
            concatenar(SimboloOperacional.ALTERNANCIA);
            concatenar(simbolo);
        }
    }

    public void alternancia(ExpressaoRegular er) {
        if (this.somenteVazio() || this.somenteEpsilon()) {
            if (!(this.somenteEpsilon() && er.somenteVazio()) || (this.somenteVazio() && er.somenteEpsilon())) {
                this.simbolos.remove(0);
                this.simbolos.addAll(er.simbolos);
            }
        } else if (!er.somenteEpsilon() && !er.somenteVazio() && !this.equals(er)) {
            agrupar(this);
            agrupar(er);
            concatenar(SimboloOperacional.ALTERNANCIA);
            concatenar(er);
        }
    }

    public void concatenarSimboloFecho(Simbolo simbolo) {
        concatenar(simbolo);
        concatenar(SimboloOperacional.FECHO);
    }

    public void concatenarERFecho(ExpressaoRegular er) {
        agrupar(er);
        concatenar(er);
        if (!er.somenteEpsilon() && !er.somenteVazio()) {
            concatenar(SimboloOperacional.FECHO);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.simbolos);
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

    public boolean somenteEpsilon() {
        boolean somenteEpsilon = true;
        for (Simbolo simbolo : simbolos) {
            if (!(simbolo instanceof SimboloOperacional) && !simbolo.equals(Simbolo.EPSILON)) {
                somenteEpsilon = false;
                break;
            }
        }

        return somenteEpsilon;
    }

    public boolean somenteVazio() {
        boolean somenteVazio = true;
        for (Simbolo simbolo : simbolos) {
            if (!(simbolo instanceof SimboloOperacional) && !simbolo.equals(Simbolo.CONJUNTO_VAZIO)) {
                somenteVazio = false;
                break;
            }
        }

        return somenteVazio;
    }

    public ExpressaoRegular clone() {
        ExpressaoRegular er = new ExpressaoRegular();
        er.simbolos.addAll(simbolos);

        return er;
    }

}
