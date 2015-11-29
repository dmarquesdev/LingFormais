/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.gramatica.glc;

import java.util.Arrays;
import java.util.LinkedList;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

/**
 * Classe que define uma cadeia de simbolos de uma gramatica.
 * 
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class CadeiaGLC {

	private LinkedList<Simbolo> simbolosCadeia;

	public CadeiaGLC(LinkedList<Simbolo> simbolosCadeia) {
		this.simbolosCadeia = simbolosCadeia;
	}

	public CadeiaGLC(String cadeia) {
		LinkedList<String> simbolos = new LinkedList<>(Arrays.asList(cadeia.split(" ")));
		for (String simbolo : simbolos) {
			if (simbolo.equals(Simbolo.EPSILON) || simbolo.equals("EPSILON")) {
				this.simbolosCadeia.add(Simbolo.EPSILON);
			} else {
				if (Character.isUpperCase(simbolo.codePointAt(0))) {
					this.simbolosCadeia.add(new SimboloNaoTerminal(simbolo));
				} else {
					this.simbolosCadeia.add(new SimboloTerminal(simbolo));
				}
			}
		}
	}

	public LinkedList<Simbolo> getSimbolosCadeia() {
		return this.simbolosCadeia;
	}

	public void setSimbolosCadeia(LinkedList<Simbolo> simbolosCadeia) {
		this.simbolosCadeia = simbolosCadeia;
	}

	public Simbolo getPrimeiroSimbolo() {
		return this.simbolosCadeia.get(0);
	}

}
