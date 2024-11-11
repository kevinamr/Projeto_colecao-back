package controller;

import java.util.ArrayList;

import model.bo.UsuarioBO;
import model.vo.UsuarioVO;


public class UsuarioController {

	private UsuarioBO usuarioBO = new UsuarioBO();
	
	public ArrayList<UsuarioVO> buscarTodosUsuarios(){
		return usuarioBO.buscarTodosProdutos();
	}
}
