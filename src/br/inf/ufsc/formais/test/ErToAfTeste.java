/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.ER2AFND;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Matheus
 */
public class ErToAfTeste {
    
    public void runTest(){
        List<Simbolo> simbolos = new ArrayList();
        simbolos.add(new Simbolo("("));
        simbolos.add(new Simbolo("a"));
        simbolos.add(new Simbolo("|"));
        simbolos.add(new Simbolo("b"));
        simbolos.add(new Simbolo(")"));
        simbolos.add(new Simbolo("*"));
        simbolos.add(new Simbolo("c"));
        ExpressaoRegular exp = new ExpressaoRegular(simbolos);
        
        AutomatoFinitoNaoDeterministico afnd;
        
        afnd = ER2AFND.converterParaAutomato(exp);
        System.out.println("ER: " + exp.toString()+ "\n\n" + afnd.toString());
    }
}
