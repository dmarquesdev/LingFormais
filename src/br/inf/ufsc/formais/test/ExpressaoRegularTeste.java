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

/**
 *
 * @author Diego
 */
public class ExpressaoRegularTeste {

    public void runTest() {
        ExpressaoRegular er = new ExpressaoRegular();
        AutomatoFinitoNaoDeterministico afnd;
        Simbolo a = new Simbolo("a");
        Simbolo b = new Simbolo("b");
        
        er.concatenar(a);
        er.concatenarSimboloFecho(a);
        er.concatenar(b);
        
        afnd = ER2AFND.converterParaAutomato(er);
        
        
        
        System.out.println(er.toString());
        
        System.out.println(afnd.toString());
    }

}
