/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoIO;
import br.inf.ufsc.formais.io.GramaticaIO;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.operacoes.Gramatica2AFD;
import java.io.IOException;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class GramaticaAfdTeste {

    public void runTest() {
        Gramatica gr = null;
        try {
            gr = new GramaticaIO().read("/Users/Matheus/Desktop/", "teste9gramaticaAfd.in");
            System.out.println(gr.toString());

            AutomatoFinitoDeterministico fsa = Gramatica2AFD.converterParaAFD(gr);

            System.out.println(fsa.toString());
            AutomatoFinitoIO ioafd = new AutomatoFinitoIO();
            ioafd.write("/Users/Matheus/Desktop/", "teste10gramaticaAfd.out", fsa);
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
