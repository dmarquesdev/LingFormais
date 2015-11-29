/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.TokensIO;
import br.inf.ufsc.formais.model.Lexema_Token;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
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
        TokensIO asio = new TokensIO();
        ArrayList<Lexema_Token> entradas;
        try {
            entradas = asio.readLexemas("", "lexemas.LEXOUT");
            for (Lexema_Token s : entradas) {
                System.out.println(s + "\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(IOtest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FormaisIOException ex) {
            Logger.getLogger(IOtest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
