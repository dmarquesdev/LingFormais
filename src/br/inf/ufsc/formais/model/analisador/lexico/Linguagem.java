/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.lexico;

import br.inf.ufsc.formais.exception.EstadoInalcancavelException;
import br.inf.ufsc.formais.model.CadeiaAutomato;
import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.model.gramatica.regular.Cadeia;
import br.inf.ufsc.formais.operacoes.AFLexico;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class Linguagem {
    private Map<Grupo,ArrayList<ExpressaoRegular>> tokens;
    private AutomatoFinitoDeterministico automato;

    public Linguagem(Map<Grupo,ArrayList<ExpressaoRegular>> tokens) {
        this.tokens = tokens;
        this.automato = AFLexico.geraAutomatoFinal(tokens);
    }
    
    public Grupo computar(ExpressaoRegular er) throws EstadoInalcancavelException {
       Estado ef = automato.computar(new CadeiaAutomato(er.toString()));
       return AFLexico.findGroup(ef);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Grupo grupo : tokens.keySet()) {
            for (ExpressaoRegular er : tokens.get(grupo)) {
                sb.append("<").append(er.toString()).append(", ").append(grupo.name()).append(">\n");
            }
            
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
