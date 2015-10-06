/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.er;

import br.inf.ufsc.formais.model.Simbolo;

/**
 *
 * Define um simbolo operacional de uma expressão regular.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class SimboloOperacional extends Simbolo {

    public static final SimboloOperacional FECHO = new SimboloOperacional("*");
    public static final SimboloOperacional ALTERNANCIA = new SimboloOperacional("|");
    public static final SimboloOperacional ABRE_GRUPO = new SimboloOperacional("(");
    public static final SimboloOperacional FECHA_GRUPO = new SimboloOperacional(")");

    public SimboloOperacional(String referencia) {
        super(referencia);
    }

}
