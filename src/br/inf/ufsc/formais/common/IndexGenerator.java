package br.inf.ufsc.formais.common;

public class IndexGenerator {
	private static int index;

	public static int newIndex() {
		return index++;
	}
}
