package com.bksoftware.ninhnd.controller;

import com.bksoftware.ninhnd.model.JsonResult;
import com.bksoftware.ninhnd.model.Product;
import com.bksoftware.ninhnd.service.ProductService;
import com.bksoftware.ninhnd.service_impl.ProductServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ProductController", value = "/api/v1/product/*")
public class ProductController extends HttpServlet {

    private ProductService productService = new ProductServiceImpl() ;

    private JsonResult jsonResult = new JsonResult();

    private Gson gson = new Gson();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //controller => service => dao

        String pathInfo = request.getPathInfo();
        String rs = null;
        if (pathInfo.equals("/find-all")){
            try {
                List<Product> list = productService.findAll();
                rs = gson.toJson(jsonResult.jsonSuccess(list));
            } catch (Exception e) {
                e.printStackTrace();
                rs = gson.toJson(jsonResult.jsonFail("product find all fail "));
            }
        }else if (pathInfo.equals("/find-by-id")){
            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Product product = productService.findById(id);
                rs = gson.toJson(jsonResult.jsonSuccess(product == null ? "" : product));
            } catch (Exception exception) {
               exception.printStackTrace();
               rs = gson.toJson(jsonResult.jsonFail("product find by id fail "));

            }
        }else {
            response.sendError(404);    
            return;
        }

        response.getWriter().println(rs);


    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
