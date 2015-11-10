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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        String[] separacao = entrada.split("[ \n\t]");
        
        List<String> lexemas = Arrays.asList(separacao);
        List<String> aux = new ArrayList<>();
        
        for (String lexema : lexemas) {
            if(lexema.endsWith(";")) {
                lexema = lexema.replaceAll(";", "");
                aux.add(lexema);
                aux.add(";");
            } else {
                aux.add(lexema);
            }
        }
        
        lexemas = aux;
        
        for (String lexema : lexemas) {
            Grupo grupo = linguagem.computar(new ExpressaoRegular(lexema));
            out.append("<").append(lexema).append(", ").append(grupo.name()).append(">\n");
            
            //TODO Tratar Separadores
        }
        
        return out.toString();
    }
    
    @Override
    public String toString() {
        return linguagem.toString();
    }
}
