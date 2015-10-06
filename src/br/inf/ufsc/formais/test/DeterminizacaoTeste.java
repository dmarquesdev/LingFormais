package br.inf.ufsc.formais.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.automato.Estados;
import br.inf.ufsc.formais.operacoes.AFND2AFD;



public class DeterminizacaoTeste {

	public void runTeste() {

		HashSet<Estado> estados = new LinkedHashSet<Estado>();
		Estado q0 = new EstadoInicial("Q0");
		estados.add(q0);
		Estado q1 = new Estado("Q1");
		estados.add(q1);
		Estado q2 = new EstadoFinal("Q2");
		estados.add(q2);

		HashSet<Simbolo> simbolos = new LinkedHashSet<Simbolo>();
		Simbolo a = new Simbolo("a");
		simbolos.add(a);
		Simbolo b = new Simbolo("b");
		simbolos.add(b);
		Alfabeto alfabeto = new Alfabeto(simbolos);

		
		HashMap<Entrada, Estados> transicoes = new HashMap<Entrada, Estados>();
		
		Entrada q0a = new Entrada(q0, a);
		Estados estadosq0 = new Estados(new HashSet<Estado>());
		estadosq0.addEstado(q1);
		transicoes.put(q0a,estadosq0);
		
		Entrada q1a = new Entrada(q1, a);
		Estados estadosq1 = new Estados(new HashSet<Estado>());
		estadosq1.addEstado(q1);
		estadosq1.addEstado(q2);
		transicoes.put(q1a,estadosq1);
		
		Entrada q1b = new Entrada(q1, b);
		Estados estadosq1b = new Estados(new HashSet<Estado>());
		estadosq1b.addEstado(q1);
		transicoes.put(q1b,estadosq1b);

		EstadoInicial estadoInicial = (EstadoInicial) q0;
		EstadoFinal estadoFinal = (EstadoFinal) q2;
		HashSet<EstadoFinal> conjuntosEstadosAceitacao = new LinkedHashSet<EstadoFinal>();
		conjuntosEstadosAceitacao.add(estadoFinal);

		AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico();

		afnd.setEstados(estados);
		afnd.setEstadoInicial(estadoInicial);
		afnd.setAlfabeto(alfabeto);
		afnd.setEstadosAceitacao(conjuntosEstadosAceitacao);
		afnd.setTransicoes(transicoes);
		
                AutomatoFinitoDeterministico AFD = AFND2AFD.determinizar(afnd);
                System.out.println("Determinização sem epsilon transição.");
		System.out.println(AFD);              

	}
}
