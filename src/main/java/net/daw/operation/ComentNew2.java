/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jordi Eslava Barrera
 */
package net.daw.operation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.daw.bean.ComentBean;
import net.daw.dao.ComentDao;
import net.daw.helper.Contexto;
import net.daw.parameter.ComentParam;

public class ComentNew2 implements Operation {

    @Override
    public Object execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Contexto oContexto = (Contexto) request.getAttribute("contexto");
        switch (oContexto.getSearchingFor()) {
            case "usuario": {
                oContexto.setVista("jsp/usuario/list.jsp");
                oContexto.setClase("usuario");
                oContexto.setMetodo("list");
                oContexto.setFase("1");
                oContexto.setSearchingFor("usuario");
                oContexto.setClaseRetorno("coment");
                oContexto.setMetodoRetorno("new");
                oContexto.setFaseRetorno("1");
                oContexto.removeParam("id_usuario");
                UsuarioList1 oOperacion = new UsuarioList1();
                return oOperacion.execute(request, response);
            }
            case "documento": {
                oContexto.setVista("jsp/documento/list.jsp");
                oContexto.setClase("documento");
                oContexto.setMetodo("list");
                oContexto.setFase("1");
                oContexto.setSearchingFor("documento");
                oContexto.setClaseRetorno("coment");
                oContexto.setMetodoRetorno("new");
                oContexto.setFaseRetorno("1");
                oContexto.removeParam("id_documento");
                DocumentoList1 oOperacion = new DocumentoList1();
                return oOperacion.execute(request, response);
            }
            default: {
                oContexto.setVista("jsp/mensaje.jsp");
                ComentBean oComentBean = new ComentBean();
                ComentDao oComentDao = new ComentDao(oContexto.getEnumTipoConexion());
                ComentParam oComentParam = new ComentParam(request);
                oComentBean = oComentParam.loadId(oComentBean);
                try {
                    oComentBean = oComentParam.load(oComentBean);
                } catch (NumberFormatException e) {
                    return "Tipo de dato incorrecto en uno de los campos del formulario";
                }
                try {
                    oComentDao.set(oComentBean);
                } catch (Exception e) {
                    throw new ServletException("ComentController: Update Error: Phase 2: " + e.getMessage());
                }
                String strMensaje = "Se ha añadido la información del comentario con id=" + Integer.toString(oComentBean.getId()) + "<br />";
                strMensaje += "<a href=\"Controller?class=coment&method=view&id=" + oComentBean.getId() + "\">Ver Comentario creado en el formulario</a><br />";
                return strMensaje;
            }
        }
    }
}
