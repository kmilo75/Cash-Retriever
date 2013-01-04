/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelovergara;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.DefaultEditorKit;
import modelovergara.bo.Municipios;
import modelovergara.bo.Roles;

/**
 *
 * @author Bruce Tape
 */
public class ModeloVergara {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//     ejemplo de inserccion de elementos en la base de datos
        EntityManagerFactory emf= Persistence.createEntityManagerFactory("ModeloVergaraPU");
        EntityManager em=emf.createEntityManager();
        
        Roles r=new Roles(1);
        r.setDescripcion("ADMINISTRADOR");
        
       insertar(r, em);
        
        
    }
    /**
     * Inserta un rol dentro de la base de datos
     * @param r rol
     * @param em entityManager
     */
    public static void insertar(Roles r, EntityManager em){
        try {
            em.getTransaction().begin();
            //           buscamos si existe el objeto con la llave 1 en roles
            Roles find = em.find(Roles.class, r.getIdroles());
            
            if (find == null) {
            em.persist(r);
            em.getTransaction().commit();
            System.out.println("Se insertó el elemento "+r);    
            }else{
                System.out.println("Ya existe el objeto en la base de datos. se actualizará");
                em.merge(r);
                em.flush();
                em.getTransaction().commit();
            }
            
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally{
            em.close();
        }
                
    }
            
    
    
}
