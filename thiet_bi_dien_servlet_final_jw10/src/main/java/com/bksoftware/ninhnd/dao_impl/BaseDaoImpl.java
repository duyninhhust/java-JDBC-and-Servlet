package com.bksoftware.ninhnd.dao_impl;

import com.bksoftware.ninhnd.dao.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    public List<T> getList(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<T>();
        //cho con trỏ resulteSet chạy lần lượt qua các bản ghi bằng hàm .next() lần đầu tiên khi
        //.next() con trỏ resultSet mới được trỏ vào bản ghi đầu tiền
        while (resultSet.next()) {
            T t = getObject(resultSet);
            if(t != null) list.add(t);
        }
        return list;
    }
}