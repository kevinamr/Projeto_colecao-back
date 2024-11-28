package model.bo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioInputStream, UsuarioVO.class);

			if (usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
				System.out.println("Usuário já cadastrado no banco de dados!");
			} else {
				usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return usuarioVO;
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
	
	public Response atualizarUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            if (!usuarioDAO.verificarCadastroUsuarioBancoDAO(usuarioVO)) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não encontrado no banco de dados.")
                        .build();
            }

            boolean sucesso = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
            if (sucesso) {
                return Response.status(Response.Status.OK)
                        .entity("Usuário atualizado com sucesso.")
                        .build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("Erro ao atualizar usuário.")
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a atualização do usuário.")
                    .build();
        }
    }
	
	public Boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean sucesso = usuarioDAO.excluirUsuarioDAO(usuarioVO);
        
        if (sucesso) {
            System.out.println("Usuário excluído com sucesso no BO.");
        } else {
            System.out.println("Falha ao excluir o usuário no BO.");
        }

        return sucesso;
	}

	public UsuarioVO loginUsuarioBO(InputStream usuarioInputStream) {
		System.out.println("Entrou no BO: loginUsuarioBO");
		UsuarioVO usuarioVO = null;

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.findAndRegisterModules();
			usuarioVO = objectMapper.readValue(usuarioInputStream, UsuarioVO.class);

			if (usuarioVO.getLogin() == null || usuarioVO.getSenha() == null) {
				throw new IllegalArgumentException("Login e senha são obrigatórios.");
			}

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			UsuarioVO usuarioAutenticado = usuarioDAO.loginUsuarioDAO(usuarioVO);

			if (usuarioAutenticado != null && usuarioAutenticado.getIdUsuario() > 0) {
				return usuarioAutenticado;
			} else {
				System.out.println("Usuário ou login inválidos.");
				return null;
			}
		} catch (IOException e) {
			System.out.println("Erro ao processar dados de entrada no loginUsuarioBO: " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}
}







