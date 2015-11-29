package br.inf.ufsc.formais.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.FirstAndFollowIO;
import br.inf.ufsc.formais.io.GramaticaLivreContextoIO;
import br.inf.ufsc.formais.io.TokensIO;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.analisador.sintatico.AnalisadorSintatico;
import br.inf.ufsc.formais.model.analisador.sintatico.GeradorTabelaAnalise;
import br.inf.ufsc.formais.model.analisador.sintatico.TabelaAnalise;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;

public class AnalisadorSintaticoTeste {

	public static void main(String[] args) {

		FirstAndFollowIO firstAndFollowIO = new FirstAndFollowIO();
		GramaticaLivreContextoIO glcIO = new GramaticaLivreContextoIO();
		TokensIO tokensIO = new TokensIO();

		try {
			Map<Simbolo, Set<Simbolo>> first = firstAndFollowIO.read("first.in");
			Map<Simbolo, Set<Simbolo>> follow = firstAndFollowIO.read("follow.in");
			GramaticaLivreContexto glc = glcIO.read("gramatica.in");

			TabelaAnalise tabelaAnalise = GeradorTabelaAnalise.gerarTabela(glc, first, follow);

			AnalisadorSintatico analisadorSintatico = new AnalisadorSintatico(tabelaAnalise, glc);

			ArrayList<SimboloTerminal> lexemas = tokensIO.readLexemas("tokens.in");

			if (!analisadorSintatico.analisar(lexemas)) {
				System.out.println("Programa sem erros sintáticos");
			} else {
				System.out.println("Erro sintático, verifique seu programa.");
			}

		} catch (IOException | FormaisIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
