/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.InputException;
import br.inf.ufsc.formais.io.GramaticaIO;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.operacoes.Gramatica2AFD;
import java.io.IOException;

/**
 *
 * @author Diego
 */
public class GramaticaTeste {

    public void runTest() {
        Gramatica gr = null;
        try {
            gr = new GramaticaIO().read("C:\\", "gramatica.in");
            System.out.println(gr.toString());

            AutomatoFinitoDeterministico fsa = Gramatica2AFD.converterParaAFD(gr);

            System.out.println(fsa.toString());
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (InputException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
