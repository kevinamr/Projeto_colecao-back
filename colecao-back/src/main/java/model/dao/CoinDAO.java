package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.vo.CoinVO;

public class CoinDAO {

	public boolean verificarCadastroCoinBancoDAO(CoinVO coinVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT idCoin FROM moeda WHERE nome = '" + coinVO.getNome() + "'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao exercutar a query do método verificarCadastroMoedaBanco");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public CoinVO cadastrarCoinDAO(CoinVO coinVO) {
		String query = "INSERT INTO coin (imagem, nome, pais, ano, valor, detalhes) VALUES (?, ?, ?, ?, ?, ?)";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			pstmt.setBytes(1, coinVO.getImagem());
			pstmt.setString(2, coinVO.getNome());
			pstmt.setString(3, coinVO.getPais());
			pstmt.setInt(4, coinVO.getAno());
			pstmt.setDouble(5, coinVO.getValor());
			pstmt.setString(6, coinVO.getDetalhes());
			pstmt.execute();
			resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				coinVO.setIdCoin(resultado.getInt(1));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método cadastrarCoinDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return coinVO;
	}

	public ArrayList<CoinVO> consultarTodasCoinDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);

		ResultSet resultado = null;
		ArrayList<CoinVO> listaMoedas = new ArrayList<>();
		String query = "SELECT idCoin, imagem, nome, pais, ano, valor, detalhes FROM coin";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				CoinVO coin = new CoinVO();
				coin.setIdCoin(Integer.parseInt(resultado.getString(1)));
				coin.setImagem(resultado.getBytes(2));
				coin.setNome(resultado.getString(3));
				coin.setPais(resultado.getString(4));
				coin.setAno(Integer.parseInt(resultado.getString(5)));
				coin.setValor(Double.parseDouble(resultado.getString(6)));
				coin.setDetalhes(resultado.getString(7));
				listaMoedas.add(coin);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarTodasMoedasDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaMoedas;
	}

	public CoinVO consultarCoinBO(int idCoin) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String query = "SELECT idCoin, imagem, nome, pais, ano, valor, detalhes FROM coin WHERE idCoin = '"
				+ idCoin;

		CoinVO coin = new CoinVO();
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				coin.setIdCoin(Integer.parseInt(resultado.getString(1)));
				coin.setImagem(resultado.getBytes(2));
				coin.setNome(resultado.getString(3));
				coin.setPais(resultado.getString(4));
				coin.setAno(Integer.parseInt(resultado.getString(5)));
				coin.setValor(Double.parseDouble(resultado.getString(6)));
				coin.setDetalhes(resultado.getString(7));
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método consultarCoinDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return coin;
	}
	
	public boolean verificarCadastroCoinPorIDDAO(CoinVO coinVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELEC idMoeda FROM moeda WHERE idMoeda = " + coinVO.getIdCoin();
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método verificarCadastroMoedaPorID");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean atualizarCoinDAO(CoinVO coinVO) {
		boolean retorno = false;
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = null;

		String query;

		if (coinVO.getImagem() != null && coinVO.getImagem().length > 0) {
			query = "UPDATE coin SET imagem = ?, nome = ?, pais = ?, ano = ?, valor = ?, detalhes = ? WHERE idCoin = ?";
		} else {
			query = "UPDATE coin SET nome = ?, pais = ?, ano = ?, valor = ?, detalhes = ? WHERE idCoin = ?";
		}

		try {
			pstmt = Banco.getPreparedStatement(conn, query);

			if (coinVO.getImagem() != null && coinVO.getImagem().length > 0) {
				pstmt.setBytes(1, coinVO.getImagem());
				pstmt.setString(2, coinVO.getNome());
				pstmt.setString(3, coinVO.getPais());
				pstmt.setInt(4, coinVO.getAno());
				pstmt.setDouble(5, coinVO.getValor());
				pstmt.setString(6, coinVO.getDetalhes());
				pstmt.setInt(7, coinVO.getIdCoin());
			} else {
				pstmt.setString(1, coinVO.getNome());
				pstmt.setString(2, coinVO.getPais());
				pstmt.setInt(3, coinVO.getAno());
				pstmt.setDouble(4, coinVO.getValor());
				pstmt.setString(5, coinVO.getDetalhes());
				pstmt.setInt(6, coinVO.getIdCoin());
			}

			if (pstmt.executeUpdate() == 1) {
				retorno = true;
			}

		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método atualizarCoinDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public boolean excluirCoinDAO(CoinVO coinVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "DELETE FROM coin WHERE idCoin = " + coinVO.getIdCoin();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao executar a query do método excluirCoinDAO");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
