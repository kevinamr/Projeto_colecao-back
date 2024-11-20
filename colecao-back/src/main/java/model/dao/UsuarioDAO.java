package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import model.vo.UsuarioVO;

public class UsuarioDAO {
	
	
	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) {
		String query = "INSERT INTO usuario (nome, email, login, senha, dataCadastro) VALUES (?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;

		try {
			pstmt.setString(1, usuarioVO.getNome());
			pstmt.setString(2, usuarioVO.getEmail());
			pstmt.setString(3, usuarioVO.getLogin());
			pstmt.setString(4, usuarioVO.getSenha());
			// Converte LocalDate para java.sql.Date:
			pstmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
			pstmt.executeUpdate();
			resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método cadastrarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}

		return usuarioVO;
	}

	public boolean verificarCadastroUsuarioBancoDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT idUsuario FROM usuario WHERE email = '" + usuarioVO.getEmail() + "'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarCadastroUsuarioBancoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public ArrayList<UsuarioVO> consultarTodosUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<UsuarioVO> listaUsuarios = new ArrayList<>();
		String query = "SELECT idUsuario, nome, email, login, senha, dataCadastro, dataExpiracao FROM usuario";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				UsuarioVO usuario = new UsuarioVO();
				usuario.setIdUsuario(resultado.getInt(1));
				usuario.setNome(resultado.getString(2));
				usuario.setEmail(resultado.getString(3));
				usuario.setLogin(resultado.getString(4));
				usuario.setSenha(resultado.getString(5));
				usuario.setDataCadastro(resultado.getDate(6).toLocalDate());
				usuario.setDataExpiracao(resultado.getDate(7).toLocalDate());
				listaUsuarios.add(usuario);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTodosUsuariosDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaUsuarios;
	}

	public UsuarioVO consultarUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		UsuarioVO usuario = null;
		String query = "SELECT idUsuario, nome, email, login, senha, dataCadastro, dataExpiracao FROM usuario WHERE idUsuario = "
				+ idUsuario;
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				usuario = new UsuarioVO();
				usuario.setIdUsuario(resultado.getInt(1));
				usuario.setNome(resultado.getString(2));
				usuario.setEmail(resultado.getString(3));
				usuario.setLogin(resultado.getString(4));
				usuario.setSenha(resultado.getString(5));
				usuario.setDataCadastro(resultado.getDate(6).toLocalDate());
				usuario.setDataExpiracao(resultado.getDate(7).toLocalDate());
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return usuario;
	}

	public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
		boolean retorno = false;
		Connection conn = Banco.getConnection();
		String query = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ?, dataExpiracao = ? WHERE idUsuario = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, usuarioVO.getNome());
			pstmt.setString(2, usuarioVO.getEmail());
			pstmt.setString(3, usuarioVO.getLogin());
			pstmt.setString(4, usuarioVO.getSenha());
			pstmt.setDate(5, java.sql.Date.valueOf(usuarioVO.getDataExpiracao()));
			pstmt.setInt(6, usuarioVO.getIdUsuario());
			if (pstmt.executeUpdate() == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		PreparedStatement pstmt = null;
		boolean retorno = false;
		String queryDelete = "DELETE FROM usuario WHERE idUsuario = " + usuarioVO.getIdUsuario();
		String queryUpdate = "UPDATE usuario SET dataExpiracao = ? WHERE idUsuario = ?";

		try {
			// Excluindo:
			if (stmt.executeUpdate(queryDelete) == 1) {
				pstmt = conn.prepareStatement(queryUpdate);
				pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
				pstmt.setInt(2, usuarioVO.getIdUsuario());

				// Atualizando data de expiração:
				pstmt.executeUpdate();
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro no método excluirUsuarioDAO");
			System.out.println(erro.getMessage());
		} finally {
			Banco.closeStatement(pstmt);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
