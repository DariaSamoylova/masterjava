<%--
  Created by IntelliJ IDEA.
  User: Даша
  Date: 19.05.2019
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<html>
<head>
    <meta charset="UTF-8"/>
    <title>File Upload Sample</title>
</head>
<body>
<form action="/upload" enctype="multipart/form-data" method="post">
    <p>
        <label>Select a file: </label>
        <input type="file" name="file"/>
    </p>
    <input type="submit" value="Upload" />
</form>
</body>
</html>