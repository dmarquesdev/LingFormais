/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Grupo;
import br.inf.ufsc.formais.model.analisador.sintatico.AnalisadorSintatico;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Matheus
 */
public class AnalisadorSintaticoIO implements IO<AnalisadorSintatico> {

    Pattern tokenPatt = Pattern.compile("<(.+?), ([A-Z_]+?)>");

    @Override
    public AnalisadorSintatico read(String file) throws IOException, FormaisIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AnalisadorSintatico read(String path, String file) throws IOException, FormaisIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<AnalisadorSintatico> readAll(String path, String file) throws IOException, FormaisIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(String fileName, AnalisadorSintatico obj) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void write(String path, String fileName, AnalisadorSintatico obj) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<String> readLexemas(String path, String file) throws IOException, FormaisIOException {
        ArrayList<String> entradas = new ArrayList<>();

        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        String line = br.readLine();

        Matcher tokenMatcher;

        while (line != null) {
            if (line.isEmpty()) {
                line = br.readLine();
                continue;
            }

            tokenMatcher = tokenPatt.matcher(line);
            if (tokenMatcher.matches()) {

                String grp = tokenMatcher.group(2);
                String entrada = tokenMatcher.group(1);

                entradas.add(entrada);

            }

            line = br.readLine();
        }

        br.close();
        return entradas;
    }
}
