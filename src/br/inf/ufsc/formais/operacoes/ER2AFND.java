/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.io.ExpressaoRegularIO;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministico;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.model.er.SimboloOperacional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Essa classe tem os procediemntos necessarios para converter uma expressão
 * regular em um automato finito não deterministico.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class ER2AFND {

    public static AutomatoFinitoNaoDeterministico analisaConverte(ExpressaoRegular er) {
        AutomatoFinitoNaoDeterministico afnd = null;

        if (er.toString().equals("|") || er.toString().equals("*")
                || er.toString().equals("(") || er.toString().equals(")")) {
            afnd = OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(0));
        } else if (er.toString().equals("||")) {
            afnd = OperacoesAFND.concatenaAFs(OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(0)), OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(1)));
        } else if (er.toString().equals("\".\"")){
            ExpressaoRegularIO erio = new ExpressaoRegularIO();
            try {
                ArrayList<ExpressaoRegular> alfa = erio.readAll("", "lista_de_caracteres.in");
                for(Simbolo s : alfa.get(0).getSimbolos()){
                    if(afnd == null){
                        afnd = OperacoesAFND.aFdeSimbolo(s);
                    }else{
                        afnd = OperacoesAFND.ouEntreAFNDs(afnd, OperacoesAFND.aFdeSimbolo(s));
                    }
                }
                afnd = OperacoesAFND.fechoDeAF(afnd);
                afnd = OperacoesAFND.concatenaAFs(OperacoesAFND.aFdeSimbolo(new Simbolo("\"")), afnd);
                afnd = OperacoesAFND.concatenaAFs(afnd, OperacoesAFND.aFdeSimbolo(new Simbolo("\"")));
            } catch (IOException ex) {
                System.out.println("Ocorreu um erro de leitura no arquivo \"alfabeto.in!\"");
            } catch (FormaisIOException ex) {
                System.out.println(ex.getMessage());
            }
            
            
        } else {
            afnd = ER2AFND.converterParaAutomato(er);
        }
        return afnd;
    }

    /**
     * Responsável por reconhecer cada símbolo da expressão regular criar o
     * automato.
     *
     * @param er Uma expressão regular.
     * @return Um automato finito não determinsitico.
     */
    private static AutomatoFinitoNaoDeterministico converterParaAutomato(ExpressaoRegular er) {

        AutomatoFinitoNaoDeterministico ultimo = null;

//        laço responsavel por analizar cada simbolo da expressão regular
        for (int i = 0; i < er.getSimbolos().size(); i++) {

            if (er.getSimbolos().get(i).getReferencia().equals("(")) {
//                quando encontra um abre grupo entra num laço gerando uma sub er ate o fecha grupo correpondente.
//                calcula essa sub er e retorna.
                List<Simbolo> subSimbolos = new ArrayList<>();
                int abregrupo = 1;
                int j = i + 1;
                while (abregrupo > 0) {
                    if (er.getSimbolos().get(j).getReferencia().equals("(")) {
                        abregrupo++;
                    } else if (er.getSimbolos().get(j).getReferencia().equals(")")) {
                        abregrupo--;
                    }
                    if (abregrupo > 0) {
                        subSimbolos.add(er.getSimbolos().get(j));
                    }
                    j++;
                }
                ExpressaoRegular subEr = new ExpressaoRegular(subSimbolos);

//                pula para simbolo imediatamente após o fecha grupo.
                i = j - 1;

//                analiza se existe um automato anterior para ser concatenado.
//                se não existir cria af, se o prox simbolo for fecho, cria o fecho desse af
//                se existir automato para concatenar, concatena com atual(com ou sem fecho).
                if (ultimo == null) {
                    if (i < er.getSimbolos().size() - 1) {
                        if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                            ultimo = OperacoesAFND.fechoDeAF(converterParaAutomato(subEr));
                            ++i;
                        } else {
                            ultimo = converterParaAutomato(subEr);
                        }
                    } else {
                        ultimo = converterParaAutomato(subEr);
                    }
                } else {
                    if (i < er.getSimbolos().size() - 1) {
                        if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                            ultimo = OperacoesAFND.concatenaAFs(ultimo, OperacoesAFND.fechoDeAF(converterParaAutomato(subEr)));
                            ++i;
                        } else {
                            ultimo = OperacoesAFND.concatenaAFs(ultimo, converterParaAutomato(subEr));
                        }
                    } else {
                        ultimo = OperacoesAFND.concatenaAFs(ultimo, converterParaAutomato(subEr));
                    }
                }
            } else if (er.getSimbolos().get(i).getReferencia().equals("*")) {
//                gera o fecho do ultimo automato
                ultimo = OperacoesAFND.fechoDeAF(ultimo);

            } else if (er.getSimbolos().get(i).getReferencia().equals("|")) {
//                cria automato depois do | e opera com o automato em espera
                List<Simbolo> subSimbolos;
                subSimbolos = er.getSimbolos().subList(i + 1, er.getSimbolos().size());
                ExpressaoRegular subEr = new ExpressaoRegular(subSimbolos);
                ultimo = OperacoesAFND.ouEntreAFNDs(ultimo, converterParaAutomato(subEr));
                i = er.getSimbolos().size();
            } else if (er.getSimbolos().get(i) == SimboloOperacional.CONJUNTO_VAZIO) {
                ultimo = OperacoesAFND.aFLingVazia();

            } else {
//                se for um simbolo é gerado um automato que reconheça esse simbolo, gera o seu fecho se o proximo
//                simbolo for fecho, concatena com o automato anterior se existir.
                if (ultimo == null) {

                    if (er.getSimbolos().get(i) == SimboloOperacional.EPSILON) {
                        ultimo = OperacoesAFND.aFPalavraVazia();
                    } else {
                        if (i < er.getSimbolos().size() - 1) {
                            if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                                ultimo = OperacoesAFND.fechoDeAF(OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i)));
                                i++;
                            } else {
                                ultimo = OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i));
                            }
                        } else {
                            ultimo = OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i));
                        }

                    }

                } else {

                    if (er.getSimbolos().get(i) == SimboloOperacional.EPSILON) {
                        ultimo = OperacoesAFND.concatenaAFs(ultimo, OperacoesAFND.aFPalavraVazia());
                    } else {
                        if (i < er.getSimbolos().size() - 1) {
                            if (er.getSimbolos().get(i + 1).getReferencia().equals("*")) {
                                ultimo = OperacoesAFND.concatenaAFs(ultimo, OperacoesAFND.fechoDeAF(OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i))));
                                ++i;
                            } else {
                                ultimo = OperacoesAFND.concatenaAFs(ultimo, OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i)));
                            }
                        } else {
                            ultimo = OperacoesAFND.concatenaAFs(ultimo, OperacoesAFND.aFdeSimbolo(er.getSimbolos().get(i)));
                        }

                    }
                }

            }
        }

        return ultimo;
    }

}
