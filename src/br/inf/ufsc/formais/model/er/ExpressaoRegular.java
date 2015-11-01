/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.er;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import br.inf.ufsc.formais.model.Simbolo;

/**
 *
 * Classe que define uma expressão regular.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class ExpressaoRegular {

    private List<Simbolo> simbolos;

    /**
     * Construtor da classe.
     *
     * @param simbolos Uma lista de simbolos que equivale a uma expressão
     * regular.
     */
    public ExpressaoRegular(List<Simbolo> simbolos) {
        this.simbolos = new ArrayList<>();
        this.simbolos.addAll(simbolos);
    }

    /**
     * Construtor da classe.
     *
     * @param simbolo Um simbolo que equivale a uma expressão regular.
     */
    public ExpressaoRegular(Simbolo simbolo) {
        this.simbolos = new ArrayList<>();
        simbolos.add(simbolo);
    }

    /**
     * Construtor da classe.
     */
    public ExpressaoRegular() {
        this.simbolos = new ArrayList<>();
    }

    /**
     * get da expressão regular.
     *
     * @return uma lista de simbolos. Equivale a expressão regular.
     */
    public List<Simbolo> getSimbolos() {
        return simbolos;
    }

    /**
     * Concatena um simbolo a expressão regular existente.
     *
     * @param simbolo Um simbolo a ser concatenado.
     */
    public void concatenar(Simbolo simbolo) {
        ExpressaoRegular antes = null;
        simbolos.add(simbolo);
    }

    /**
     * concatena uma expressão regular a expressão existente.
     *
     * @param er A expressão regular a ser concatenada.
     */
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

    /**
     * Agrupa uma expressão regular (entre parenteses).
     *
     * @param er A expressão regular a ser agrupada.
     */
    private void agrupar(ExpressaoRegular er) {
        ExpressaoRegular antes = null;
        if (!er.somenteEpsilon() && !er.somenteVazio() && simbolos.size() > 1) {
            er.simbolos.add(0, SimboloOperacional.ABRE_GRUPO);
            er.simbolos.add(SimboloOperacional.FECHA_GRUPO);
        }
    }

    /**
     * Cria operação de alternancia(OU) entre a expressão existente e um
     * simbolo.
     *
     * @param simbolo O simbolo a ser alternado.
     */
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

    /**
     * Cria operação de alternancia(OU) entre a expressão existente e outra
     * expressão.
     *
     * @param er A expressão regular a ser alternado.
     */
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

    /**
     * Concatena a expressão regular existente ao fecho de simbolo.
     *
     * @param simbolo Simbolo a ser concatenado.
     */
    public void concatenarSimboloFecho(Simbolo simbolo) {
        concatenar(simbolo);
        concatenar(SimboloOperacional.FECHO);
    }

    /**
     * Concatena a expressão regular existente ao fecho de uma expressão
     * regular.
     *
     * @param er Expressão regular a ser concatenada.
     */
    public void concatenarERFecho(ExpressaoRegular er) {
        agrupar(er);
        concatenar(er);
        if (!er.somenteEpsilon() && !er.somenteVazio()) {
            concatenar(SimboloOperacional.FECHO);
        }
    }

    /**
     * Gera o codigo hash da expressão regular.
     *
     * @return O código hash.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.simbolos);
        return hash;
    }

    /**
     * Implementa o metodo equals da classe.
     *
     * @param obj Objeto a ser comparado.
     * @return Um boolean com a resposta da igualdade.
     */
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

    /**
     * Implemnat o metodo toString da classe.
     *
     * @return Uma string.
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for (Simbolo simbolo : simbolos) {
            out.append(simbolo.getReferencia());
        }

        return out.toString();
    }

    /**
     * Verifica se a expressão regular contem apenas epsilon.
     *
     * @return Boolean com a resposta.
     */
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

    /**
     * Verifica se a expressão regular contem apenas o simbolo de vazio.
     *
     * @return Boolean com a resposta.
     */
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

    /**
     * Copia a expressão regular atual para outra.
     *
     * @return A Expressão regular copiada.
     */
    public ExpressaoRegular clone() {
        ExpressaoRegular er = new ExpressaoRegular();
        er.simbolos.addAll(simbolos);

        return er;
    }

}
