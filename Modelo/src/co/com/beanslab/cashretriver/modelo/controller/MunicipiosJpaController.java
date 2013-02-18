/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.controller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.beanslab.cashretriver.modelo.Departamentos;
import co.com.beanslab.cashretriver.modelo.Barrios;
import co.com.beanslab.cashretriver.modelo.Municipios;
import co.com.beanslab.cashretriver.modelo.MunicipiosPK;
import co.com.beanslab.cashretriver.modelo.controller.exceptions.IllegalOrphanException;
import co.com.beanslab.cashretriver.modelo.controller.exceptions.NonexistentEntityException;
import co.com.beanslab.cashretriver.modelo.controller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class MunicipiosJpaController implements Serializable {

    public MunicipiosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipios municipios) throws PreexistingEntityException, Exception {
        if (municipios.getMunicipiosPK() == null) {
            municipios.setMunicipiosPK(new MunicipiosPK());
        }
        if (municipios.getBarriosCollection() == null) {
            municipios.setBarriosCollection(new ArrayList<Barrios>());
        }
        municipios.getMunicipiosPK().setDepartamento(municipios.getDepartamentos().getIddepartamentos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos departamentos = municipios.getDepartamentos();
            if (departamentos != null) {
                departamentos = em.getReference(departamentos.getClass(), departamentos.getIddepartamentos());
                municipios.setDepartamentos(departamentos);
            }
            Collection<Barrios> attachedBarriosCollection = new ArrayList<Barrios>();
            for (Barrios barriosCollectionBarriosToAttach : municipios.getBarriosCollection()) {
                barriosCollectionBarriosToAttach = em.getReference(barriosCollectionBarriosToAttach.getClass(), barriosCollectionBarriosToAttach.getBarriosPK());
                attachedBarriosCollection.add(barriosCollectionBarriosToAttach);
            }
            municipios.setBarriosCollection(attachedBarriosCollection);
            em.persist(municipios);
            if (departamentos != null) {
                departamentos.getMunicipiosCollection().add(municipios);
                departamentos = em.merge(departamentos);
            }
            for (Barrios barriosCollectionBarrios : municipios.getBarriosCollection()) {
                Municipios oldMunicipiosOfBarriosCollectionBarrios = barriosCollectionBarrios.getMunicipios();
                barriosCollectionBarrios.setMunicipios(municipios);
                barriosCollectionBarrios = em.merge(barriosCollectionBarrios);
                if (oldMunicipiosOfBarriosCollectionBarrios != null) {
                    oldMunicipiosOfBarriosCollectionBarrios.getBarriosCollection().remove(barriosCollectionBarrios);
                    oldMunicipiosOfBarriosCollectionBarrios = em.merge(oldMunicipiosOfBarriosCollectionBarrios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipios(municipios.getMunicipiosPK()) != null) {
                throw new PreexistingEntityException("Municipios " + municipios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipios municipios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        municipios.getMunicipiosPK().setDepartamento(municipios.getDepartamentos().getIddepartamentos());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios persistentMunicipios = em.find(Municipios.class, municipios.getMunicipiosPK());
            Departamentos departamentosOld = persistentMunicipios.getDepartamentos();
            Departamentos departamentosNew = municipios.getDepartamentos();
            Collection<Barrios> barriosCollectionOld = persistentMunicipios.getBarriosCollection();
            Collection<Barrios> barriosCollectionNew = municipios.getBarriosCollection();
            List<String> illegalOrphanMessages = null;
            for (Barrios barriosCollectionOldBarrios : barriosCollectionOld) {
                if (!barriosCollectionNew.contains(barriosCollectionOldBarrios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Barrios " + barriosCollectionOldBarrios + " since its municipios field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentosNew != null) {
                departamentosNew = em.getReference(departamentosNew.getClass(), departamentosNew.getIddepartamentos());
                municipios.setDepartamentos(departamentosNew);
            }
            Collection<Barrios> attachedBarriosCollectionNew = new ArrayList<Barrios>();
            for (Barrios barriosCollectionNewBarriosToAttach : barriosCollectionNew) {
                barriosCollectionNewBarriosToAttach = em.getReference(barriosCollectionNewBarriosToAttach.getClass(), barriosCollectionNewBarriosToAttach.getBarriosPK());
                attachedBarriosCollectionNew.add(barriosCollectionNewBarriosToAttach);
            }
            barriosCollectionNew = attachedBarriosCollectionNew;
            municipios.setBarriosCollection(barriosCollectionNew);
            municipios = em.merge(municipios);
            if (departamentosOld != null && !departamentosOld.equals(departamentosNew)) {
                departamentosOld.getMunicipiosCollection().remove(municipios);
                departamentosOld = em.merge(departamentosOld);
            }
            if (departamentosNew != null && !departamentosNew.equals(departamentosOld)) {
                departamentosNew.getMunicipiosCollection().add(municipios);
                departamentosNew = em.merge(departamentosNew);
            }
            for (Barrios barriosCollectionNewBarrios : barriosCollectionNew) {
                if (!barriosCollectionOld.contains(barriosCollectionNewBarrios)) {
                    Municipios oldMunicipiosOfBarriosCollectionNewBarrios = barriosCollectionNewBarrios.getMunicipios();
                    barriosCollectionNewBarrios.setMunicipios(municipios);
                    barriosCollectionNewBarrios = em.merge(barriosCollectionNewBarrios);
                    if (oldMunicipiosOfBarriosCollectionNewBarrios != null && !oldMunicipiosOfBarriosCollectionNewBarrios.equals(municipios)) {
                        oldMunicipiosOfBarriosCollectionNewBarrios.getBarriosCollection().remove(barriosCollectionNewBarrios);
                        oldMunicipiosOfBarriosCollectionNewBarrios = em.merge(oldMunicipiosOfBarriosCollectionNewBarrios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                MunicipiosPK id = municipios.getMunicipiosPK();
                if (findMunicipios(id) == null) {
                    throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(MunicipiosPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios municipios;
            try {
                municipios = em.getReference(Municipios.class, id);
                municipios.getMunicipiosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Barrios> barriosCollectionOrphanCheck = municipios.getBarriosCollection();
            for (Barrios barriosCollectionOrphanCheckBarrios : barriosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipios (" + municipios + ") cannot be destroyed since the Barrios " + barriosCollectionOrphanCheckBarrios + " in its barriosCollection field has a non-nullable municipios field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamentos departamentos = municipios.getDepartamentos();
            if (departamentos != null) {
                departamentos.getMunicipiosCollection().remove(municipios);
                departamentos = em.merge(departamentos);
            }
            em.remove(municipios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipios> findMunicipiosEntities() {
        return findMunicipiosEntities(true, -1, -1);
    }

    public List<Municipios> findMunicipiosEntities(int maxResults, int firstResult) {
        return findMunicipiosEntities(false, maxResults, firstResult);
    }

    private List<Municipios> findMunicipiosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipios.class));
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

    public Municipios findMunicipios(MunicipiosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipios.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipiosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipios> rt = cq.from(Municipios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
