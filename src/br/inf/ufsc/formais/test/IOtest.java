/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AnalisadorSintaticoIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Matheus
 */
public class IOtest {

    public void runTest() {
        AnalisadorSintaticoIO asio = new AnalisadorSintaticoIO();
        ArrayList<String> entradas;
        try {
            entradas = asio.readLexemas("", "lexemas.LEXOUT");
            for (String s : entradas) {
                System.out.println(s + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(IOtest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FormaisIOException ex) {
            Logger.getLogger(IOtest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
