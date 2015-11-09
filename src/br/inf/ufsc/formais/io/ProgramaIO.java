/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Diego
 */
public class ProgramaIO {
    
    public static String read(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = br.readLine();
        
        StringBuilder prg = new StringBuilder();
        
        while(line != null) {
            prg.append(line).append("\n");
            line = br.readLine();
        }
        
        br.close();
        
        return prg.toString();
    }
}
