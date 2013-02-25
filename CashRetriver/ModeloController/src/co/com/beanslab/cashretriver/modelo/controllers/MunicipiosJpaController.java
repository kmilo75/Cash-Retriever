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
import co.com.beanslab.cashretriver.modelo.Departamentos;
import co.com.beanslab.cashretriver.modelo.Barrios;
import co.com.beanslab.cashretriver.modelo.Municipios;
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
public class MunicipiosJpaController implements Serializable {

    public MunicipiosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipios municipios) {
        if (municipios.getBarriosCollection() == null) {
            municipios.setBarriosCollection(new ArrayList<Barrios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos departamento = municipios.getDepartamento();
            if (departamento != null) {
                departamento = em.getReference(departamento.getClass(), departamento.getIddepartamentos());
                municipios.setDepartamento(departamento);
            }
            Collection<Barrios> attachedBarriosCollection = new ArrayList<Barrios>();
            for (Barrios barriosCollectionBarriosToAttach : municipios.getBarriosCollection()) {
                barriosCollectionBarriosToAttach = em.getReference(barriosCollectionBarriosToAttach.getClass(), barriosCollectionBarriosToAttach.getIdbarrios());
                attachedBarriosCollection.add(barriosCollectionBarriosToAttach);
            }
            municipios.setBarriosCollection(attachedBarriosCollection);
            em.persist(municipios);
            if (departamento != null) {
                departamento.getMunicipiosCollection().add(municipios);
                departamento = em.merge(departamento);
            }
            for (Barrios barriosCollectionBarrios : municipios.getBarriosCollection()) {
                Municipios oldMunicipioOfBarriosCollectionBarrios = barriosCollectionBarrios.getMunicipio();
                barriosCollectionBarrios.setMunicipio(municipios);
                barriosCollectionBarrios = em.merge(barriosCollectionBarrios);
                if (oldMunicipioOfBarriosCollectionBarrios != null) {
                    oldMunicipioOfBarriosCollectionBarrios.getBarriosCollection().remove(barriosCollectionBarrios);
                    oldMunicipioOfBarriosCollectionBarrios = em.merge(oldMunicipioOfBarriosCollectionBarrios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipios municipios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios persistentMunicipios = em.find(Municipios.class, municipios.getIdmunicipios());
            Departamentos departamentoOld = persistentMunicipios.getDepartamento();
            Departamentos departamentoNew = municipios.getDepartamento();
            Collection<Barrios> barriosCollectionOld = persistentMunicipios.getBarriosCollection();
            Collection<Barrios> barriosCollectionNew = municipios.getBarriosCollection();
            List<String> illegalOrphanMessages = null;
            for (Barrios barriosCollectionOldBarrios : barriosCollectionOld) {
                if (!barriosCollectionNew.contains(barriosCollectionOldBarrios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Barrios " + barriosCollectionOldBarrios + " since its municipio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoNew != null) {
                departamentoNew = em.getReference(departamentoNew.getClass(), departamentoNew.getIddepartamentos());
                municipios.setDepartamento(departamentoNew);
            }
            Collection<Barrios> attachedBarriosCollectionNew = new ArrayList<Barrios>();
            for (Barrios barriosCollectionNewBarriosToAttach : barriosCollectionNew) {
                barriosCollectionNewBarriosToAttach = em.getReference(barriosCollectionNewBarriosToAttach.getClass(), barriosCollectionNewBarriosToAttach.getIdbarrios());
                attachedBarriosCollectionNew.add(barriosCollectionNewBarriosToAttach);
            }
            barriosCollectionNew = attachedBarriosCollectionNew;
            municipios.setBarriosCollection(barriosCollectionNew);
            municipios = em.merge(municipios);
            if (departamentoOld != null && !departamentoOld.equals(departamentoNew)) {
                departamentoOld.getMunicipiosCollection().remove(municipios);
                departamentoOld = em.merge(departamentoOld);
            }
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                departamentoNew.getMunicipiosCollection().add(municipios);
                departamentoNew = em.merge(departamentoNew);
            }
            for (Barrios barriosCollectionNewBarrios : barriosCollectionNew) {
                if (!barriosCollectionOld.contains(barriosCollectionNewBarrios)) {
                    Municipios oldMunicipioOfBarriosCollectionNewBarrios = barriosCollectionNewBarrios.getMunicipio();
                    barriosCollectionNewBarrios.setMunicipio(municipios);
                    barriosCollectionNewBarrios = em.merge(barriosCollectionNewBarrios);
                    if (oldMunicipioOfBarriosCollectionNewBarrios != null && !oldMunicipioOfBarriosCollectionNewBarrios.equals(municipios)) {
                        oldMunicipioOfBarriosCollectionNewBarrios.getBarriosCollection().remove(barriosCollectionNewBarrios);
                        oldMunicipioOfBarriosCollectionNewBarrios = em.merge(oldMunicipioOfBarriosCollectionNewBarrios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipios.getIdmunicipios();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios municipios;
            try {
                municipios = em.getReference(Municipios.class, id);
                municipios.getIdmunicipios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Barrios> barriosCollectionOrphanCheck = municipios.getBarriosCollection();
            for (Barrios barriosCollectionOrphanCheckBarrios : barriosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Municipios (" + municipios + ") cannot be destroyed since the Barrios " + barriosCollectionOrphanCheckBarrios + " in its barriosCollection field has a non-nullable municipio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Departamentos departamento = municipios.getDepartamento();
            if (departamento != null) {
                departamento.getMunicipiosCollection().remove(municipios);
                departamento = em.merge(departamento);
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

    public Municipios findMunicipios(Integer id) {
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
