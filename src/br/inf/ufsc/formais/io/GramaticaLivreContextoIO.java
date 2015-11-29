package br.inf.ufsc.formais.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.inf.ufsc.formais.exception.FormaisIOException;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.gramatica.SimboloNaoTerminal;
import br.inf.ufsc.formais.model.gramatica.SimboloTerminal;
import br.inf.ufsc.formais.model.gramatica.glc.CadeiaGLC;
import br.inf.ufsc.formais.model.gramatica.glc.GramaticaLivreContexto;
import br.inf.ufsc.formais.model.gramatica.glc.RegraProducaoGLC;

/**
 * Classe responsável pela entrada/saída de Gramáticas.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class GramaticaLivreContextoIO implements IO<GramaticaLivreContexto> {
	/**
	 * Expressões Regulares responsáveis por reconhecer a estrutura de uma Gramática.
	 */
	Pattern glcLineValidation = Pattern.compile("[A-Z]+[a-z]*[0-9]* -> [[\\p{Alnum}]*[\\p{Punct}]*ε*( )?]+");

	/**
	 * Lê um arquivo que contenha uma Gramática.
	 * 
	 * @param file
	 *            Arquivo para ser lido (caminho completo).
	 * @return Uma Gramática.
	 * @throws IOException
	 *             Quando ocorre algum erro na leitura/escrita do arquivo.
	 * @throws FormaisIOException
	 *             Quando ocorre algum erro na leitura da estrutura do arquivo.
	 */
	@Override
	public GramaticaLivreContexto read(String file) throws IOException, FormaisIOException {
		return this.read(null, file);
	}

	/**
	 * Lê um arquivo que contenha uma Gramática.
	 * 
	 * @param path
	 *            Caminho do arquivo que será lido.
	 * @param file
	 *            Arquivo para ser lido.
	 * @return Uma Gramática.
	 * @throws IOException
	 *             Quando ocorre algum erro na leitura/escrita do arquivo.
	 * @throws FormaisIOException
	 *             Quando ocorre algum erro na leitura da estrutura do arquivo.
	 */
	@Override
	public GramaticaLivreContexto read(String path, String file) throws IOException, FormaisIOException {
		String completePath = "";
		if (path != null) {
			completePath += path;
		}
		completePath += file;

		BufferedReader br = new BufferedReader(new FileReader(completePath));
		String line = br.readLine();

		Set<SimboloNaoTerminal> naoTerminais = new LinkedHashSet<>();
		Set<SimboloTerminal> terminais = new LinkedHashSet<>();
		Set<RegraProducaoGLC> regras = new LinkedHashSet<>();
		SimboloNaoTerminal inicial = null;

		while (line != null && !line.isEmpty()) {
			Matcher m = this.glcLineValidation.matcher(line);
			if (m.matches()) {
				LinkedList<String> splitedLine = new LinkedList<>(Arrays.asList(line.split(" -> ")));

				SimboloNaoTerminal snt = new SimboloNaoTerminal(splitedLine.get(0));
				naoTerminais.add(snt);
				if (inicial == null) {
					inicial = snt;
				}

				String producao = splitedLine.get(1);
				LinkedList<String> simbolosProducao = new LinkedList<>(Arrays.asList(producao.split("\\s+")));
				LinkedList<Simbolo> cadeiaProduzida = new LinkedList<>();

				for (String simbolo : simbolosProducao) {
					if (simbolo.equals(Simbolo.EPSILON.getReferencia()) || simbolo.equals("EPSILON")) {
						cadeiaProduzida.add(Simbolo.EPSILON);
					} else {
						if (Character.isUpperCase(simbolo.codePointAt(0))) {
							SimboloNaoTerminal naoTerminal = new SimboloNaoTerminal(simbolo);
							cadeiaProduzida.add(naoTerminal);
							naoTerminais.add(naoTerminal);
						} else {
							SimboloTerminal terminal = new SimboloTerminal(simbolo);
							cadeiaProduzida.add(terminal);
							terminais.add(terminal);
						}
					}
				}

				RegraProducaoGLC regraGLC = new RegraProducaoGLC(snt, new CadeiaGLC(cadeiaProduzida));
				regras.add(regraGLC);
			}

			line = br.readLine();
		}

		br.close();

		return new GramaticaLivreContexto(naoTerminais, terminais, regras, inicial);
	}

	/**
	 * Escreve uma Gramática em um arquivo.
	 * 
	 * @param fileName
	 *            Nome do arquivo a ser escrito.
	 * @param obj
	 *            Gramática que será escrito no arquivo.
	 * @throws IOException
	 *             Quando não for possível escrever o arquivo em disco.
	 */
	@Override
	public void write(String fileName, GramaticaLivreContexto obj) throws IOException {
		this.write(null, fileName, obj);
	}

	/**
	 * Escreve uma Gramática em um arquivo.
	 * 
	 * @param path
	 *            Caminho do arquivo que será escrito.
	 * @param fileName
	 *            Nome do arquivo a ser escrito.
	 * @param obj
	 *            Gramática que será escrito no arquivo.
	 * @throws IOException
	 *             Quando não for possível escrever o arquivo em disco.
	 */
	@Override
	public void write(String path, String fileName, GramaticaLivreContexto obj) throws IOException {
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
	public ArrayList<GramaticaLivreContexto> readAll(String path, String file) throws IOException, FormaisIOException {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose Tools | Templates.
	}

}
