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
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matheus
 */
public class AFLexico {
	
	private static LinkedHashMap<Estado, Grupo> states2GroupMap = new LinkedHashMap<Estado, Grupo>();
	
	private static void generateStates2GroupMap(Map<Estado, Estado> old2NewFinalStates, Map<Set<EstadoFinal>, Grupo> finalStatesOfEachGroup){
		states2GroupMap = new LinkedHashMap<Estado, Grupo>();
		for(Set<EstadoFinal> estadosFinaisGrupo : finalStatesOfEachGroup.keySet()){
			for(EstadoFinal ef : estadosFinaisGrupo){
				Estado novoEstadoFinal = old2NewFinalStates.get(ef);
				states2GroupMap.put(novoEstadoFinal, finalStatesOfEachGroup.get(estadosFinaisGrupo));
			}
		}
	}

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

    public static AutomatoFinitoDeterministico geraAutomatoFinal(Map<Grupo,ArrayList<ExpressaoRegular>> grupos) {
    	Map<Set<EstadoFinal>, Grupo> finalStatesOfEachGroup = new LinkedHashMap<Set<EstadoFinal>, Grupo>(); 
        AutomatoFinitoNaoDeterministico afnd = null;
        for (Grupo grupo : grupos.keySet()) {
            if (afnd == null) {
            	AutomatoFinitoDeterministico afd = geraAutomatoDoGrupo(grupos.get(grupo));
            	finalStatesOfEachGroup.put(afd.getEstadosAceitacao(), grupo);
                afnd = converteToAfnd(afd);
            } else {
              	AutomatoFinitoDeterministico afd = geraAutomatoDoGrupo(grupos.get(grupo));
            	finalStatesOfEachGroup.put(afd.getEstadosAceitacao(), grupo);
                afnd = OperacoesAFND.ouEntreAFNDs(afnd, converteToAfnd(afd));
            }
            
        }
        System.out.println(afnd);
        AutomatoFinitoDeterministico bigAutomato = AFND2AFD.determinizar(afnd);
        generateStates2GroupMap(AFND2AFD.getOld2NewFinalStatesMap(), finalStatesOfEachGroup);
        return bigAutomato;
    }


    private static AutomatoFinitoNaoDeterministico converteToAfnd(AutomatoFinitoDeterministico afd) {
        
        Map<Entrada, Estados> transicoes = new HashMap<>();

        for (Entrada ent : afd.getTransicoes().keySet()) {
            Estados ests = new Estados();
            ests.addEstado(afd.getTransicoes().get(ent));
            transicoes.put(ent, ests);
        }

        return new AutomatoFinitoNaoDeterministico(afd.getEstados(), afd.getAlfabeto(), afd.getEstadoInicial(), afd.getEstadosAceitacao(), transicoes);
    }
    
    public static Grupo getGrupo(Estado estado){
        return states2GroupMap.get(estado);
    }

}
