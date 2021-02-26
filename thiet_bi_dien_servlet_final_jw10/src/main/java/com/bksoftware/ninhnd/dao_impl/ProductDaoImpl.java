package com.bksoftware.ninhnd.dao_impl;

import com.bksoftware.ninhnd.dao.ProductDao;
import com.bksoftware.ninhnd.model.MyConnection;
import com.bksoftware.ninhnd.model.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDao {

    private MyConnection myConnection = new MyConnection();

    public Product getObject(ResultSet resultSet) throws SQLException {
        Product product = null;
        product = new Product(resultSet.getInt("id"), resultSet.getString("name"),
                resultSet.getDouble("price"), resultSet.getDate("create_time"),
                resultSet.getBoolean("deleted"), resultSet.getString("image"),
                resultSet.getString("introduction"),
                resultSet.getString("specification"),
                resultSet.getBoolean("sold_out"), resultSet.getInt("guarantee"),
                resultSet.getInt("category_id"), resultSet.getInt("bought"),
                resultSet.getInt("promotion"));
        return product;
    }

    public List<Product> findAll() throws SQLException {
        return null;
    }

    public Product findById(int id) throws SQLException {
        return null;
    }

    public Product insert(Product product) throws SQLException {
        return null;
    }

    public boolean update(Product product) throws SQLException {
        return false;
    }

    public boolean delete(int id) throws SQLException {
        return false;
    }

    //Hàm sort by nhận vào trường (field) muốn sắp xếp
    //và sắp xếp trường đấy tăng dần hoặc giảm dần
    //isASC = true thì sẽ là sắp xếp tăng dần
    //isASC = false thì sẽ là sắp xếp giảm dần DESC
    public List<Product> sortBy(String field, boolean isASC) throws SQLException {
        String sql = "select * from product where deleted = false order by " + field + (isASC ? " ASC" : " DESC");
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }

    //Hàm sử dụng để lấy ra danh sách các product có cùng category và phải đẩm bảo category chưa bị xóa
    //dể giải quyết được vấn đè trên thì phải thực hiện tìm kiếm thông tin các bản ghi trên 2 bảng product để lấy
    //ra thông tin của product và bảng category để kiểm tra xem category có id tương ứng đã bị xóa hay chưa
    public List<Product> findByCategory(int idCategory) throws SQLException {
        //product as p để rút gọn cú pháp
        //p.* đẻ lấy chỉ lấy tất cả thông tin ở trong bảng product
        //p.category_id = c.id là điều kiện nối
        //distinct để tránh trường hợp trùng lặp lặp tìm kiếm chỉ xảy ra khi tìm kiếm thông tin trên nhiều bảng một lần
        String sql = "select distinct p.* from product as p, category as c where c.deleted = false and c.id = ? and p.category_id = c.id";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1, idCategory);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }

    public List<Product> search(String name, String startDate, String endDate, Boolean soldOut, int guarantee, int categoryId, int bought, int promotion) throws SQLException {
        String sql = "select p.* from product as p, category as c where p.deleted = false and " +
                "p.name like ? and " +
                "p.create_time >= ? and " +
                "p.create_time <= ? and " +
                "(? is null or p.sold_out = ?) and " +
                "(? = -1 or p.guarantee >= ?) and " +
                "(? = -1 or p.bought >= ?) and " +
                "(? = -1 or p.promotion >= ?) and " +
                "(? > 0 or (c.deleted = false and c.id = ? and p.category_id = c.id))";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setString(1,"%" +name+"%");
        preparedStatement.setString(2, startDate == null ? "0000-01-01" : startDate);
        preparedStatement.setString(3, endDate == null ? "9999-12-31" : endDate);
        if(soldOut == null) {
            preparedStatement.setString(4, null);
            preparedStatement.setBoolean(5, true);
        } else {
            preparedStatement.setString(4, "");
            preparedStatement.setBoolean(5, soldOut);
        }
        preparedStatement.setInt(6, guarantee);
        preparedStatement.setInt(7, guarantee);
        preparedStatement.setInt(8, bought);
        preparedStatement.setInt(9, bought);
        preparedStatement.setInt(10,  promotion);
        preparedStatement.setInt(11, promotion);
        preparedStatement.setInt(12, categoryId);
        preparedStatement.setInt(13, categoryId);
        ResultSet resultSet = preparedStatement.executeQuery();
        return getList(resultSet);
    }
}
