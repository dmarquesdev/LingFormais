/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.analisador.lexico.AnalisadorLexico;
import br.inf.ufsc.formais.model.analisador.lexico.Linguagem;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego
 */
public class AnalisadorLexicoIO implements IO<AnalisadorLexico> {

    Pattern tokenPatt = Pattern.compile("<(.+?), ([A-Z_])+?>");

    @Override
    public AnalisadorLexico read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }

    @Override
    public AnalisadorLexico read(String path, String file) throws IOException, FormaisIOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        String line = br.readLine();

        Matcher tokenMatcher;
        
        Map<Grupo, ArrayList<ExpressaoRegular>> grupos = new LinkedHashMap<>();

        while (line != null) {
            if (line.isEmpty()) {
                line = br.readLine();
                continue;
            }
            
            tokenMatcher = tokenPatt.matcher(line);
            if (tokenMatcher.matches()) {
                String grp = tokenMatcher.group(2);
                Grupo grupo = Grupo.valueOf(grp);
                
                ExpressaoRegular value = new ExpressaoRegular(tokenMatcher.group(1));
                
                ArrayList<ExpressaoRegular> ers;
                if(!grupos.containsKey(grupo)) {
                    ers = new ArrayList<>();
                    grupos.put(grupo, ers);
                }
                
                ers = grupos.get(grupo);
                ers.add(value);
            }
            
            line = br.readLine();
        }
        
        br.close();
        
        Linguagem ling = new Linguagem(grupos);
        
        return new AnalisadorLexico(ling);
    }

    @Override
    public ArrayList<AnalisadorLexico> readAll(String path, String file) throws IOException, FormaisIOException {
        throw new UnsupportedOperationException("Não implementado");
    }

    @Override
    public void write(String fileName, AnalisadorLexico obj) throws IOException {
        write(null, fileName, obj);
    }

    @Override
    public void write(String path, String fileName, AnalisadorLexico obj) throws IOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += fileName;

        File arq = new File(completePath);
        if (arq.exists()) {
            throw new IOException();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(arq));
        bw.write(obj.toString());
        bw.close();
    }
    
    public void writeTokens(String fileName, String tokens) throws IOException {
        writeTokens(null, fileName, tokens);
    }
    
    public void writeTokens(String path, String fileName, String tokens) throws IOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += fileName;

        File arq = new File(completePath);
        if (arq.exists()) {
            throw new IOException();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(arq));
        bw.write(tokens);
        bw.close();
    }

}
