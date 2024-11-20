package model.dto;

import java.time.LocalDate;

public class UsuarioDTO {

	private String login;
	private String senha;
	private String nome;
	private String email;
	private LocalDate dataCadastro;
	
	
	public UsuarioDTO(String login, String senha, String nome, String email, LocalDate dataCadastro) {
		super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.dataCadastro = dataCadastro;
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	
}
