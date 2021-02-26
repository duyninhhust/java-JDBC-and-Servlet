<%--
  Created by IntelliJ IDEA.
  User: NinhND's PC
  Date: 11/2/2020
  Time: 7:48 PM
  To change this template use File | Settings | File Templates.
--%>

<%--Jsp là 1 trang chưa các cấu trúc html và thêm 1 sô thẻ đặ biệt của JSP để viết code java--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload File</title>
</head>
<body>
    <h1>Demo Upload File:</h1>
<%--    trong html có 1 cấu trúc thẻ form, thẻ này dùng để chứa các thông tin muốn sẻver--%>
<%--        sử dụng thẻ form để demo với chức năng upload file--%>
    <form action="api/v1/upload-file" method="post" enctype="multipart/form-data">
        <label>Chọn file cần upload:</label>
        <input type="file" name="file" multiple="multiple">
        <input type="submit" value="Tải lên">
    </form>
</body> 
</html>
