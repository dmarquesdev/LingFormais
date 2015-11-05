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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cria e executa os testes.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class TestRunner {

    public static void main(String[] args) {

//        //Gramatica <-> AFD
//        GramaticaAfdTeste gramTOafd = new GramaticaAfdTeste();
//        AfdGramaticaTeste afdTOgram = new AfdGramaticaTeste();
//        gramTOafd.runTest();
//        afdTOgram.runTest();
//
//        //Determinizações
//        DeterminizacaoTeste detT = new DeterminizacaoTeste();
//        detT.runTeste();
//
//        //ER <-> AF
//        AfdToErTeste afdTOer = new AfdToErTeste();
//        ErToAfTeste etaT = new ErToAfTeste();
//        afdTOer.runTest();
//        etaT.runTest();
//        
//        //Minimizacao
//        MinimizacaoAfdTeste afdMinTeste = new MinimizacaoAfdTeste();
//        afdMinTeste.runTeste();
        
        //Analisador Lexico
        AnalisadorLexicoTeste lexico = new AnalisadorLexicoTeste();
        lexico.runTest();
        
        
    }
}
