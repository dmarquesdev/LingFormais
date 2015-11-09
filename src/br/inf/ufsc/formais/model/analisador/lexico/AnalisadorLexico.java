/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.lexico;

import br.inf.ufsc.formais.exception.AnaliseLexicaException;
import br.inf.ufsc.formais.exception.EstadoInalcancavelException;
import br.inf.ufsc.formais.model.CadeiaAutomato;
import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;

/**
 *
 * @author Diego
 */
public class AnalisadorLexico {
    private Linguagem linguagem;

    public AnalisadorLexico(Linguagem linguagem) {
        this.linguagem = linguagem;
    }
    
    public String analisar(String entrada) throws AnaliseLexicaException {
        StringBuilder out = new StringBuilder();
        String[] lexemas = entrada.split(" ");
        
        for (String lexema : lexemas) {
            Grupo grupo = linguagem.computar(new ExpressaoRegular(lexema));
            out.append("<").append(lexema).append(", ").append(grupo.getDescricao()).append(">\n");
            
            //TODO Tratar Separadores
        }
        
        return out.toString();
    }
    
    @Override
    public String toString() {
        return linguagem.toString();
    }
}
