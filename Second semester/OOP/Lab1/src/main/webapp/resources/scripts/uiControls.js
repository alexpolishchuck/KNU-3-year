    function setUpLogoutAction(btnId)
{
    document.getElementById(btnId).onclick = function ()
    {
        localStorage.removeItem("token");
        window.location.href = '/';
    }
}

function setUpAddCardAction(btnId)
{
    document.getElementById(btnId).onclick = function ()
    {
        window.location.href = 'http://localhost:8080/user_page/add_card'
    }
}

function setUpUnblockCardAction(btnId)
{
    document.getElementById(btnId).onclick = function ()
    {
        $.ajax({
            url: "/admin_page/changeCardStatus",
            type: "GET",
            beforeSend: function(xhr) {
                xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
            },
            success: function(response) {
                    document.open();
                    document.write(response);
                    document.close();
            },
            error: function (a, b, c)
            {
                console.log(a.status + " " + b + " " + c);
            }
        });
    }
}

function blockCardAction(e)
{
    let stack = document.querySelector(".stack");
    let children = stack.children;
    let valid_thru_date = children[children.length - 1].querySelector('.valid_thru');
    let cardNumber = children[children.length - 1].querySelector('.number').innerHTML;
    cardNumber = cardNumber.replaceAll(/[ \n\r]*/ig, "")

    let request_url = "http://localhost:8080/userPage/changeCardStatus";

    let cardData = {
        number: cardNumber
    };

    $.ajax({
        url: request_url.toString(),
        type: "POST",
        data: cardData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        success: function(d, status) {
            if(status === "success")
                valid_thru_date.innerHTML="BLOCKED";
        }
    });
}

function setUpBlockCardAction(btnId)
{
    document.querySelector("#" + btnId).addEventListener('click', blockCardAction);
}