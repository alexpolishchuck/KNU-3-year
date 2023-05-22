<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
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
                <button id="addCard" class="col-12 btn btn-outline-light me-2">Add card</button>
            </div>
        </div>
    </div>
</header>
    <div class="wrapper">
        <div class="container controller">
            <div class="container stack">
               <jsp:useBean id="cards" scope="request" type="java.util.List"/>
               <c:if test="${cards != null && cards.size() != 0}">
                   <c:forEach items ="${cards}" var = "card">
                       <div class="debit_card p-4">
                           <span class="bank_name">The Best Bank</span>
                           <img src="/resources/img/chip.png" alt="chip.png" class="chip_img mt-4">
                           <span class="number mt-4" style="letter-spacing: 10px">
                                   <c:out value="${card.getNumber()}"/>
                           </span>
                           <div class="mt-4" style="display: flex; justify-content: center">
                           <span style="font-size: 9px;">
                                VALID<br>THRU
                           </span>
                               <c:if test="${card.is_blocked() == false}">
                                   <span class="ps-2 valid_thru">12/24</span>
                               </c:if>
                               <c:if test="${card.is_blocked() == true}">
                                   <span class="ps-2 valid_thru">BLOCKED</span>
                               </c:if>

                           </div>
                       </div>
                   </c:forEach>
               </c:if>
            </div>

            <div class="btn_holder mt-4">
                <div class="btn custom_button btn-warning" id="pay_button">Pay</div>
                <div class="btn custom_button btn-warning ms-4" id="block_button">Block</div>
                <div class="btn custom_button btn-warning ms-4" id="deposit_button">Deposit</div>
            </div>

        </div>
        <div class="side_bar p-4" style="display: none">
            <h1 class="text-warning balance">BALANCE:</h1>
            <h3 class="text-warning balance_value"></h3>
            <form action="">

            </form>
        </div>
    </div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js" integrity="sha384-w76AqPfDkMBDXo30jS1Sgez6pr3x5MlQ1ZAGC+nuZB+EYdgRZgiwxhTBTkF7CXvN" crossorigin="anonymous"></script>
<script src="../resources/scripts/card_movement.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="../resources/scripts/uiControls.js"></script></script>
<script>
    setUpLogoutAction("logoutBtn");
    setUpAddCardAction("addCard");
    setUpBlockCardAction("block_button");
</script>
</body>
</html>