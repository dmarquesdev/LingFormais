package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.LinkedHashMap;
import java.util.Map;

import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;

public class TabelaAnalise {

	private Map<EntradaTabelaAnalise, CadeiaGLC> mapaSintatico;

	public TabelaAnalise(Map<EntradaTabelaAnalise, CadeiaGLC> mapaSintatico) {
		this.mapaSintatico = mapaSintatico;
	}

	public TabelaAnalise() {
		this.mapaSintatico = new LinkedHashMap<EntradaTabelaAnalise, CadeiaGLC>();
	}

	public void add(EntradaTabelaAnalise entrada, CadeiaGLC cadeia) {
		this.mapaSintatico.put(entrada, cadeia);
	}

	public CadeiaGLC getCadeia(EntradaTabelaAnalise entrada) {
		if (this.mapaSintatico.containsKey(entrada)) {
			return this.mapaSintatico.get(entrada);
		}
		// lançar exception de erro
		return null;
	}
}
