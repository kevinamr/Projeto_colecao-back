package model.bo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import model.dao.CoinDAO;
import model.vo.CoinVO;



public class CoinBO {
	
	private byte[] converterByteParaArray(InputStream inputStream) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int read = 0;
		byte[] dados = new byte[1024];
		while ((read = inputStream.read(dados, 0, dados.length)) != -1) {
			buffer.write(dados, 0, read);
		}
		buffer.flush();
		return buffer.toByteArray();
	}

	public CoinVO cadastrarCoinBO(InputStream moedaInputStream, InputStream fileInputStream,
			FormDataContentDisposition fileMetaData) {
		CoinDAO coinDAO = new CoinDAO();
		CoinVO coinVO = null;
		try {
			byte[] arquivo = this.converterByteParaArray(fileInputStream); // Lê o conteúdo do arquivo

			String coinJson = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8); // Lê
																													// o
																													// JSON

			// Converte JSON em objeto JAVA:
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			coinVO = objectMapper.readValue(coinJson, CoinVO.class);
			coinVO.setImagem(arquivo);

			if (coinDAO.verificarCadastroCoinBancoDAO(coinVO)) {
				System.out.println("Moeda já cadastrada no banco de dados!");
			} else {
				coinVO = coinDAO.cadastrarCoinDAO(coinVO);
			}
		} catch (FileNotFoundException erro) {
			System.out.println(erro);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return coinVO;

	}

	public Response consultarTodasCoinBO() {
		CoinDAO coinDAO = new CoinDAO();
		ArrayList<CoinVO> listaCoinVO = coinDAO.consultarTodasCoinDAO();
		if (listaCoinVO.isEmpty()) {
			System.out.println("\nLista de Coins está vazia");
			return Response.status(Response.Status.NO_CONTENT).entity("Nenhuma Coin foi encontrada").build();
		}

		MultiPart multiPart = new FormDataMultiPart();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {
			for (CoinVO coinVO : listaCoinVO) {
				byte[] imagem = coinVO.getImagem();
				coinVO.setImagem(null);

				String moedaJson = objectMapper.writeValueAsString(coinVO);
				multiPart.bodyPart(new StreamDataBodyPart("coinVO", new ByteArrayInputStream(moedaJson.getBytes()),
						coinVO.getIdCoin() + "-coin.json"));

				if (imagem != null) {
					multiPart.bodyPart(new StreamDataBodyPart("imagem", new ByteArrayInputStream(imagem),
							coinVO.getIdCoin() + "-imagem.jpg"));
				}
			}
			return Response.ok(multiPart).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao processar a resposta multipart").build();
		}
	}

	public Response consultarCoinBO(int idCoin) {
		CoinDAO coinDAO = new CoinDAO();
		CoinVO coinVO = coinDAO.consultarCoinBO(idCoin);
		if (coinVO == null) {
			System.out.println("\nObjeto Moeda está vazio");
			return Response.status(Response.Status.NO_CONTENT).entity("Nenhuma Coin foi encontrada").build();
		}

		MultiPart multiPart = new FormDataMultiPart();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {
			byte[] imagem = coinVO.getImagem();
			coinVO.setImagem(imagem);

			// Adiciona JSON da moeda:
			String coinJson = objectMapper.writeValueAsString(coinVO);
			multiPart.bodyPart(new StreamDataBodyPart("coinVO", new ByteArrayInputStream(coinJson.getBytes()),
					coinVO.getIdCoin() + "-coin.json"));

			// Adiciona a imagem como um arquivo:
			if (imagem != null) {
				multiPart.bodyPart(new StreamDataBodyPart("imagem", new ByteArrayInputStream(imagem),
						coinVO.getIdCoin() + "-imagem.jpg"));
			}
			return Response.ok(multiPart).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao processar a resposta multipart").build();
		}
	}

	public Boolean atualizarCoinBOs(InputStream moedaInputStream, InputStream fileInputStream,
			FormDataContentDisposition fileMetaData) {
	boolean resultado = false;
	CoinDAO coinDAO = new CoinDAO();
	CoinVO coinVO = null;
	try {
		// Lê o conteudo do arquivo:
		byte[] arquivo = null;
		if (fileInputStream != null) {
			arquivo = this.converterByteParaArray(fileInputStream);
		}

		// Lê conteúdo do JSON:
		String coinJson = new String(this.converterByteParaArray(moedaInputStream), StandardCharsets.UTF_8);

		// Converte JSON em objeto java
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();
		coinVO = objectMapper.readValue(coinJson, CoinVO.class);

		if (arquivo.length > 0) {
			coinVO.setImagem(arquivo);
		}

		if (coinDAO.verificarCadastroCoinBancoDAO(coinVO)) {
			resultado = coinDAO.atualizarCoinDAO(coinVO);
		} else {
			System.out.println("Moeda não consta na base de dados");
		}

	} catch (FileNotFoundException erro) {
		System.out.println(erro);
	} catch (IOException e) {
		e.printStackTrace();
	}
	return resultado;
	}

	public Boolean excluirCoinBO(CoinVO coinVO) {
		boolean resultado = false;
		CoinDAO coinDAO = new CoinDAO();
		if (coinDAO.verificarCadastroCoinPorIDDAO(coinVO)) {
			resultado = coinDAO.excluirCoinDAO(coinVO);
		} else {
			System.out.println("\nCoin não existe na base de dados");
		}
		return resultado;
	}

}
