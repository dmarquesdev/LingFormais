
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.io.AutomatoFinitoNaoDeterministicoIO;
import br.inf.ufsc.formais.io.AutomatoFinitoIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;

import br.inf.ufsc.formais.operacoes.AFNDEpsilonTransition2AFD;
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
            AutomatoFinitoNaoDeterministico afnd = ioafnd.read("/home/nathan/Documentos/Formais/DiegoMarquesMatheusDemetrioNathanMolinari/Testes/", "teste7detAfnd.in");
            
            AutomatoFinitoDeterministico AFD = AFNDEpsilonTransition2AFD.determinizar(afnd);
            
            AutomatoFinitoIO afdio = new AutomatoFinitoIO();
            //afdio.write("teste8detAfnd.out", AFD);

            System.out.println("Determinização sem epsilon transição.");
            System.out.println(AFD);
            
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }

    }
}

