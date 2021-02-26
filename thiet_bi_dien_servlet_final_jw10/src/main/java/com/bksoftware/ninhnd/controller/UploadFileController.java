package com.bksoftware.ninhnd.controller;

import com.bksoftware.ninhnd.model.JsonResult;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "UploadFileController", value = "/api/v1/upload-file/*")
@MultipartConfig(fileSizeThreshold = 1024*1024*2,
maxRequestSize = 1024*1024*50,maxFileSize = 1024*1024*50)
//fileSizeThreshold nếu file truyền vào lớn hơn nguuowng quy định thì sữ thực hiện cơ chế ghi thẳng vào ổ
//đĩa không thông bộ đệm (sử dụng bộ đệm chắc chắc bit trong file sẽ đc giữ hoàn toàn
//nếu không dùng bộ đẹm sẽ bị maatss dữ liệu )

//maxrequestSize : kích thước tối đa xủa 1 request (1 request có thể chứa nhiều file)
//maxFileSize: kick thước tối đã của 1 file (1 request 1 file = maxRequestSize)
//để demo với api uploadfile phải có giao diện
/**
 * Khi upload file cùng 1 file có tên phần mở rroong trùng nhau thì file upload trước
 * bị ghi đè (giải quyết vấn đè này mà vẫn giữ nguyên tên file):
 *      + mỗi 1 file upload lên sẽ nằm trong 2 folder riêng (folder sẽ l à new Date().getTime())
 *      + mỗi 1 request sẽ là 1 thư mục (new Date().getTime());
 */
public class UploadFileController extends HttpServlet {

    private JsonResult jsonResult = new JsonResult();

    private Gson gson = new Gson();

    public static final String SAVE_DIRECTORY = "file-upload";

    private String getFileName(Part part) {
        String fileNameRs = null;
        //thuộc tính header của đối tượng part tương ứng với key content-disposition
        // thì sẽ chưa một một chuỗi có định dạng tương tự
        // form-data; name="file"; filename="C:\a\file1.zip"
        //từ chuỗi này mình lấy ra tên file và phần mở rộng của file.
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s: items) {
            if(s.trim().startsWith("filename")) {
                //filename="C:\file1.zip"
                fileNameRs = s.substring(s.indexOf("=") + 2, s.length() - 1);
                fileNameRs = fileNameRs.replace("\\", "/");
                int i = fileNameRs.lastIndexOf("/");
                fileNameRs = fileNameRs.substring(i + 1);
            }
        }
        return fileNameRs;
    }

    private File getFolderUpload(){
        //chỉ rõ đường dẫn tuyệt đối vào folder muốn chưa file upload lên
        String appPath = "D:\\";
        appPath += SAVE_DIRECTORY;
                //xảy ra 2 trường hợp thư mục đã tồn tại hoặc chưa tồn tại
        File folderupload = new File(appPath);
        if (folderupload.exists()){
            folderupload.mkdirs();
        }
        return folderupload;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rs = null;

        try {
            //lấy được các file người dùng truyền lên
            Collection<Part> partCollection = request.getParts();

            //duyệt từng file 1 để lưu vào
            List<String> listFileName = new ArrayList<>();
            for (Part part:partCollection) {
                //khi uploadfile ngoài trừ lưu file thành công thì phải giữu được tên file của file vừa được lưu
                String fileName = getFileName(part); // tạo ra 1 hàm để lấy fileName đầu vào Part
                if (fileName != null){
                    //khi mà upload file lên server thì file đó sẽ đuqọc upaload vào thư mục nào
                    //để quản lý đường dẫn vào thu mwucj nào thì cần viết thêm 1 hàm

                    //getAbsolutePath() trả về đường đẫn tuyệt đối của 1 file
                    String filePath = getFolderUpload().getAbsolutePath() + File.separator + fileName ;
                    System.out.println("Write file "+ filePath);
                    //thực hiên ghi file
                    part.write(filePath);
                    //muốn kết quả trả về cho client danh sách list file đã được upload
                    listFileName.add(SAVE_DIRECTORY + "/" + fileName);
                }
            }
            rs = gson.toJson(jsonResult.jsonSuccess(listFileName));
        }catch (Exception ex){
            ex.printStackTrace();
            rs = gson.toJson(jsonResult.jsonFail("Tải file thất bại"));
        }

        response.getWriter().println(rs);

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
