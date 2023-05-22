<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>

</body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
<script>
    let token = '${token}';

    if(token)
    {
        localStorage.setItem("token", token);
    }
    else
        token = localStorage.getItem("token");

    $.ajax({
        url: 'http://localhost:8080/userPage',
        type: "GET",
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function() {
            setTimeout(function() {
                window.location.href = 'user_page.jsp';
            }, 333);
        }
    });
</script>
</html>
