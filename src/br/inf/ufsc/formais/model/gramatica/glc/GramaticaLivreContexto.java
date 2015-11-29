package br.inf.ufsc.formais.model.gramatica.glc;

import java.util.Set;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

public class GramaticaLivreContexto {

	private Set<SimboloNaoTerminal> simbolosNaoTerminais;
	private Set<SimboloTerminal> simbolosTerminais;
	private Set<RegraProducaoGLC> regrasDeProducao;
	private SimboloNaoTerminal simboloInicial;

	public GramaticaLivreContexto(Set<SimboloNaoTerminal> simbolosNaoTerminais, Set<SimboloTerminal> simbolosTerminais, Set<RegraProducaoGLC> regrasDeProducao, SimboloNaoTerminal simboloInicial) {
		this.simbolosNaoTerminais = simbolosNaoTerminais;
		this.simbolosTerminais = simbolosTerminais;
		this.regrasDeProducao = regrasDeProducao;
		this.simboloInicial = simboloInicial;
	}

	public Set<SimboloNaoTerminal> getSimbolosNaoTerminais() {
		return this.simbolosNaoTerminais;
	}

	public Set<SimboloTerminal> getSimbolosTerminais() {
		return this.simbolosTerminais;
	}

	public void setSimbolosTerminais(Set<SimboloTerminal> simbolosTerminais) {
		this.simbolosTerminais = simbolosTerminais;
	}

	public Set<RegraProducaoGLC> getRegrasDeProducao() {
		return this.regrasDeProducao;
	}

	public void setRegrasDeProducao(Set<RegraProducaoGLC> regrasDeProducao) {
		this.regrasDeProducao = regrasDeProducao;
	}

	public SimboloNaoTerminal getSimboloInicial() {
		return this.simboloInicial;
	}

	public void setSimboloInicial(SimboloNaoTerminal simboloInicial) {
		this.simboloInicial = simboloInicial;
	}

	public void setSimbolosNaoTerminais(Set<SimboloNaoTerminal> simbolosNaoTerminais) {
		this.simbolosNaoTerminais = simbolosNaoTerminais;
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (RegraProducaoGLC regra : this.regrasDeProducao) {
			out.append(regra.toString()).append("\n");
		}

		return out.toString();
	}
}
