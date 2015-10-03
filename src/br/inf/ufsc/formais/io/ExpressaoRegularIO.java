/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import br.inf.ufsc.formais.model.er.SimboloOperacional;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Diego
 */
public class ExpressaoRegularIO implements IO<ExpressaoRegular> {

    @Override
    public ExpressaoRegular read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }

    @Override
    public ExpressaoRegular read(String path, String file) throws IOException, FormaisIOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        String line = br.readLine();

        List<Simbolo> simbolos = new ArrayList<>();

        if (line != null && !line.isEmpty()) {
            for (char c : line.toCharArray()) {
                if(Character.isAlphabetic(c) || Character.isDigit(c)) {
                    Simbolo a = new Simbolo(""+c);
                    simbolos.add(a);
                } else if(c == '(') {
                    simbolos.add(SimboloOperacional.ABRE_GRUPO);
                } else if(c == ')') {
                    simbolos.add(SimboloOperacional.FECHA_GRUPO);
                } else if(c == '*') {
                    simbolos.add(SimboloOperacional.FECHO);
                } else if(c == '|') {
                    simbolos.add(SimboloOperacional.ALTERNANCIA);
                } else {
                    throw new FormaisIOException("Expressão regular contém símbolo inválido: " + c);
                }
            }
        }
        
        br.close();
        
        return new ExpressaoRegular(simbolos);
    }

    @Override
    public void write(String fileName, ExpressaoRegular obj) throws IOException {
        write(null, fileName, obj);
    }

    @Override
    public void write(String path, String fileName, ExpressaoRegular obj) throws IOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += fileName;
        
        File arq = new File(completePath);
        if(arq.exists()) {
            throw new IOException();
        }
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(arq));
        bw.write(obj.toString());
        bw.close();
    }

}
