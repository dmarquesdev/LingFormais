/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import java.io.IOException;

/**
 *
 * @author Diego
 */
public interface IO<T> {

    public T read(String file) throws IOException, FormaisIOException;

    public T read(String path, String file) throws IOException, FormaisIOException;
    
    public void write(String fileName, T obj) throws IOException;
    
    public void write(String path, String fileName, T obj) throws IOException;
}
