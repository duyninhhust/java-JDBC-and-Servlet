package com.bksoftware.ninhnd.service_impl;

import com.bksoftware.ninhnd.dao.CategoryDao;
import com.bksoftware.ninhnd.dao_impl.CategoryDaoImpl;
import com.bksoftware.ninhnd.model.Category;
import com.bksoftware.ninhnd.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao = new CategoryDaoImpl();

    public List<Category> findAll() throws SQLException {
        return categoryDao.findAll();
    }

    public Category findById(int id) throws SQLException {
        //kiểm soát vì id luôn là một số > 0 nên
        //nếu người dùng truyền vào một id < 1 thì sẽ trả về null không
        //phải thực hiện câu lệnh để tìm kiếm nữa
        //người lại nếu truyền id > 0 thì sẽ gọi tầng dao để thực hiện
        //câu lệnh tìm kiếm và lấy kết quả trả về;
        return id > 0 ? categoryDao.findById(id) : null;
    }

    public Category insert(Category category) throws SQLException {
        category.setDeleted(false);
        return categoryDao.insert(category);
    }

    public boolean update(Category category) throws SQLException {
        return category.getId() > 0 ? categoryDao.update(category) : false;
    }

    public boolean delete(int id) throws SQLException {
        return id > 0 ? categoryDao.delete(id) : false;
    }
}
