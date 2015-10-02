/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.inf.ufsc.formais.model.automato;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.Simbolo;
import br.inf.ufsc.formais.model.er.ExpressaoRegular;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Diego
 */
public class AutomatoFinitoNaoDeterministicoGeneralizado implements AutomatoFinito {

    protected Set<Estado> estados;
    protected Alfabeto alfabeto;
    protected EstadoInicial estadoInicial;
    protected Set<EstadoFinal> estadosAceitacao;
    protected Map<EntradaAFNDG, Estados> transicoes;

    public AutomatoFinitoNaoDeterministicoGeneralizado(Set<Estado> estados, Alfabeto alfabeto,
            EstadoInicial estadoInicial, Set<EstadoFinal> estadosAceitacao,
            Map<EntradaAFNDG, Estados> transicoes) {
        this.estados = estados;
        this.alfabeto = alfabeto;
        this.estadoInicial = estadoInicial;
        this.estadosAceitacao = estadosAceitacao;
        this.transicoes = transicoes;
    }

    public AutomatoFinitoNaoDeterministicoGeneralizado() {
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

    @Override
    public void addEstadoFinal(EstadoFinal estado) {
        estadosAceitacao.add(estado);
    }

    public EstadoFinal getEstadoFinal() {
        return estadosAceitacao.toArray(new EstadoFinal[1])[0];
    }

    @Override
    @Deprecated
    public Estado getEstadoTransicao(Entrada entrada) {
        // TODO
        return null;
    }

    public Estado getEstadoTransicao(EntradaAFNDG entrada) {
        return transicoes.get(entrada);
    }

    @Override
    @Deprecated
    public void addTransicao(Entrada entrada, Estado destino) {
        // TODO
    }

    @Override
    public void removeEstado(Estado k) {
        ExpressaoRegular kk = getExpressaoRegular(k, k);
        ExpressaoRegular erAnt = null;
        boolean nd = false;
        Map<EntradaAFNDG, Estados> novasTransicoes = new LinkedHashMap<>();
        for (Estado i : estados) {
            if (!i.equals(k)) {
                if (!i.equals(estadoInicial) && !i.equals(getEstadoFinal())) {
                    ExpressaoRegular ii = getExpressaoRegular(i, i);
                    ExpressaoRegular ik = getExpressaoRegular(i, k);
                    ExpressaoRegular ki = getExpressaoRegular(k, i);
                    ik.concatenarERFecho(kk);
                    ik.concatenar(ki);
                    ii.alternancia(ik);
                    
                    EntradaAFNDG novaEntrada = new EntradaAFNDG(i, ii);
                    Estados novoEstados = new Estados();
                    novoEstados.addEstado(i);
                    novasTransicoes.put(novaEntrada, novoEstados);
                    System.out.println(i.toString() + ", " + i.toString() + " -> " + ii.toString());
                }

                for (Estado j : estados) {
                    if (!j.equals(k) && !j.equals(i)) {
                        if (!j.equals(estadoInicial) && !i.equals(getEstadoFinal())) {
                            ExpressaoRegular ij = getExpressaoRegular(i, j);
                            ExpressaoRegular ik = getExpressaoRegular(i, k);
                            ExpressaoRegular kj = getExpressaoRegular(k, j);
                            ik.concatenarERFecho(kk);
                            ik.concatenar(kj);
                            ij.alternancia(ik);
                            
                            EntradaAFNDG novaEntrada = new EntradaAFNDG(i, ij);
                            Estados novoEstados = transicoes.get(novaEntrada);
                            if(novoEstados == null) {
                                novoEstados = new Estados();
                            }
                            novoEstados.addEstado(j);
                            novasTransicoes.put(novaEntrada, novoEstados);
                            System.out.println(i.toString() + ", " + j.toString() + " -> " + ij.toString());
                        }
                    }
                }
            }
        }

        estados.remove(k);
        transicoes = novasTransicoes;

    }

    @Override
    public Map<EntradaAFNDG, Estados> getTransicoes() {
        return transicoes;
    }

    @Override
    @Deprecated
    public Estado removeEstadoFinal(EstadoFinal estado) {
        // NÃO IMPLEMENTADO
        return null;
    }

    @Override
    public boolean existeTransicao(Estado de, Estado para) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean existeTransicao(Estado de, Simbolo entrada, Estado para) {
        EntradaAFNDG ent = new EntradaAFNDG(de, entrada);
        return (transicoes.get(ent) != null);
    }

    public ExpressaoRegular getExpressaoRegular(Estado de, Estado para) {
        for (EntradaAFNDG entrada : transicoes.keySet()) {
            if (entrada.getEstado().equals(de)
                    && transicoes.get(entrada).get().contains(para)) {
                return entrada.getExpressaoRegular().clone();
            }
        }

        return null;
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

        for (EntradaAFNDG ent : transicoes.keySet()) {
            for (Estado estado : transicoes.get(ent).get()) {
                out.append("T(")
                        .append(ent.toString())
                        .append(") -> ").append(estado.toString())
                        .append("\n");
            }
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

}
