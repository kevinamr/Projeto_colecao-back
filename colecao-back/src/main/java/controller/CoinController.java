package controller;

import java.io.InputStream;

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
import model.bo.CoinBO;
import model.vo.CoinVO;



public class CoinController {
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public CoinVO cadastrarMoedaController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("coinBO") InputStream moedaInputStream) throws Exception {
		CoinBO coinBO = new CoinBO();
		return coinBO.cadastrarCoinBO(moedaInputStream, fileInputStream, fileMetaData);
	}

	
	@GET
	@Path("/consultar")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response consultarTodasMoedasController() {
		CoinBO coinBO = new CoinBO();
		return coinBO.consultarTodasCoinBO();
	}
	
	@GET
	@Path("/pesquisar/{Id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response verificarCadastroMoedaPorIDController(@PathParam("udcoin") int idCoin) {
		CoinBO coinBO = new CoinBO();
		return coinBO.consultarCoinBO(idCoin);
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Boolean atualizarMoedaController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("usuarioVO") InputStream moedaInputStream) throws Exception {
		CoinBO coinBO = new CoinBO();
		return coinBO.atualizarCoinBOs(moedaInputStream, fileInputStream, fileMetaData);
	}
	
	@DELETE
	@Path("/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Boolean excluirMoedaController(CoinVO coinVO) {
		CoinBO coinBO = new CoinBO();
		return coinBO.excluirCoinBO(coinVO);
	}
}
