
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoNaoDeterministicoIO;
import br.inf.ufsc.formais.io.AutomatoFinitoIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.operacoes.AFND2AFD;
import br.inf.ufsc.formais.operacoes.AFDMinimizer;
import java.io.IOException;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class DeterminizacaoTeste {

	public void runTeste() {

		try {
			// sem epsilon transição
			AutomatoFinitoNaoDeterministicoIO ioafnd = new AutomatoFinitoNaoDeterministicoIO();
			AutomatoFinitoNaoDeterministico afnd = ioafnd.read("", "teste8detAfnd.in");
			AutomatoFinitoDeterministico AFD = AFND2AFD.determinizar(afnd);
			AutomatoFinitoIO afdio = new AutomatoFinitoIO();
			afdio.write("teste8detAfnd.out", AFD);

			//com epsilon transição
			AutomatoFinitoNaoDeterministico afndEpsilon = ioafnd.read("", "teste6detAfnd_epsilon.in");
			AFD = AFND2AFD.determinizar(afndEpsilon);
			afdio.write("teste6detAfnd_epsilon.out", AFD);

		} catch (IOException ex) {
			System.out.println("Ocorreu um erro de leitura no arquivo!");
		} catch (FormaisIOException ex) {
			System.out.println(ex.getMessage());
		}

	}
}
