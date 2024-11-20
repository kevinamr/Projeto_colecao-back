package model.vo;

import java.time.LocalDate;

public class CoinVO {
	
	private int idCoin;
	private int idUsuario;
	private String nome;
	private String pais;
	private int ano;
	private double valor;
	private String detalhes;
	private LocalDate dataCadastro;
	private byte[] imagem;

	public CoinVO(int idCoin, int idUsuario, String nome, String pais, int ano, double valor, String detalhes,
			LocalDate dataCadastro, byte[] imagem) {
		super();
		this.idCoin = idCoin;
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.pais = pais;
		this.ano = ano;
		this.valor = valor;
		this.detalhes = detalhes;
		this.dataCadastro = dataCadastro;
		this.imagem = imagem;
	}
	
	public CoinVO() {
		super();
	}

	public int getIdCoin() {
		return idCoin;
	}
	public void setIdCoin(int idCoin) {
		this.idCoin = idCoin;
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
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
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
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public byte[] getImagem() {
		return imagem;
	}
	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	

}
