package br.inf.ufsc.formais.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;

public class FirstAndFollowIO implements IO<Map<Simbolo, Set<Simbolo>>> {

	// Pattern pattern = Pattern.compile("[A-Z]+[a-z]*[0-9]* = \\{[[\\p{Alnum}|\\p{Punct}]+( ,)?]+\\}");

	/**
	 * Expressões Regulares responsáveis por reconhecer a estrutura de um Autômato Finito.
	 */
	Pattern firstAndFollowPattern = Pattern.compile("[A-Z]+[a-z]*[0-9]* = \\{[[[a-z]|\\p{Punct}|ε|EPSILON]+( )?]+\\}");

	/**
	 * Lê um arquivo que contenha um Automato Finito.
	 * 
	 * @param file
	 *            Arquivo para ser lido (caminho completo).
	 * @return Um Automato Finito.
	 * @throws IOException
	 *             Quando ocorre algum erro na leitura/escrita do arquivo.
	 * @throws FormaisIOException
	 *             Quando ocorre algum erro na leitura da estrutura do arquivo.
	 */
	@Override
	public Map<Simbolo, Set<Simbolo>> read(String file) throws IOException, FormaisIOException {
		return this.read(null, file);
	}

	/**
	 * Lê um arquivo que contenha um Automato Finito.
	 * 
	 * @param file
	 *            Arquivo para ser lido.
	 * @param path
	 *            Caminho onde se encontra o arquivo.
	 * @return Um Automato Finito.
	 * @throws IOException
	 *             Quando ocorre algum erro na leitura/escrita do arquivo.
	 * @throws FormaisIOException
	 *             Quando ocorre algum erro na leitura da estrutura do arquivo.
	 */
	@Override
	public Map<Simbolo, Set<Simbolo>> read(String path, String file) throws IOException, FormaisIOException {
		String completePath = "";
		if (path != null) {
			completePath += path;
		}
		completePath += file;

		BufferedReader br = new BufferedReader(new FileReader(completePath));
		String line = br.readLine();

		Map<Simbolo, Set<Simbolo>> firstAndFollowMap = new LinkedHashMap<>();

		Matcher firstAndFollowMatcher;

		while (line != null) {
			if (line.isEmpty()) {
				line = br.readLine();
				continue;
			}
			firstAndFollowMatcher = this.firstAndFollowPattern.matcher(line);

			if (firstAndFollowMatcher.matches()) {
				LinkedList<String> splitedLine = new LinkedList<>(Arrays.asList(line.split(" = \\{")));
				SimboloNaoTerminal snt = new SimboloNaoTerminal(splitedLine.get(0));
				String inputSet = splitedLine.get(1);
				inputSet = inputSet.substring(0, inputSet.length() - 1);
				LinkedList<String> firstAndFollowStrings = new LinkedList<>(Arrays.asList(inputSet.split("\\s+")));
				Set<Simbolo> firstAndFollowSet = new LinkedHashSet<>();
				for (String simbolo : firstAndFollowStrings) {
					if (simbolo.equals(Simbolo.EPSILON.getReferencia()) || simbolo.equals("EPSILON")) {
						firstAndFollowSet.add(Simbolo.EPSILON);
					} else {
						firstAndFollowSet.add(new SimboloTerminal(simbolo));
					}
				}
				System.out.println(snt + "= {" + firstAndFollowSet + "}");
				firstAndFollowMap.put(snt, firstAndFollowSet);

			} else {
				throw new FormaisIOException("Entrada de First e follow inválida: " + line);
			}

			line = br.readLine();
		}

		br.close();

		return firstAndFollowMap;
	}

	/**
	 * Escreve um Automato Finito em um arquivo.
	 * 
	 * @param fileName
	 *            Nome do arquivo a ser escrito.
	 * @param obj
	 *            Automato Finito que será escrito no arquivo.
	 * @throws IOException
	 *             Quando não for possível escrever o arquivo em disco.
	 */
	@Override
	public void write(String fileName, Map<Simbolo, Set<Simbolo>> obj) throws IOException {
		this.write(null, fileName, obj);
	}

	/**
	 * Escreve um Automato Finito em um arquivo.
	 * 
	 * @param path
	 *            Caminho onde será escrito o arquivo.
	 * @param fileName
	 *            Nome do arquivo a ser escrito.
	 * @param obj
	 *            Automato Finito que será escrito no arquivo.
	 * @throws IOException
	 *             Quando não for possível escrever o arquivo em disco.
	 */
	@Override
	public void write(String path, String fileName, Map<Simbolo, Set<Simbolo>> obj) throws IOException {
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
	public ArrayList<Map<Simbolo, Set<Simbolo>>> readAll(String path, String file) throws IOException, FormaisIOException {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

}
