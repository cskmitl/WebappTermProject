/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.ArrayList;
import javax.servlet.ServletContext;

/**
 *
 * @author sarun
 */
public class UpdatedShoppingcartList {
     public static boolean isUpdating(ServletContext sc, int id) {
        ArrayList<Integer> idUsed = (ArrayList<Integer>) sc.getAttribute("idUsed");
        if (idUsed == null) {
            idUsed = new ArrayList<>();
            idUsed.add(id);
            sc.setAttribute("idUsed", idUsed);
        }
        else {
            if (idUsed.indexOf(id) == -1) {
                idUsed.add(id);
            }
            else {
                return true;
            }
        }
        return false;
    }
    public static void finishUpdating(ServletContext sc, Integer id) {
         ArrayList<Integer> idUsed = (ArrayList<Integer>) sc.getAttribute("idUsed");
         if (idUsed == null) return;
         idUsed.remove(id);
        
    }
}
