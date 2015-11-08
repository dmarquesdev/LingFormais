/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.lexicalanalyzer;

import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.gramatica.Cadeia;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Diego
 */
public class Linguagem {
    private Map<EstadoFinal, TipoToken> tokens;
    private AutomatoFinitoDeterministico automato;

    public Linguagem(Map<EstadoFinal, TipoToken> tokens, AutomatoFinitoDeterministico automato) {
        this.tokens = tokens;
        this.automato = automato;
    }

    public Linguagem() {
        tokens = new LinkedHashMap<>();
    }
    
    public TipoToken computarCadeia(Cadeia cadeia) {
        return null;
    }
}
