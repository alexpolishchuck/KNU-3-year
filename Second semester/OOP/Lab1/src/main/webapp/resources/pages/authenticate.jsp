<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
  <style>
    html
    {
      height: 100%;
    }
    body
    {
      display: flex;
      flex-direction: column;
      height: 100%;
    }
    input
    {
      max-width: 50%;
    }
  </style>
</head>
<body>
<div class="container" style="display: flex; flex-direction: column; align-items: center;">

</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
<script>
  $(document).ready(function () {
    $("form").submit(function (event) {
      let formData = {
        name: $("#name").val(),
        password: $("#password").val()
      };

      $.ajax({
        type: "POST",
        url: "login",
        data: formData,
        encode: true,
        success : function(token) {
          if(token && token.length !== 0)
          {
            localStorage.setItem("token", token);
            preloadFunc();
          }

        }
      });

      event.preventDefault();
    });//form.submit
  });//document.ready
  function preloadFunc()
  {
    let token = localStorage.getItem("token");
    let requestedUrl = '${requestedUrl}';

    if(!requestedUrl || requestedUrl.length === 0 || requestedUrl === "http://localhost:8080/")
    {
      requestedUrl = "/userPage";
    }

    if(token && token.length !== 0)
    {
      $.ajax({
        url: requestedUrl,
        type: "GET",
        beforeSend: function(xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + token);
        },
        success: function(response) {
          document.open();
          document.write(response);
          document.close();
        },
        error: function (err)
        {
          if(err.status === 404)
          {
            document.open();
            document.write("<h1>Page hasn't been found</h1>");
            document.close();
          }
        }
      });
    }
  }

  window.onpaint = preloadFunc();
</script>
</body>
</html>