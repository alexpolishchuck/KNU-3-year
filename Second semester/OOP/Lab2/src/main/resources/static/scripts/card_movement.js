start();

function swap(e)
{
    let stack = document.querySelector(".stack");
    let children = stack.children;
    if (children.length <= 1 || e.target != children[children.length - 1])
        return;

    children[children.length - 1].style.animation = "swap 700ms forwards";

    setTimeout(()=> {
        children[children.length - 1].style.animation = "";
        stack.prepend(children[children.length - 1]);
    }, 700);
}

function side_bar_display(e)
{
    let side_bar = document.querySelector(".side_bar");

    if(side_bar.style.display === "none")
    {
        side_bar.style.display = "block";
        side_bar.style.animation = "side_bar_display_show_animation 750ms ease";
    }
    else
    {
        side_bar.style.animation = "side_bar_display_hide_animation 750ms ease";
        setTimeout(() => {
            side_bar.style.display = "none";
        }, 800);
    }
    side_bar.classList.toggle("show");
}

function block(e)
{
    let stack = document.querySelector(".stack");
    let children = stack.children;
    let valid_thru_date = children[children.length - 1].querySelector('.valid_thru');
    let number = children[children.length - 1].querySelector('.number').innerHTML;
    number = number.replaceAll(/[ \n\r]*/ig, "")

    let request_url = "/changeCardStatus";

    let cardData = {
        cardNumber: number
    };

    $.ajax({
        url: request_url.toString(),
        type: "POST",
        data: cardData,
        success: function(d, status) {
            if(status === "success")
                valid_thru_date.innerHTML="BLOCKED";
        }
    });
}

function start()
{

    $("#container_stack").on("click", swap);

    $("#payBtn").on("click", side_bar_display);
    $("#depositBtn").on("click", side_bar_display);

    $('#blockBtn').on('click', block);
}