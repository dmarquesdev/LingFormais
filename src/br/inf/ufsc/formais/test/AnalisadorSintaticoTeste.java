package br.inf.ufsc.formais.test;


import br.inf.ufsc.formais.exception.AnaliseSintaticaException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.FirstAndFollowIO;
import br.inf.ufsc.formais.io.GramaticaLivreContextoIO;
import br.inf.ufsc.formais.io.TokensIO;
import br.inf.ufsc.formais.model.Lexema_Token;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.analisador.sintatico.AnalisadorSintatico;
import br.inf.ufsc.formais.model.analisador.sintatico.GeradorTabelaAnalise;
import br.inf.ufsc.formais.model.analisador.sintatico.TabelaAnalise;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;

public class AnalisadorSintaticoTeste {


    public void runTest() {


        String path = "./teste/";

        FirstAndFollowIO firstAndFollowIO = new FirstAndFollowIO();
        GramaticaLivreContextoIO glcIO = new GramaticaLivreContextoIO();
        TokensIO tokenIO = new TokensIO();

        try {
            Map<Simbolo, Set<Simbolo>> first = firstAndFollowIO.read(path, "first.in");
            Map<Simbolo, Set<Simbolo>> follow = firstAndFollowIO.read(path, "follow.in");
            GramaticaLivreContexto glc = glcIO.read(path, "gramatica.in");

            TabelaAnalise tabelaAnalise = GeradorTabelaAnalise.gerarTabela(glc, first, follow);

            AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(tabelaAnalise, glc);


            ArrayList<Lexema_Token> lexemas = tokenIO.readLexemas(path, "tokens.lexOut");


            analisadorSintatico.analisar(lexemas);
            System.out.println("Programa sem erros sintáticos");

        } catch (IOException | FormaisIOException ex) {
            System.out.println("Impossível encontrar arquivo de código!");
        } catch (AnaliseSintaticaException ex) {
            System.out.println("Ocorreu um erro sintatico!");
            ex.printStackTrace();
        }

    }


}
