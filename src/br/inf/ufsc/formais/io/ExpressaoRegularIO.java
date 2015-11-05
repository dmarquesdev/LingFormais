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
 * Classe responsável pela entrada/saída de Expressões Regulares.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class ExpressaoRegularIO implements IO<ExpressaoRegular> {

    /**
     * Lê um arquivo que contenha uma Expressão Regular.
     *
     * @param file Arquivo para ser lido (caminho completo).
     * @return Uma Expressão Regular.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do
     * arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da
     * estrutura do arquivo.
     */
    @Override
    public ExpressaoRegular read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }
    
    @Override
    public ArrayList<ExpressaoRegular> readAll(String path, String file) throws IOException, FormaisIOException{
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        
        String line = br.readLine();

        ArrayList<ExpressaoRegular> listaEr = new ArrayList<>();
        
        while (line != null && !line.isEmpty()) {
            List<Simbolo> simbolos = new ArrayList<>();
            for (char c : line.toCharArray()) {
                if (c == '(') {
                    simbolos.add(SimboloOperacional.ABRE_GRUPO);
                } else if (c == ')') {
                    simbolos.add(SimboloOperacional.FECHA_GRUPO);
                } else if (c == '*') {
                    simbolos.add(SimboloOperacional.FECHO);
                } else if (c == '|') {
                    simbolos.add(SimboloOperacional.ALTERNANCIA);
                } else {
                    Simbolo a = new Simbolo("" + c);
                    simbolos.add(a);
                }
            }
            listaEr.add(new ExpressaoRegular(simbolos));
            line = br.readLine();
        }

        br.close();

        return listaEr;
    }

    /**
     * Lê um arquivo que contenha uma Expressão Regular.
     *
     * @param file Arquivo para ser lido.
     * @param path Caminho onde se encontra o arquivo.
     * @return Uma Expressão Regular.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do
     * arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da
     * estrutura do arquivo.
     */
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
            System.out.println(line);
            for (char c : line.toCharArray()) {
                if (Character.isAlphabetic(c) || Character.isDigit(c)) {
                    Simbolo a = new Simbolo("" + c);
                    simbolos.add(a);
                } else if (c == '(') {
                    simbolos.add(SimboloOperacional.ABRE_GRUPO);
                } else if (c == ')') {
                    simbolos.add(SimboloOperacional.FECHA_GRUPO);
                } else if (c == '*') {
                    simbolos.add(SimboloOperacional.FECHO);
                } else if (c == '|') {
                    simbolos.add(SimboloOperacional.ALTERNANCIA);
                } else {
                    throw new FormaisIOException("Expressão regular contém símbolo inválido: " + c);
                }
            }
            
        }

        br.close();

        return new ExpressaoRegular(simbolos);
    }

    /**
     * Escreve uma Expressão Regular em um arquivo.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Expressão Regular que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String fileName, ExpressaoRegular obj) throws IOException {
        write(null, fileName, obj);
    }

    /**
     * Escreve uma Expressão Regular em um arquivo.
     * @param path Caminho do arquivo que será escrito.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Expressão Regular que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String path, String fileName, ExpressaoRegular obj) throws IOException {
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

}
