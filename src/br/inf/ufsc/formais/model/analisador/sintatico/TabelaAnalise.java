package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.LinkedHashMap;
import java.util.Map;

import br.inf.ufsc.formais.exception.AnaliseSintaticaException;
import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;
/**
 * Classe que define uma tabela de parsing.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
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

	public CadeiaGLC getCadeia(EntradaTabelaAnalise entrada) throws AnaliseSintaticaException {
            
		if (this.mapaSintatico.containsKey(entrada)) {
			return this.mapaSintatico.get(entrada);
		}
		System.out.println(entrada);
		throw new AnaliseSintaticaException();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();

		for (EntradaTabelaAnalise entrada : this.mapaSintatico.keySet()) {
			out.append(entrada.toString()).append(" = ").append(this.mapaSintatico.get(entrada).toString()).append("\n");
		}

		return out.toString();
	}
}
