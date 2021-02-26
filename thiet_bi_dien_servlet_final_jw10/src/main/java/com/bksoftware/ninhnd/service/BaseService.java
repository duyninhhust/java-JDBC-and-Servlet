package com.bksoftware.ninhnd.service;

import java.sql.SQLException;
import java.util.List;

public interface BaseService<T> {
    //tầng controller có nhiệm vụ nhận vào các thông của của đùng để xử lý
    //và lấy ra dữ liệu tương ứng bằng cách gọi tầng service
    //tâng service sẽ gọi đến tầng dao để thực hiện lấy dữ liệu trong database
    //=> Nhiệm vụ của tầng service sẽ kiểm soát đầu vào của người dùng truyền vào
    //trước khi gọi tầng dao => cho nên ở tầng dao có phương thức nào thì
    //tầng service cũng có phương thức tương tự

    List<T> findAll() throws SQLException;

    T findById(int id) throws SQLException;

    T insert(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    boolean delete(int id) throws SQLException;
}