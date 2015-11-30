package br.inf.ufsc.formais.model.gramatica.glc;

import br.inf.ufsc.formais.model.Simbolo;
import java.util.Set;

import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class GramaticaLivreContexto {

	private Set<SimboloNaoTerminal> simbolosNaoTerminais;
	private Set<SimboloTerminal> simbolosTerminais;
	private Set<RegraProducaoGLC> regrasDeProducao;
	private SimboloNaoTerminal simboloInicial;

        /**
         * Construtor da classe GLC
         * @param simbolosNaoTerminais
         * @param simbolosTerminais
         * @param regrasDeProducao
         * @param simboloInicial 
         */
	public GramaticaLivreContexto(Set<SimboloNaoTerminal> simbolosNaoTerminais, Set<SimboloTerminal> simbolosTerminais, Set<RegraProducaoGLC> regrasDeProducao, SimboloNaoTerminal simboloInicial) {
		this.simbolosNaoTerminais = simbolosNaoTerminais;
		this.simbolosTerminais = simbolosTerminais;
		this.regrasDeProducao = regrasDeProducao;
		this.simboloInicial = simboloInicial;
	}

        /**
         * Verifica se a gramatica é LL(1) comparando os first e follow de cada NT.
         * @param first
         * @param follow
         * @return boolean.
         */
        public boolean isLL1(Map<Simbolo, Set<Simbolo>> first, Map<Simbolo, Set<Simbolo>> follow) {
		for (Simbolo simbolo : first.keySet()) {
			LinkedHashSet<Simbolo> interseccao = new LinkedHashSet<>();
			interseccao.addAll(first.get(simbolo));
			interseccao.retainAll(follow.get(simbolo));
			if (!interseccao.isEmpty()) {
				// System.out.println(simbolo + " intersecção fisrtFollow: " + interseccao);
				return true;
			}
		}
		return false;
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
