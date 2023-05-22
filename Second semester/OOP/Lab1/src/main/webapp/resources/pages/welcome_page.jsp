<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <link rel="stylesheet" href="../styles/debit_card.css">
    <link rel="stylesheet" href="../styles/buttons.css">
    <link rel="stylesheet" href="../styles/side_bar.css">
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
    </style>
</head>
<body>
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li><button id="homeBtn" class="btn px-2 text-white">Home</button></li>
                <li><a href="#" class="nav-link px-2 text-white">About</a></li>
            </ul>
            <div class="text-end">
                <button id="loginBtn" class="btn btn-outline-light me-2">Login</button>
                <button id="signUpBtn" class="btn btn-outline-light me-2">Sign-up</button>
            </div>
        </div>
    </div>
</header>
    <div id="formHolder" style="display: flex; justify-content: center;">

    </div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" type="text/javascript"></script>
<script>
    $(document).ready(function () {
        $("#loginBtn").on("click", function ()
        {
            console.log("Login clicked");

            $.ajax({
                url: "/login",
                type: "GET",
                success: function(response) {
                    console.log("Login success");
                    $("#formHolder").html(response.form);

                    $("#formScript").remove();

                    let script = document.createElement("script");

                    script.innerHTML = response.script;

                    script.id = "formScript";

                    document.body.appendChild(script);
                },
                error: function (a ,b ,c)
                {
                    console.log(a.status + " " + b + " " + c);
                }
            });
        });

        $("#signUpBtn").on("click", function ()
        {
            $.ajax({
                url: "/register",
                type: "GET",
                success: function(response) {
                    $("#formHolder").html(response.form);

                    $("#formScript").remove();

                    let script = document.createElement("script");

                    script.innerHTML = response.script;

                    script.id = "formScript";

                    document.body.appendChild(script);
                }
            });
        });
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