package com.bksoftware.ninhnd.model;

import com.bksoftware.ninhnd.common.AppConfig;

import java.sql.*;

public class MyConnection {

    //thường một project thì chỉ kết nối 1 database nên mình kết nối 1 lần sau dùng lại
    public static Connection connection = null;

    /**
     * Các bước để kết nối đến database
     * B1: Kiểm tra dirver jdbc đã tồn tại hay chưa bằng hàm driverTest();
     * B2: thực hiện kết nối với db bằng hàm connectDB cần có các tham số đầu vào url, username, passowrd;
     * B3: Dùng prepare và pareparUpdate để thực hiện các câu lệnh thao tác với db
     * B4: đóng kết nối
     * */

    public void driverTest() throws ClassNotFoundException{
        try {
            Class.forName(AppConfig.DRIVER);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("JDBC Driver not found" + e.getMessage());
        }
    }

    public Connection connectDB() throws ClassNotFoundException, SQLException {
        if (connection == null ){
            driverTest();
//        thực hiện kết nôi và lấy ra đối tượng connection
            try {
                //sử dụng drivermanager,getconnection truyền vào 3 tham số để connecy
                connection = DriverManager.getConnection(AppConfig.URL_DATABASE, AppConfig.USERNAME, AppConfig.PASSWORD);
                if(connection != null) System.out.println("Connect DB successfully");
            } catch (SQLException e) {
                throw new SQLException("Connect DB fail " + e.getMessage());
            }
        }
        return  connection;
    }

    //sau khi kết nối thành công thì tạo ra các hàm để thực hiện các câu lệnh
    /**
     * Chia ra 2 loại câu lệnh:
     *      + Câu lệnh dùng để tìm kiếm dữ liệu: select
     *      + Các câu lệnh dùng để thay đổi dữ liệu: insert, update, delete
     * Tương ứng với 2 loại câu lệnh trên thì mình sẽ tạo ra 2 hàm
     *      + prapare: dùng để gọi các câu lệnh tìm kiếm dữ liệu
     *      + prapareUpdate: dùng để gọi các câu lệnh thay đổi dữ liệu
     */

    //hàm prepare: hàm này sẽ nhận vào một câu lệnh sql (String) và
    // trả về cho mình một đối tượng PreparedStatement đối tượng này dùng để
    //thực hiện câu lệnh sql vừa truyền vào
    // Lưu ý: để thực hiện được các câu lệnh sql thì có 2 đối tượng có thể đấp ứng được nhu cầu đấy
    //Statement, PreparedStatement tuy nhiên thằng
    // Statment sẽ không kiểm soat được lỗi bảo mật do sql injection
    //PreparedStatement sinh ra để khắc phục lỗi cho Statment
    public PreparedStatement prepare(String sql) {
        System.out.println(">> "+sql);
        //dùng connection để lấy ra đối tượng PreparedStatement
        try {
            //cần truyền thêm tham số thứ 2 vào hàm prepareStatement ResultSet.TYPE_SCROLL_SENSITIVE
            //ResultSet.TYPE_SCROLL_SENSITIVE cho phép con trỏ resultset
            // chạy từ bản ghi đầu tiền đến bản ghi cuối cùng.
            return connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    //hàm nhận vào một câu lệnh sql và trể một đối tượng PreparedSatement nhưng đối tượng này
    //dùng để thực hiện các câu lệnh thay đổi dữ liệu
    public PreparedStatement prepareUpdate(String sql) {
        System.out.println(">> "+sql);
        try {
            //cần truyền thêm tham số thứ 2 là Statement.RETURN_GENERATED_KEYS
            //Statement.RETURN_GENERATED_KEYS có tác dụng (giả sử khi thêm một bản ghi category thì
            // chỉ truyền tên và trường deleted và không được truyền id vì id là tự động tăng
            // thì tham số thứ 2 giúp chúng ta lấy được id sau khi thêm bản ghi thành công)
            return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    //sau khi kết nối và thao tác trên db thành công, nếu không làm việc tiếp thì sẽ phải giải phóng
    //connection
    public void closeConnection() throws SQLException {
        if(connection != null) {
            connection.close();
            System.out.println("Connection is closed");
        }
    }
}
