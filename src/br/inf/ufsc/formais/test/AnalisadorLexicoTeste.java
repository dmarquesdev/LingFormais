/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.ER2AFND;
import br.inf.ufsc.formais.operacoes.OperacoesAFND;
import java.io.IOException;


/**
 *
 * @author Matheus
 */
public class AnalisadorLexicoTeste {

    public void runTest() {
        ExpressaoRegularIO ioer = new ExpressaoRegularIO();
        try {
            for (ExpressaoRegular er : ioer.readAll("", "testeer.in")) {
                System.out.println(er.toString());
                AutomatoFinitoNaoDeterministico afnd;
                if (er.toString().equals("|")
                        || er.toString().equals("*") || er.toString().equals("(")
                        || er.toString().equals(")")) {
                           afnd = OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(0));
                }else if(er.toString().equals("||")){
                    afnd = OperacoesAFND.concatenaAFs(OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(0)), OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(1)));
                }else{
                afnd = ER2AFND.converterParaAutomato(er);
                }
                System.out.println(afnd.toString());
            }

        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
