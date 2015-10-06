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

/**
 * Implementação do algoritmo de determinização de automatos finitos não
 * deterministicos sem epsilon transição.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFND2AFD {

    /**
     * Retorna um Automato Finito Deterministico.
     * A partir do AFND, o algortimo encontra todos os estados alcançaveis, podendo ser mais que um, por um simbolo.
     * Então todos os conjuntos de estados alcançaveis são agrupados para formar os novos estados deterministicos. 
     * Depois disso, cada conjunto é mapeado para um novo estado deterministico e são definidos os novos estados finais.
     * Por fim, as novas transições deterministicas são encontradas e o Automato Finito Deterministico é gerado. 
     * @param AFND Automato finito não deterministico a ser determinizado.
     * @return O Automato finito determinizado.
     */
    public static AutomatoFinitoDeterministico determinizar(AutomatoFinitoNaoDeterministico AFND) {

        Set<Estados> estadosAgrupados = new LinkedHashSet<Estados>();
        Set<Estados> estadosToBeGrouped = new LinkedHashSet<Estados>();
        Set<Estados> estadosAlcancaveis = new LinkedHashSet<Estados>();

        Estados estadoInicial = new Estados(new HashSet<Estado>());
        estadoInicial.addEstado(AFND.getEstadoInicial());
        estadosToBeGrouped.add(estadoInicial);

        // agrupar estados para formar novos deterministicos
        while (!estadosToBeGrouped.isEmpty()) {

            Estados estadoAtual = (Estados) estadosToBeGrouped.toArray()[0];

            for (Simbolo simbolo : AFND.getAlfabeto().getSimbolos()) {

                HashSet<Estado> novoEstado = new LinkedHashSet<Estado>();

                for (Estado estado : estadoAtual.get()) {

                    Entrada entrada = new Entrada(estado, simbolo);
                    Set<Estado> alcancaveis = AFND.getEstadosTransicao(entrada).get();
                    novoEstado.addAll(AFND.getEstadosTransicao(entrada).get());

                }
                Estados novoEstados = new Estados(novoEstado);
                // trata o caso de transição para o vazio
                if (!novoEstados.get().isEmpty()) {
                    estadosAlcancaveis.add(novoEstados);

                }

            }
            estadosAgrupados.add(estadoAtual);
            estadosToBeGrouped.addAll(estadosAlcancaveis);
            estadosToBeGrouped.removeAll(estadosAgrupados);
        }

        // criar novos estados deterministicos
        HashMap<Estados, Estado> estadosDeterministicos = new LinkedHashMap<Estados, Estado>();
        Set<EstadoFinal> estadosAceitacaoDeterministico = new HashSet<EstadoFinal>();
        Estado estadoInicialDeterministico = new EstadoInicial("Q0");
        estadosDeterministicos.put(estadoInicial, estadoInicialDeterministico);

        estadosAgrupados.remove(estadoInicial);

        int indexEstados = 1;

        for (Estados estadosAgrupado : estadosAgrupados) {

            if (isFinalState(estadosAgrupado, AFND.getEstadosAceitacao())) {
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
                // só cria transicao deterministica se o conjunto dos alcançaveis não for nulo
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
    
     /**
     * Método auxiliar que retorna se dado um conjunto de estados algum deles é final(Estado de aceitação).
     * O metodo faz a intersecção do conjunto de estados finais com o conjunto
     * de estados passado por parâmetro, se a intersecção não for vazia
     * temos que o conjunto passado por paramêtro contem pelo menos um estado final.
     * @param estados Conjunto de estados que se deseja saber se contem ao menos um estado que é final.
     * @param finais Conjunto de estados finais do Automato finito não deterministico. 
     * @return Verdadeiro se estados contem um estado que é final, falso caso contrário.
     */
    private static boolean isFinalState(Estados estados, Set<EstadoFinal> finais) {
        Set<EstadoFinal> interseccao = new LinkedHashSet<EstadoFinal>();
        interseccao.addAll(finais);
        interseccao.retainAll(estados.get());
        if (!interseccao.isEmpty()) {
            return true;
        }
        return false;
    }

}
