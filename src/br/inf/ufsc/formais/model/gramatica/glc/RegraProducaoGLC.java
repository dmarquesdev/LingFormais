package br.inf.ufsc.formais.model.gramatica.glc;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;

public class RegraProducaoGLC {

	private SimboloNaoTerminal simboloProducao;
	private CadeiaGLC cadeiaProduzida;

	public RegraProducaoGLC(SimboloNaoTerminal simboloProducao, CadeiaGLC cadeiaProduzida) {
		this.simboloProducao = simboloProducao;
		this.cadeiaProduzida = cadeiaProduzida;
	}

	public SimboloNaoTerminal getSimboloProducao() {
		return this.simboloProducao;
	}

	public void setSimboloProducao(SimboloNaoTerminal simboloProducao) {
		this.simboloProducao = simboloProducao;
	}

	public CadeiaGLC getCadeiaProduzida() {
		return this.cadeiaProduzida;
	}

	public void setCadeiaProduzida(CadeiaGLC cadeiaProduzida) {
		this.cadeiaProduzida = cadeiaProduzida;
	}

	@Override
	public String toString() {
		return this.simboloProducao.getReferencia() + " -> " + this.cadeiaProduzida.toString();
	}

}
