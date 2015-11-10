/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFLexico {

    private static Map<Grupo, Set<EstadoFinal>> finalStatesOfEachGroup = new LinkedHashMap<Grupo, Set<EstadoFinal>>();
    private static List<Grupo> groupPriority = new ArrayList<Grupo>();

    static {
        groupPriority.add(Grupo.PALAVRASRESERVADAS);
        groupPriority.add(Grupo.CONDICIONAL);
        groupPriority.add(Grupo.LOOP);
        groupPriority.add(Grupo.OPERADORES);
        groupPriority.add(Grupo.OPERADORESLOGICOS);
        groupPriority.add(Grupo.SEPARADORES);
        groupPriority.add(Grupo.LITERAL);
        groupPriority.add(Grupo.ATRIBUICAO);
        groupPriority.add(Grupo.TIPO);
        groupPriority.add(Grupo.IDENTIFICADORES);
    }

    /**
     * Retorna o grupo a qual o estado passado como parametro pertence.
     *
     * @param estadoAceitacao Um estado de aceitação.
     * @return Um grupo.
     */
    public static Grupo findGroup(Estado estadoAceitacao) {
        //obtem os antigos estados finais
        Estados antigosFinais = AFND2AFD.getNew2OldFinalStatesMap().get(estadoAceitacao);
        //itera sobre todos os grupos
        for (Grupo grupo : groupPriority) {
            //faz a interseccao dos antigos estados finais com os finais do grupo
            Set<Estado> interseccao = new LinkedHashSet<Estado>(antigosFinais.get());
            interseccao.retainAll(finalStatesOfEachGroup.get(grupo));
            // se a interseccao não é vazia o estadodeAceitacao pertence ao grupo
            if (!interseccao.isEmpty()) {
                return grupo;
            }
        }
        //temporario ...
        return Grupo.LOOP;
    }

    /**
     * Responsável por criar o automato reconhecedor de um grupo de expressões
     * regulares.
     *
     * @param ers Lista de expressões regulares.
     * @return Um automato finito deterministico.
     */
    private static AutomatoFinitoDeterministico geraAutomatoDoGrupo(ArrayList<ExpressaoRegular> ers) {
        AutomatoFinitoNaoDeterministico afnd = null;
        for (ExpressaoRegular er : ers) {
            System.out.println(er.toString());
            if (afnd == null) {
                afnd = ER2AFND.analisaConverte(er);
            } else {
                afnd = OperacoesAFND.ouEntreAFNDs(afnd, ER2AFND.analisaConverte(er));
            }
        }
        afnd = OperacoesAFND.concatenaAFs(afnd, OperacoesAFND.aFPalavraVazia());
        AutomatoFinitoDeterministico afd = AFND2AFD.determinizar(afnd);
        afd = AFDMinimizer.minimizar(afd);
        System.out.println(afd.toString());
        return afd;
    }

    /**
     * Responsavel por gerar o automato reconhecedor da linguagem.
     *
     * @param grupos Um map entre Grupo e lista de expressões regulares.
     * @return Um automato finito deterministico.
     */
    public static AutomatoFinitoDeterministico geraAutomatoFinal(Map<Grupo, ArrayList<ExpressaoRegular>> grupos) {
        finalStatesOfEachGroup = new LinkedHashMap<Grupo, Set<EstadoFinal>>();
        AutomatoFinitoNaoDeterministico afnd = null;
        for (Grupo grupo : grupos.keySet()) {
            if (afnd == null) {
                AutomatoFinitoDeterministico afd = geraAutomatoDoGrupo(grupos.get(grupo));
                finalStatesOfEachGroup.put(grupo, afd.getEstadosAceitacao());
                afnd = converteToAfnd(afd);
            } else {
                AutomatoFinitoDeterministico afd = geraAutomatoDoGrupo(grupos.get(grupo));
                finalStatesOfEachGroup.put(grupo, afd.getEstadosAceitacao());
                afnd = OperacoesAFND.ouEntreAFNDs(afnd, converteToAfnd(afd));
            }

        }
        System.out.println(afnd.toString());
        AutomatoFinitoDeterministico bigAutomato = AFND2AFD.determinizar(afnd);
        return bigAutomato;
    }

    /**
     * Responsável por converter o tipo do Automato. converte de AFD para AFND.
     *
     * @param afd Um automato finito deterministico.
     * @return Um automato finito não deterministico.
     */
    private static AutomatoFinitoNaoDeterministico converteToAfnd(AutomatoFinitoDeterministico afd) {

        Map<Entrada, Estados> transicoes = new HashMap<>();

        for (Entrada ent : afd.getTransicoes().keySet()) {
            Estados ests = new Estados();
            ests.addEstado(afd.getTransicoes().get(ent));
            transicoes.put(ent, ests);
        }

        return new AutomatoFinitoNaoDeterministico(afd.getEstados(), afd.getAlfabeto(), afd.getEstadoInicial(), afd.getEstadosAceitacao(), transicoes);
    }

}
