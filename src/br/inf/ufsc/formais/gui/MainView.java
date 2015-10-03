/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.gui;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.AutomatoFinitoDeterministicoIO;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.io.GramaticaIO;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministicoGeneralizado;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.operacoes.AFD2AFNDG;
import br.inf.ufsc.formais.operacoes.AFD2Gramatica;
import br.inf.ufsc.formais.operacoes.AFNDG2ER;
import br.inf.ufsc.formais.operacoes.ER2AFND;
import br.inf.ufsc.formais.operacoes.Gramatica2AFD;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Diego
 */
public class MainView {

    static ExpressaoRegular er;
    static AutomatoFinitoDeterministico afd;
    static AutomatoFinitoNaoDeterministico afnd;
    static Gramatica gramatica;
    static AutomatoFinitoNaoDeterministicoGeneralizado afndg;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.out.println("Número de parâmetros incorretos, use: formais.jar <ER|Gramatica|AFD|AFND> <arquivo>");
                System.exit(0);
            }
            if (!args[0].equalsIgnoreCase("ER")
                    && !args[0].equalsIgnoreCase("Gramatica")
                    && !args[0].equalsIgnoreCase("AFD")
                    && !args[0].equalsIgnoreCase("AFND")) {
                System.out.println("Tipo selecionado não suportado! use: formais.jar <ER|Gramatica|AFD|AFND> <arquivo>");
                System.exit(0);
            }
            String tipo = args[0], arquivo = args[1];
            System.out.println("Você selecionou: " + tipo);
            System.out.println("Selecione uma operação: ");
            String opt = "";
            Scanner scanInput = new Scanner(System.in);
            switch (tipo) {
                case "ER":
                    er = new ExpressaoRegularIO().read(arquivo);
                    System.out.println("(1)Expressão Regular -> AFND");
                    opt = scanInput.nextLine();
                    scanInput.close();
                    if (opt.equals("1")) {
                        afnd = ER2AFND.converterParaAutomato(er);
//                        new AutomatoFinitoNaoDeterministicoIO().write("er_afnd.out", afnd);
                    } else {
                        System.out.println("Opção inválida!");
                        System.exit(0);
                    }
                    break;
                case "Gramatica":
                    gramatica = new GramaticaIO().read(arquivo);
                    System.out.println("(1)Gramática -> AFD");
                    System.out.println("(2)Gramática -> AFND");
                    opt = scanInput.nextLine();
                    scanInput.close();
                    if (opt.equals("1")) {
                        afd = Gramatica2AFD.converterParaAFD(gramatica);
                        new AutomatoFinitoDeterministicoIO().write("gramatica_afd.out", afd);
                    } else if (opt.equals("2")) {
//                        afnd = Gramatica2AFND.converterParaAFND(gramatica);
//                        new AutomatoFinitoNaoDeterministicoIO().write("er_afnd.out", afnd);
                    } else {
                        System.out.println("Opção inválida!");
                        System.exit(0);
                    }
                    break;
                case "AFD":
                    afd = new AutomatoFinitoDeterministicoIO().read(arquivo);
                    System.out.println("(1)AFD -> Gramática");
                    System.out.println("(2)AFD -> Expressão Regular");
                    opt = scanInput.nextLine();
                    scanInput.close();
                    if (opt.equals("1")) {
                        gramatica = AFD2Gramatica.converterParaGramatica(afd);
                        new GramaticaIO().write("afd_gramatica.out", gramatica);
                    } else if(opt.equals("2")) {
                        afndg = AFD2AFNDG.converterParaAFDNG(afd);
                        er = AFNDG2ER.converterParaER(afndg);
                        new ExpressaoRegularIO().write("afd_er.out", er);
                    } else {
                        System.out.println("Opção inválida!");
                        System.exit(0);
                    }
                    break;
                default:
                    break;
            }
        } catch (IOException ex) {
            System.out.println("Erro na leitura/escrita do arquivo!");
        } catch (FormaisIOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
