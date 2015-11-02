package br.inf.ufsc.formais.model.automato;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import br.inf.ufsc.formais.model.Alfabeto;
import br.inf.ufsc.formais.model.CadeiaAutomato;
import br.inf.ufsc.formais.model.Simbolo;

/**
 * Classe que representa um Automato Finito Deterministico.
 *
 * @author Diego Marques
 * @author Matheus Demetrio
 * @author Nathan Molinari
 */
public class AutomatoFinitoDeterministico implements AutomatoFinito {

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
	protected Map<Entrada, Estado> transicoes;

	/**
	 * Contrutor, inicializa todos os atributos da classe.
	 * 
	 * @param estados
	 *            conjunto de estados que será inicializado o Automato.
	 * @param alfabeto
	 *            alfabeto que será inicializado o Automato.
	 * @param estadoInicial
	 *            estado inicial que será inicializado o Automato.
	 * @param estadosAceitacao
	 *            conjunto de estados de aceitação(Finais) que será inicializado o Automato.
	 * @param transicoes
	 *            mapa de transições que será inicializado o Automato.
	 */
	public AutomatoFinitoDeterministico(Set<Estado> estados, Alfabeto alfabeto, EstadoInicial estadoInicial, Set<EstadoFinal> estadosAceitacao, Map<Entrada, Estado> transicoes) {
		this.estados = estados;
		this.alfabeto = alfabeto;
		this.estadoInicial = estadoInicial;
		this.estadosAceitacao = estadosAceitacao;
		this.transicoes = transicoes;
	}

	/**
	 * Contrutor vazio, inicializa os atributos da classe com valores padrão.
	 */
	public AutomatoFinitoDeterministico() {
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

	@Override
	public Estado getEstadoTransicao(Entrada entrada) {
		return transicoes.get(entrada);
	}

	@Override
	public void addTransicao(Entrada entrada, Estado destino) {
		transicoes.put(entrada, destino);
	}

	@Override
	public void removeEstado(Estado estado) {
		// TODO
	}

	@Override
	public Map<Entrada, Estado> getTransicoes() {
		return transicoes;
	}

	@Override
	public boolean existeTransicao(Estado de, Estado para) {
		for (Simbolo s : alfabeto.getSimbolos()) {
			Entrada ent = new Entrada(de, s);
			if (transicoes.get(ent) != null) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean existeTransicao(Estado de, Simbolo entrada, Estado para) {
		Entrada ent = new Entrada(de, entrada);
		return (transicoes.get(ent) != null && transicoes.get(ent).equals(para));
	}
	
	public boolean existeTransicao(Entrada entrada){
		return this.transicoes.get(entrada) != null;
	}

	@Override
	public Estado removeEstadoFinal(EstadoFinal estado) {
		Estado novo = new Estado(estado.getId());
		for (Entrada ent : transicoes.keySet()) {
			if (ent.getEstado().equals(estado)) {
				ent.setEstado(novo);
			}

			if (transicoes.get(ent).equals(estado)) {
				// transicoes.replace(ent, novo);
			}
		}
		estadosAceitacao.remove(estado);
		estados.remove(estado);
		estados.add(novo);
		return novo;
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
			out.append("T(").append(ent.toString()).append(") -> ").append(transicoes.get(ent).toString()).append("\n");
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

	public void removeUnreachableStates() {
		Estados unreachableStates = getUnreachableStates();
		removeUnreachableStates(unreachableStates);
	}

	private void removeUnreachableStates(Estados unreachableStates) {
		if(unreachableStates.isEmpty()){
			return;
		}
		
		this.estados.removeAll(unreachableStates.get());
		this.estadosAceitacao.removeAll(unreachableStates.get());
		
		Set<Entrada> temp = new HashSet<Entrada>(this.transicoes.keySet());
		for (Entrada entrada : temp) {
			if (unreachableStates.get().contains(entrada.getEstado())) {
				this.transicoes.remove(entrada);
			}
		}
	}
	
	public void removeDeadStates(){
		Estados deadStates = getDeadStates();
		
		if(deadStates.isEmpty()){
			return;
		}
		
		this.estados.removeAll(deadStates.get());
		
		Set<Entrada> temp = new HashSet<Entrada>(this.transicoes.keySet());
		for (Entrada entrada : temp) {
			Estado alcancavel = this.transicoes.get(entrada);
	
			if (deadStates.get().contains(entrada.getEstado()) || deadStates.get().contains(alcancavel)) {
				this.transicoes.remove(entrada);
			}
		}	
	}

	private Estados getUnreachableStates() {
		Estados estadosAlcancaveis = new Estados();
		estadosAlcancaveis.addEstado(this.estadoInicial);
		Estados novosEstados = new Estados();
		novosEstados.addEstado(this.estadoInicial);

		while (!novosEstados.isEmpty()) {
			Estados temp = new Estados();

			for (Estado estadoAtual : novosEstados.get()) {
				for (Simbolo simboloAtual : this.alfabeto.getSimbolos()) {
					Entrada entrada = new Entrada(estadoAtual, simboloAtual);
					temp.addEstado(this.getEstadoTransicao(entrada));
				}
			}
			novosEstados = diferenca(temp, estadosAlcancaveis);
			estadosAlcancaveis = uniao(estadosAlcancaveis, novosEstados);
		}
		Estados estadosInalcancaveis = new Estados();
		Estados estadosAFD = new Estados(this.estados);
		estadosInalcancaveis = diferenca(estadosAFD, estadosAlcancaveis);
		return estadosInalcancaveis;

	}

	private Estados getDeadStates() {
		Estados deadStates = new Estados();

		for (Estado estado : this.estados) {
			Estados estadosAlcancaveis = new Estados();
			for (Simbolo simbolo : this.getAlfabeto().getSimbolos()) {
				Entrada entrada = new Entrada(estado, simbolo);
				estadosAlcancaveis.addEstado(this.transicoes.get(entrada));
			}

			Estados estadoAtual = new Estados();
			estadoAtual.addEstado(estado);
			if (estadoAtual.equals(estadosAlcancaveis)) {
				deadStates.addEstado(estado);
			}
		}
		deadStates.get().removeAll(this.estadosAceitacao);
		return deadStates;
	}
	

	private Estados uniao(Estados one, Estados other) {
		LinkedHashSet<Estado> uniao = new LinkedHashSet<Estado>();
		uniao.addAll(one.get());
		uniao.addAll(other.get());
		return new Estados(uniao);
	}

	private Estados diferenca(Estados one, Estados other) {
		LinkedHashSet<Estado> diferenca = new LinkedHashSet<Estado>();
		diferenca.addAll(one.get());
		diferenca.removeAll(other.get());
		return new Estados(diferenca);
	}
	
	public Estado computar(CadeiaAutomato cadeia){
		Estado estadoAtual = this.estadoInicial;
		Estado proximoEstado = new Estado("");
		
		for(Simbolo simbolo : cadeia.getSimbolos()){
			Entrada entrada = new Entrada(estadoAtual, simbolo);
			if(!existeTransicao(entrada)){
				//	lança exception
			}
			estadoAtual = transicoes.get(entrada);
		}
		if(!isFinalState(estadoAtual)){
			//lança exception
		}
		return estadoAtual;	
	}
	
	private boolean isFinalState(Estado estado){
		return this.estadosAceitacao.contains(estado);
	}

}
