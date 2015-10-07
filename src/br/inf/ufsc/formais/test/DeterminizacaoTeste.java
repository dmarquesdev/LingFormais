package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.io.AutomatoFinitoNaoDeterministicoIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;

import br.inf.ufsc.formais.operacoes.AFND2AFD;
import java.io.IOException;
import br.inf.ufsc.formais.exception.FormaisIOException;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class DeterminizacaoTeste {

    public void runTeste() {

        try {
            AutomatoFinitoNaoDeterministicoIO ioafnd = new AutomatoFinitoNaoDeterministicoIO();
            AutomatoFinitoNaoDeterministico afnd = ioafnd.read("/Users/Matheus/Desktop/", "teste7detAfnd.in");
            AutomatoFinitoDeterministico AFD = AFND2AFD.determinizar(afnd);
            ioafnd.write("/Users/Matheus/Desktop/", "teste8detAfnd.out", afnd);
            //System.out.println("Determinização sem epsilon transição.");
            //System.out.println(AFD);
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
