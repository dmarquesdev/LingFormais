package br.inf.ufsc.formais.operacoes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.common.IndexGenerator;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;

/**
 * Implementação do algoritmo de determinização de automatos finitos não deterministicos COM epsilon transição.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFND2AFD {

	private static AutomatoFinitoNaoDeterministico AFND;
	private static Map<Estados, Estados> epsilonFechoMap = new LinkedHashMap<Estados, Estados>();
	private static Map<Estado, Estado> old2NewFinalStatesMap = new LinkedHashMap<Estado, Estado>();

	/**
	 * Retorna um Automato Finito Deterministico. A partir do AFND, o algortimo encontra todos os estados alcançaveis, podendo ser mais que um, por um simbolo inclusive Epsilon. Então todos os
	 * conjuntos de estados alcançaveis são agrupados, utilizando epsilon fecho, para formar os novos estados deterministicos. Depois disso, cada conjunto é mapeado para um novo estado deterministico
	 * e são definidos os novos estados finais. Por fim, as novas transições deterministicas são encontradas e o Automato Finito Deterministico é gerado.
	 * 
	 * @param AFND
	 *            Automato finito não deterministico a ser determinizado.
	 * @return O Automato finito determinizado.
	 */
	public static AutomatoFinitoDeterministico determinizar(AutomatoFinitoNaoDeterministico AFND) {
		/**
		 * Atributo responsável por armazenar o Automato finito não deterministico.
		 */
		AFND2AFD.AFND = AFND;
		Set<Estados> estadosAgrupados = new LinkedHashSet<Estados>();
		Set<Estados> estadosToBeGrouped = new LinkedHashSet<Estados>();
		Set<Estados> estadosAlcancaveis = new LinkedHashSet<Estados>();

		Estados estadoInicial = new Estados(new HashSet<Estado>());
		estadoInicial.addEstado(AFND.getEstadoInicial());
		estadosToBeGrouped.add(estadoInicial);
		AFND.getAlfabeto().getSimbolos().remove(Simbolo.EPSILON);

		// agrupar estados para formar novos deterministicos
		while (!estadosToBeGrouped.isEmpty()) {

			Estados estadoAtual = ((Estados) estadosToBeGrouped.toArray()[0]);

			for (Simbolo simbolo : AFND.getAlfabeto().getSimbolos()) {

				Estados novosEstados = new Estados();
				Estados epsilonFecho = getEpsilonFecho(estadoAtual);

				for (Estado estado : epsilonFecho.get()) {
					Entrada entrada = new Entrada(estado, simbolo);
					if (AFND.existeTransicao(entrada)) {
						Estados alcancaveis = AFND.getEstadosTransicao(entrada);
						novosEstados.addEstados(alcancaveis);
					}
				}
				// trata o caso de transição para o vazio
				if (!novosEstados.isEmpty()) {
					estadosAlcancaveis.add(novosEstados);
				}
			}

			estadosAgrupados.add(estadoAtual);
			estadosToBeGrouped.addAll(estadosAlcancaveis);
			estadosToBeGrouped.removeAll(estadosAgrupados);
		}

		// criar novos estados deterministicos
		HashMap<Estados, Estado> estadosDeterministicos = new LinkedHashMap<Estados, Estado>();
		Set<EstadoFinal> estadosAceitacaoDeterministico = new HashSet<EstadoFinal>();
		Estado estadoInicialDeterministico = new EstadoInicial("Q" + IndexGenerator.newIndex());
		estadosDeterministicos.put(estadoInicial, estadoInicialDeterministico);

		estadosAgrupados.remove(estadoInicial);
		old2NewFinalStatesMap = new LinkedHashMap<Estado, Estado>();

		for (Estados estadosAgrupado : estadosAgrupados) {
			Estado novoEstado = new Estado("Q" + IndexGenerator.newIndex());
			estadosDeterministicos.put(estadosAgrupado, novoEstado);
			if (!getFinalStates(estadosAgrupado).isEmpty()) {
				EstadoFinal novoEstadoFinal = new EstadoFinal(novoEstado.getId());
				estadosAceitacaoDeterministico.add(novoEstadoFinal);
				putInFinalStatesMap(getFinalStates(estadosAgrupado), novoEstadoFinal);
			}
		}

		// criar novas transições deterministicas
		Map<Entrada, Estado> transicoesDeterministicas = new HashMap<Entrada, Estado>();

		estadosAgrupados.add(estadoInicial);

		for (Estados estadosEntrada : estadosAgrupados) {

			for (Simbolo simboloAtual : AFND.getAlfabeto().getSimbolos()) {
				Estados alcancaveis = new Estados();
				Estados epsilonFecho = getEpsilonFecho(estadosEntrada);

				for (Estado estadoAtual : epsilonFecho.get()) {
					Entrada entrada = new Entrada(estadoAtual, simboloAtual);
					if (AFND.existeTransicao(entrada)) {
						alcancaveis.addEstados(AFND.getEstadosTransicao(entrada));
					}
				}
				// só cria transicao deterministica se o conjunto dos alcançaveis não for nulo
				if (!alcancaveis.isEmpty()) {
					Entrada entradaDeterministica = new Entrada(estadosDeterministicos.get(estadosEntrada), simboloAtual);
					Estado estadoAlcancavelDeterministico = estadosDeterministicos.get(alcancaveis);
					transicoesDeterministicas.put(entradaDeterministica, estadoAlcancavelDeterministico);
				}
			}

		}

		Set<Estado> estadosAFD = new LinkedHashSet<Estado>();
		estadosAFD.addAll(estadosDeterministicos.values());
		epsilonFechoMap.clear();
		return new AutomatoFinitoDeterministico(estadosAFD, AFND.getAlfabeto(), (EstadoInicial) estadoInicialDeterministico, estadosAceitacaoDeterministico, transicoesDeterministicas);
	}

	/**
	 * Método auxiliar que retorna os estados finais contidos no conjunto de estados recebido por parametro.
	 * O metodo retorna a intersecção do conjunto de estados finais com o conjunto de estados passado
	 * por parâmetro.
	 * 
	 * @param estados
	 *            Conjunto de estados que se deseja obter os estados finais.
	 * @param finais
	 *            Conjunto de estados finais do Automato finito não deterministico.
	 * @return Conjunto que contem os estados finais encontrados.
	 */
	private static Estados getFinalStates(Estados estados) {
		Set<Estado> interseccao = new LinkedHashSet<Estado>();
		interseccao.addAll(AFND.getEstadosAceitacao());
		interseccao.retainAll(getEpsilonFecho(estados).get());
		return new Estados(interseccao);
	}

	private static Estados getEpsilonFecho(Estados estados) {
		if (epsilonFechoMap.containsKey(estados)) {
			return epsilonFechoMap.get(estados);
		}
		Estados epsilonFecho = AFND.epsilonFecho(estados);
		epsilonFechoMap.put(estados, epsilonFecho);
		return epsilonFecho;
	}

	private static void putInFinalStatesMap(Estados estadosFinais, EstadoFinal novoEstadoFinal) {
		for (Estado estadoFinal : estadosFinais.get()) {
			old2NewFinalStatesMap.put(estadoFinal, novoEstadoFinal);
		}
	}
	
	public static Map<Estado, Estado> getOld2NewFinalStatesMap(){
		return old2NewFinalStatesMap;
	}
}
