/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.EstadoInalcancavelException;
import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.model.CadeiaAutomato;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.AFDMinimizer;
import br.inf.ufsc.formais.operacoes.AFND2AFD;
import br.inf.ufsc.formais.operacoes.ER2AFND;
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
                
                AutomatoFinitoNaoDeterministico afnd = ER2AFND.analisaConverte(er);
                System.out.println(afnd.toString());

                AutomatoFinitoDeterministico afd = AFND2AFD.determinizar(afnd);
                AFDMinimizer minimizer = new AFDMinimizer(afd);
                afd = minimizer.minimizar();
                System.out.println(afd.toString());

                try {
                    Estado estado = afd.computar(new CadeiaAutomato("program"));
                    System.out.println("\nEstado em que a palavra foi aceita: "+estado.toString() +"\n\n");
                } catch (EstadoInalcancavelException e) {
                    System.out.println("\n\nEntrada não é aceita pela linguagem!\n\n");
                }

            }

        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
