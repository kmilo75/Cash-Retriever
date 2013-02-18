/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.beanslab.cashretriver.modelo.controller;

import co.com.beanslab.cashretriver.modelo.Barrios;
import co.com.beanslab.cashretriver.modelo.BarriosPK;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import co.com.beanslab.cashretriver.modelo.Municipios;
import co.com.beanslab.cashretriver.modelo.Personas;
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
public class BarriosJpaController implements Serializable {

    public BarriosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Barrios barrios) throws PreexistingEntityException, Exception {
        if (barrios.getBarriosPK() == null) {
            barrios.setBarriosPK(new BarriosPK());
        }
        if (barrios.getPersonasCollection() == null) {
            barrios.setPersonasCollection(new ArrayList<Personas>());
        }
        barrios.getBarriosPK().setMunicipio(barrios.getMunicipios().getMunicipiosPK().getIdmunicipios());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipios municipios = barrios.getMunicipios();
            if (municipios != null) {
                municipios = em.getReference(municipios.getClass(), municipios.getMunicipiosPK());
                barrios.setMunicipios(municipios);
            }
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : barrios.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getIdpersonas());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            barrios.setPersonasCollection(attachedPersonasCollection);
            em.persist(barrios);
            if (municipios != null) {
                municipios.getBarriosCollection().add(barrios);
                municipios = em.merge(municipios);
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
        } catch (Exception ex) {
            if (findBarrios(barrios.getBarriosPK()) != null) {
                throw new PreexistingEntityException("Barrios " + barrios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barrios barrios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        barrios.getBarriosPK().setMunicipio(barrios.getMunicipios().getMunicipiosPK().getIdmunicipios());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios persistentBarrios = em.find(Barrios.class, barrios.getBarriosPK());
            Municipios municipiosOld = persistentBarrios.getMunicipios();
            Municipios municipiosNew = barrios.getMunicipios();
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
            if (municipiosNew != null) {
                municipiosNew = em.getReference(municipiosNew.getClass(), municipiosNew.getMunicipiosPK());
                barrios.setMunicipios(municipiosNew);
            }
            Collection<Personas> attachedPersonasCollectionNew = new ArrayList<Personas>();
            for (Personas personasCollectionNewPersonasToAttach : personasCollectionNew) {
                personasCollectionNewPersonasToAttach = em.getReference(personasCollectionNewPersonasToAttach.getClass(), personasCollectionNewPersonasToAttach.getIdpersonas());
                attachedPersonasCollectionNew.add(personasCollectionNewPersonasToAttach);
            }
            personasCollectionNew = attachedPersonasCollectionNew;
            barrios.setPersonasCollection(personasCollectionNew);
            barrios = em.merge(barrios);
            if (municipiosOld != null && !municipiosOld.equals(municipiosNew)) {
                municipiosOld.getBarriosCollection().remove(barrios);
                municipiosOld = em.merge(municipiosOld);
            }
            if (municipiosNew != null && !municipiosNew.equals(municipiosOld)) {
                municipiosNew.getBarriosCollection().add(barrios);
                municipiosNew = em.merge(municipiosNew);
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
                BarriosPK id = barrios.getBarriosPK();
                if (findBarrios(id) == null) {
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

    public void destroy(BarriosPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barrios barrios;
            try {
                barrios = em.getReference(Barrios.class, id);
                barrios.getBarriosPK();
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
            Municipios municipios = barrios.getMunicipios();
            if (municipios != null) {
                municipios.getBarriosCollection().remove(barrios);
                municipios = em.merge(municipios);
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

    public Barrios findBarrios(BarriosPK id) {
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
