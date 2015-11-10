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
 * 
 * Classe de testes do Analisador Lexico.
 */
public class AnalisadorLexicoTeste {

    public void runTest() {
        ExpressaoRegularIO ioer = new ExpressaoRegularIO();
Map<Grupo, ArrayList<ExpressaoRegular>> grupos = new LinkedHashMap<Grupo, ArrayList<ExpressaoRegular>>();
        try {

                grupos.put(Grupo.PALAVRASRESERVADAS, ioer.readAll("", "er_palavras_reservadas.in"));
                grupos.put(Grupo.OPERADORESLOGICOS, ioer.readAll("", "er_operadores_logicos.in"));
                grupos.put(Grupo.OPERADORES, ioer.readAll("", "er_operadores_algebricos.in"));
                grupos.put(Grupo.OPERADORES, ioer.readAll("", "er_operadore_atribuicao.in"));
                grupos.put(Grupo.SEPARADORES, ioer.readAll("", "er_separadores.in"));
                //grupos.put(Grupo.CONSTANTES, ioer.readAll("", "er_literal.in"));
                grupos.put(Grupo.IDENTIFICADORES, ioer.readAll("", "er_identificadores.in"));

        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }

        AutomatoFinitoDeterministico afd = AFLexico.geraAutomatoFinal(grupos);
             System.out.println(afd.toString());

        try {


            Estado estado = afd.computar(new CadeiaAutomato("-1324.129876"));

            System.out.println("\nEstado em que a palavra foi aceita: " + estado.toString());
            System.out.println("Grupo o qual a palavra pertence: " + AFLexico.findGroup(estado) + "\n\n");

        } catch (EstadoInalcancavelException e) {
            System.out.println("\n\nEntrada não é aceita pela linguagem!\n\n");
        }

    }

}
