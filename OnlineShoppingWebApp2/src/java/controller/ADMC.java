/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProductsTable;
import model.ShoppingcartTable;
import model.Products;
import model.Shoppingcart;
import model.ShoppingcartPK;
import utilities.UpdatedShoppingcartList;

/**
 *
 * @author tleku
 */
public class ADMC extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int checkUser = 1;
        
        synchronized(this.getServletContext()) {
            if (UpdatedShoppingcartList.isUpdating(this.getServletContext(), checkUser)) {
                try {
                    this.getServletContext().wait(); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        String[] selectedMovies = request.getParameterValues("selectedMovies");
        int cartIdCheck = 1;
        
        List<Shoppingcart> cartList = ShoppingcartTable.findAllShopppingcart();
        for (int i = 0; i < cartList.size(); i++) {
            if (cartIdCheck <= cartList.get(i).getShoppingcartPK().getCartId()) {
                cartIdCheck = cartList.get(i).getShoppingcartPK().getCartId() + 1;
            }
        }
        
        ShoppingcartPK cartPK = new ShoppingcartPK();
        cartPK.setCartId(cartIdCheck);
        
        if (selectedMovies != null) {
            for (String movieIdStr : selectedMovies) {
                int movieId = Integer.parseInt(movieIdStr);
                String quantityStr = request.getParameter("quantity" + movieId);
                int quantity = Integer.parseInt(quantityStr);
                
                Products mov = ProductsTable.findProductsById(movieId);
               
                cartPK.setMovieId(mov.getId());
                
                Shoppingcart cart = new Shoppingcart(cartPK);
                cart.setProducts(mov);
                cart.setQuantity(quantity);
                
                ShoppingcartTable.insertShoppingcart(cart);  
            }
        
        HttpSession session = request.getSession();
        session.setAttribute("cartPK", cartPK);
        
        request.getRequestDispatcher("showCartList.jsp").forward(request, response);
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
