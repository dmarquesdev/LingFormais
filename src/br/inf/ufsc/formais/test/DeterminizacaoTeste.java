package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.io.AutomatoFinitoNaoDeterministicoIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;

import br.inf.ufsc.formais.operacoes.AFND2AFD;
import java.io.IOException;
import br.inf.ufsc.formais.exception.FormaisIOException;

public class DeterminizacaoTeste {

    public void runTeste() {

        try {
            AutomatoFinitoNaoDeterministicoIO ioafnd = new AutomatoFinitoNaoDeterministicoIO();
            AutomatoFinitoNaoDeterministico afnd = ioafnd.read("/home/nathan/Documentos/Formais/LingFormais/dist/", "afnd.in");
            AutomatoFinitoDeterministico AFD = AFND2AFD.determinizar(afnd);
            ioafnd.write("/home/nathan/Documentos/Formais/LingFormais/dist/", "afnd.out", afnd);
            //System.out.println("Determinização sem epsilon transição.");
            //System.out.println(AFD);
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
