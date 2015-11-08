package br.inf.ufsc.formais.operacoes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import br.inf.ufsc.formais.common.IndexGenerator;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;

/**
 * Implementação do algoritmo de minimização de automatos finitos deterministicos.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFDMinimizer {

        
	private static AutomatoFinitoDeterministico afd;

        /**
         * Minimiza o automato finito deterministico recebido por parâmetro.
         * Primeiro os estados inacalcaçaveis e mortos são removidos.
         * Depois as classes de equivalencia são determinidas e ṕor fim o automato minimo é gerado.
         * @param AFD automato finito deterministico a ser minimizado.
         * @return automato finito deterministico minimo.
         */
	public static AutomatoFinitoDeterministico minimizar(AutomatoFinitoDeterministico AFD) {
		afd = AFD;
		afd.removeUnreachableStates();
		afd.removeDeadStates();
		LinkedHashSet<Estados> classesEquivalencia = determineEquivalenceClasses();
		AutomatoFinitoDeterministico afdMin = generateMinimumAutomaton(classesEquivalencia);
		return afdMin;
	}

	/**
         * Implementação do algortimo de Hopcroft’s que determina as classes de equivalencia de um AFD.
         * @return Conjuntos com as classes de equivalencia.
         */
	private static LinkedHashSet<Estados> determineEquivalenceClasses() {

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
        
        /**
         * Gera um automato minimo a partir das classes de equivalencia.
         * @param classesEquivalencia classes de equivalencia a serem utilizadas para gerar o automato.
         * @return um novo AFD mínimo.
         */
	private static AutomatoFinitoDeterministico generateMinimumAutomaton(LinkedHashSet<Estados> classesEquivalencia) {

		if (isMinimumAutomatom(afd, classesEquivalencia)) {
			return afd;
		}

		// cria novos estados para o automato minimizado
		LinkedHashMap<Estados, Estado> novosEstados = new LinkedHashMap<Estados, Estado>();
		Estados estadoInicial = getEstadoInicial(classesEquivalencia);
		classesEquivalencia.remove(estadoInicial);
		novosEstados.put(estadoInicial, new EstadoInicial("Q" + IndexGenerator.newIndex()));

		for (Estados estados : classesEquivalencia) {
			Estado novoEstado = new Estado("Q" + IndexGenerator.newIndex());
			novosEstados.put(estados, novoEstado);
		}

		// cria as novas transições
		LinkedHashMap<Entrada, Estado> novasTransicoes = new LinkedHashMap<Entrada, Estado>();
		classesEquivalencia.add(estadoInicial);
		for (Estados particao : novosEstados.keySet()) {

			Estado estadoAtual = (Estado) particao.get().toArray()[0];

			for (Simbolo simboloAtual : afd.getAlfabeto().getSimbolos()) {
				Entrada entrada = new Entrada(estadoAtual, simboloAtual);
				if (afd.existeTransicao(entrada)) {
					Estado alcancavel = afd.getEstadoTransicao(entrada);

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

	/**
         * Retorna todos os estados que dado um simbolo alcançam a particao
         * @param currentPartition partição que se deseja obter os estados que a alcançam por um simbolo
         * @param simbolo simbolo utilizado para ver as transições que alcançam a partição.
         * @return conjunto de estados que alcançam a partição recebida por parâmetro
         */ 
	private static Estados statesThatReachCurrentPartition(Estados currentPartition, Simbolo simbolo) {
		Estados reachCurrentPartition = new Estados();

		for (Entrada entrada : afd.getTransicoes().keySet()) {
			if (entrada.getSimbolo().equals(simbolo)) {
				Estado alcancavel = afd.getEstadoTransicao(entrada);
				if (currentPartition.get().contains(alcancavel)) {
					reachCurrentPartition.addEstado(entrada.getEstado());
				}
			}
		}
		return reachCurrentPartition;
	}

        /**
         * Obtem as partições que irão sofrer o "Split" (irão ser divididas)
         * Retorna todas as partições que a intersecção com a partição recebida por parâmetro
         * não for vazia e que não esteja contida na partição
         * for all R in P(Classes de equivalencia) such that R ∩ la ̸= ∅ and R ̸⊆ la
         * @param classesEquivalencia Todas as classes de equivalencia já calculadas
         * @param toBeRefined partição que se deseja obter a intersecção.
         * @return particões que serão divididas pelo algortimo de Hopcroft
         */
	private static LinkedHashSet<Estados> getPartitionsTobePartitioned(LinkedHashSet<Estados> classesEquivalencia, Estados toBeRefined) {
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

        /**
         * Encontra a intersecçao entre dois estados
         * @param one conjunto que será utilizado para obter a intersecção
         * @param other conjunto que será utilizado para obter a intersecção
         * @return A intersecção entre os dois conuntos de estados.
         */
	private static Estados interseccao(Estados one, Estados other) {
		LinkedHashSet<Estado> interseccao = new LinkedHashSet<Estado>();
		interseccao.addAll(one.get());
		interseccao.retainAll(other.get());
		return new Estados(interseccao);
	}

        /**
        * Encontra a diferença entre dois estados
        * @param one conjunto que será utilizado para obter a diferença
        * @param other conjunto que será utilizado para obter a diferença
        * @return A diferença entre os dois conuntos de estados.
        */
	private static Estados diferenca(Estados one, Estados other) {
		LinkedHashSet<Estado> diferenca = new LinkedHashSet<Estado>();
		diferenca.addAll(one.get());
		diferenca.removeAll(other.get());
		return new Estados(diferenca);
	}

        /**
         * Retorna a partição que contem o estado inicial do AFD
         * @param classesEquivalencia conjunto que contém todas as classes de equivalencia
         * @return Partição que contém o estado incicial do AFD
         */
	private static Estados getEstadoInicial(LinkedHashSet<Estados> classesEquivalencia) {
		for (Estados particao : classesEquivalencia) {
			if (particao.get().contains(afd.getEstadoInicial())) {
				return particao;
			}
		}
		return new Estados();
	}
        
        /**
         * Retorna a classe de equivalencia a que o estado pertence
         * @param classesEquivalencia conjunto com todas as classes de equivalencias
         * @param estado estado que se deseja encontrar a classe de equivalencia a que ele pertence
         * @return classe de equivalencia que contem o estado
         */
	private static Estados getClasseEquivalencia(LinkedHashSet<Estados> classesEquivalencia, Estado estado) {
		for (Estados particao : classesEquivalencia) {
			if (particao.get().contains(estado)) {
				return particao;
			}
		}
		return new Estados();
	}

        /**
         * Retorna as classes de equivalencia que contém pelo menos um estado de aceitação
         * @param classesEquivalencia conjunto com todas as classes de equivalencias
         * @return Conjunto com as classes de equivalencia que contem ao menos um estado de aceitação
         */
	private static LinkedHashSet<Estados> getClassesEquivalenciaAceitacao(LinkedHashSet<Estados> classesEquivalencia) {
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

        /**
         * Retorna se o automato é mínimo
         * @param afd AFD que se deseja saber se é mínimo
         * @param classesEquivalencia conjuntos com todas as classes de equivalencia do automato
         * @return Verdadeiro se o automato é minimo, falso caso não seja.
         */
	private static boolean isMinimumAutomatom(AutomatoFinitoDeterministico afd, LinkedHashSet<Estados> classesEquivalencia) {
		if (classesEquivalencia.size() != afd.getEstados().size()) {
			return false;
		}
		return true;
	}
}
