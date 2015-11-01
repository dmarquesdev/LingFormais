package br.inf.ufsc.formais.operacoes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;

public class AFDMinimizer {

	private AutomatoFinitoDeterministico afd;

	public AFDMinimizer(AutomatoFinitoDeterministico afd) {
		this.afd = afd;
	}

	public AutomatoFinitoDeterministico minimizar() {
		this.afd.removeUnreachableStates();
		this.afd.removeDeadStates();
		LinkedHashSet<Estados> classesEquivalencia = this.determineEquivalenceClasses();
		AutomatoFinitoDeterministico afdMin = this.generateMinimumAutomaton(classesEquivalencia);
		return afdMin;
	}

	// Hopcroft’s algorithm
	private LinkedHashSet<Estados> determineEquivalenceClasses() {

		Estados terminais = new Estados();

		for (EstadoFinal ef : afd.getEstadosAceitacao()) {
			terminais.addEstado((Estado) ef);
		}

		Estados naoTerminais = new Estados();
		naoTerminais.get().addAll(afd.getEstados());
		naoTerminais.get().removeAll(terminais.get());

		LinkedHashSet<Estados> classesEquivalencia = new LinkedHashSet<Estados>();
		classesEquivalencia.add(terminais);
		classesEquivalencia.add(naoTerminais);
		LinkedHashSet<Estados> toBePartitioned = new LinkedHashSet<Estados>();
		toBePartitioned.add(terminais);
		toBePartitioned.add(naoTerminais);

		while (!toBePartitioned.isEmpty()) {
			Estados atual = (Estados) toBePartitioned.toArray()[0];
			toBePartitioned.remove(atual);

			for (Simbolo simbolo : afd.getAlfabeto().getSimbolos()) {
				Estados reachCurrentPartition = statesThatReachCurrentPartition(atual, simbolo);

				LinkedHashSet<Estados> partitions = getPartitionsTobePartitioned(classesEquivalencia, reachCurrentPartition);

				for (Estados partition : partitions) {

					Estados interseccao = interseccao(partition, reachCurrentPartition);
					Estados diferenca = diferenca(partition, reachCurrentPartition);

					classesEquivalencia.remove(partition);
					classesEquivalencia.add(interseccao);
					classesEquivalencia.add(diferenca);

					if (toBePartitioned.contains(partition)) {
						toBePartitioned.remove(partition);
						toBePartitioned.add(interseccao);
						toBePartitioned.add(diferenca);
					} else {
						if (interseccao.get().size() <= diferenca.get().size()) {
							toBePartitioned.add(interseccao);
						} else {
							toBePartitioned.add(diferenca);
						}
					}
				}
			}
		}
		return classesEquivalencia;
	}

	private AutomatoFinitoDeterministico generateMinimumAutomaton(LinkedHashSet<Estados> classesEquivalencia) {

		if (isMinimumAutomatom(this.afd, classesEquivalencia)) {
			return this.afd;
		}

		// cria novos estados para o automato minimizado
		LinkedHashMap<Estados, Estado> novosEstados = new LinkedHashMap<Estados, Estado>();
		Estados estadoInicial = getEstadoInicial(classesEquivalencia);
		classesEquivalencia.remove(estadoInicial);
		novosEstados.put(estadoInicial, new EstadoInicial("Q0"));
		int index = 1;

		for (Estados estados : classesEquivalencia) {
			Estado novoEstado = new Estado("Q" + index);
			novosEstados.put(estados, novoEstado);
			index++;
		}

		// cria as novas transições
		LinkedHashMap<Entrada, Estado> novasTransicoes = new LinkedHashMap<Entrada, Estado>();
		for (Estados particao : novosEstados.keySet()) {

			Estado estadoAtual = (Estado) particao.get().toArray()[0];

			for (Simbolo simboloAtual : this.afd.getAlfabeto().getSimbolos()) {
				Entrada entrada = new Entrada(estadoAtual, simboloAtual);
				Estado alcancavel = this.afd.getEstadoTransicao(entrada);
				
				//trata o caso de transições para vazio
				if (alcancavel != null) {
					Estados classeEquivalenciaAlcancavel = getClasseEquivalencia(classesEquivalencia, alcancavel);

					Entrada novaEntrada = new Entrada(novosEstados.get(particao), simboloAtual);
					Estado novoEstadoAlcancavel = novosEstados.get(classeEquivalenciaAlcancavel);

					novasTransicoes.put(novaEntrada, novoEstadoAlcancavel);
				}
			}
		}

		// cria novos estados de aceitacao
		LinkedHashSet<EstadoFinal> novosEstadosFinais = new LinkedHashSet<EstadoFinal>();
		LinkedHashSet<Estados> classesEquivalenciaAceitacao = getClassesEquivalenciaAceitacao(classesEquivalencia);
		for (Estados particao : classesEquivalenciaAceitacao) {
			novosEstadosFinais.add(new EstadoFinal(novosEstados.get(particao).getId()));
		}
		// conjunto de estados do novo automato minimo
		LinkedHashSet<Estado> estados = new LinkedHashSet<>(novosEstados.values());

		return new AutomatoFinitoDeterministico(estados, afd.getAlfabeto(), (EstadoInicial) novosEstados.get(estadoInicial), novosEstadosFinais, novasTransicoes);
	}

