/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.Cadeia;
import br.inf.ufsc.formais.model.gramatica.Gramatica;
import br.inf.ufsc.formais.model.gramatica.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Diego
 */
public class GramaticaIO implements IO<Gramatica> {

    Pattern gramaticaLineValidation = Pattern.compile("[A-Z] -> (([a-z&&[^e]][A-Z]?)|e)");

    @Override
    public Gramatica read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }

    @Override
    public Gramatica read(String path, String file) throws IOException, FormaisIOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        String line = br.readLine();

        Set<SimboloNaoTerminal> naoTerminais = new LinkedHashSet<>();
        Set<SimboloTerminal> terminais = new LinkedHashSet<>();
        Set<RegraProducao> regras = new LinkedHashSet<>();
        SimboloNaoTerminal inicial = null;

        while (line != null && !line.isEmpty()) {
            Matcher m = gramaticaLineValidation.matcher(line);
            if (m.matches()) {
                SimboloNaoTerminal snt = new SimboloNaoTerminal("" + line.charAt(0));
                if (inicial == null) {
                    inicial = snt;
                }
                if (!naoTerminais.contains(snt)) {
                    naoTerminais.add(snt);
                }

                char a = line.charAt(5);
                SimboloTerminal sta = new SimboloTerminal(""+a);
                
                if(a == 'e') {
                    sta = SimboloTerminal.EPSILON;
                }
                
                if(!terminais.contains(sta)) {
                    terminais.add(sta);
                }
                
                Cadeia cad = new Cadeia(sta);
                
                if(line.length() > 6) {
                    char aA = line.charAt(6);
                    SimboloNaoTerminal snta = new SimboloNaoTerminal(""+aA);
                
                    if(!naoTerminais.contains(snta)) {
                        naoTerminais.add(snta);
                    }
                    cad = new Cadeia(sta, snta);
                }

                RegraProducao rp = new RegraProducao(snt, cad);
                regras.add(rp);
            }
            
            line = br.readLine();
        }
        
        br.close();
        
        return new Gramatica(naoTerminais, terminais, regras, inicial);
    }

    @Override
    public void write(String fileName, Gramatica obj) throws IOException {
        write(null, fileName, obj);
    }

    @Override
    public void write(String path, String fileName, Gramatica obj) throws IOException {
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
