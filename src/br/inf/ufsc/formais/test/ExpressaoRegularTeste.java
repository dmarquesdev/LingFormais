/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.test;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.io.IOException;

/**
 *
 * @author Diego
 */
public class ExpressaoRegularTeste {

    public void runTest() {
        ExpressaoRegular er = null;
        try {
            er = new ExpressaoRegularIO().read("C:\\", "er.in");
            System.out.println(er.toString());
        } catch (IOException ex) {
            System.out.println("Ocorreu um erro de leitura no arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
