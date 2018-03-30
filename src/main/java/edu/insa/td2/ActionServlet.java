/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.insa.td2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.insa.td2.Service.Personne;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aleconte
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        Service service = new Service();
        
        String action = request.getParameter("action");
        if(action.equals("ConsulterListePersonnes")) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonArray jsonListe = new JsonArray();
            for(Personne p : service.consulterListePersonnes()) {
                JsonObject jsonPersonne = new JsonObject();
                jsonPersonne.addProperty("id", p.getId());
                jsonPersonne.addProperty("civilite", p.getCivilite());
                jsonPersonne.addProperty("nom", p.getNom());
                jsonPersonne.addProperty("prenom", p.getPrenom());
                jsonPersonne.addProperty("mail", p.getMail());
                jsonPersonne.addProperty("adresse", p.getAdresse());
                jsonListe.add(jsonPersonne);
            }
            JsonObject container = new JsonObject();
            container.add("personnes", jsonListe);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson(container));
            }
        } else if(action.equals("ConsulterDetailPersonne")) {
            long id = Long.valueOf(request.getParameter("id"));
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Personne p = service.consulterDetailPersonne(id);
            JsonObject jsonPersonne = new JsonObject();
            jsonPersonne.addProperty("id", p.getId());
            jsonPersonne.addProperty("civilite", p.getCivilite());
            jsonPersonne.addProperty("nom", p.getNom());
            jsonPersonne.addProperty("prenom", p.getPrenom());
            jsonPersonne.addProperty("mail", p.getMail());
            jsonPersonne.addProperty("adresse", p.getAdresse());
            
            JsonObject container = new JsonObject();
            container.add("details", jsonPersonne);

            try (PrintWriter out = response.getWriter()) {
                out.println(gson.toJson(container));
            }
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
