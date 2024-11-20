package controller;

import java.io.InputStream;
import java.util.ArrayList;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

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
import model.bo.UsuarioBO;
import model.vo.UsuarioVO;


public class UsuarioController {

	private UsuarioBO usuarioBO = new UsuarioBO();
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public UsuarioVO cadastrarUsuarioController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("usuarioVO") InputStream usuarioInputStream) throws Exception {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.cadastrarUsuarioBO(usuarioInputStream, fileInputStream, fileMetaData);
	}

	
	@GET
	@Path("/Consultar")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response consultarTodosUsuariosController() {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.consultarTodosUsuarioBO();
	}
	
	@GET
	@Path("/Consultar/{Id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response verificarCadastroUsuarioPorIDController(@PathParam("idusuario") int idUsuario) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.consultarUsuarioBO(idUsuario);
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Boolean atualizarUsuarioController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("usuarioVO") InputStream usuarioInputStream) throws Exception {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.atualizarUsuarioBO(usuarioInputStream, fileInputStream, fileMetaData);
	}
	
	@DELETE
	@Path("/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Boolean excluirUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.excluirUsuarioBO(usuarioVO);
	}
}
