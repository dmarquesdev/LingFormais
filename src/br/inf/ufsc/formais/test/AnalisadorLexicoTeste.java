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
import br.inf.ufsc.formais.model.Grupo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AnalisadorLexicoTeste {

    public void runTest() {
        ExpressaoRegularIO ioer = new ExpressaoRegularIO();
        
            Map<Grupo,ArrayList<ExpressaoRegular>> grupos = new LinkedHashMap<Grupo,ArrayList<ExpressaoRegular>>();
            try {

                grupos.put(Grupo.PALAVRASRESERVADAS, ioer.readAll("", "pr.in"));
                grupos.put(Grupo.CONDICIONAL, ioer.readAll("", "co.in"));
                grupos.put(Grupo.LOOP, ioer.readAll("", "lp.in"));
                grupos.put(Grupo.OPERADORESLOGICOS, ioer.readAll("", "ol.in"));
                grupos.put(Grupo.OPERADORES, ioer.readAll("", "op.in"));
                grupos.put(Grupo.SEPARADORES, ioer.readAll("", "se.in"));
                grupos.put(Grupo.VARIAVEIS, ioer.readAll("", "vr.in"));
                grupos.put(Grupo.CONSTANTES, ioer.readAll("", "constante.in"));

            } catch (IOException ex) {
                System.out.println("Ocorreu um erro de leitura no arquivo!");
            } catch (FormaisIOException ex) {
                System.out.println(ex.getMessage());
            }
            
            AutomatoFinitoDeterministico afd = AFLexico.geraAutomatoFinal(grupos);
            System.out.println(afd.toString());

        try {

            Estado estado = afd.computar(new CadeiaAutomato("program"));
            System.out.println("\nEstado em que a palavra foi aceita: " + estado.toString());
            System.out.println("Grupo o qual a palavra pertence: " + AFLexico.getGrupo(estado) + "\n\n");

        } catch (EstadoInalcancavelException e) {
            System.out.println("\n\nEntrada não é aceita pela linguagem!\n\n");
        }

    }

}
