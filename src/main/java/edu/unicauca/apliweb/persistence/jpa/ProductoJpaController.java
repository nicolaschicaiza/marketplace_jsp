/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.unicauca.apliweb.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import edu.unicauca.apliweb.persistence.entities.Pedido;
import edu.unicauca.apliweb.persistence.entities.Producto;
import edu.unicauca.apliweb.persistence.jpa.exceptions.IllegalOrphanException;
import edu.unicauca.apliweb.persistence.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author jnico
 */
public class ProductoJpaController implements Serializable {

    public ProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Producto producto) {
        if (producto.getPedidoList() == null) {
            producto.setPedidoList(new ArrayList<Pedido>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Pedido> attachedPedidoList = new ArrayList<Pedido>();
            for (Pedido pedidoListPedidoToAttach : producto.getPedidoList()) {
                pedidoListPedidoToAttach = em.getReference(pedidoListPedidoToAttach.getClass(), pedidoListPedidoToAttach.getIdPedido());
                attachedPedidoList.add(pedidoListPedidoToAttach);
            }
            producto.setPedidoList(attachedPedidoList);
            em.persist(producto);
            for (Pedido pedidoListPedido : producto.getPedidoList()) {
                Producto oldIdProductoOfPedidoListPedido = pedidoListPedido.getIdProducto();
                pedidoListPedido.setIdProducto(producto);
                pedidoListPedido = em.merge(pedidoListPedido);
                if (oldIdProductoOfPedidoListPedido != null) {
                    oldIdProductoOfPedidoListPedido.getPedidoList().remove(pedidoListPedido);
                    oldIdProductoOfPedidoListPedido = em.merge(oldIdProductoOfPedidoListPedido);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Producto producto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto persistentProducto = em.find(Producto.class, producto.getIdProducto());
//            List<Pedido> pedidoListOld = persistentProducto.getPedidoList();
//            List<Pedido> pedidoListNew = producto.getPedidoList();
//            List<String> illegalOrphanMessages = null;
//            for (Pedido pedidoListOldPedido : pedidoListOld) {
//                if (!pedidoListNew.contains(pedidoListOldPedido)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Pedido " + pedidoListOldPedido + " since its idProducto field is not nullable.");
//                }
//            }
//            if (illegalOrphanMessages != null) {
//                throw new IllegalOrphanException(illegalOrphanMessages);
//            }
//            List<Pedido> attachedPedidoListNew = new ArrayList<Pedido>();
//            for (Pedido pedidoListNewPedidoToAttach : pedidoListNew) {
//                pedidoListNewPedidoToAttach = em.getReference(pedidoListNewPedidoToAttach.getClass(), pedidoListNewPedidoToAttach.getIdPedido());
//                attachedPedidoListNew.add(pedidoListNewPedidoToAttach);
//            }
//            pedidoListNew = attachedPedidoListNew;
//            producto.setPedidoList(pedidoListNew);
            producto = em.merge(producto);
//            for (Pedido pedidoListNewPedido : pedidoListNew) {
//                if (!pedidoListOld.contains(pedidoListNewPedido)) {
//                    Producto oldIdProductoOfPedidoListNewPedido = pedidoListNewPedido.getIdProducto();
//                    pedidoListNewPedido.setIdProducto(producto);
//                    pedidoListNewPedido = em.merge(pedidoListNewPedido);
//                    if (oldIdProductoOfPedidoListNewPedido != null && !oldIdProductoOfPedidoListNewPedido.equals(producto)) {
//                        oldIdProductoOfPedidoListNewPedido.getPedidoList().remove(pedidoListNewPedido);
//                        oldIdProductoOfPedidoListNewPedido = em.merge(oldIdProductoOfPedidoListNewPedido);
//                    }
//                }
//            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = producto.getIdProducto();
                if (findProducto(id) == null) {
                    throw new NonexistentEntityException("The producto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Producto producto;
            try {
                producto = em.getReference(Producto.class, id);
                producto.getIdProducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The producto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pedido> pedidoListOrphanCheck = producto.getPedidoList();
            for (Pedido pedidoListOrphanCheckPedido : pedidoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Producto (" + producto + ") cannot be destroyed since the Pedido " + pedidoListOrphanCheckPedido + " in its pedidoList field has a non-nullable idProducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(producto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Producto> findProductoEntities() {
        return findProductoEntities(true, -1, -1);
    }

    public List<Producto> findProductoEntities(int maxResults, int firstResult) {
        return findProductoEntities(false, maxResults, firstResult);
    }

    private List<Producto> findProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Producto.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Producto findProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Producto> rt = cq.from(Producto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
