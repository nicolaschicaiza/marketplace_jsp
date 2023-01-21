/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.unicauca.apliweb.control;

import edu.unicauca.apliweb.persistence.entities.Pedido;
import edu.unicauca.apliweb.persistence.entities.Usuario;
import edu.unicauca.apliweb.persistence.jpa.PedidoJpaController;
import edu.unicauca.apliweb.persistence.jpa.UsuarioJpaController;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
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
 *
 * @author jnico
 */
@WebServlet("/usuario")
public class ServletAppUsuario extends HttpServlet {
    private UsuarioJpaController usuarioJPA;
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
        usuarioJPA = new UsuarioJpaController(emf);
        //esta parte es solamente para realizar la prueba:
        //listamos todos los clientes de la base de datos y los imprimimos en consola
        List<Usuario> listaUsuario = usuarioJPA.findUsuarioEntities();
        //imprimimos los clientes en consola
        for (Usuario usuario : listaUsuario ) {
            System.out.println("Nombre: " + usuario.getNombre() + ", Cédula: " + usuario.getCedula() +
                    ", Username: " + usuario.getUsername() + ", Password: " + usuario.getPassword());
        }
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Awayhub, Controla tus usuarios</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de Usuarios en el Servlet " + request.getContextPath() + "</h1>");
            List<Usuario> listaUsuarios = usuarioJPA.findUsuarioEntities();
            for (Usuario usuario : listaUsuarios) {
                System.out.println("Nombre: " + usuario.getNombre() + ", Cédula: " + usuario.getCedula() +
                        ", Username: " + usuario.getUsername() + ", Password: " + usuario.getPassword());
            }
            out.println("</body>");
            out.println("</html>");
            String action = request.getServletPath();
            switch (action) {
                case "/new" -> showNewFrom(request, response);
                case "/insert" -> insertUsuario(request, response);
                case "/delete" -> deleteUsuario(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> updateUsuario(request, response);
                default -> listUsuarios(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * Permite obtener todos los objetos de tipo usuario para mostrarlos en la tabla de la interfaz.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws ServletException si se produce un error en el servidor
     * @throws IOException      si se produce un error en I/O
     */
    private void listUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Usuario> listaUsuario = usuarioJPA.findUsuarioEntities();
        request.setAttribute("listUsuario", listaUsuario);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/usuario/usuario.list.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Permite recibir los atributos de un objeto de tipo usuario para crearlo dentro de la base de datos.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws ServletException si se produce un error en el servidor
     * @throws IOException      si se produce un error en I/O
     */
    private void showNewFrom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/usuario/usuario.from.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Permite obtener los atributos de un objeto de tipo usuario para mostrarlos en el formulario de actualización
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
        // toma el id del usuario a ser editado
        int id = Integer.parseInt(request.getParameter("id"));
        // busca el usuario en la base de datos
        Usuario existingUsuario = usuarioJPA.findUsuario(id);
        RequestDispatcher dispatcher = null;
        if (existingUsuario != null) {
            // si lo encuentra lo envía al formulario
            dispatcher = request.getRequestDispatcher("vistas/usuario/usuario.form.jps");
            request.setAttribute("usuario", existingUsuario);
        } else {
            // si no lo encuentra regresa a la página con la lista de los clientes
            dispatcher = request.getRequestDispatcher("vistas/usuario/usuario.list.jsp");
        }
        dispatcher.forward(request, response);
    }

    /**
     * Crear un objeto de tipo usuario.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void insertUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // toma los datos del formulario de usuarios
        String nombre =request.getParameter("name");
        int cedula = Integer.parseInt(request.getParameter("id_card"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // crea un objeto de tipo Usuario vacío y lo llena con los datos obtenidos
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCedula(cedula);
        usuario.setUsername(username);
        usuario.setPassword(password);

        // crea el usuario utilizando el objeto controlador JPA
        usuarioJPA.create(usuario);

        // solícita al Servlet que muestre la página actualizada con la lista de usuario
        response.sendRedirect("list");
    }

    /**
     * Actualiza un objeto de tipo usuario a través de un proceso equivalente a la funcionalidad PATH.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void updateUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // toma los datos enviados por el formulario de clientes
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre =request.getParameter("name");
        int cedula = Integer.parseInt(request.getParameter("id_card"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // crea un objeto vacío y lo llena con los datos del usuario
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setCedula(cedula);
        usuario.setUsername(username);
        usuario.setPassword(password);

        try {
            // edita el cliente en la BD
            usuarioJPA.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ServletAppUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("list");
    }

    /**
     * Elimina un objeto de tipo usuario a través de su identificador.
     *
     * @param request  servlet solicitud
     * @param response servlet respuesta
     * @throws SQLException si se produce un error en la BD
     * @throws IOException  si se produce un error en I/O
     */
    private void deleteUsuario(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        // recibe el ID del usuario que se espera eliminar de la BD
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            // elimina el cliente con el id indicado
            usuarioJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ServletAppUsuario.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalOrphanException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("list");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
        return "Short description";
    }// </editor-fold>

}
