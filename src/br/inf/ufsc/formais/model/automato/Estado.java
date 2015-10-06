package br.inf.ufsc.formais.model.automato;

import java.util.Objects;

/**
 * Clase que representa um Estado do Automato.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class Estado {
    
    /**
     * Atributo que armazena o identificador do estado.
     */
    private String id;

    /**
     * Contrutor, inicializa o único atributo da classe.
     * @param id identificador em que será inicializado o objeto.
     */
    public Estado(String id) {
        this.id = id;
    }

    /**
     * Retorna o id do Estado.
     * @return o identificador do Estado.
     */
    public String getId() {
        return id;
    }

    /**
     * Seta o id do Estado.
     * @param id identificador a ser setado.
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final Estado other = (Estado) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
}
