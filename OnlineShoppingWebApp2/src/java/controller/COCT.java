/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Products;
import model.Shoppingcart;
import model.ShoppingcartPK;
import model.ShoppingcartTable;
import utilities.UpdatedShoppingcartList;

/**
 *
 * @author tleku
 */
public class COCT extends HttpServlet {

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
            UpdatedShoppingcartList.finishUpdating(this.getServletContext(), checkUser);
            this.getServletContext().notifyAll(); 
        }
        
        double totalPrice  = 0;
        String totalText = "";
        ShoppingcartPK cartPK = (ShoppingcartPK) request.getSession().getAttribute("cartPK");
        List<Shoppingcart> movList = ShoppingcartTable.findListShoppingcartById(cartPK);
        for (Shoppingcart cart : movList) {
            Products mov = cart.getProducts();
            totalPrice += mov.getPrice() * cart.getQuantity();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        totalText = "The total amount is $" + decimalFormat.format(totalPrice);
        
        request.setAttribute("totalText", totalText);
        request.getRequestDispatcher("RSC").forward(request, response);
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
