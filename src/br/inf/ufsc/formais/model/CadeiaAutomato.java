package br.inf.ufsc.formais.model;

import java.util.ArrayList;
import java.util.List;

public class CadeiaAutomato {

	private List<Simbolo> simbolos;

	public CadeiaAutomato(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}

	public CadeiaAutomato(String simbolos) {
		this.simbolos = new ArrayList<Simbolo>();
		for (char simbolo : simbolos.toCharArray()) {
			Simbolo novoSimbolo = new Simbolo(String.valueOf(simbolo));
			this.simbolos.add(novoSimbolo);
		}
	}

	public List<Simbolo> getSimbolos() {
		return simbolos;
	}

	public void setSimbolos(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}
	
	@Override
	public String toString() {
		return this.simbolos.toString();
	}

}
