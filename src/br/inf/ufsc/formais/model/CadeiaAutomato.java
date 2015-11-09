package br.inf.ufsc.formais.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a cadeia de simbolos que será computada por um automato finito.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class CadeiaAutomato {

	private List<Simbolo> simbolos;

        /**
         * Contrutor. Iniciliaza a lista de simbolos da classe com a lista de simbolo recebido por parâmetro.
         * @param simbolos lista de simbolos que serão inicializados a classe.
         */
	public CadeiaAutomato(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}

         /**
         * Contrutor. Iniciliaza a lista de simbolos da classe.
         * Transforma a string recebida por parâmetros em uma lista de simbolos e seta no atributo da classe.
         * @param simbolos String que sera inicializada a classe.
         */
	public CadeiaAutomato(String simbolos) {
		this.simbolos = new ArrayList<Simbolo>();
		for (char simbolo : simbolos.toCharArray()) {
			Simbolo novoSimbolo = new Simbolo(String.valueOf(simbolo));
			this.simbolos.add(novoSimbolo);
		}
	}

        /**
         * Retorna a lista de simbolos da cadeia.
         * @return lista de simbolos da cadeia.
         */
	public List<Simbolo> getSimbolos() {
		return simbolos;
	}

        /**
         * Seta a lista de simbolos da classe.
         * @param simbolos lista de simbolos que se deseja setar na classe.
         */
	public void setSimbolos(List<Simbolo> simbolos) {
		this.simbolos = simbolos;
	}
	
	@Override
	public String toString() {
		return this.simbolos.toString();
	}

}
