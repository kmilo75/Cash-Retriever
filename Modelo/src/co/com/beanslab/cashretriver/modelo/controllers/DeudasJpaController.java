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
import co.com.beanslab.cashretriver.modelo.Deudas;
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
public class DeudasJpaController implements Serializable {

    public DeudasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Deudas deudas) {
        if (deudas.getCobrosCollection() == null) {
            deudas.setCobrosCollection(new ArrayList<Cobros>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas moroso = deudas.getMoroso();
            if (moroso != null) {
                moroso = em.getReference(moroso.getClass(), moroso.getIdpersonas());
                deudas.setMoroso(moroso);
            }
            Personas cobrador = deudas.getCobrador();
            if (cobrador != null) {
                cobrador = em.getReference(cobrador.getClass(), cobrador.getIdpersonas());
                deudas.setCobrador(cobrador);
            }
            Collection<Cobros> attachedCobrosCollection = new ArrayList<Cobros>();
            for (Cobros cobrosCollectionCobrosToAttach : deudas.getCobrosCollection()) {
                cobrosCollectionCobrosToAttach = em.getReference(cobrosCollectionCobrosToAttach.getClass(), cobrosCollectionCobrosToAttach.getIdcobros());
                attachedCobrosCollection.add(cobrosCollectionCobrosToAttach);
            }
            deudas.setCobrosCollection(attachedCobrosCollection);
            em.persist(deudas);
            if (moroso != null) {
                moroso.getDeudasCollection().add(deudas);
                moroso = em.merge(moroso);
            }
            if (cobrador != null) {
                cobrador.getDeudasCollection().add(deudas);
                cobrador = em.merge(cobrador);
            }
            for (Cobros cobrosCollectionCobros : deudas.getCobrosCollection()) {
                Deudas oldDeudaOfCobrosCollectionCobros = cobrosCollectionCobros.getDeuda();
                cobrosCollectionCobros.setDeuda(deudas);
                cobrosCollectionCobros = em.merge(cobrosCollectionCobros);
                if (oldDeudaOfCobrosCollectionCobros != null) {
                    oldDeudaOfCobrosCollectionCobros.getCobrosCollection().remove(cobrosCollectionCobros);
                    oldDeudaOfCobrosCollectionCobros = em.merge(oldDeudaOfCobrosCollectionCobros);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Deudas deudas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Deudas persistentDeudas = em.find(Deudas.class, deudas.getIddeudas());
            Personas morosoOld = persistentDeudas.getMoroso();
            Personas morosoNew = deudas.getMoroso();
            Personas cobradorOld = persistentDeudas.getCobrador();
            Personas cobradorNew = deudas.getCobrador();
            Collection<Cobros> cobrosCollectionOld = persistentDeudas.getCobrosCollection();
            Collection<Cobros> cobrosCollectionNew = deudas.getCobrosCollection();
            List<String> illegalOrphanMessages = null;
            for (Cobros cobrosCollectionOldCobros : cobrosCollectionOld) {
                if (!cobrosCollectionNew.contains(cobrosCollectionOldCobros)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cobros " + cobrosCollectionOldCobros + " since its deuda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (morosoNew != null) {
                morosoNew = em.getReference(morosoNew.getClass(), morosoNew.getIdpersonas());
                deudas.setMoroso(morosoNew);
            }
            if (cobradorNew != null) {
                cobradorNew = em.getReference(cobradorNew.getClass(), cobradorNew.getIdpersonas());
                deudas.setCobrador(cobradorNew);
            }
            Collection<Cobros> attachedCobrosCollectionNew = new ArrayList<Cobros>();
            for (Cobros cobrosCollectionNewCobrosToAttach : cobrosCollectionNew) {
                cobrosCollectionNewCobrosToAttach = em.getReference(cobrosCollectionNewCobrosToAttach.getClass(), cobrosCollectionNewCobrosToAttach.getIdcobros());
                attachedCobrosCollectionNew.add(cobrosCollectionNewCobrosToAttach);
            }
            cobrosCollectionNew = attachedCobrosCollectionNew;
            deudas.setCobrosCollection(cobrosCollectionNew);
            deudas = em.merge(deudas);
            if (morosoOld != null && !morosoOld.equals(morosoNew)) {
                morosoOld.getDeudasCollection().remove(deudas);
                morosoOld = em.merge(morosoOld);
            }
            if (morosoNew != null && !morosoNew.equals(morosoOld)) {
                morosoNew.getDeudasCollection().add(deudas);
                morosoNew = em.merge(morosoNew);
            }
            if (cobradorOld != null && !cobradorOld.equals(cobradorNew)) {
                cobradorOld.getDeudasCollection().remove(deudas);
                cobradorOld = em.merge(cobradorOld);
            }
            if (cobradorNew != null && !cobradorNew.equals(cobradorOld)) {
                cobradorNew.getDeudasCollection().add(deudas);
                cobradorNew = em.merge(cobradorNew);
            }
            for (Cobros cobrosCollectionNewCobros : cobrosCollectionNew) {
                if (!cobrosCollectionOld.contains(cobrosCollectionNewCobros)) {
                    Deudas oldDeudaOfCobrosCollectionNewCobros = cobrosCollectionNewCobros.getDeuda();
                    cobrosCollectionNewCobros.setDeuda(deudas);
                    cobrosCollectionNewCobros = em.merge(cobrosCollectionNewCobros);
                    if (oldDeudaOfCobrosCollectionNewCobros != null && !oldDeudaOfCobrosCollectionNewCobros.equals(deudas)) {
                        oldDeudaOfCobrosCollectionNewCobros.getCobrosCollection().remove(cobrosCollectionNewCobros);
                        oldDeudaOfCobrosCollectionNewCobros = em.merge(oldDeudaOfCobrosCollectionNewCobros);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = deudas.getIddeudas();
                if (findDeudas(id) == null) {
                    throw new NonexistentEntityException("The deudas with id " + id + " no longer exists.");
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
            Deudas deudas;
            try {
                deudas = em.getReference(Deudas.class, id);
                deudas.getIddeudas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The deudas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cobros> cobrosCollectionOrphanCheck = deudas.getCobrosCollection();
            for (Cobros cobrosCollectionOrphanCheckCobros : cobrosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Deudas (" + deudas + ") cannot be destroyed since the Cobros " + cobrosCollectionOrphanCheckCobros + " in its cobrosCollection field has a non-nullable deuda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Personas moroso = deudas.getMoroso();
            if (moroso != null) {
                moroso.getDeudasCollection().remove(deudas);
                moroso = em.merge(moroso);
            }
            Personas cobrador = deudas.getCobrador();
            if (cobrador != null) {
                cobrador.getDeudasCollection().remove(deudas);
                cobrador = em.merge(cobrador);
            }
            em.remove(deudas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Deudas> findDeudasEntities() {
        return findDeudasEntities(true, -1, -1);
    }

    public List<Deudas> findDeudasEntities(int maxResults, int firstResult) {
        return findDeudasEntities(false, maxResults, firstResult);
    }

    private List<Deudas> findDeudasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Deudas.class));
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

    public Deudas findDeudas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Deudas.class, id);
        } finally {
            em.close();
        }
    }

    public int getDeudasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Deudas> rt = cq.from(Deudas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
