package br.inf.ufsc.formais.model;

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
	
	private Grupo(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return this.descricao;
	}
	
	@Override
	public String toString() {
		return this.descricao;
	}
}

