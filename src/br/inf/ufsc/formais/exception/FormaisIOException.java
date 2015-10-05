package br.inf.ufsc.formais.exception;

/**
 * Exception gerada ao encontrar alguma inconsistência na análise da estrutura
 * do arquivo que está sendo lido.
 * 
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class FormaisIOException extends Exception{

    public FormaisIOException(String message) {
        super(message);
    }
    
}
