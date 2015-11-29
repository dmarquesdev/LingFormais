/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.sintatico;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;
import java.util.ArrayList;

/**
 *
 * @author Matheus
 */
public class AnalisadorSintatico {

    TabelaAnalise tabela = GeradorTabelaAnalise.gerarTabela(null, null, null);
    GramaticaLivreContexto gramatica = new GramaticaLivreContexto(null, null, null, null);

    public AnalisadorSintatico(){
        
    }
    
    public boolean analisar(ArrayList<SimboloTerminal> lexemas) {
        lexemas.add(new SimboloTerminal("$"));
        ArrayList<Simbolo> pilha = new ArrayList<>();
        pilha.add(new Simbolo("$"));
        pilha.add(gramatica.getSimboloInicial());

        for (SimboloTerminal t : lexemas) {

            while (!t.equals(pilha.get(pilha.size() - 1))) {
                CadeiaGLC producao;
                producao = tabela.getCadeia(new EntradaTabelaAnalise((SimboloNaoTerminal) pilha.get(pilha.size() - 1), t));
                if (producao.getPrimeiroSimbolo().equals(Simbolo.EPSILON)) {
                    pilha.remove(pilha.size() - 1);
                } else {
                    pilha.remove(pilha.size() - 1);
                    for (int i = producao.getSimbolosCadeia().size() - 1; i >= 0; i--) {
                        pilha.add(producao.getSimbolosCadeia().get(i));
                    }
                }
            }

            pilha.remove(pilha.size() - 1);
        }

        return pilha.isEmpty();
    }
}
