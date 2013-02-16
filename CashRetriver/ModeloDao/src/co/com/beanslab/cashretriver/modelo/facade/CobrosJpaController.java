/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.facade;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelovergara.bo.Cobros;
import modelovergara.bo.Salidas;
import modelovergara.bo.Deudas;
import co.com.beanslab.cashretriver.modelo.facade.exceptions.NonexistentEntityException;

/**
 *
 * @author bruce
 */
public class CobrosJpaController implements Serializable {

    public CobrosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cobros cobros) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salidas salida = cobros.getSalida();
            if (salida != null) {
                salida = em.getReference(salida.getClass(), salida.getIdsalidas());
                cobros.setSalida(salida);
            }
            Deudas deuda = cobros.getDeuda();
            if (deuda != null) {
                deuda = em.getReference(deuda.getClass(), deuda.getIddeudas());
                cobros.setDeuda(deuda);
            }
            em.persist(cobros);
            if (salida != null) {
                salida.getCobrosCollection().add(cobros);
                salida = em.merge(salida);
            }
            if (deuda != null) {
                deuda.getCobrosCollection().add(cobros);
                deuda = em.merge(deuda);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cobros cobros) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cobros persistentCobros = em.find(Cobros.class, cobros.getIdcobros());
            Salidas salidaOld = persistentCobros.getSalida();
            Salidas salidaNew = cobros.getSalida();
            Deudas deudaOld = persistentCobros.getDeuda();
            Deudas deudaNew = cobros.getDeuda();
            if (salidaNew != null) {
                salidaNew = em.getReference(salidaNew.getClass(), salidaNew.getIdsalidas());
                cobros.setSalida(salidaNew);
            }
            if (deudaNew != null) {
                deudaNew = em.getReference(deudaNew.getClass(), deudaNew.getIddeudas());
                cobros.setDeuda(deudaNew);
            }
            cobros = em.merge(cobros);
            if (salidaOld != null && !salidaOld.equals(salidaNew)) {
                salidaOld.getCobrosCollection().remove(cobros);
                salidaOld = em.merge(salidaOld);
            }
            if (salidaNew != null && !salidaNew.equals(salidaOld)) {
                salidaNew.getCobrosCollection().add(cobros);
                salidaNew = em.merge(salidaNew);
            }
            if (deudaOld != null && !deudaOld.equals(deudaNew)) {
                deudaOld.getCobrosCollection().remove(cobros);
                deudaOld = em.merge(deudaOld);
            }
            if (deudaNew != null && !deudaNew.equals(deudaOld)) {
                deudaNew.getCobrosCollection().add(cobros);
                deudaNew = em.merge(deudaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cobros.getIdcobros();
                if (findCobros(id) == null) {
                    throw new NonexistentEntityException("The cobros with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cobros cobros;
            try {
                cobros = em.getReference(Cobros.class, id);
                cobros.getIdcobros();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cobros with id " + id + " no longer exists.", enfe);
            }
            Salidas salida = cobros.getSalida();
            if (salida != null) {
                salida.getCobrosCollection().remove(cobros);
                salida = em.merge(salida);
            }
            Deudas deuda = cobros.getDeuda();
            if (deuda != null) {
                deuda.getCobrosCollection().remove(cobros);
                deuda = em.merge(deuda);
            }
            em.remove(cobros);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cobros> findCobrosEntities() {
        return findCobrosEntities(true, -1, -1);
    }

    public List<Cobros> findCobrosEntities(int maxResults, int firstResult) {
        return findCobrosEntities(false, maxResults, firstResult);
    }

    private List<Cobros> findCobrosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cobros.class));
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

    public Cobros findCobros(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cobros.class, id);
        } finally {
            em.close();
        }
    }

    public int getCobrosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cobros> rt = cq.from(Cobros.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
