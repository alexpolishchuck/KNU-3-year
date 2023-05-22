<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/debit_card.css">
    <link rel="stylesheet" href="/resources/styles/buttons.css">
    <link rel="stylesheet" href="/resources/styles/side_bar.css">
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
        .controller
        {
            margin-top: 10%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>
<header class="p-3 text-bg-dark">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center justify-content-end">
            <div class="text-end" >
                <button id="logoutBtn" class="col-12 btn btn-outline-light me-2">Log out</button>
                <button id="unblockCard" class="col-12 btn btn-outline-light me-2">Unblock card</button>
            </div>
        </div>
    </div>
</header>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="../resources/scripts/uiControls.js"></script>
<script>
    $(document).ready(function ()
    {
        setUpLogoutAction("logoutBtn");
        setUpUnblockCardAction("unblockCard");
    })
</script>
</body>
</html>