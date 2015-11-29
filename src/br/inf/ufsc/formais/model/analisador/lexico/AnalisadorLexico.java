/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.analisador.lexico;

import br.inf.ufsc.formais.exception.AnaliseLexicaException;
import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class AnalisadorLexico {

    private Linguagem linguagem;
    private Map<Grupo, List<String>> tokensOfEachGroupMap;
    private List<String> invalidTokens;

    public AnalisadorLexico(Linguagem linguagem) {
        this.linguagem = linguagem;

        this.tokensOfEachGroupMap = new LinkedHashMap<>();
    }

    public String analisar(String entrada) throws AnaliseLexicaException {
        this.buildTokensStructures();

        String[] separacao = entrada.split("[ \n\t]");

        List<String> lexemas = Arrays.asList(separacao);
        List<String> aux = new ArrayList<>();

        for (String lexema : lexemas) {
            if (lexema.endsWith(";")) {
                lexema = lexema.replaceAll(";", "");
                aux.add(lexema);
                aux.add(";");
            } else {
                aux.add(lexema);
            }
        }

        lexemas = aux;
        for (String lexema : lexemas) {
            try {
                Grupo grupo = linguagem.computar(new ExpressaoRegular(lexema));
                tokensOfEachGroupMap.get(grupo).add(lexema);
            } catch (Exception e) {
                invalidTokens.add(lexema);
            }
        }
        return buildTokensOutput();
    }

    private String buildTokensOutput() {
        StringBuilder out = new StringBuilder();

        for (Grupo grupo : tokensOfEachGroupMap.keySet()) {
            List<String> tokens = tokensOfEachGroupMap.get(grupo);
            if (!tokens.isEmpty()) {
                out.append("Classe de palavras: ").append(grupo.name()).append("\n");
                for (String token : tokens) {
                    out.append("<").append(token).append(", ").append(grupo.name()).append(">\n");
                }
                out.append("\n");
            }
        }

        if (!invalidTokens.isEmpty()) {
            out.append("Tokens inválidos: ").append("\n");
            for (String invalidtoken : invalidTokens) {
                out.append("<").append(invalidtoken).append(", ").append("INVALIDTOKEN").append(">\n");
            }
        }

        return out.toString();
    }

    private void buildTokensStructures() {
        tokensOfEachGroupMap.put(Grupo.PALAVRASRESERVADAS, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.CONDICIONAL, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.LOOP, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.OPERADORES, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.OPERADORESLOGICOS, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.SEPARADORES, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.LITERAL, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.ATRIBUICAO, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.TIPO, new LinkedList<String>());
        tokensOfEachGroupMap.put(Grupo.IDENTIFICADORES, new LinkedList<String>());
        invalidTokens = new LinkedList<>();
    }

    @Override
    public String toString() {
        return linguagem.toString();
    }
}
