package com.bksoftware.ninhnd.service;

import com.bksoftware.ninhnd.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService extends BaseService<Product> {

    List<Product> sortBy(String field, boolean isASC) throws SQLException;

    List<Product> findByCategory(int idCategory) throws SQLException;

    List<Product> search(String name, String startDate, String endDate, Boolean soldOut,
                         int guarantee, int categoryId, int bought, int promotion) throws SQLException;
}
