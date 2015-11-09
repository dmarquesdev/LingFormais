package br.inf.ufsc.formais.common;

/**
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class IndexGenerator {
	private static int index;

        /**
         * Gera um novo indice para identificação de estados dos automatos.
         * @return Um indice inteiro.
         */
	public static int newIndex() {
		return index++;
	}
}
