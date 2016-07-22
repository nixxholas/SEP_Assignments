package A6_servlets;

import CorporateManagement.ItemManagement.ItemManagementBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RawMaterialManagement_AddRawMaterialServlet extends HttpServlet {

    @EJB
    private ItemManagementBeanLocal itemManagementBean;
    String result;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            // Let's slot the source at the top as we need it everywhere below
            String source = request.getParameter("source");
            
            String SKU = "";
            Integer _length = 0, width = 0, height = 0;
            /**
             * Regex String for identifying certain
             * types of datatype
             * 
             * And in this case, numeric inputs only.
             */
            String regexNumericOnly = "[0-9]+";
            String regexCheckSKU = "[Rr][Mm][0-9]+$";
            
            // Get the SKU input then run the RegEx checks
            String preSKU = request.getParameter("SKU");
            String preLength = request.getParameter("length");
            String preWidth = request.getParameter("width");
            String preHeight = request.getParameter("height");
            
            /**
             * This enforces the user to only key in integers into the
             * input box, such that we will not need to bother the user to key 
             * in any "RM" before the integers.
             * 
             * However, because the assignment clearly indicated 
             * the coding standards, this has been commented out for 
             * the proposed method
             */
            //            // If the SKU only contains integers ONLY,
            //            if (preSKU.matches(regexNumericOnly)) {
            //                // Then we'll properly let it go through
            //                SKU = "RM" + preSKU;
            //            } else {
            //                // Else we'll have to toss it back to tell the user that 
            //                // the data is bad
            //                result = "You have entered non-numeric values into the SKU number.";
            //                response.sendRedirect(source + result);
            //            }

            if (preSKU.matches(regexCheckSKU)) {
                // Then we'll properly let it go through
                SKU = preSKU;
            } else {
                result = "You have entered an invalid SKU numbering format.";
                response.sendRedirect(source + result);
            }
            
            if (preLength.matches(regexNumericOnly)) {
                // Then we'll properly let it go through
                // And then parse it into an integer
                _length = Integer.parseInt(preLength);
            } else {
                // Else we'll have to toss it back to tell the user that 
                // the data is bad
                result = "You have entered non-numeric values into the length input box.";
                response.sendRedirect(source + result);
            }
            
            if (preWidth.matches(regexNumericOnly)) {
                // Then we'll properly let it go through
                // And then parse it into an integer
                width = Integer.parseInt(preWidth);
            } else {
                // Else we'll have to toss it back to tell the user that 
                // the data is bad
                result = "You have entered non-numeric values into the width input box.";
                response.sendRedirect(source + result);
            }
                        
            if (preHeight.matches(regexNumericOnly)) {
                // Then we'll properly let it go through
                // And then parse it into an integer
                height = Integer.parseInt(preHeight);
            } else {
                // Else we'll have to toss it back to tell the user that 
                // the data is bad
                result = "You have entered non-numeric values into the height input box.";
                response.sendRedirect(source + result);
            }
                       
            // Nothing to check for these            
            String name = request.getParameter("name");
            String category = request.getParameter("category");
            String description = request.getParameter("description");
            
            System.out.println("source is " + source);
            
            if (!itemManagementBean.checkSKUExists(SKU)) {
                itemManagementBean.addRawMaterial(SKU, name, category, description, _length, width, height);
                result = "?goodMsg=Raw Material with SKU: " + SKU + " has been created successfully.";
                response.sendRedirect("RawMaterialManagement_RawMaterialServlet" + result);
            } else {
                result = "?errMsg=Failed to add raw material, SKU: " + SKU + " already exist.";
                response.sendRedirect(source + result);
            }
        } catch (Exception ex) {
            out.println(ex);
        } finally {
            out.close();
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
