/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class Estados extends Estado {
	private Set<Estado> estados;

	public Estados(Set<Estado> estados) {
		super("");
		this.estados = estados;
	}
        
        public Estados(){
            super("");
            this.estados = new LinkedHashSet<>();
        }

	public void addEstado(Estado estado) {
		estados.add(estado);
	}

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
