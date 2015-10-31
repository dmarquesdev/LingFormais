package br.inf.ufsc.formais.operacoes;

import java.util.LinkedHashSet;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.Estados;

public class UnreachableStatesRemover {

	private AutomatoFinitoDeterministico afd;

	public UnreachableStatesRemover(AutomatoFinitoDeterministico af) {
		this.afd = af;
	}
	public AutomatoFinitoDeterministico removeUnreachableStates(){
		Estados unreachableStates = getUnreachableStates();
		removeUnreachableStates(unreachableStates);
		return this.afd;
	}
	private void removeUnreachableStates(Estados unreachableStates){
		this.afd.getEstados().removeAll(unreachableStates.get());
		this.afd.getEstadosAceitacao().removeAll(unreachableStates.get());
		
		for(Entrada entrada : this.afd.getTransicoes().keySet()){
			if(unreachableStates.get().contains(entrada.getEstado())){
				this.afd.getTransicoes().remove(entrada);
			}
		}
	}

	private Estados getUnreachableStates() {
		Estados estadosAlcancaveis = new Estados();
		estadosAlcancaveis.addEstado(this.afd.getEstadoInicial());
		Estados novosEstados = new Estados();
		novosEstados.addEstado(this.afd.getEstadoInicial());

		while (!novosEstados.isEmpty()) {
			Estados temp = new Estados();

			for (Estado estadoAtual : novosEstados.get()) {
				for (Simbolo simboloAtual : this.afd.getAlfabeto().getSimbolos()) {
					Entrada entrada = new Entrada(estadoAtual, simboloAtual);
					temp.addEstado(afd.getEstadoTransicao(entrada));
				}
			}
			novosEstados = diferenca(temp, estadosAlcancaveis);
			estadosAlcancaveis = uniao(estadosAlcancaveis, novosEstados);
		}
		Estados estadosInalcancaveis = new Estados();
		Estados estadosAFD = new Estados(this.afd.getEstados());
		estadosInalcancaveis = diferenca(estadosAFD, estadosAlcancaveis);
		return estadosInalcancaveis;
		
	}

	private Estados uniao(Estados one, Estados other) {
		LinkedHashSet<Estado> uniao = new LinkedHashSet<Estado>();
		uniao.addAll(one.get());
		uniao.addAll(other.get());
		return new Estados(uniao);
	}

	private Estados diferenca(Estados one, Estados other) {
		LinkedHashSet<Estado> diferenca = new LinkedHashSet<Estado>();
		diferenca.addAll(one.get());
		diferenca.removeAll(other.get());
		return new Estados(diferenca);
	}

}
