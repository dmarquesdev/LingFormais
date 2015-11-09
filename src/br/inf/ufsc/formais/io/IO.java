package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface que contém os métodos necessários para entrada/saída.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 * @param <T> Classe na qual ocorrerá entrada/saída.
 */
public interface IO<T> {

    /**
     * Lê um arquivo.
     * @param file Arquivo para ser lido.
     * @return Um objeto da classe T.
     * @throws IOException Quando não é possível ler o arquivo.
     * @throws FormaisIOException Quando a estrutura do arquivo está incorreta.
     */
    public T read(String file) throws IOException, FormaisIOException;

    /**
     * Lê um arquivo.
     * @param path Caminho onde se encontra o arquivo.
     * @param file Arquivo para ser lido.
     * @return Um objeto da classe T.
     * @throws IOException Quando não é possível ler o arquivo.
     * @throws FormaisIOException Quando a estrutura do arquivo está incorreta.
     */
    public T read(String path, String file) throws IOException, FormaisIOException;
    
    /**
     * Lê um arquivo.
     * @param path Caminho onde se encontra o arquivo.
     * @param file Arquivo para ser lido.
     * @return Um arrayList de objetos da classe T.
     * @throws IOException Quando não é possível ler o arquivo.
     * @throws FormaisIOException Quando a estrutura do arquivo está incorreta.
     */
    public ArrayList<T> readAll(String path, String file) throws IOException, FormaisIOException;
    
    /**
     * Escreve em um arquivo.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj objeto que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    public void write(String fileName, T obj) throws IOException;
    
    /**
     * Escreve em um arquivo.
     * @param path Caminho onde o arquivo será escrito.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj objeto que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    public void write(String path, String fileName, T obj) throws IOException;
}
