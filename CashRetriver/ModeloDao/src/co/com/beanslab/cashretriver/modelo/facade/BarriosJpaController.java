/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.facade;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelovergara.bo.Municipios;
import modelovergara.bo.Personas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelovergara.bo.Barrios;
import co.com.beanslab.cashretriver.modelo.facade.exceptions.IllegalOrphanException;
import co.com.beanslab.cashretriver.modelo.facade.exceptions.NonexistentEntityException;

/**
 *
 * @author bruce
 */
public class BarriosJpaController implements Serializable {

    public BarriosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barrios barrios) {
        if (barrios.getPersonasCollection() == null) {
            barrios.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios municipio = barrios.getMunicipio();
            if (municipio != null) {
                municipio = em.getReference(municipio.getClass(), municipio.getIdmunicipios());
                barrios.setMunicipio(municipio);
            }
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : barrios.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getIdpersonas());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            barrios.setPersonasCollection(attachedPersonasCollection);
            em.persist(barrios);
            if (municipio != null) {
                municipio.getBarriosCollection().add(barrios);
                municipio = em.merge(municipio);
            }
            for (Personas personasCollectionPersonas : barrios.getPersonasCollection()) {
                Barrios oldBarrioOfPersonasCollectionPersonas = personasCollectionPersonas.getBarrio();
                personasCollectionPersonas.setBarrio(barrios);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldBarrioOfPersonasCollectionPersonas != null) {
                    oldBarrioOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldBarrioOfPersonasCollectionPersonas = em.merge(oldBarrioOfPersonasCollectionPersonas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barrios barrios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios persistentBarrios = em.find(Barrios.class, barrios.getIdbarrios());
            Municipios municipioOld = persistentBarrios.getMunicipio();
            Municipios municipioNew = barrios.getMunicipio();
            Collection<Personas> personasCollectionOld = persistentBarrios.getPersonasCollection();
            Collection<Personas> personasCollectionNew = barrios.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its barrio field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (municipioNew != null) {
                municipioNew = em.getReference(municipioNew.getClass(), municipioNew.getIdmunicipios());
                barrios.setMunicipio(municipioNew);
            }
            Collection<Personas> attachedPersonasCollectionNew = new ArrayList<Personas>();
            for (Personas personasCollectionNewPersonasToAttach : personasCollectionNew) {
                personasCollectionNewPersonasToAttach = em.getReference(personasCollectionNewPersonasToAttach.getClass(), personasCollectionNewPersonasToAttach.getIdpersonas());
                attachedPersonasCollectionNew.add(personasCollectionNewPersonasToAttach);
            }
            personasCollectionNew = attachedPersonasCollectionNew;
            barrios.setPersonasCollection(personasCollectionNew);
            barrios = em.merge(barrios);
            if (municipioOld != null && !municipioOld.equals(municipioNew)) {
                municipioOld.getBarriosCollection().remove(barrios);
                municipioOld = em.merge(municipioOld);
            }
            if (municipioNew != null && !municipioNew.equals(municipioOld)) {
                municipioNew.getBarriosCollection().add(barrios);
                municipioNew = em.merge(municipioNew);
            }
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Barrios oldBarrioOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getBarrio();
                    personasCollectionNewPersonas.setBarrio(barrios);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldBarrioOfPersonasCollectionNewPersonas != null && !oldBarrioOfPersonasCollectionNewPersonas.equals(barrios)) {
                        oldBarrioOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldBarrioOfPersonasCollectionNewPersonas = em.merge(oldBarrioOfPersonasCollectionNewPersonas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = barrios.getIdbarrios();
                if (findElement(id) == null) {
                    throw new NonexistentEntityException("The barrios with id " + id + " no longer exists.");
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
            Barrios barrios;
            try {
                barrios = em.getReference(Barrios.class, id);
                barrios.getIdbarrios();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barrios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = barrios.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barrios (" + barrios + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable barrio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Municipios municipio = barrios.getMunicipio();
            if (municipio != null) {
                municipio.getBarriosCollection().remove(barrios);
                municipio = em.merge(municipio);
            }
            em.remove(barrios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barrios> findBarriosEntities() {
        return findBarriosEntities(true, -1, -1);
    }

    public List<Barrios> findBarriosEntities(int maxResults, int firstResult) {
        return findBarriosEntities(false, maxResults, firstResult);
    }

    private List<Barrios> findBarriosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barrios.class));
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

    public Barrios findElement(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barrios.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarriosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barrios> rt = cq.from(Barrios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
