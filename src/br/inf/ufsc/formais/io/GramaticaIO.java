package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.gramatica.regular.Cadeia;
import br.inf.ufsc.formais.model.gramatica.regular.GramaticaRegular;
import br.inf.ufsc.formais.model.gramatica.regular.RegraProducao;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela entrada/saída de Gramáticas.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class GramaticaIO implements IO<GramaticaRegular> {

    /**
     * Expressões Regulares responsáveis por reconhecer a estrutura de uma
     * Gramática.
     */
    Pattern gramaticaLineValidation = Pattern.compile("[A-Z] -> (([a-z&&[^e]][A-Z]?)|e)");

    /**
     * Lê um arquivo que contenha uma Gramática.
     * @param file Arquivo para ser lido (caminho completo).
     * @return Uma Gramática.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da estrutura do arquivo.
     */
    @Override
    public GramaticaRegular read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }

    /**
     * Lê um arquivo que contenha uma Gramática.
     * @param path Caminho do arquivo que será lido.
     * @param file Arquivo para ser lido.
     * @return Uma Gramática.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da estrutura do arquivo.
     */
    @Override
    public GramaticaRegular read(String path, String file) throws IOException, FormaisIOException {
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
                SimboloTerminal sta = new SimboloTerminal("" + a);

                if (a == 'e') {
                    sta = SimboloTerminal.EPSILON;
                }

                if (!terminais.contains(sta)) {
                    terminais.add(sta);
                }

                Cadeia cad = new Cadeia(sta);

                if (line.length() > 6) {
                    char aA = line.charAt(6);
                    SimboloNaoTerminal snta = new SimboloNaoTerminal("" + aA);

                    if (!naoTerminais.contains(snta)) {
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

        return new GramaticaRegular(naoTerminais, terminais, regras, inicial);
    }

    /**
     * Escreve uma Gramática em um arquivo.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Gramática que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String fileName, GramaticaRegular obj) throws IOException {
        write(null, fileName, obj);
    }

    /**
     * Escreve uma Gramática em um arquivo.
     * @param path Caminho do arquivo que será escrito.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Gramática que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String path, String fileName, GramaticaRegular obj) throws IOException {
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

    @Override
    public ArrayList<GramaticaRegular> readAll(String path, String file) throws IOException, FormaisIOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
