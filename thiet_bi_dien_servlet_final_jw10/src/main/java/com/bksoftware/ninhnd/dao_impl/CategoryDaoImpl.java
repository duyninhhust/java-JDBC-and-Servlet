package com.bksoftware.ninhnd.dao_impl;

import com.bksoftware.ninhnd.dao.CategoryDao;
import com.bksoftware.ninhnd.model.Category;
import com.bksoftware.ninhnd.model.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl extends BaseDaoImpl<Category> implements CategoryDao {

    private MyConnection myConnection = new MyConnection();

    //nhận vào một đối tượng resultSet đại diện cho một dòng (bản ghi) chứa các thông
    //tin của đối tượng
    //Nhiệm vụ hàm: đọc các thông tin của đối tượng bằng các hàm get kiểu dữ liệu tương ứng
    //để chuyển về một đối tượng trong java
    public Category getObject(ResultSet resultSet) throws SQLException {
        Category category  = null;
        //sử dụng contructor full tham số để tạo ra đối tượng và lần lượt
        //truyền vào các giái trị thuộc tính tương ứng từ resultSet
        category = new Category(resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getBoolean("deleted"));
        //đối với các đối tượng có nhiều thuộc tính đê tránh nhầm trường
        //có thể tạo đối tượng bằng contructor mặc định và sử dụng các hàm set để
        //truyền giá trị cho đối tượng
        return category;
    }

    //hàm getList tham số đầu vào đối tượng resultSet ở đây là một danh sách các bản ghi
    //khi tìm kiếm được
    //Nhiệm vụ của hàm là đọc từ bản ghi một chuyển thành đối tượng và trả về một list đối tượng
//    public List<Category> getList(ResultSet resultSet) throws SQLException {
//        List<Category> list = new ArrayList<Category>();
//        //cho con trỏ resulteSet chạy lần lượt qua các bản ghi bằng hàm .next() lần đầu tiên khi
//        //.next() con trỏ resultSet mới được trỏ vào bản ghi đầu tiền
//        while (resultSet.next()) {
//            Category category = getObject(resultSet);
//            if(category != null) list.add(category);
//        }
//        return list;
//    }

    public List<Category> findAll() throws SQLException {
        //select *(lấy tất cả các trường) from table(table là tên bảng muốn lấy dữ liệu)
        //where (điều kiện khi tìm kiếm cá bản ghi) deleted = false (tức chỉ lấy các bản ghi
        // chưa bị xóa)
        String sql = "select * from category where deleted = false";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        return getList(preparedStatement.executeQuery());
    }

    public Category findById(int id) throws SQLException {
        Category category = null;
        //kiểm tra deleted trước id để tối ưu thời gian câu lệnh
        String sql = "select * from category where deleted = false and id = ?";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        //kiểm tra xem có bản ghi hay không nếu có sử dụng hàm getObject để lấy ra đối tượng.
        if(resultSet.next()) {
            category = getObject(resultSet);
        }
        return category;
    }

    public Category insert(Category category) throws SQLException {
        Category newCategory = null;
        String sql = "insert into category (name, deleted) values (?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setBoolean(2, category.isDeleted());
        int rs = preparedStatement.executeUpdate();
        //nếu insert thành công rs là số bản ghi mình vừa thay đổi dữ liệu
        if(rs > 0) {
            //sử dụng hầm getGenerateKeys để trả về cho mình resultSete
            // chứa id tương ứng với bản ghi vừa thêm vào;
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            //kiểm tra xem có kết quả result trả về hay không
            if(resultSet.next()) {
                //resultSet.getLong(số thứ tự cột) ở đây chỉ trả về 1 ô nên stt sẽ là 1
                //getGeneratedKeys chứa kiểu trả về là long
                newCategory = findById((int) resultSet.getLong(1));
            }
        }
        return newCategory;
    }

    public boolean update(Category category) throws SQLException {
        boolean result = false;
        String sql = "update category set name = ? where id = ?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setInt(2, category.getId());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }

    public boolean delete(int id) throws SQLException {
        boolean result = false;
        //xóa một bản ghi tức là chuyển trường deleted của bản ghi ấy về true
        String sql = "update category set deleted = true where id = ?";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setInt(1, id);
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }
}
