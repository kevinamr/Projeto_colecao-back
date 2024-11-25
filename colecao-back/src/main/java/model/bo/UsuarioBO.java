package model.bo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

public class UsuarioBO {
	
	private String converterInputStreamParaString(InputStream inputStream) throws IOException {
		return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
	}

	public UsuarioVO cadastrarUsuarioBO(InputStream usuarioInputStream) {
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = null;
		try {
			String usuarioJSON = this.converterInputStreamParaString(usuarioInputStream);

			// Converte JSON em objeto JAVA:
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioJSON, UsuarioVO.class);

			if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
				System.out.println("Usuário já cadastrado no banco de dados!");
			} else {
				usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Response consultarTodosUsuariosBO() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ArrayList<UsuarioVO> listaUsuarioVO = usuarioDAO.consultarTodosUsuariosDAO();
		if (listaUsuarioVO.isEmpty()) {
			System.out.println("\nLista de usuários está vazia");
			return Response.status(Response.Status.NO_CONTENT).entity("Nenhum usuário encontrado").build();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {
			String usuariosJson = objectMapper.writeValueAsString(listaUsuarioVO);
			return Response.ok(usuariosJson).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar a resposta JSON")
					.build();
		}
	}
	
	public Response consultarUsuarioBO(int idUsuario) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = usuarioDAO.consultarUsuarioDAO(idUsuario);
		if (usuarioVO == null) {
			System.out.println("\nObjeto Usuario está vazio");
			return Response.status(Response.Status.NO_CONTENT).entity("Nenhum usuário encontrado").build();
		}

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules();

		try {
			String usuarioJson = objectMapper.writeValueAsString(usuarioVO);
			return Response.ok(usuarioJson).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao processar a resposta JSON")
					.build();
		}
	}
	
	public Boolean atualizarUsuarioBO(InputStream usuarioInputStream) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		UsuarioVO usuarioVO = null;
		try {
			String usuarioJson = this.converterInputStreamParaString(usuarioInputStream);

			// Converte JSON em objeto JAVA
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioJson, UsuarioVO.class);

			if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
				resultado = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
			} else {
				System.out.println("Usuário não consta na base de dados");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return resultado;
	}
	
	public Boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
			resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
		} else {
			System.out.println("\nUsuário não existe na base de dados");
		}
		return resultado;
	}
}
