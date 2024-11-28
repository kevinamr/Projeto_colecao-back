package controller;

import java.io.InputStream;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import model.bo.UsuarioBO;
import model.dao.UsuarioDAO;
import model.vo.UsuarioVO;

@Path("/usuario")
public class UsuarioController {

	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO cadastrarUsuarioController(InputStream usuarioInputStream) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.cadastrarUsuarioBO(usuarioInputStream);
	}

	
	@GET
	@Path("/consultar")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response consultarTodosUsuariosController() {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.consultarTodosUsuariosBO();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginUsuarioController(InputStream usuarioInputStream) {
		UsuarioBO usuarioBO = new UsuarioBO();
		try {
			UsuarioVO usuarioAutenticado = usuarioBO.loginUsuarioBO(usuarioInputStream);

			if (usuarioAutenticado != null && usuarioAutenticado.getIdUsuario() > 0) {
				return Response.status(Response.Status.OK).entity(usuarioAutenticado).build();
			} else {
				return Response.status(Response.Status.UNAUTHORIZED)
						.entity("{\"message\": \"Usuário inválido ou excluído.\"}").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\": \"Erro no servidor.\"}")
					.build();
		}
	}
	
	@GET
	@Path("/consultar/{idusuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response verificarCadastroUsuarioPorIDController(@PathParam("idusuario") int idUsuario) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.consultarUsuarioBO(idUsuario);
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarUsuarioController(UsuarioVO usuarioVO) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        
        boolean sucesso = usuarioDAO.atualizarUsuarioDAO(usuarioVO);

        if (sucesso) {
            return Response.ok(usuarioVO).build();  
        } else {
            return Response.status(Status.NOT_MODIFIED).entity("Falha ao atualizar usuário.").build();
        }
	}
	
	@DELETE
	@Path("/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean excluirUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.excluirUsuarioBO(usuarioVO);
	}
}
