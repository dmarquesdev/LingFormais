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
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Matheus
 */
public class AFLexico {
	
	private static Map<Grupo, Set<EstadoFinal>> finalStatesOfEachGroup = new LinkedHashMap<Grupo, Set<EstadoFinal>>(); 

	public static Grupo findGroup(Estado estadoAceitacao){
		//obtem os antigos estados finais
		Estados antigosFinais = AFND2AFD.getNew2OldFinalStatesMap().get(estadoAceitacao);
		//itera sobre todos os grupos
		for(Grupo grupo : finalStatesOfEachGroup.keySet()){	
			//faz a interseccao dos antigos estados finais com os finais do grupo
			Set<Estado> interseccao = new LinkedHashSet<Estado>(antigosFinais.get());
			interseccao.retainAll(finalStatesOfEachGroup.get(grupo));
			// se a interseccao não é vazia o estadodeAceitacao pertence ao grupo
			if(!interseccao.isEmpty()){
				return grupo;
			}
		}
                //temporario ...
		return Grupo.LOOP;
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
