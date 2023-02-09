/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.unicauca.apliweb.control;

import edu.unicauca.apliweb.persistence.entities.Pedido;
import edu.unicauca.apliweb.persistence.jpa.PedidoJpaController;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jnico
 */
@WebServlet("/pedido")
public class ServletAppPedido extends HttpServlet {
    private PedidoJpaController pedidoJPA;
    private final static String PU = "edu.unicauca.apliweb_Marketplace_war_1.0PU";

    @Override
    public void init() throws ServletException {
        super.init();
        //creamos una instancia de la clase EntityManagerFactory
        //esta clase se encarga de gestionar la construcción de entidades y
        //permite a los controladores JPA ejecutar las operaciones CRUD
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU);
        //creamos una instancia del controlador JPA para la clase productos y le
        //pasamos el gestor de entidades
        pedidoJPA = new PedidoJpaController(emf);
        //esta parte es solamente para realizar la prueba:
        //listamos todos los clientes de la base de datos y los imprimimos en consola
        List<Pedido> listaPedido = pedidoJPA.findPedidoEntities();
        //imprimimos los clientes en consola
        for (Pedido pedido : listaPedido) {
            System.out.println("Cliente: " + pedido.getIdCliente().getNombre() + ", Producto: " + pedido.getIdProducto().getNombre() +
                    ", Cantidad: " + pedido.getCantidad() + ", Subtotal: " + pedido.getSubtotal());
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Awayhub, Controla tus pedidos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Pedidos en el Servlet " + request.getContextPath() + "</h1>");
            List<Pedido> listaPedidos = pedidoJPA.findPedidoEntities();
            for (Pedido pedido : listaPedidos) {
                out.println("Cliente: " + pedido.getIdCliente().getNombre() + ", Producto: " + pedido.getIdProducto().getNombre() +
                        ", Cantidad: " + pedido.getCantidad() + ", Subtotal: " + pedido.getSubtotal() + "<br>");
            }
            out.println("</body>");
            out.println("</html>");
            String action = request.getServletPath();
            switch (action) {
                case "/new": //Muestra el formulario para crear un nuevo cliente
                    showNewForm(request, response);
                    break;
                case "/insert": //ejecuta la creación de un nuevo cliente en la DB
                    insertPedido(request, response);
                    break;
                case "/delete": //Ejecuta la eliminación de un cliente de la BD
                    deletePedido(request, response);
                    break;
                case "/edit": //Muestra el formulario para editar un cliente
                    showEditForm(request, response);
                    break;
                case "/update": //Ejecuta la edición de un cliente de la BD
                    updatePedido(request, response);
                    break;
                default:
                    listPedidos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Permite obtener todos los objetos de tipo pedido para mostrarlos en la tabla de la interfaz.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws ServletException si se produce un error en el servidor
     * @throws IOException      si se produce un error en I/O
     */
    private void listPedidos(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Pedido> listaPedido = pedidoJPA.findPedidoEntities();
        System.out.println("Lista pedido: " + listaPedido.size());
        request.setAttribute("listPedido", listaPedido);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/pedido/pedido.list.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Permite recibir los atributos de un objeto de tipo pedido para crearlo dentro de la base de datos.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws ServletException si se produce un error en el servidor
     * @throws IOException      si se produce un error en I/O
     */
    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/pedido/pedido.from.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Permite obtener los atributos de un objeto de tipo pedido para mostrarlos en el formulario de actualización
     * del elemento.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException     si se produce un error en la BD
     * @throws ServletException si se produce un error en el servidor
     * @throws IOException      si se produce un error en I/O
     */
    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // toma el id del pedido a ser editado
        int id = Integer.parseInt(request.getParameter("id"));
        // busca el pedido en la base de datos
        Pedido existingPedido = pedidoJPA.findPedido(id);
        RequestDispatcher dispatcher = null;
        if (existingPedido != null) {
            // si lo encuentra lo envía al formulario
            dispatcher = request.getRequestDispatcher("vistas/pedido/pedido.form.jps");
            request.setAttribute("pedido", existingPedido);
        } else {
            // si no lo encuentra regresa a la página con la lista de los clientes
            dispatcher = request.getRequestDispatcher("vistas/pedido/pedido.list.jsp");
        }
        dispatcher.forward(request, response);
    }

    /**
     * Crear un objeto de tipo pedido.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void insertPedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // toma los datos del formulario de pedidos
        int cantidad = Integer.parseInt(request.getParameter("count"));
        int subtotal = Integer.parseInt(request.getParameter("subtotal"));

        // crea un objeto de tipo Pedido vacío y lo llena con los datos obtenidos
        Pedido pedido = new Pedido();
        pedido.setCantidad(cantidad);
        pedido.setSubtotal(subtotal);

        // crea el pedido utilizando el objeto controlador JPA
        pedidoJPA.create(pedido);

        // solícita al Servlet que muestre la página actualizada con la lista de pedido
        response.sendRedirect("list");
    }

    /**
     * Actualiza un objeto de tipo pedido a través de un proceso equivalente a la funcionalidad PATH.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void updatePedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // toma los datos enviados por el formulario de clientes
        int id = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("count"));
        int subtotal = Integer.parseInt(request.getParameter("subtotal"));

        // crea un objeto vacío y lo llena con los datos del pedido
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        pedido.setCantidad(cantidad);
        pedido.setSubtotal(subtotal);

        try {
            // edita el cliente en la BD
            pedidoJPA.edit(pedido);
        } catch (Exception ex) {
            Logger.getLogger(ServletAppPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("list");
    }

    /**
     * Elimina un objeto de tipo pedido a través de su identificador.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void deletePedido(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // recibe el ID del pedido que se espera eliminar de la BD
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            // elimina el cliente con el id indicado
            pedidoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServletAppPedido.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("list");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.
    // Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Descripción del ServletAppPedidos";
    }// </editor-fold>

}
