/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.AnaliseLexicaException;
import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AnalisadorLexicoIO;
import br.inf.ufsc.formais.io.ProgramaIO;
import br.inf.ufsc.formais.model.analisador.lexico.AnalisadorLexico;
import java.io.IOException;

/**
 *
 * @author Diego
 */
public class AnalisadorLexicoTeste {
    public static void main(String[] args) {
        AnalisadorLexico lex = null;
        AnalisadorLexicoIO lexIO = new AnalisadorLexicoIO();
        try {
            lex = lexIO.read("linguagem.lex");
        } catch (IOException | FormaisIOException ex) {
            System.out.println("Não foi possível encontrar o arquivo com a estrutura do analisador léxico!");
        }
        
        try {
            String program = ProgramaIO.read("teste.prg");
            String tokens = lex.analisar(program);
            lexIO.writeTokens("tokens.lexOut", tokens);
            System.out.println(tokens);
        } catch (IOException ex) {
            System.out.println("Impossível encontrar arquivo de código!");
        } catch (AnaliseLexicaException ex) {
            System.out.println("Ocorreu um erro léxico!");
        }
    }
}
