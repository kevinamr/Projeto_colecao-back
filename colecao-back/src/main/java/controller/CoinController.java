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


@Path("/coin")
public class CoinController {
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public CoinVO cadastrarCoinController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("coinVO") InputStream coinInputStream) throws Exception {
		CoinBO coinBO = new CoinBO();
		return coinBO.cadastrarCoinBO(coinInputStream, fileInputStream, fileMetaData);
	}

	
	@GET
	@Path("/consultar")
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response consultarTodasCoinsController() {
		CoinBO coinBO = new CoinBO();
		return coinBO.consultarTodasCoinBO();
	}
	
	@GET
	@Path("/pesquisar/{idcoin}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response verificarCadastroCoinPorIDController(@PathParam("idcoin") int idCoin) {
		CoinBO coinBO = new CoinBO();
		return coinBO.consultarCoinBO(idCoin);
	}
	
	@PUT
	@Path("/atualizar")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean atualizarCoinController(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData,
			@FormDataParam("coinVO") InputStream coinInputStream) throws Exception {
		CoinBO coinBO = new CoinBO();
		return coinBO.atualizarCoinBOs(coinInputStream, fileInputStream, fileMetaData);
	}
	
	@DELETE
	@Path("/excluir")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean excluirCoinController(CoinVO coinVO) {
		CoinBO coinBO = new CoinBO();
		return coinBO.excluirCoinBO(coinVO);
	}
}
