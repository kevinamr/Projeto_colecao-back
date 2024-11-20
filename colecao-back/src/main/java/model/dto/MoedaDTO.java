package model.dto;

import java.time.LocalDateTime;

public class MoedaDTO {
	
	private int idMoeada;
	private int idUsuario;
	private String nome;
	private int ano;
	private double valor;
	private String detalhes;
	private LocalDateTime dataCadastro;
	
	
	
	public MoedaDTO(int idMoeada, int idUsuario, String nome, int ano, double valor, String detalhes,
			LocalDateTime dataCadastro) {
		super();
		this.idMoeada = idMoeada;
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.ano = ano;
		this.valor = valor;
		this.detalhes = detalhes;
		this.dataCadastro = dataCadastro;
	}
	
	public int getIdMoeada() {
		return idMoeada;
	}
	public void setIdMoeada(int idMoeada) {
		this.idMoeada = idMoeada;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public String getDetalhes() {
		return detalhes;
	}
	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}
	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	
}
