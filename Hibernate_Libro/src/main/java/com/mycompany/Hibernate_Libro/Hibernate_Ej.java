/**
 *
 * @author Rodrigo
 */
package com.mycompany.Hibernate_Libro;


// Importa del paquete java "Hibernate_Libro.modelo.Autor" el archivo "Autor".
import com.mycompany.Hibernate_Libro.modelo.Autor;
// Importa del paquete java "Hibernate_Libro.modelo.Autor" el archivo "Autor".
import com.mycompany.Hibernate_Libro.modelo.Libro;
// Importa de la biblioteca/librería el paquete "List".
import java.util.List;
// Importa de la biblioteca/librería el paquete "JOptionPane".
import javax.swing.JOptionPane;
// Importa de la biblioteca/librería el paquete "Session".
import org.hibernate.Session;
// Importa de la biblioteca/librería el paquete "Transaction".
import org.hibernate.Transaction;
// Importa de la biblioteca/librería el paquete "Query".
import org.hibernate.query.Query;

public class Hibernate_Ej{
    public static void main(String[] args){
        System.out.println("\n\tInicializando la aplicación de Hibernate...");
        
        // Llama a los métodos "InsertarLibro" y "leerLibro".
        insertarLibro();
        leerLibro();
    }
    
    public static void insertarLibro(){
        Session session = null;
        Transaction  transaction = null;
        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            System.out.println("\n\tInsertando autores...");
            
            Autor autor1 = new Autor();
            autor1.setNombre("Roberto Santiago");
            autor1.setNacionalidad("Española");
            
            Autor autor2 = new Autor();
            autor2.setNombre("James Clear");
            autor2.setNacionalidad("Estadounidense");
            
            session.save(autor1);
            session.save(autor2);
            
            
            System.out.println("\n\tInsertando libros...");
            
            Libro libro1 = new Libro();
            libro1.setTitulo("El misterio de los árbitros dormidos");
            libro1.setIsbn("9784767839530"); 
            libro1.setAnioPublicacion(2013);
            libro1.setAutor(autor1);
            
            Libro libro2 = new Libro();
            libro2.setTitulo("El Misterio de Los Siete Goles En Propia Puerta");
            libro2.setIsbn("9781421381701"); 
            libro2.setAnioPublicacion(2013);
            libro2.setAutor(autor1);
            
            Libro libro3 = new Libro();
            libro3.setTitulo("El misterio del portero fantasma");
            libro3.setIsbn("9790345784629"); 
            libro3.setAnioPublicacion(2014);
            libro3.setAutor(autor1);
            
            Libro libro4 = new Libro();
            libro4.setTitulo("El misterio del ojo de halcón");
            libro4.setIsbn("9780316298454"); 
            libro4.setAnioPublicacion(2013);
            libro4.setAutor(autor1);
            
            Libro libro5 = new Libro();
            libro5.setTitulo("Hab́itos atoḿicos: cambios pequeños, resultados extraordinarios");
            libro5.setIsbn("9791204563026"); 
            libro5.setAnioPublicacion(2020);
            libro5.setAutor(autor2);
            
            session.save(libro1);
            session.save(libro2);
            session.save(libro3);
            session.save(libro4);
            session.save(libro5);
            
            
            transaction.commit();
            System.out.println("\n\tLos lisbros han sido añadidos junto a sus respectivos autores a la base de datos.");
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            
            JOptionPane.showConfirmDialog(null, "Error de inserción", "Error al insertar los autores en las bases de datos: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        } finally{
            if (session != null){
                session.close();
            }
        }
    }
    
    public static void leerLibro(){
        Session session = null;
        
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            
            // Operación CRUD.
            Query<Libro> query = session.createQuery("FROM libro", Libro.class);
            List<Libro> libros = query.list();
            
            System.out.println("\n\n\t<==================== LISTA de LIBROS ====================>\n\n");
            if (libros.isEmpty()){
                System.out.println("No se han encontrado libros en la base de datos.");
            } else{
                for (Libro libro : libros){
                    System.out.println("\n\t" + libro);
                }
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Error de sesiones", "'Session Factory' creation failed / La fabricación de sesiones ha fallado: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        } finally{
            if (session != null && session.isOpen()){ session.close(); }
        }
    }
}