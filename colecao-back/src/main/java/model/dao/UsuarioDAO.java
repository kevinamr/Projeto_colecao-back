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
		PreparedStatement pstmt = null;
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT COUNT(*) FROM usuario WHERE idUsuario = ?";
		try {
			pstmt = Banco.getPreparedStatement(conn, query);
			pstmt.setInt(1, usuarioVO.getIdUsuario());
			resultado = pstmt.executeQuery();
			
			if (resultado.next()) {
				retorno = resultado.getInt(1) > 0;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarCadastroUsuarioBancoDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public ArrayList<UsuarioVO> consultarTodosUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<UsuarioVO> listaUsuarios = new ArrayList<>();
		String query = "SELECT idUsuario, nome, email, login, senha FROM usuario";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				UsuarioVO usuario = new UsuarioVO();
				usuario.setIdUsuario(resultado.getInt(1));
				usuario.setNome(resultado.getString(2));
				usuario.setEmail(resultado.getString(3));
				usuario.setLogin(resultado.getString(4));
				usuario.setSenha(resultado.getString(5));
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
		String query = "SELECT idUsuario, nome, email, login, senha FROM usuario WHERE idUsuario = " + idUsuario;
		
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				usuario = new UsuarioVO();
				usuario.setIdUsuario(resultado.getInt(1));
				usuario.setNome(resultado.getString(2));
				usuario.setEmail(resultado.getString(3));
				usuario.setLogin(resultado.getString(4));
				usuario.setSenha(resultado.getString(5));

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
		String query = "UPDATE usuario SET nome = ?, email = ?, login = ?, senha = ? WHERE idUsuario = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		try {
			pstmt.setString(1, usuarioVO.getNome());
			pstmt.setString(2, usuarioVO.getEmail());
			pstmt.setString(3, usuarioVO.getLogin());
			pstmt.setString(4, usuarioVO.getSenha());
			pstmt.setInt(5, usuarioVO.getIdUsuario());
			
			int linhasAfetadas = pstmt.executeUpdate();
			retorno = (linhasAfetadas > 0);
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarUsuarioDAO");
			System.out.println("Erro: " + erro.getMessage());
		}
		return retorno;
	}

	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
		boolean retorno = false;
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;

		String query = "UPDATE usuario SET dataExpiracao = ? WHERE idUsuario = ?";
		pstmt = Banco.getPreparedStatement(conn, query);

		try {
			System.out.println("Executando query: " + query);
			pstmt.setObject(1, LocalDate.now());
			pstmt.setInt(2, usuarioVO.getIdUsuario());
			if (pstmt.executeUpdate() == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirUsuarioDAO");
			System.out.println("Erro: " + erro);
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public UsuarioVO loginUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;
		ResultSet resultado = null;

		String query = "SELECT idUsuario, login, senha FROM usuario WHERE login = ? AND senha = ? AND dataExpiracao IS NULL";
		try {
			pstmt = Banco.getPreparedStatement(conn, query);
			pstmt.setString(1, usuarioVO.getLogin());
			pstmt.setString(2, usuarioVO.getSenha());

			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				usuarioVO.setIdUsuario(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao verificar a query do método loginUsuarioDAO");
			System.out.println("Erro: " + erro);
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return usuarioVO;

	}

}
