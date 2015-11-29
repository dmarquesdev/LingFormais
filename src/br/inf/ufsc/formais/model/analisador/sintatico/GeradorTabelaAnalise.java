package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;
import br.inf.ufsc.formais.model.gramatica.glc.RegraProducaoGLC;

public class GeradorTabelaAnalise {

	public static TabelaAnalise gerarTabela(GramaticaLivreContexto glc, Map<Simbolo, Set<Simbolo>> first, Map<Simbolo, Set<Simbolo>> follow) {
		// pegar o first do nao terminal e
		TabelaAnalise tabelaAnalise = new TabelaAnalise();

		// para cada regra
		for (RegraProducaoGLC regra : glc.getRegrasDeProducao()) {
			Simbolo primeiroSimbolo = regra.getCadeiaProduzida().getPrimeiroSimbolo();

			Set<Simbolo> firstSet = getFirst(first, primeiroSimbolo);

			// para cada simbolo terminal em first da producao
			for (SimboloTerminal terminalFirst : getTerminais(firstSet)) {
				EntradaTabelaAnalise entrada = new EntradaTabelaAnalise(regra.getSimboloProducao(), terminalFirst);
				tabelaAnalise.add(entrada, regra.getCadeiaProduzida());
			}

			if (firstSet.contains(Simbolo.EPSILON)) {

				Set<Simbolo> followSet = follow.get(regra.getCadeiaProduzida().getPrimeiroSimbolo());
				for (SimboloTerminal terminalFollow : getTerminais(followSet)) {
					EntradaTabelaAnalise entrada = new EntradaTabelaAnalise(regra.getSimboloProducao(), terminalFollow);
					tabelaAnalise.add(entrada, new CadeiaGLC("EPSILON"));

				}
			}
		}

		return tabelaAnalise;
	}

	private static Set<SimboloTerminal> getTerminais(Set<Simbolo> simbolos) {
		LinkedHashSet<SimboloTerminal> terminais = new LinkedHashSet<>();

		for (Simbolo simbolo : simbolos) {
			if (simbolo instanceof SimboloTerminal) {
				terminais.add((SimboloTerminal) simbolo);
			}
		}
		return terminais;
	}

	private static Set<Simbolo> getFirst(Map<Simbolo, Set<Simbolo>> first, Simbolo simbolo) {
		if (simbolo instanceof SimboloTerminal) {
			LinkedHashSet<Simbolo> terminalSet = new LinkedHashSet<>();
			terminalSet.add(simbolo);
			return terminalSet;
		} else {
			return first.get(simbolo);
		}

	}

}
