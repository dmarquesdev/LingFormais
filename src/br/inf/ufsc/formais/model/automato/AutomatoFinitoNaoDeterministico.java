package br.inf.ufsc.formais.model.automato;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import java.util.HashSet;

/**
 * Classe que representa um Automato Finito Não Deterministico.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AutomatoFinitoNaoDeterministico implements AutomatoFinito {

    /**
    * Conjunto de estados do Automato.
    */
    protected Set<Estado> estados;
    
    /**
    * Alfabeto do Automato.
    */
    protected Alfabeto alfabeto;
    
    /**
    * Estado inicial do Automato.
    */
    protected EstadoInicial estadoInicial;
    
    /**
    * Conjunto de estados de aceitação(Finais) do Automato.
    */
    protected Set<EstadoFinal> estadosAceitacao;
    
    /**
    * Mapa de transições do Automato.
    */
    protected Map<Entrada, Estados> transicoes;

    /**
    * Contrutor, inicializa todos os atributos da classe.
    * @param estados conjunto de estados que será inicializado o Automato.
    * @param alfabeto  alfabeto que será inicializado o Automato.
    * @param estadoInicial estado inicial que será inicializado o Automato.
    * @param estadosAceitacao conjunto de estados de aceitação(Finais) que será inicializado o Automato.
    * @param transicoes mapa de transições que será inicializado o Automato.
    */
    public AutomatoFinitoNaoDeterministico(Set<Estado> estados, Alfabeto alfabeto, EstadoInicial estadoInicial, Set<EstadoFinal> estadosAceitacao, Map<Entrada, Estados> transicoes) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.transicoes = transicoes;
    }

    /**
    * Contrutor vazio, inicializa os atributos da classe com valores padrão.
    */
    public AutomatoFinitoNaoDeterministico() {
        estados = new LinkedHashSet<>();
        estadosAceitacao = new LinkedHashSet<>();
        transicoes = new HashMap<>();
    }

    @Override
    public void addEstado(Estado estado) {
        estados.add(estado);
    }

    @Override
    public Set<Estado> getEstados() {
        return estados;
    }

    @Override
    public void setEstados(Set<Estado> estados) {
        this.estados = estados;
    }

    @Override
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    @Override
    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    @Override
    public EstadoInicial getEstadoInicial() {
        return estadoInicial;
    }

    @Override
    public void setEstadoInicial(EstadoInicial estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    @Override
    public Set<EstadoFinal> getEstadosAceitacao() {
        return estadosAceitacao;
    }

    @Override
    public void setEstadosAceitacao(Set<EstadoFinal> estadosAceitacao) {
        this.estadosAceitacao = estadosAceitacao;
    }
    
    /**
     * Seta o mapa de transições do Automato.
     * @param transicoes mapa de transições a ser setado.
     */
    public void setTransicoes(Map<Entrada, Estados> transicoes) {
        this.transicoes = transicoes;
    }

    @Override
    public void addEstadoFinal(EstadoFinal estado) {
        estadosAceitacao.add(estado);
    }

    @Override
    public Estado getEstadoTransicao(Entrada entrada) {
        return transicoes.get(entrada);
    }
    
    
    /**
     * Obtem o conjunto de estados alcançaveis dado uma entrada.
     * @param entrada chave para o mapa de transições.
     * @return O conjunto de estados alcançaveis a partir da entrada.
     */
    public Estados getEstadosTransicao(Entrada entrada) {
            if (transicoes.get(entrada) != null) {
                    return transicoes.get(entrada);
            } else {
                    return new Estados(new HashSet<Estado>());
            }
    }

    /**
     * Adicionar uma transição no mapa de transições do Automato.
     * @param entrada chave do mapa de transições.
     * @param destino Conjunto de estados do destino(Alcançavel).
     */
    public void addTransicao(Entrada entrada, Estados destino) {
        transicoes.put(entrada, destino);
    }

    @Override
    public Map<Entrada, Estados> getTransicoes() {
        return transicoes;
    }

    @Override
    public void removeEstado(Estado estado) {
        // TODO
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("M = (E,A,T,I,F)\n");

        out.append("E = {");
        for (Estado estado : estados) {
            out.append(estado.getId()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("}\n");

        out.append(alfabeto.toString()).append("\n");

        for (Entrada ent : transicoes.keySet()) { 
                Estados est = transicoes.get(ent);
                out.append("T(")
                        .append(ent.toString())
                        .append(") -> ").append(est.toString())
                        .append("\n");
            
        }

        out.append("\n");

        out.append("I = ").append(estadoInicial.getId()).append("\n");

        out.append("F = {");
        for (EstadoFinal estAceita : estadosAceitacao) {
            out.append(estAceita.getId()).append(", ");
        }
        out.delete(out.length() - 2, out.length());
        out.append("}\n");

        return out.toString();
    }

    @Override
    @Deprecated
    public void addTransicao(Entrada entrada, Estado destino) {
        // NÃO IMPLEMENTADO
    }

    @Override
    public Estado removeEstadoFinal(EstadoFinal estado) {
        
        Estado novo = new Estado(estado.getId());
        for (Entrada ent : transicoes.keySet()) {

            Estados ests = transicoes.get(ent);

            for (Estado est : ests.get()) {
                if (est.equals(estado)) {
                    ests.get().remove(estado);
                    ests.get().add(est);
                }
            }
            if (ent.getEstado().equals(estado)) {
                ent.setEstado(novo);
            }

        }
        estadosAceitacao.remove(estado);
        estados.remove(estado);
        estados.add(novo);
        return novo;
    }

    @Override
    public boolean existeTransicao(Estado de, Estado para) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeTransicao(Estado de, Simbolo entrada, Estado para) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Remove o estado inicial.
     * @return o estado inicial.
     */
    public Estado removeEstadoInicial() {
        Estado novo = new Estado(estadoInicial.getId());
        for (Entrada ent : transicoes.keySet()) {
            
            Estados ests = transicoes.get(ent);
            Estados apoio = new Estados();
            
            apoio.addEstado(transicoes.get(ent));
            
                for(Estado est : apoio.get()){
                    if(est.equals(estadoInicial)){
                        ests.get().remove(estadoInicial);
                        ests.get().add(est);
                    }
                }
            if (ent.getEstado().equals(estadoInicial)) {
                ent.setEstado(novo);
            }
            
        }
        
        estados.remove(estadoInicial);
        estados.add(novo);
        return novo;
    }
}
