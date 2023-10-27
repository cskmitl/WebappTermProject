/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author sarun
 */
public class ShoppingcartTable {

    public static List<Shoppingcart> findAllShopppingcart() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingWebApp2PU");
        EntityManager em = emf.createEntityManager();
        List<Shoppingcart> movCartList = null;
        try {
            movCartList = (List<Shoppingcart>) em.createNamedQuery("Shoppingcart.findAll").getResultList();         
        } catch (Exception e) {
            throw new RuntimeException(e);
            
        }
        finally {
            em.close();
            emf.close();
        }
        return movCartList;
    }
    
    public static List<Shoppingcart> findListShoppingcartById(ShoppingcartPK id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingWebApp2PU");
        EntityManager em = emf.createEntityManager();
        List<Shoppingcart> cartList = null;
        try {
            TypedQuery<Shoppingcart> query = em.createNamedQuery("Shoppingcart.findByCartId", Shoppingcart.class);
            query.setParameter("cartId", id.getCartId()); 
            cartList = query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            em.close();
            emf.close();
        }
        return cartList;
    }
    
    public static int insertShoppingcart(Shoppingcart cart) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingWebApp2PU");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(cart);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            e.printStackTrace();
            return 0; 
        } finally {
            em.close();
            emf.close();
        }
        return 1; 
    }
    
    public static void removeShoppingCartByCartId(int cartId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShoppingWebApp2PU");
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Shoppingcart> query = em.createQuery("SELECT s FROM Shoppingcart s WHERE s.shoppingcartPK.cartId = :cartId", Shoppingcart.class);
            query.setParameter("cartId", cartId);
            List<Shoppingcart> shoppingCarts = query.getResultList();

            for (Shoppingcart cart : shoppingCarts) {
                em.remove(cart);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            Logger.getLogger(ShoppingcartTable.class.getName()).log(Level.SEVERE, "Error removing shopping cart", e);
        } finally {
            em.close();
            emf.close();
        }
    }


}
