/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.io;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.automato.AutomatoFinito;
import br.inf.ufsc.formais.model.automato.AutomatoFinitoDeterministico;
import br.inf.ufsc.formais.model.automato.Entrada;
import br.inf.ufsc.formais.model.automato.Estado;
import br.inf.ufsc.formais.model.automato.EstadoFinal;
import br.inf.ufsc.formais.model.automato.EstadoInicial;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe responsável pela entrada/saída de Automatos finitos.
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AutomatoFinitoIO implements IO<AutomatoFinito> {

    /**
     * Expressões Regulares responsáveis por reconhecer a estrutura de um
     * Autômato Finito.
     */
    Pattern estadosPatt = Pattern.compile("E = \\{(([a-zA-Z0-9]+(, )?)+)\\}"),
            alfabetoPatt = Pattern.compile("A = \\{(([a-zA-Z0-9]+(, )?)+)\\}"),
            transicaoPatt = Pattern.compile("T = \\(([a-zA-Z0-9]+, [a-zA-Z0-9]+)\\) -> ([a-zA-Z0-9]+)"),
            inicialPatt = Pattern.compile("I = ([a-zA-Z0-9]+)"),
            finalPatt = Pattern.compile("F = \\{(([a-zA-Z0-9]+(, )?)+)\\}");

    /**
     * Lê um arquivo que contenha um Automato Finito.
     * @param file Arquivo para ser lido (caminho completo).
     * @return Um Automato Finito.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da estrutura do arquivo.
     */
    @Override
    public AutomatoFinito read(String file) throws IOException, FormaisIOException {
        return read(null, file);
    }

    /**
     * Lê um arquivo que contenha um Automato Finito.
     * @param file Arquivo para ser lido.
     * @param path Caminho onde se encontra o arquivo.
     * @return Um Automato Finito.
     * @throws IOException Quando ocorre algum erro na leitura/escrita do arquivo.
     * @throws FormaisIOException Quando ocorre algum erro na leitura da estrutura do arquivo.
     */
    @Override
    public AutomatoFinito read(String path, String file) throws IOException, FormaisIOException {
        String completePath = "";
        if (path != null) {
            completePath += path;
        }
        completePath += file;

        BufferedReader br = new BufferedReader(new FileReader(completePath));
        String line = br.readLine();

        if (!line.equals("M = (E,A,T,I,F)")) {
            throw new FormaisIOException("Declaração de AFD fora do padrão!");
        }

        line = br.readLine();

        Set<Simbolo> simbolosAlfa = new LinkedHashSet<>();
        Set<Estado> estados = new LinkedHashSet<>();
        Set<EstadoFinal> estadosFinais = new LinkedHashSet<>();
        EstadoInicial inicial = null;
        Map<Entrada, Estado> transicoes = new LinkedHashMap<>();

        Matcher estadosMatcher, alfabetoMatcher,
                transicaoMatcher, inicialMatcher, finalMatcher;

        while (line != null) {
            if (line.isEmpty()) {
                line = br.readLine();
                continue;
            }

            estadosMatcher = estadosPatt.matcher(line);
            alfabetoMatcher = alfabetoPatt.matcher(line);
            transicaoMatcher = transicaoPatt.matcher(line);
            inicialMatcher = inicialPatt.matcher(line);
            finalMatcher = finalPatt.matcher(line);

            if (estadosMatcher.matches()) {
                String group = estadosMatcher.group(1);
                String[] ests = group.split(", ");
                for (String est : ests) {
                    Estado estado = new Estado(est);
                    estados.add(estado);
                }
            } else if (alfabetoMatcher.matches()) {
                String group = alfabetoMatcher.group(1);
                String[] simbs = group.split(", ");
                for (String simb : simbs) {
                    Simbolo simbolo = new Simbolo(simb);
                    simbolosAlfa.add(simbolo);
                }
            } else if (transicaoMatcher.matches()) {
                String ent = transicaoMatcher.group(1),
                        paraStr = transicaoMatcher.group(2);
                String[] estSimb = ent.split(", ");
                Estado de = new Estado(estSimb[0]), para = new Estado(paraStr);
                Simbolo simb = new Simbolo(estSimb[1]);
                Entrada entrada = new Entrada(de, simb);
                for (Estado e : estados) {
                    if (e.getId().equals(de.getId())) {
                        de = e;
                    } else if (e.getId().equals(para.getId())) {
                        para = e;
                    }
                }
                transicoes.put(entrada, para);
            } else if (inicialMatcher.matches()) {
                inicial = new EstadoInicial(inicialMatcher.group(1));
                for (Estado e : estados) {
                    if (e.getId().equals(inicial.getId())) {
                        e = inicial;
                        break;
                    }
                }
            } else if (finalMatcher.matches()) {
                String group = finalMatcher.group(1);
                String[] finais = group.split(", ");
                for (String fim : finais) {
                    EstadoFinal ef = new EstadoFinal(fim);
                    estadosFinais.add(ef);
                    for (Estado e : estados) {
                        if (e.getId().equals(ef.getId())) {
                            e = ef;
                            break;
                        }
                    }
                }
            } else {
                throw new FormaisIOException("Entrada da AFD inválida: " + line);
            }

            line = br.readLine();
        }

        br.close();

        Alfabeto alfa = new Alfabeto(simbolosAlfa);

        return new AutomatoFinitoDeterministico(estados, alfa, inicial, estadosFinais, transicoes);
    }

    /**
     * Escreve um Automato Finito em um arquivo.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Automato Finito que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String fileName, AutomatoFinito obj) throws IOException {
        write(null, fileName, obj);
    }

    /**
     * Escreve um Automato Finito em um arquivo.
     * @param  path Caminho onde será escrito o arquivo.
     * @param fileName Nome do arquivo a ser escrito.
     * @param obj Automato Finito que será escrito no arquivo.
     * @throws IOException Quando não for possível escrever o arquivo em disco.
     */
    @Override
    public void write(String path, String fileName, AutomatoFinito obj) throws IOException {
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
