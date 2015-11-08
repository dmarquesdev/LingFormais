/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Matheus
 */
public class AFLexico {

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
        System.out.println("AFND\n\n"+afnd.toString());
        AutomatoFinitoDeterministico afd = AFND2AFD.determinizar(afnd);
        System.out.println("Determinizado\n\n"+afd.toString());
        afd = AFDMinimizer.minimizar(afd);
        System.out.println("Minimizado\n\n"+afd.toString());
        return afd;
    }

    public static AutomatoFinitoDeterministico geraAutomatoFinal(ArrayList<ArrayList<ExpressaoRegular>> grupo) {
        AutomatoFinitoNaoDeterministico afnd = null;
        for (ArrayList<ExpressaoRegular> ers : grupo) {
            if (afnd == null) {
                afnd = converteToAfnd(geraAutomatoDoGrupo(ers));
            } else {
                afnd = OperacoesAFND.ouEntreAFNDs(afnd, converteToAfnd(geraAutomatoDoGrupo(ers)));
            }
            
        }
        System.out.println(afnd.toString());
        return AFND2AFD.determinizar(afnd);
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
