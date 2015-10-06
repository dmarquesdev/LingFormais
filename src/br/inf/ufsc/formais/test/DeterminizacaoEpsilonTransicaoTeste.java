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
import br.inf.ufsc.formais.operacoes.AFNDEpsilonTransition2AFD2;

public class DeterminizacaoEpsilonTransicaoTeste {

    public void runTeste() {

        HashSet<Estado> estados = new LinkedHashSet<Estado>();
        Estado p = new EstadoInicial("p");
        estados.add(p);
        Estado q = new Estado("q");
        estados.add(q);
        Estado r = new EstadoFinal("r");
        estados.add(r);

        HashSet<Simbolo> simbolos = new LinkedHashSet<Simbolo>();
        Simbolo epsilon = Simbolo.EPSILON;
        simbolos.add(epsilon);
        Simbolo a = new Simbolo("a");
        simbolos.add(a);
        Simbolo b = new Simbolo("b");
        simbolos.add(b);
        Simbolo c = new Simbolo("c");
        simbolos.add(c);
        Alfabeto alfabeto = new Alfabeto(simbolos);

        HashMap<Entrada, Estados> transicoes = new HashMap<Entrada, Estados>();

        // transicoes do estado Inicial p
        Entrada pa = new Entrada(p, a);
        Estados estadospa = new Estados(new HashSet<Estado>());
        estadospa.addEstado(p);
        transicoes.put(pa, estadospa);

        Entrada pb = new Entrada(p, b);
        Estados estadospb = new Estados(new HashSet<Estado>());
        estadospb.addEstado(q); //vai para
        transicoes.put(pb, estadospb);

        Entrada pc = new Entrada(p, c);
        Estados estadospc = new Estados(new HashSet<Estado>());
        estadospc.addEstado(r); //vai para
        transicoes.put(pc, estadospc);

        //transições do estado q
        Entrada qepsilon = new Entrada(q, epsilon);
        Estados estadosqepsilon = new Estados(new HashSet<Estado>());
        estadosqepsilon.addEstado(p);// vai para
        transicoes.put(qepsilon, estadosqepsilon);

        Entrada qa = new Entrada(q, a);
        Estados estadosqa = new Estados(new HashSet<Estado>());
        estadosqa.addEstado(q); //vai para
        transicoes.put(qa, estadosqa);

        Entrada qb = new Entrada(q, b);
        Estados estadosqb = new Estados(new HashSet<Estado>());
        estadosqb.addEstado(r); //vai para
        transicoes.put(qb, estadosqb);

        //transições do estado final r
        Entrada repsilon = new Entrada(r, epsilon);
        Estados estadosrepsilon = new Estados(new HashSet<Estado>());
        estadosrepsilon.addEstado(q);// vai para
        transicoes.put(repsilon, estadosrepsilon);

        Entrada ra = new Entrada(r, a);
        Estados estadosra = new Estados(new HashSet<Estado>());
        estadosra.addEstado(r); //vai para
        transicoes.put(ra, estadosra);

        Entrada rc = new Entrada(r, c);
        Estados estadosrc = new Estados(new HashSet<Estado>());
        estadosrc.addEstado(p); //vai para
        transicoes.put(rc, estadosrc);

        EstadoInicial estadoInicial = (EstadoInicial) p;
        EstadoFinal estadoFinal = (EstadoFinal) r;
        HashSet<EstadoFinal> conjuntosEstadosAceitacao = new LinkedHashSet<EstadoFinal>();
        conjuntosEstadosAceitacao.add(estadoFinal);

        AutomatoFinitoNaoDeterministico afnd = new AutomatoFinitoNaoDeterministico();

        afnd.setEstados(estados);
        afnd.setEstadoInicial(estadoInicial);
        afnd.setAlfabeto(alfabeto);
        afnd.setEstadosAceitacao(conjuntosEstadosAceitacao);
        afnd.setTransicoes(transicoes);

        System.out.println("Determinização com epsilon transição.");
        System.out.println(AFNDEpsilonTransition2AFD2.determinizar(afnd));

    }
}
