package br.inf.ufsc.formais.operacoes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;

public class AFND2AFD {

	public static AutomatoFinitoDeterministico determinizar(AutomatoFinitoNaoDeterministico AFND) {

		Set<Estados> estadosAgrupados = new LinkedHashSet<Estados>();
		Set<Estados> estadosToBeGrouped = new LinkedHashSet<Estados>();

		Estados estadoInicial = new Estados(new HashSet<Estado>());
		estadoInicial.addEstado(AFND.getEstadoInicial());
		estadosAgrupados.add(estadoInicial);
		estadosToBeGrouped.add(estadoInicial);

		// agrupar estados para formar novos deterministicos
		while (!estadosToBeGrouped.isEmpty()) {

			HashSet<Estado> novoEstado = new LinkedHashSet<Estado>();
			Estados estadoAtual = (Estados) estadosToBeGrouped.toArray()[0];

			for (Simbolo simbolo : AFND.getAlfabeto().getSimbolos()) {

				for (Estado estado : estadoAtual.get()) {

					Entrada entrada = new Entrada(estado, simbolo);
					Set<Estado> alcancaveis = AFND.getEstadosTransicao(entrada).get();

					// trata o caso de transição para o vazio
					if (!alcancaveis.isEmpty()) {
						novoEstado.addAll(AFND.getEstadosTransicao(entrada).get());
					}
				}
			}
			Estados novoEstados = new Estados(novoEstado);
			estadosAgrupados.add(novoEstados);
			estadosToBeGrouped.add(novoEstados);
			estadosToBeGrouped.remove(estadoAtual);

		}

		// criar novos estados deterministicos
		HashMap<Estados, Estado> estadosDeterministicos = new LinkedHashMap<Estados, Estado>();
		Set<EstadoFinal> estadosAceitacaoDeterministico = new HashSet<EstadoFinal>();
		Estado estadoInicialDeterministico = new EstadoInicial("Q0");
		estadosDeterministicos.put(estadoInicial, estadoInicialDeterministico);

		estadosAgrupados.remove(estadoInicial);

		int indexEstados = 1;

		for (Estados estadosAgrupado : estadosAgrupados) {

			if (isFinalState(estadosAgrupado)) {
				Estado novoEstadoFinal = new EstadoFinal("Q" + indexEstados);
				estadosDeterministicos.put(estadosAgrupado, novoEstadoFinal);
				estadosAceitacaoDeterministico.add((EstadoFinal) novoEstadoFinal);
			} else {

				estadosDeterministicos.put(estadosAgrupado, new Estado("Q" + indexEstados));
			}

			indexEstados++;
		}

		// criar novas transições deterministicas
		Map<Entrada, Estado> transicoesDeterministicas = new HashMap<Entrada, Estado>();

		estadosAgrupados.add(estadoInicial);

		for (Estados estadosEntrada : estadosAgrupados) {

			for (Simbolo simboloAtual : AFND.getAlfabeto().getSimbolos()) {
				HashSet<Estado> novoEstado = new LinkedHashSet<Estado>();

				for (Estado estadoAtual : estadosEntrada.get()) {
					Entrada entrada = new Entrada(estadoAtual, simboloAtual);
					Set<Estado> alcancaveis = AFND.getEstadosTransicao(entrada).get();

					// trata o caso de transição para o vazio
					if (!alcancaveis.isEmpty()) {
						novoEstado.addAll(AFND.getEstadosTransicao(entrada).get());
					}
				}

				Estados alcancaveis = new Estados(novoEstado);
				//só cria transicao deterministica se o conjunto dos alcançaveis não for nulo
				if (!alcancaveis.get().isEmpty()) {
					Entrada entradaDeterministica = new Entrada(estadosDeterministicos.get(estadosEntrada), simboloAtual);
					Estado estadoAlcancavelDeterministico = estadosDeterministicos.get(alcancaveis);

					transicoesDeterministicas.put(entradaDeterministica, estadoAlcancavelDeterministico);
				}
			}

		}

		Set<Estado> estadosAFD = new LinkedHashSet<Estado>();
		estadosAFD.addAll(estadosDeterministicos.values());

		return new AutomatoFinitoDeterministico(estadosAFD, AFND.getAlfabeto(), (EstadoInicial) estadoInicialDeterministico, estadosAceitacaoDeterministico, transicoesDeterministicas);
	}

	private static boolean isFinalState(Estados estados) {
		for (Estado estadoAtual : estados.get()) {
			if (estadoAtual instanceof EstadoFinal) {
				return true;
			}
		}
		return false;
	}

}
