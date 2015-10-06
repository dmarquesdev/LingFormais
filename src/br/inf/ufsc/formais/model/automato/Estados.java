package br.inf.ufsc.formais.model.automato;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Clase que representa um conjunto de estados do Automato.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Estados extends Estado {
    
        /**
         * Conjunto de estados
         */
	private Set<Estado> estados;

        /**
         * Contrutor, inicializa todos os atributos da classe.
         * @param estados conjunto de estados em que será inicializado o objeto.
         */
	public Estados(Set<Estado> estados) {
		super("");
		this.estados = estados;
	}
        
        /**
         * Contrutor vazio, inicializa os atributos da classe com um valor padrão.
         */
        public Estados(){
            super("");
            this.estados = new LinkedHashSet<>();
        }
        
        /**
         * Adiciona um estado no conjunto de estados da classe.
         * @param estado Estado a ser adicionado ao conjunto.
         */
	public void addEstado(Estado estado) {
		estados.add(estado);
	}

        /**
         * Retorna o conjunto de estados da classe.
         * @return conjunto de estados da classe.
         */
	public Set<Estado> get() {
		return estados;
	}

	@Override
	public String getId() {
		StringBuilder out = new StringBuilder();
                out.append("{");
		for (Estado estado : estados) {
			out.append(estado.getId()).append(", ");
		}

		out.delete(out.length() - 2, out.length());
                out.append("}");
		return out.toString();

	}
	
        /**
         * Retorna se o conjunto de estados da classe está vazio.
         * @return Verdadeiro se está vazio, falso caso contrario.
         */
	public boolean isEmpty() {
		return estados.isEmpty();
	}

	@Override
	public String toString() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Estados other = (Estados) obj;
		if (!estados.equals(other.get())) {
			return false;
		}
		return true;
	}

}
