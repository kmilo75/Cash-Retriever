/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.beanslab.cashretriver.modelo.Personas;
import co.com.beanslab.cashretriver.modelo.Cobros;
import co.com.beanslab.cashretriver.modelo.Salidas;
import co.com.beanslab.cashretriver.modelo.controllers.exceptions.IllegalOrphanException;
import co.com.beanslab.cashretriver.modelo.controllers.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class SalidasJpaController implements Serializable {

    public SalidasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salidas salidas) {
        if (salidas.getCobrosCollection() == null) {
            salidas.setCobrosCollection(new ArrayList<Cobros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas cobrador = salidas.getCobrador();
            if (cobrador != null) {
                cobrador = em.getReference(cobrador.getClass(), cobrador.getIdpersonas());
                salidas.setCobrador(cobrador);
            }
            Collection<Cobros> attachedCobrosCollection = new ArrayList<Cobros>();
            for (Cobros cobrosCollectionCobrosToAttach : salidas.getCobrosCollection()) {
                cobrosCollectionCobrosToAttach = em.getReference(cobrosCollectionCobrosToAttach.getClass(), cobrosCollectionCobrosToAttach.getIdcobros());
                attachedCobrosCollection.add(cobrosCollectionCobrosToAttach);
            }
            salidas.setCobrosCollection(attachedCobrosCollection);
            em.persist(salidas);
            if (cobrador != null) {
                cobrador.getSalidasCollection().add(salidas);
                cobrador = em.merge(cobrador);
            }
            for (Cobros cobrosCollectionCobros : salidas.getCobrosCollection()) {
                Salidas oldSalidaOfCobrosCollectionCobros = cobrosCollectionCobros.getSalida();
                cobrosCollectionCobros.setSalida(salidas);
                cobrosCollectionCobros = em.merge(cobrosCollectionCobros);
                if (oldSalidaOfCobrosCollectionCobros != null) {
                    oldSalidaOfCobrosCollectionCobros.getCobrosCollection().remove(cobrosCollectionCobros);
                    oldSalidaOfCobrosCollectionCobros = em.merge(oldSalidaOfCobrosCollectionCobros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salidas salidas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salidas persistentSalidas = em.find(Salidas.class, salidas.getIdsalidas());
            Personas cobradorOld = persistentSalidas.getCobrador();
            Personas cobradorNew = salidas.getCobrador();
            Collection<Cobros> cobrosCollectionOld = persistentSalidas.getCobrosCollection();
            Collection<Cobros> cobrosCollectionNew = salidas.getCobrosCollection();
            List<String> illegalOrphanMessages = null;
            for (Cobros cobrosCollectionOldCobros : cobrosCollectionOld) {
                if (!cobrosCollectionNew.contains(cobrosCollectionOldCobros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cobros " + cobrosCollectionOldCobros + " since its salida field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cobradorNew != null) {
                cobradorNew = em.getReference(cobradorNew.getClass(), cobradorNew.getIdpersonas());
                salidas.setCobrador(cobradorNew);
            }
            Collection<Cobros> attachedCobrosCollectionNew = new ArrayList<Cobros>();
            for (Cobros cobrosCollectionNewCobrosToAttach : cobrosCollectionNew) {
                cobrosCollectionNewCobrosToAttach = em.getReference(cobrosCollectionNewCobrosToAttach.getClass(), cobrosCollectionNewCobrosToAttach.getIdcobros());
                attachedCobrosCollectionNew.add(cobrosCollectionNewCobrosToAttach);
            }
            cobrosCollectionNew = attachedCobrosCollectionNew;
            salidas.setCobrosCollection(cobrosCollectionNew);
            salidas = em.merge(salidas);
            if (cobradorOld != null && !cobradorOld.equals(cobradorNew)) {
                cobradorOld.getSalidasCollection().remove(salidas);
                cobradorOld = em.merge(cobradorOld);
            }
            if (cobradorNew != null && !cobradorNew.equals(cobradorOld)) {
                cobradorNew.getSalidasCollection().add(salidas);
                cobradorNew = em.merge(cobradorNew);
            }
            for (Cobros cobrosCollectionNewCobros : cobrosCollectionNew) {
                if (!cobrosCollectionOld.contains(cobrosCollectionNewCobros)) {
                    Salidas oldSalidaOfCobrosCollectionNewCobros = cobrosCollectionNewCobros.getSalida();
                    cobrosCollectionNewCobros.setSalida(salidas);
                    cobrosCollectionNewCobros = em.merge(cobrosCollectionNewCobros);
                    if (oldSalidaOfCobrosCollectionNewCobros != null && !oldSalidaOfCobrosCollectionNewCobros.equals(salidas)) {
                        oldSalidaOfCobrosCollectionNewCobros.getCobrosCollection().remove(cobrosCollectionNewCobros);
                        oldSalidaOfCobrosCollectionNewCobros = em.merge(oldSalidaOfCobrosCollectionNewCobros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = salidas.getIdsalidas();
                if (findSalidas(id) == null) {
                    throw new NonexistentEntityException("The salidas with id " + id + " no longer exists.");
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
            Salidas salidas;
            try {
                salidas = em.getReference(Salidas.class, id);
                salidas.getIdsalidas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salidas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cobros> cobrosCollectionOrphanCheck = salidas.getCobrosCollection();
            for (Cobros cobrosCollectionOrphanCheckCobros : cobrosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Salidas (" + salidas + ") cannot be destroyed since the Cobros " + cobrosCollectionOrphanCheckCobros + " in its cobrosCollection field has a non-nullable salida field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personas cobrador = salidas.getCobrador();
            if (cobrador != null) {
                cobrador.getSalidasCollection().remove(salidas);
                cobrador = em.merge(cobrador);
            }
            em.remove(salidas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salidas> findSalidasEntities() {
        return findSalidasEntities(true, -1, -1);
    }

    public List<Salidas> findSalidasEntities(int maxResults, int firstResult) {
        return findSalidasEntities(false, maxResults, firstResult);
    }

    private List<Salidas> findSalidasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salidas.class));
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

    public Salidas findSalidas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salidas.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalidasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salidas> rt = cq.from(Salidas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
