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
import br.inf.ufsc.formais.operacoes.AFD2Gramatica;
import java.io.IOException;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AfdGramaticaTeste {
    public void runTest() {
        AutomatoFinitoDeterministico afd = null;
        try {
            afd = (AutomatoFinitoDeterministico) new AutomatoFinitoIO()
                    .read("/Users/Matheus/Desktop/", "teste11AfdGramatica.in");
            System.out.println(afd.toString());

            Gramatica gram = AFD2Gramatica.converterParaGramatica(afd);

            System.out.println(gram.toString());
            GramaticaIO iogr = new GramaticaIO();
            iogr.write("/Users/Matheus/Desktop/", "teste12AfdGramatica.out", gram);
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
