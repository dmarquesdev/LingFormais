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
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.AFLexico;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AnalisadorLexicoTeste {

    public void runTest() {
        ExpressaoRegularIO ioer = new ExpressaoRegularIO();
        
            ArrayList<ArrayList<ExpressaoRegular>> grupos = new ArrayList<>();
            try {
                grupos.add(ioer.readAll("", "pr.in"));
                grupos.add(ioer.readAll("", "co.in"));
                grupos.add(ioer.readAll("", "lp.in"));
                grupos.add(ioer.readAll("", "ol.in"));
                grupos.add(ioer.readAll("", "op.in"));
                grupos.add(ioer.readAll("", "se.in"));
                grupos.add(ioer.readAll("", "vr.in"));
                grupos.add(ioer.readAll("", "constante.in"));
            } catch (IOException ex) {
                System.out.println("Ocorreu um erro de leitura no arquivo!");
            } catch (FormaisIOException ex) {
                System.out.println(ex.getMessage());
            }
            
            AutomatoFinitoDeterministico afd = AFLexico.geraAutomatoFinal(grupos);
            System.out.println(afd.toString());

        try {
            Estado estado = afd.computar(new CadeiaAutomato("\" teste de espaço na string !!$#@$@%#\""));
            System.out.println("\nEstado em que a palavra foi aceita: " + estado.toString() + "\n\n");
        } catch (EstadoInalcancavelException e) {
            System.out.println("\n\nEntrada não é aceita pela linguagem!\n\n");
        }

    }

}
