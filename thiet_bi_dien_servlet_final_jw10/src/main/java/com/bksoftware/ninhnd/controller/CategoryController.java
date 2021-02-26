package com.bksoftware.ninhnd.controller;

import com.bksoftware.ninhnd.model.Category;
import com.bksoftware.ninhnd.model.JsonResult;
import com.bksoftware.ninhnd.service.CategoryService;
import com.bksoftware.ninhnd.service_impl.CategoryServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryController", value = "/api/v1/category/*")
public class CategoryController extends HttpServlet {

    private CategoryService categoryService = new CategoryServiceImpl();

    private JsonResult jsonResult = new JsonResult(); // em phải khởi tạo nó chứ không thì nó  = null sẽ lỗi

    private Gson gson = new Gson();


    // xử lý những api thêm dữ liệu
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //xử lí api liên quan đến thêm 1 ban ghi category
        String rs = null;
        try {
            //hàm insert cần truyền vào 1 đối tượng category
            //thì đối tuowngj category là dữ liệu ammaf người dùng cần truyền lên cho mình

            //Cách truyền dữ liêu từ client lên server
            /**
             * C1: truyền qua parameter, các thông tin của ng dùng đc truyền lên bằng queryString: key = value
             *  value là String, k thể truyền được đối tượng
             * C2: truyền thông tin qua body
             *          -chỉ có trong các method: PUT, POST, DELETE;
             *          -có thể truyenf đối tượng
             */

            //hướng dẫn lây thông tin từ body
            //chuyển 1 chuỗi json thành 1 đối tượng nhờ thu viện gson

            //tham số đầu tiên của hàm fromJson có thể là 1 chuỗi json haowcj là 1 bộ đọc để đọc chuôi json
            //request.getReader() chính là bộ đọc dùng để đọc thông tin người dùng truyền vào body

            Category category = gson.fromJson(request.getReader(),Category.class);

            rs = gson.toJson(jsonResult.jsonSuccess(categoryService.insert(category)));
        }
        catch (Exception ex){
            ex.printStackTrace();
            rs = gson.toJson(jsonResult.jsonFail("insert category fail"));
        }

        response.getWriter().println(rs);
    }
    //xử lí những api tìm keiems dữu liệu
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //controller=> service=>dao
        /**
         * Đối với  tìm kiếm dữ liệu trong service cung cấp 2  hàm
         *      -findALL
         *      -findById
         *  -api/v1/category/find-all =>findAll
         *  -api/v1/category/find-by-id => findById
         */
        String pathInfor = request.getPathInfo();
        String rs = null;
        if (pathInfor.equals("/find-all")){
            //gọi đến service findAll của category để lấy kết quả
            try {
                List<Category> list = categoryService.findAll();

                    rs = gson.toJson(jsonResult.jsonSuccess(list));


            } catch (Exception ex){
                ex.printStackTrace();
                rs = gson.toJson(jsonResult.jsonFail("category find all fail"));
                //kết quả trả về luôn là một chuỗi
                //client không nhận biết được
            }
            //

        }
        else  if (pathInfor.equals("/find-by-id")){
            //gọi đến service findById
            try {
                //đoạn này em phải truyền đúng id là int
                int id =Integer.parseInt(request.getParameter("id"));
                Category category = categoryService.findById(id);
                rs = gson.toJson(jsonResult.jsonSuccess(category == null ? "" :category));
            } catch (Exception e) {
                e.printStackTrace();
                rs = gson.toJson(jsonResult.jsonFail("category find by id fail"));
            }
        }
        else {
            //404
            //int status nó Http status code
            response.sendError(404);
            return;
        }

        response.getWriter().println(rs);


    }
    //xử lí những api xóa dữ liệu
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //gọi đến service xóa category
        //tham số đầu vào của hàm delete chỉ là id int
        //chỉ cần lấy thông tin bằng param
        String rs = null;
        try {
            int id  = Integer.parseInt(request.getParameter("id"));
             rs = gson.toJson(jsonResult.jsonSuccess(categoryService.delete(id))) ;
        }catch (Exception ex){
            ex.printStackTrace();
            rs = gson.toJson(jsonResult.jsonFail("Delete category fail"));
        }

        response.getWriter().println(rs);
    }
    // xử lí những api sửa dữ liệu
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //sẽ gọi đến hàm update trong service
        //hàm update trong service cần truyền vào tham sô gì
        //trả về cho mình tham sô gì
        //ở tham sô truyền vào khác gì so với tham sô truyền
        //vào ở hàm insert
        //phải dử dụng truyền thông tin bằng body
        String rs = null;
        try {
            Category category = gson.fromJson(request.getReader(),Category.class);
            rs = gson.toJson(jsonResult.jsonSuccess(categoryService.update(category)));
        }catch (Exception ex){
            ex.printStackTrace();
            rs = gson.toJson(jsonResult.jsonFail("update category fail"));
        }
        response.getWriter().println(rs);
    }


}
