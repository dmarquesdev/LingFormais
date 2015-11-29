/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model;

/**
 *
 * @author Matheus
 */
public class Lexema_Token {
    private String lexema, token;
    
    public Lexema_Token(String lexema, String token){
        this.lexema = lexema;
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public String getToken() {
        return token;
    }
}
