/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package edu.unicauca.apliweb.control;

import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.jpa.ProductoJpaController;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author jnico
 */
public class ServletAppProducto extends HttpServlet {
    private ProductoJpaController productoJPA;
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
        productoJPA = new ProductoJpaController(emf);
        //esta parte es solamente para realizar la prueba:
        //listamos todos los clientes de la base de datos y los imprimimos en consola
        List<Producto> listaProductos = productoJPA.findProductoEntities();
        //imprimimos los clientes en consola
        for (Producto producto : listaProductos) {
            System.out.println("Nombre: " + producto.getNombre() + " Precio: " + producto.getPrecio());
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
        List<Producto> listaProductos = productoJPA.findProductoEntities();
        for (Producto producto : listaProductos) {
            System.out.println("Nombre: " + producto.getNombre() + " Precio: " + producto.getPrecio());
        }
        String action = request.getServletPath();
        try {//(PrintWriter out = response.getWriter())
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Awayhub, Controla tus productos</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Lista de Productos en el Servlet " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            switch (action) {
                case "/new": //Muestra el formulario para crear un nuevo cliente
                    showNewForm(request, response);
                    break;
                case "/producto/insert": //ejecuta la creación de un nuevo cliente en la DB
                    insertProducto(request, response);
                    break;
                case "/producto/delete": //Ejecuta la eliminación de un cliente de la BD
                    deleteProducto(request, response);
                    break;
                case "/producto/edit": //Muestra el formulario para editar un cliente
                    showEditForm(request, response);
                    break;
                case "/producto/update": //Ejecuta la edición de un cliente de la BD
                    updateProducto(request, response);
                    break;
                case "/list":
                    listProductos(request, response);
                    break;
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }

        /**
         * Permite obtener todos los objetos de tipo producto para mostrarlos en la tabla de la interfaz.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws ServletException si se produce un error en el servidor
         * @throws IOException      si se produce un error en I/O
         */
        private void listProductos (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            List<Producto> listaProducto = productoJPA.findProductoEntities();
            request.setAttribute("listProducto", listaProducto);
            RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/producto/producto.list.jsp");
            dispatcher.forward(request, response);
        }

        /**
         * Permite recibir los atributos de un objeto de tipo producto para crearlo dentro de la base de datos.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws ServletException si se produce un error en el servidor
         * @throws IOException      si se produce un error en I/O
         */
        private void showNewForm (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            RequestDispatcher dispatcher = request.getRequestDispatcher("vistas/producto/producto.from.jsp");
            dispatcher.forward(request, response);
        }

        /**
         * Permite obtener los atributos de un objeto de tipo producto para mostrarlos en el formulario de actualización
         * del elemento.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws SQLException     si se produce un error en la BD
         * @throws ServletException si se produce un error en el servidor
         * @throws IOException      si se produce un error en I/O
         */
        private void showEditForm (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
            // toma el id del producto a ser editado
            int id = Integer.parseInt(request.getParameter("id"));
            // busca el producto en la base de datos
            Producto existingProducto = productoJPA.findProducto(id);
            RequestDispatcher dispatcher = null;
            if (existingProducto != null) {
                // si lo encuentra lo envía al formulario
                dispatcher = request.getRequestDispatcher("vistas/producto/producto.form.jps");
                request.setAttribute("producto", existingProducto);
            } else {
                // si no lo encuentra regresa a la página con la lista de los clientes
                dispatcher = request.getRequestDispatcher("vistas/producto/producto.list.jsp");
            }
            dispatcher.forward(request, response);
        }

        /**
         * Crear un objeto de tipo producto.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws SQLException si se produce un error en la BD
         * @throws IOException  si se produce un error en I/O
         */
        private void insertProducto (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
            // toma los datos del formulario de productos
            String nombre = request.getParameter("name");
            int precio = Integer.parseInt(request.getParameter("price"));

            // crea un objeto de tipo Producto vacío y lo llena con los datos obtenidos
            Producto producto = new Producto();
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            // crea el producto utilizando el objeto controlador JPA
            productoJPA.create(producto);

            // solícita al Servlet que muestre la página actualizada con la lista de producto
            response.sendRedirect("list");
        }

        /**
         * Actualiza un objeto de tipo producto a través de un proceso equivalente a la funcionalidad PATH.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws SQLException si se produce un error en la BD
         * @throws IOException  si se produce un error en I/O
         */
        private void updateProducto (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
            // toma los datos enviados por el formulario de clientes
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("name");
            int precio = Integer.parseInt(request.getParameter("price"));

            // crea un objeto vacío y lo llena con los datos del producto
            Producto producto = new Producto();
            producto.setIdProducto(id);
            producto.setNombre(nombre);
            producto.setPrecio(precio);

            try {
                // edita el cliente en la BD
                productoJPA.edit(producto);
            } catch (Exception ex) {
                Logger.getLogger(ServletAppProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.sendRedirect("list");
        }

        /**
         * Elimina un objeto de tipo producto a través de su identificador.
         *
         * @param request  servlet solicitud
         * @param response servlet respuesta
         * @throws SQLException si se produce un error en la BD
         * @throws IOException  si se produce un error en I/O
         */
        private void deleteProducto (HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
            // recibe el ID del producto que se espera eliminar de la BD
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                // elimina el cliente con el id indicado
                productoJPA.destroy(id);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(ServletAppProducto.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalOrphanException e) {
                throw new RuntimeException(e);
            }
            response.sendRedirect("list");
        }

        /**
         }
         }
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
        protected void doGet (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo () {
            return "Short description";
        }// </editor-fold>

    }
