
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.operacoes.AFDMinimizer;
import java.io.IOException;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class MinimizacaoAfdTeste {

	public void runTeste() {

		try {
			AutomatoFinitoIO ioafd = new AutomatoFinitoIO();
			AutomatoFinitoDeterministico afd = (AutomatoFinitoDeterministico) ioafd.read("/home/nathan/Eclipse/eclipse/workspace/formais/arquivos/", "toMinimize.in");
			
			System.out.println(AFDMinimizer.minimizar(afd));
			

//			AutomatoFinitoIO afdio = new AutomatoFinitoIO();
//			afdio.write("teste6detAfnd_epsilon.out", AFD);
//
//			System.out.println("Determinização COM epsilon transição.");
//			System.out.println(AFD);

		} catch (IOException ex) {
			System.out.println("Ocorreu um erro de leitura no arquivo!");
		} catch (FormaisIOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
