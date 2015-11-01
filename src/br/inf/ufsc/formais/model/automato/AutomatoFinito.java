package br.inf.ufsc.formais.model.automato;

import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;

/**
 * Interface responsável pela regras minímas que qualquer implementação de
 * Automato Finito deve obedecer.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public interface AutomatoFinito {

    /**
     * Adiciona um estado no Automato.
     * @param estado Estado a ser adicionado no Automato.
     */
    public void addEstado(Estado estado);

    /**
     * Obtem o conjunto de estados do Automato.
     * @return Conjunto de estados do automato.
     */
    public Set<Estado> getEstados();

    /**
     * Seta o conjunto de estados do Automato.
     * @param estados Conjunto de estados a serem setados no Automato.
     */
    public void setEstados(Set<Estado> estados);
    
    /**
    * Remove um estado do Automato.
    * @param estado Estado a ser removido no Automato.
    */
    public void removeEstado(Estado estado);
    
    /**
    * Obtem o alfabeto do Automato.
    * @return  Alfabeto do Automato.
    */
    public Alfabeto getAlfabeto();
    
    /**
    * Seta o alfabeto do Automato.
    * @param alfabeto Alfabeto a ser setado no Automato.
    */   
    public void setAlfabeto(Alfabeto alfabeto);
    
    /**
    * Obtem o estado inicial do Automato.
    * @return  O estado inicial do Automato.
    */
    public EstadoInicial getEstadoInicial();
    
    /**
    * Seta o estado inicial do Automato.
    * @param  estadoInicial  a ser setado no Automato.
    */
    public void setEstadoInicial(EstadoInicial estadoInicial);

    /**
    * Obtem o conjunto de estados de aceitação(Finais) do Automato.
    * @return Conjunto de estados de aceitação(Finais).
    */
    public Set<EstadoFinal> getEstadosAceitacao();
    
    /**
    * Seta o conjunto de estados de aceitação(Finais) do Automato.
    * @param estadosAceitacao Conjunto de estados de aceitação(Finais) a ser setados.
    */
    public void setEstadosAceitacao(Set<EstadoFinal> estadosAceitacao);
    
    /**
    * Adiciona um novo estado final ao conjunto de estados de aceitação(Finais) do Automato.
    * @param estadoFinal estado final no qual se deseja adicionar ao conjunto de estados de aceitação(Finais).
    */
    public void addEstadoFinal(EstadoFinal estadoFinal);

    /**
    * Remove um estado final do conjunto de estados de aceitação(Finais) do Automato.
    * @param estadoFinal estado final no qual se deseja remover do conjunto de estados de aceitação(Finais).
    * @return Estado final que foi removido do conjunto de estados de aceitação(Finais).
    */
    public Estado removeEstadoFinal(EstadoFinal estadoFinal);

    /**
    * Obtem o mapa de transições do Automato.
    * @return Mapa de transições do Automato.
    */
    public Map<? extends Entrada, ? extends Estado> getTransicoes();

    /**
    * Obtem o estado alcançavel a partir de uma entrada.
    * @param entrada Chave para o mapa de transição, contém o estado atual e o simbolo.
    * @return Estado alcançavel a partir da Entrada recebida por paramêtro.
    */
    public Estado getEstadoTransicao(Entrada entrada);

    /**
    * Adiciona uma transição ao mapa de transições do Automato.
    * @param entrada chave para o mapa de transições.
    * @param destino estado alcançavel a partir da entrada.
    */
    public void addTransicao(Entrada entrada, Estado destino);
    
    /**
    * Verifica se existe uma determinda transição no mapa de transições.
    * @param de Estado de origem.
    * @param para estado alcançavel a partir do estado de origem.
    * @return boolean verdadeiro se existe a transição no mapa, falso caso contrario.
    */
    public boolean existeTransicao(Estado de, Estado para);

    /**
    * Verifica se existe uma determinda transição no mapa de transições.
    * @param de Estado de origem.
    * @param entrada Simbolo pelo qual se deseja verificar se existe transição.
    * @param para Estado alcançavel a partir do estado de origem.
    * @return boolean verdadeiro se existe a transição no mapa, falso caso contrario.
    */
    public boolean existeTransicao(Estado de, Simbolo entrada, Estado para);

}
