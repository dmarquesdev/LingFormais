/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.ArrayList;

import br.inf.ufsc.formais.exception.AnaliseSintaticaException;
import br.inf.ufsc.formais.model.Lexema_Token;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;

/**
 *
 * @author Matheus Demetrio
 */
public class AnalisadorSintatico {


    TabelaAnalise tabela;
    GramaticaLivreContexto gramatica;

    public AnalisadorSintatico(TabelaAnalise tabela, GramaticaLivreContexto gramatica) {
        this.tabela = tabela;
        this.gramatica = gramatica;
    }

    public boolean analisar(ArrayList<Lexema_Token> lex_tok) throws AnaliseSintaticaException {
        ArrayList<SimboloTerminal> sentenca = new ArrayList<>();
        for(Lexema_Token lt : lex_tok){
            sentenca.add(new SimboloTerminal(lt.getLexema()));
        }
        sentenca.add(new SimboloTerminal("$"));
        ArrayList<Simbolo> pilha = new ArrayList<>();
        pilha.add(new Simbolo("$"));
        pilha.add(this.gramatica.getSimboloInicial());

        for (SimboloTerminal t : sentenca) {

            while (!t.equals(pilha.get(pilha.size() - 1))) {
                CadeiaGLC producao;
                producao = this.tabela.getCadeia(new EntradaTabelaAnalise((SimboloNaoTerminal) pilha.get(pilha.size() - 1), t));

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
