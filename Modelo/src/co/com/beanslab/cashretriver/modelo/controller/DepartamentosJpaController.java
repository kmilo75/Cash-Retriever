/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.controller;

import co.com.beanslab.cashretriver.modelo.Departamentos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.beanslab.cashretriver.modelo.Municipios;
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
public class DepartamentosJpaController implements Serializable {

    public DepartamentosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Departamentos departamentos) throws PreexistingEntityException, Exception {
        if (departamentos.getMunicipiosCollection() == null) {
            departamentos.setMunicipiosCollection(new ArrayList<Municipios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Municipios> attachedMunicipiosCollection = new ArrayList<Municipios>();
            for (Municipios municipiosCollectionMunicipiosToAttach : departamentos.getMunicipiosCollection()) {
                municipiosCollectionMunicipiosToAttach = em.getReference(municipiosCollectionMunicipiosToAttach.getClass(), municipiosCollectionMunicipiosToAttach.getMunicipiosPK());
                attachedMunicipiosCollection.add(municipiosCollectionMunicipiosToAttach);
            }
            departamentos.setMunicipiosCollection(attachedMunicipiosCollection);
            em.persist(departamentos);
            for (Municipios municipiosCollectionMunicipios : departamentos.getMunicipiosCollection()) {
                Departamentos oldDepartamentosOfMunicipiosCollectionMunicipios = municipiosCollectionMunicipios.getDepartamentos();
                municipiosCollectionMunicipios.setDepartamentos(departamentos);
                municipiosCollectionMunicipios = em.merge(municipiosCollectionMunicipios);
                if (oldDepartamentosOfMunicipiosCollectionMunicipios != null) {
                    oldDepartamentosOfMunicipiosCollectionMunicipios.getMunicipiosCollection().remove(municipiosCollectionMunicipios);
                    oldDepartamentosOfMunicipiosCollectionMunicipios = em.merge(oldDepartamentosOfMunicipiosCollectionMunicipios);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDepartamentos(departamentos.getIddepartamentos()) != null) {
                throw new PreexistingEntityException("Departamentos " + departamentos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Departamentos departamentos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos persistentDepartamentos = em.find(Departamentos.class, departamentos.getIddepartamentos());
            Collection<Municipios> municipiosCollectionOld = persistentDepartamentos.getMunicipiosCollection();
            Collection<Municipios> municipiosCollectionNew = departamentos.getMunicipiosCollection();
            List<String> illegalOrphanMessages = null;
            for (Municipios municipiosCollectionOldMunicipios : municipiosCollectionOld) {
                if (!municipiosCollectionNew.contains(municipiosCollectionOldMunicipios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Municipios " + municipiosCollectionOldMunicipios + " since its departamentos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Municipios> attachedMunicipiosCollectionNew = new ArrayList<Municipios>();
            for (Municipios municipiosCollectionNewMunicipiosToAttach : municipiosCollectionNew) {
                municipiosCollectionNewMunicipiosToAttach = em.getReference(municipiosCollectionNewMunicipiosToAttach.getClass(), municipiosCollectionNewMunicipiosToAttach.getMunicipiosPK());
                attachedMunicipiosCollectionNew.add(municipiosCollectionNewMunicipiosToAttach);
            }
            municipiosCollectionNew = attachedMunicipiosCollectionNew;
            departamentos.setMunicipiosCollection(municipiosCollectionNew);
            departamentos = em.merge(departamentos);
            for (Municipios municipiosCollectionNewMunicipios : municipiosCollectionNew) {
                if (!municipiosCollectionOld.contains(municipiosCollectionNewMunicipios)) {
                    Departamentos oldDepartamentosOfMunicipiosCollectionNewMunicipios = municipiosCollectionNewMunicipios.getDepartamentos();
                    municipiosCollectionNewMunicipios.setDepartamentos(departamentos);
                    municipiosCollectionNewMunicipios = em.merge(municipiosCollectionNewMunicipios);
                    if (oldDepartamentosOfMunicipiosCollectionNewMunicipios != null && !oldDepartamentosOfMunicipiosCollectionNewMunicipios.equals(departamentos)) {
                        oldDepartamentosOfMunicipiosCollectionNewMunicipios.getMunicipiosCollection().remove(municipiosCollectionNewMunicipios);
                        oldDepartamentosOfMunicipiosCollectionNewMunicipios = em.merge(oldDepartamentosOfMunicipiosCollectionNewMunicipios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = departamentos.getIddepartamentos();
                if (findDepartamentos(id) == null) {
                    throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamentos departamentos;
            try {
                departamentos = em.getReference(Departamentos.class, id);
                departamentos.getIddepartamentos();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The departamentos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Municipios> municipiosCollectionOrphanCheck = departamentos.getMunicipiosCollection();
            for (Municipios municipiosCollectionOrphanCheckMunicipios : municipiosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Departamentos (" + departamentos + ") cannot be destroyed since the Municipios " + municipiosCollectionOrphanCheckMunicipios + " in its municipiosCollection field has a non-nullable departamentos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(departamentos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Departamentos> findDepartamentosEntities() {
        return findDepartamentosEntities(true, -1, -1);
    }

    public List<Departamentos> findDepartamentosEntities(int maxResults, int firstResult) {
        return findDepartamentosEntities(false, maxResults, firstResult);
    }

    private List<Departamentos> findDepartamentosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Departamentos.class));
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

    public Departamentos findDepartamentos(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Departamentos.class, id);
        } finally {
            em.close();
        }
    }

    public int getDepartamentosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Departamentos> rt = cq.from(Departamentos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
