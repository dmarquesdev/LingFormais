/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoNaoDeterministicoIO;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.er.ExpressaoRegular; 
import java.io.IOException;
import br.inf.ufsc.formais.operacoes.ER2AFND;


/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class ErToAfTeste {
    
    public void runTest(){
        
        try {
            ExpressaoRegularIO ioer = new ExpressaoRegularIO();
            ExpressaoRegular er = ioer.read("", "teste1ErAfnd.in");
            AutomatoFinitoNaoDeterministico afnd = ER2AFND.converterParaAutomato(er);
            AutomatoFinitoNaoDeterministicoIO ioafnd = new AutomatoFinitoNaoDeterministicoIO();
            ioafnd.write("", "teste2ErAfnd.out", afnd);
            //System.out.println("Determinização sem epsilon transição.");
            //System.out.println(AFD);
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
}
