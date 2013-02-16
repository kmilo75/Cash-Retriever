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
import modelovergara.bo.Roles;
import modelovergara.bo.Barrios;
import modelovergara.bo.Deudas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelovergara.bo.Personas;
import modelovergara.bo.Salidas;
import modelovergara.bo.Usuarios;
import co.com.beanslab.cashretriver.modelo.facade.exceptions.IllegalOrphanException;
import co.com.beanslab.cashretriver.modelo.facade.exceptions.NonexistentEntityException;

/**
 *
 * @author bruce
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) {
        if (personas.getDeudasCollection() == null) {
            personas.setDeudasCollection(new ArrayList<Deudas>());
        }
        if (personas.getDeudasCollection1() == null) {
            personas.setDeudasCollection1(new ArrayList<Deudas>());
        }
        if (personas.getSalidasCollection() == null) {
            personas.setSalidasCollection(new ArrayList<Salidas>());
        }
        if (personas.getUsuariosCollection() == null) {
            personas.setUsuariosCollection(new ArrayList<Usuarios>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Roles rol = personas.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getIdroles());
                personas.setRol(rol);
            }
            Barrios barrio = personas.getBarrio();
            if (barrio != null) {
                barrio = em.getReference(barrio.getClass(), barrio.getIdbarrios());
                personas.setBarrio(barrio);
            }
            Collection<Deudas> attachedDeudasCollection = new ArrayList<Deudas>();
            for (Deudas deudasCollectionDeudasToAttach : personas.getDeudasCollection()) {
                deudasCollectionDeudasToAttach = em.getReference(deudasCollectionDeudasToAttach.getClass(), deudasCollectionDeudasToAttach.getIddeudas());
                attachedDeudasCollection.add(deudasCollectionDeudasToAttach);
            }
            personas.setDeudasCollection(attachedDeudasCollection);
            Collection<Deudas> attachedDeudasCollection1 = new ArrayList<Deudas>();
            for (Deudas deudasCollection1DeudasToAttach : personas.getDeudasCollection1()) {
                deudasCollection1DeudasToAttach = em.getReference(deudasCollection1DeudasToAttach.getClass(), deudasCollection1DeudasToAttach.getIddeudas());
                attachedDeudasCollection1.add(deudasCollection1DeudasToAttach);
            }
            personas.setDeudasCollection1(attachedDeudasCollection1);
            Collection<Salidas> attachedSalidasCollection = new ArrayList<Salidas>();
            for (Salidas salidasCollectionSalidasToAttach : personas.getSalidasCollection()) {
                salidasCollectionSalidasToAttach = em.getReference(salidasCollectionSalidasToAttach.getClass(), salidasCollectionSalidasToAttach.getIdsalidas());
                attachedSalidasCollection.add(salidasCollectionSalidasToAttach);
            }
            personas.setSalidasCollection(attachedSalidasCollection);
            Collection<Usuarios> attachedUsuariosCollection = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionUsuariosToAttach : personas.getUsuariosCollection()) {
                usuariosCollectionUsuariosToAttach = em.getReference(usuariosCollectionUsuariosToAttach.getClass(), usuariosCollectionUsuariosToAttach.getUsuariosPK());
                attachedUsuariosCollection.add(usuariosCollectionUsuariosToAttach);
            }
            personas.setUsuariosCollection(attachedUsuariosCollection);
            em.persist(personas);
            if (rol != null) {
                rol.getPersonasCollection().add(personas);
                rol = em.merge(rol);
            }
            if (barrio != null) {
                barrio.getPersonasCollection().add(personas);
                barrio = em.merge(barrio);
            }
            for (Deudas deudasCollectionDeudas : personas.getDeudasCollection()) {
                Personas oldMorosoOfDeudasCollectionDeudas = deudasCollectionDeudas.getMoroso();
                deudasCollectionDeudas.setMoroso(personas);
                deudasCollectionDeudas = em.merge(deudasCollectionDeudas);
                if (oldMorosoOfDeudasCollectionDeudas != null) {
                    oldMorosoOfDeudasCollectionDeudas.getDeudasCollection().remove(deudasCollectionDeudas);
                    oldMorosoOfDeudasCollectionDeudas = em.merge(oldMorosoOfDeudasCollectionDeudas);
                }
            }
            for (Deudas deudasCollection1Deudas : personas.getDeudasCollection1()) {
                Personas oldCobradorOfDeudasCollection1Deudas = deudasCollection1Deudas.getCobrador();
                deudasCollection1Deudas.setCobrador(personas);
                deudasCollection1Deudas = em.merge(deudasCollection1Deudas);
                if (oldCobradorOfDeudasCollection1Deudas != null) {
                    oldCobradorOfDeudasCollection1Deudas.getDeudasCollection1().remove(deudasCollection1Deudas);
                    oldCobradorOfDeudasCollection1Deudas = em.merge(oldCobradorOfDeudasCollection1Deudas);
                }
            }
            for (Salidas salidasCollectionSalidas : personas.getSalidasCollection()) {
                Personas oldCobradorOfSalidasCollectionSalidas = salidasCollectionSalidas.getCobrador();
                salidasCollectionSalidas.setCobrador(personas);
                salidasCollectionSalidas = em.merge(salidasCollectionSalidas);
                if (oldCobradorOfSalidasCollectionSalidas != null) {
                    oldCobradorOfSalidasCollectionSalidas.getSalidasCollection().remove(salidasCollectionSalidas);
                    oldCobradorOfSalidasCollectionSalidas = em.merge(oldCobradorOfSalidasCollectionSalidas);
                }
            }
            for (Usuarios usuariosCollectionUsuarios : personas.getUsuariosCollection()) {
                Personas oldPersonasOfUsuariosCollectionUsuarios = usuariosCollectionUsuarios.getPersonas();
                usuariosCollectionUsuarios.setPersonas(personas);
                usuariosCollectionUsuarios = em.merge(usuariosCollectionUsuarios);
                if (oldPersonasOfUsuariosCollectionUsuarios != null) {
                    oldPersonasOfUsuariosCollectionUsuarios.getUsuariosCollection().remove(usuariosCollectionUsuarios);
                    oldPersonasOfUsuariosCollectionUsuarios = em.merge(oldPersonasOfUsuariosCollectionUsuarios);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getIdpersonas());
            Roles rolOld = persistentPersonas.getRol();
            Roles rolNew = personas.getRol();
            Barrios barrioOld = persistentPersonas.getBarrio();
            Barrios barrioNew = personas.getBarrio();
            Collection<Deudas> deudasCollectionOld = persistentPersonas.getDeudasCollection();
            Collection<Deudas> deudasCollectionNew = personas.getDeudasCollection();
            Collection<Deudas> deudasCollection1Old = persistentPersonas.getDeudasCollection1();
            Collection<Deudas> deudasCollection1New = personas.getDeudasCollection1();
            Collection<Salidas> salidasCollectionOld = persistentPersonas.getSalidasCollection();
            Collection<Salidas> salidasCollectionNew = personas.getSalidasCollection();
            Collection<Usuarios> usuariosCollectionOld = persistentPersonas.getUsuariosCollection();
            Collection<Usuarios> usuariosCollectionNew = personas.getUsuariosCollection();
            List<String> illegalOrphanMessages = null;
            for (Deudas deudasCollectionOldDeudas : deudasCollectionOld) {
                if (!deudasCollectionNew.contains(deudasCollectionOldDeudas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deudas " + deudasCollectionOldDeudas + " since its moroso field is not nullable.");
                }
            }
            for (Deudas deudasCollection1OldDeudas : deudasCollection1Old) {
                if (!deudasCollection1New.contains(deudasCollection1OldDeudas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Deudas " + deudasCollection1OldDeudas + " since its cobrador field is not nullable.");
                }
            }
            for (Salidas salidasCollectionOldSalidas : salidasCollectionOld) {
                if (!salidasCollectionNew.contains(salidasCollectionOldSalidas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Salidas " + salidasCollectionOldSalidas + " since its cobrador field is not nullable.");
                }
            }
            for (Usuarios usuariosCollectionOldUsuarios : usuariosCollectionOld) {
                if (!usuariosCollectionNew.contains(usuariosCollectionOldUsuarios)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Usuarios " + usuariosCollectionOldUsuarios + " since its personas field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getIdroles());
                personas.setRol(rolNew);
            }
            if (barrioNew != null) {
                barrioNew = em.getReference(barrioNew.getClass(), barrioNew.getIdbarrios());
                personas.setBarrio(barrioNew);
            }
            Collection<Deudas> attachedDeudasCollectionNew = new ArrayList<Deudas>();
            for (Deudas deudasCollectionNewDeudasToAttach : deudasCollectionNew) {
                deudasCollectionNewDeudasToAttach = em.getReference(deudasCollectionNewDeudasToAttach.getClass(), deudasCollectionNewDeudasToAttach.getIddeudas());
                attachedDeudasCollectionNew.add(deudasCollectionNewDeudasToAttach);
            }
            deudasCollectionNew = attachedDeudasCollectionNew;
            personas.setDeudasCollection(deudasCollectionNew);
            Collection<Deudas> attachedDeudasCollection1New = new ArrayList<Deudas>();
            for (Deudas deudasCollection1NewDeudasToAttach : deudasCollection1New) {
                deudasCollection1NewDeudasToAttach = em.getReference(deudasCollection1NewDeudasToAttach.getClass(), deudasCollection1NewDeudasToAttach.getIddeudas());
                attachedDeudasCollection1New.add(deudasCollection1NewDeudasToAttach);
            }
            deudasCollection1New = attachedDeudasCollection1New;
            personas.setDeudasCollection1(deudasCollection1New);
            Collection<Salidas> attachedSalidasCollectionNew = new ArrayList<Salidas>();
            for (Salidas salidasCollectionNewSalidasToAttach : salidasCollectionNew) {
                salidasCollectionNewSalidasToAttach = em.getReference(salidasCollectionNewSalidasToAttach.getClass(), salidasCollectionNewSalidasToAttach.getIdsalidas());
                attachedSalidasCollectionNew.add(salidasCollectionNewSalidasToAttach);
            }
            salidasCollectionNew = attachedSalidasCollectionNew;
            personas.setSalidasCollection(salidasCollectionNew);
            Collection<Usuarios> attachedUsuariosCollectionNew = new ArrayList<Usuarios>();
            for (Usuarios usuariosCollectionNewUsuariosToAttach : usuariosCollectionNew) {
                usuariosCollectionNewUsuariosToAttach = em.getReference(usuariosCollectionNewUsuariosToAttach.getClass(), usuariosCollectionNewUsuariosToAttach.getUsuariosPK());
                attachedUsuariosCollectionNew.add(usuariosCollectionNewUsuariosToAttach);
            }
            usuariosCollectionNew = attachedUsuariosCollectionNew;
            personas.setUsuariosCollection(usuariosCollectionNew);
            personas = em.merge(personas);
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getPersonasCollection().remove(personas);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getPersonasCollection().add(personas);
                rolNew = em.merge(rolNew);
            }
            if (barrioOld != null && !barrioOld.equals(barrioNew)) {
                barrioOld.getPersonasCollection().remove(personas);
                barrioOld = em.merge(barrioOld);
            }
            if (barrioNew != null && !barrioNew.equals(barrioOld)) {
                barrioNew.getPersonasCollection().add(personas);
                barrioNew = em.merge(barrioNew);
            }
            for (Deudas deudasCollectionNewDeudas : deudasCollectionNew) {
                if (!deudasCollectionOld.contains(deudasCollectionNewDeudas)) {
                    Personas oldMorosoOfDeudasCollectionNewDeudas = deudasCollectionNewDeudas.getMoroso();
                    deudasCollectionNewDeudas.setMoroso(personas);
                    deudasCollectionNewDeudas = em.merge(deudasCollectionNewDeudas);
                    if (oldMorosoOfDeudasCollectionNewDeudas != null && !oldMorosoOfDeudasCollectionNewDeudas.equals(personas)) {
                        oldMorosoOfDeudasCollectionNewDeudas.getDeudasCollection().remove(deudasCollectionNewDeudas);
                        oldMorosoOfDeudasCollectionNewDeudas = em.merge(oldMorosoOfDeudasCollectionNewDeudas);
                    }
                }
            }
            for (Deudas deudasCollection1NewDeudas : deudasCollection1New) {
                if (!deudasCollection1Old.contains(deudasCollection1NewDeudas)) {
                    Personas oldCobradorOfDeudasCollection1NewDeudas = deudasCollection1NewDeudas.getCobrador();
                    deudasCollection1NewDeudas.setCobrador(personas);
                    deudasCollection1NewDeudas = em.merge(deudasCollection1NewDeudas);
                    if (oldCobradorOfDeudasCollection1NewDeudas != null && !oldCobradorOfDeudasCollection1NewDeudas.equals(personas)) {
                        oldCobradorOfDeudasCollection1NewDeudas.getDeudasCollection1().remove(deudasCollection1NewDeudas);
                        oldCobradorOfDeudasCollection1NewDeudas = em.merge(oldCobradorOfDeudasCollection1NewDeudas);
                    }
                }
            }
            for (Salidas salidasCollectionNewSalidas : salidasCollectionNew) {
                if (!salidasCollectionOld.contains(salidasCollectionNewSalidas)) {
                    Personas oldCobradorOfSalidasCollectionNewSalidas = salidasCollectionNewSalidas.getCobrador();
                    salidasCollectionNewSalidas.setCobrador(personas);
                    salidasCollectionNewSalidas = em.merge(salidasCollectionNewSalidas);
                    if (oldCobradorOfSalidasCollectionNewSalidas != null && !oldCobradorOfSalidasCollectionNewSalidas.equals(personas)) {
                        oldCobradorOfSalidasCollectionNewSalidas.getSalidasCollection().remove(salidasCollectionNewSalidas);
                        oldCobradorOfSalidasCollectionNewSalidas = em.merge(oldCobradorOfSalidasCollectionNewSalidas);
                    }
                }
            }
            for (Usuarios usuariosCollectionNewUsuarios : usuariosCollectionNew) {
                if (!usuariosCollectionOld.contains(usuariosCollectionNewUsuarios)) {
                    Personas oldPersonasOfUsuariosCollectionNewUsuarios = usuariosCollectionNewUsuarios.getPersonas();
                    usuariosCollectionNewUsuarios.setPersonas(personas);
                    usuariosCollectionNewUsuarios = em.merge(usuariosCollectionNewUsuarios);
                    if (oldPersonasOfUsuariosCollectionNewUsuarios != null && !oldPersonasOfUsuariosCollectionNewUsuarios.equals(personas)) {
                        oldPersonasOfUsuariosCollectionNewUsuarios.getUsuariosCollection().remove(usuariosCollectionNewUsuarios);
                        oldPersonasOfUsuariosCollectionNewUsuarios = em.merge(oldPersonasOfUsuariosCollectionNewUsuarios);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = personas.getIdpersonas();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
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
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getIdpersonas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Deudas> deudasCollectionOrphanCheck = personas.getDeudasCollection();
            for (Deudas deudasCollectionOrphanCheckDeudas : deudasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Deudas " + deudasCollectionOrphanCheckDeudas + " in its deudasCollection field has a non-nullable moroso field.");
            }
            Collection<Deudas> deudasCollection1OrphanCheck = personas.getDeudasCollection1();
            for (Deudas deudasCollection1OrphanCheckDeudas : deudasCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Deudas " + deudasCollection1OrphanCheckDeudas + " in its deudasCollection1 field has a non-nullable cobrador field.");
            }
            Collection<Salidas> salidasCollectionOrphanCheck = personas.getSalidasCollection();
            for (Salidas salidasCollectionOrphanCheckSalidas : salidasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Salidas " + salidasCollectionOrphanCheckSalidas + " in its salidasCollection field has a non-nullable cobrador field.");
            }
            Collection<Usuarios> usuariosCollectionOrphanCheck = personas.getUsuariosCollection();
            for (Usuarios usuariosCollectionOrphanCheckUsuarios : usuariosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Usuarios " + usuariosCollectionOrphanCheckUsuarios + " in its usuariosCollection field has a non-nullable personas field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Roles rol = personas.getRol();
            if (rol != null) {
                rol.getPersonasCollection().remove(personas);
                rol = em.merge(rol);
            }
            Barrios barrio = personas.getBarrio();
            if (barrio != null) {
                barrio.getPersonasCollection().remove(personas);
                barrio = em.merge(barrio);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
