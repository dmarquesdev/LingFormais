
package br.inf.ufsc.formais.model;

/**
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 * 
 * Classe define cada grupo de expressões regulares. É utilizado para distinguir 
 * os itens da linguagem.
 */
public enum Grupo {
	IDENTIFICADORES("Identificadores"),
	OPERADORESLOGICOS("Operadores lógicos"),
	LOOP("Loop"),
	PALAVRASRESERVADAS("Palavras reservadas"),
	SEPARADORES("Separadores"),
	OPERADORES("Operadores"),
	CONDICIONAL("Condicional"),
        CONSTANTES("Constantes");
        
	private final String descricao;
	
        /**
         * construtor da classe.
         * @param descricao String
         */
	private Grupo(String descricao) {
		this.descricao = descricao;
	}
	
        /**
         * Metodo get do atribulo descrição.
         * @return Uma String
         */
	public String getDescricao(){
		return this.descricao;
	}
	
        /**
         * Implementação do método toString().
         * @return 
         */
	@Override
	public String toString() {
		return this.descricao;
	}
}

