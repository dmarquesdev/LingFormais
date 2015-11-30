/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.sintatico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

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

        for (Lexema_Token lt : lex_tok) {
            if ("IDENTIFICADORES".equals(lt.getToken()) || "LITERAIS".equals(lt.getToken())) {
                sentenca.add(new SimboloTerminal(lt.getToken()));
            } else {
                sentenca.add(new SimboloTerminal(lt.getLexema()));
            }
        }
        sentenca.add(new SimboloTerminal("$"));
        ArrayList<Simbolo> pilha = new ArrayList<>();
        pilha.add(new SimboloTerminal("$"));
        pilha.add(this.gramatica.getSimboloInicial());

        for (SimboloTerminal t : sentenca) {

            while (!t.equals(pilha.get(pilha.size() - 1))) {
                CadeiaGLC producao;
                String referencia = pilha.get(pilha.size() - 1).getReferencia();
                producao = this.tabela.getCadeia(new EntradaTabelaAnalise(new SimboloNaoTerminal(pilha.get(pilha.size() - 1).getReferencia()), t));

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


	public boolean analisar2(ArrayList<SimboloTerminal> lexemas) throws AnaliseSintaticaException {
		lexemas.add(SimboloTerminal.FINAL_SENTENCA);
		Stack<Simbolo> pilha = new Stack<>();
		pilha.push(SimboloTerminal.FINAL_SENTENCA);
		pilha.push(this.gramatica.getSimboloInicial());

		int index = 0;
		SimboloTerminal simboloAtual = lexemas.get(index);

		while (!pilha.isEmpty()) {
			// se o topo de pilha = final de setença termina a analise aceitando os tokens de entrada
			if (pilha.peek().equals(SimboloTerminal.FINAL_SENTENCA)) {
				return true;
			}
			// se o topo de pilha igual ao token atual desempilha e avança para o proximo token
			if (pilha.peek().equals(simboloAtual)) {
				pilha.pop();
				index++;
				simboloAtual = lexemas.get(index);
			} else {
				// obtem a producao na tabela de analise para o token atual e o topo da pilha
				SimboloNaoTerminal snt = (SimboloNaoTerminal) pilha.peek();
				EntradaTabelaAnalise entrada = new EntradaTabelaAnalise(snt, simboloAtual);
				CadeiaGLC producaoTabela = this.tabela.getCadeia(entrada);
				// se o mapeamento na tabela foi pra epsilon desempilha
				if (producaoTabela.getPrimeiroSimbolo().equals(Simbolo.EPSILON)) {
					pilha.pop();
				} else { // se nao empilha as produções obtidas da tabela de analise
					pilha.pop();
					LinkedList<Simbolo> producaoTabelaInversa = new LinkedList<>(producaoTabela.getSimbolosCadeia());
					Collections.reverse(producaoTabelaInversa);
					for (Simbolo simbolo : producaoTabelaInversa) {
						pilha.push(simbolo);
					}
				}
			}
		}
		return false;
	}


}
