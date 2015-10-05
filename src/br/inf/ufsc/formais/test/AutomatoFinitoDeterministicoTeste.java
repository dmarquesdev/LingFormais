/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoIO;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministicoGeneralizado;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.operacoes.AFD2AFNDG;
import br.inf.ufsc.formais.operacoes.AFNDG2ER;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class AutomatoFinitoDeterministicoTeste {

    public void runTest() {
        AutomatoFinitoDeterministico dfa = null;
        try {
            dfa = (AutomatoFinitoDeterministico) new AutomatoFinitoIO().read("C:\\", "afd.in");
            System.out.println(dfa.toString());
            AutomatoFinitoNaoDeterministicoGeneralizado afndg = AFD2AFNDG.converterParaAFDNG(dfa);

            System.out.println(afndg.toString());

            ExpressaoRegular er = AFNDG2ER.converterParaER(afndg);

            System.out.println(er.toString());
        } catch (IOException ex) {
             System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
