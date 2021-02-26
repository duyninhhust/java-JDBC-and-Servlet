package com.bksoftware.ninhnd.filter;

import com.bksoftware.ninhnd.model.MyConnection;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

// cấu trúc api luôn tuân thủ /api/v1/product, api/v1/category, .....

/**
 * Mục đích cảu api sẽ tìm kiếm hoặc thao tacs với dữ liệu trong
 * database:
 *      +Phải có kết với database
 *      =>Phải đảm bảo khi/api/ thì phải có thằng connection thì mới thực hiên
 *      thao tác với database để trả về ét quả tuongw ứng
 *      =>Ngoài chức năng định nghĩa đây trả về file json
 *      thì ApìFilter còn chức năng kiểm soát connection trước khi request đến servlet
 *      + Để đảm bảo có connecttion trước khi requesst và servlet
 *          - Mỗi lần request vào api thì đều thực hiện kết nối
 *          -Kiểm soát kết nối bằng cách bằng neeú connection đã tồn tại
 *          thì chuyển đến servlet luôn, còn nếu chưa tồn tại thì mới tạo ra
 *          connection kết nôi mới
 *
 *  -Coppy code nhưng theo thú tự
 *
 */
@WebFilter(filterName = "APIFilter", urlPatterns = "/api/*")
public class APIFilter implements Filter {
    private MyConnection myConnection = new MyConnection();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        //Kiểm soát connection
        //đỂ kết nối đến db thì phải sử dụng hàm getConnection trong lớp MyConnection
        try {
            myConnection.connectDB();
        } catch (Exception e) {
            e.printStackTrace();

        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