	// Retorna todos os estados que dado um simbolo alcançam a particao
	private Estados statesThatReachCurrentPartition(Estados currentPartition, Simbolo simbolo) {
		Estados reachCurrentPartition = new Estados();

		for (Entrada entrada : this.afd.getTransicoes().keySet()) {
			if (entrada.getSimbolo().equals(simbolo)) {
				Estado alcancavel = this.afd.getEstadoTransicao(entrada);
				if (currentPartition.get().contains(alcancavel)) {
					reachCurrentPartition.addEstado(entrada.getEstado());
				}
			}
		}
		return reachCurrentPartition;
	}

	// for all R in P(Classes de equivalencia) such that R ∩ la ̸= ∅ and R ̸⊆ la
	// la ← δ^-1(S,a)

	private LinkedHashSet<Estados> getPartitionsTobePartitioned(LinkedHashSet<Estados> classesEquivalencia, Estados toBeRefined) {
		LinkedHashSet<Estados> refinados = new LinkedHashSet<Estados>();

		for (Estados CEAtual : classesEquivalencia) {
			LinkedHashSet<Estado> interseccao = new LinkedHashSet<Estado>();
			interseccao.addAll(CEAtual.get());
			interseccao.retainAll(toBeRefined.get());

			boolean estaContido = toBeRefined.get().containsAll(CEAtual.get());

			if (!interseccao.isEmpty() && !estaContido) {
				refinados.add(CEAtual);
			}
		}
		return refinados;
	}

	private Estados interseccao(Estados one, Estados other) {
		LinkedHashSet<Estado> interseccao = new LinkedHashSet<Estado>();
		interseccao.addAll(one.get());
		interseccao.retainAll(other.get());
		return new Estados(interseccao);
	}

	private Estados diferenca(Estados one, Estados other) {
		LinkedHashSet<Estado> diferenca = new LinkedHashSet<Estado>();
		diferenca.addAll(one.get());
		diferenca.removeAll(other.get());
		return new Estados(diferenca);
	}

	private Estados getEstadoInicial(LinkedHashSet<Estados> classesEquivalencia) {
		for (Estados particao : classesEquivalencia) {
			if (particao.get().contains(this.afd.getEstadoInicial())) {
				return particao;
			}
		}
		return new Estados();
	}

	private Estados getClasseEquivalencia(LinkedHashSet<Estados> classesEquivalencia, Estado estado) {
		for (Estados particao : classesEquivalencia) {
			if (particao.get().contains(estado)) {
				return particao;
			}
		}
		return new Estados();
	}

	private LinkedHashSet<Estados> getClassesEquivalenciaAceitacao(LinkedHashSet<Estados> classesEquivalencia) {
		LinkedHashSet<Estados> classesEquivalenciaAceitacao = new LinkedHashSet<Estados>();

		for (Estados particao : classesEquivalencia) {
			LinkedHashSet<Estado> interseccao = new LinkedHashSet<Estado>();
			interseccao.addAll(particao.get());
			interseccao.retainAll(afd.getEstadosAceitacao());

			if (!interseccao.isEmpty()) {
				classesEquivalenciaAceitacao.add(particao);
			}
		}
		return classesEquivalenciaAceitacao;
	}

	private boolean isMinimumAutomatom(AutomatoFinitoDeterministico afd, LinkedHashSet<Estados> classesEquivalencia) {
		if (classesEquivalencia.size() != afd.getEstados().size()) {
			return false;
		}
		return true;
	}

}
