/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.operacoes;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoNaoDeterministicoGeneralizado;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;

/**
 *  Converte um Automato finito não deterministico em uma expressão regular.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AFNDG2ER {

    public static ExpressaoRegular converterParaER(AutomatoFinitoNaoDeterministicoGeneralizado afndg) {
        Estado[] estados = afndg.getEstados().toArray(new Estado[afndg.getEstados().size()]);
        int k = estados.length, i = 0;
        while (k > 2) {
            if (!estados[i].equals(afndg.getEstadoInicial()) && !estados[i].equals(afndg.getEstadoFinal())) {
                afndg.removeEstado(estados[i]);
                i++;
                k--;
            }
        }

        return afndg.getExpressaoRegular(afndg.getEstadoInicial(), afndg.getEstadoFinal());
    }
}
