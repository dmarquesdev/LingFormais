package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.Objects;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

public class EntradaTabelaAnalise {

	private SimboloNaoTerminal naoTerminal;
	private SimboloTerminal terminal;

	public EntradaTabelaAnalise(SimboloNaoTerminal naoTerminal, SimboloTerminal terminal) {
		this.naoTerminal = naoTerminal;
		this.terminal = terminal;
	}

	public SimboloNaoTerminal getNaoTerminal() {
		return this.naoTerminal;
	}

	public void setNaoTerminal(SimboloNaoTerminal naoTerminal) {
		this.naoTerminal = naoTerminal;
	}

	public SimboloTerminal getTerminal() {
		return this.terminal;
	}

	public void setTerminal(SimboloTerminal terminal) {
		this.terminal = terminal;
	}

	@Override
	public int hashCode() {
		int hash = 11;
		hash = 47 * hash + Objects.hashCode(this.naoTerminal);
		hash = 47 * hash + Objects.hashCode(this.terminal);
		return hash;
	}

	/**
	 * Verifica se a classe é igual ao Objeto recebido por paramêtro.
	 * 
	 * @param obj
	 *            Objeto a ser comparado.
	 * @return Verdadeiro se for igual, falso caso contrário.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		final EntradaTabelaAnalise other = (EntradaTabelaAnalise) obj;
		if (!Objects.equals(this.naoTerminal, other.getNaoTerminal())) {
			return false;
		}
		if (!Objects.equals(this.terminal, other.getTerminal())) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("[ ").append(this.naoTerminal).append(", ").append(this.terminal).append(" ]");
		return out.toString();
	}

}
