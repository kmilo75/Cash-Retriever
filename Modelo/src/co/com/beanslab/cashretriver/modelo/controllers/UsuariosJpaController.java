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
import co.com.beanslab.cashretriver.modelo.Usuarios;
import co.com.beanslab.cashretriver.modelo.UsuariosPK;
import co.com.beanslab.cashretriver.modelo.controllers.exceptions.NonexistentEntityException;
import co.com.beanslab.cashretriver.modelo.controllers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Juan Camilo Cañas G
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuarios usuarios) throws PreexistingEntityException, Exception {
        if (usuarios.getUsuariosPK() == null) {
            usuarios.setUsuariosPK(new UsuariosPK());
        }
        usuarios.getUsuariosPK().setPersona(usuarios.getPersonas().getIdpersonas());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas personas = usuarios.getPersonas();
            if (personas != null) {
                personas = em.getReference(personas.getClass(), personas.getIdpersonas());
                usuarios.setPersonas(personas);
            }
            em.persist(usuarios);
            if (personas != null) {
                personas.getUsuariosCollection().add(usuarios);
                personas = em.merge(personas);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuarios(usuarios.getUsuariosPK()) != null) {
                throw new PreexistingEntityException("Usuarios " + usuarios + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws NonexistentEntityException, Exception {
        usuarios.getUsuariosPK().setPersona(usuarios.getPersonas().getIdpersonas());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getUsuariosPK());
            Personas personasOld = persistentUsuarios.getPersonas();
            Personas personasNew = usuarios.getPersonas();
            if (personasNew != null) {
                personasNew = em.getReference(personasNew.getClass(), personasNew.getIdpersonas());
                usuarios.setPersonas(personasNew);
            }
            usuarios = em.merge(usuarios);
            if (personasOld != null && !personasOld.equals(personasNew)) {
                personasOld.getUsuariosCollection().remove(usuarios);
                personasOld = em.merge(personasOld);
            }
            if (personasNew != null && !personasNew.equals(personasOld)) {
                personasNew.getUsuariosCollection().add(usuarios);
                personasNew = em.merge(personasNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                UsuariosPK id = usuarios.getUsuariosPK();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(UsuariosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getUsuariosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            Personas personas = usuarios.getPersonas();
            if (personas != null) {
                personas.getUsuariosCollection().remove(usuarios);
                personas = em.merge(personas);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(UsuariosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
