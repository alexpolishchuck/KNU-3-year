//hide login and sign up buttons and add logout btn

$(".main_page_element").remove();
$(".admin_page_element").remove();

let account_manipulation_classes = ["btn","btn-outline-light","me-2", "user_page_element"];
let card_manipulation_classes = ["btn", "custom_button", "btn-warning", "ms-4", "user_page_element"];

create_element(
    "button", account_manipulation_classes, "logoutBtn", "Logout", "authorization_btns_holder")
create_element(
    "button", account_manipulation_classes, "addCardBtn", "Add card", "authorization_btns_holder")

$.ajax({
    url: '/get_card_holder',
    type: "GET",
    success: function(result) {
        console.log(result);

        let wrapper = document.createElement("div");
        wrapper.classList.add("wrapper", "user_page_element");
        wrapper.style.width = "100%";
        wrapper.innerHTML = result;
        wrapper.id = "card_holder";
        $("#body_wrapper").append(wrapper);

        let script = document.createElement("script");
        script.classList.add("user_page_element");
        script.id = "card_movement_script";
        script.src = "../scripts/card_movement.js";
        document.body.appendChild(script);

        add_cards();
    },
    error: function(a, b, c) {
        console.log(a.status + " " + b + " " + c);
    }
});

$("#logoutBtn").on("click",function ()
{
    window.location.href = "/logout";
});

$("#addCardBtn").on("click", function ()
{
    window.location.href = "/add_card"
})

function add_cards()
{
    $.ajax({
        url: '/get_user_cards',
        type: "GET",
        success: function(result) {
            console.log(result);
            let cards = [...result];
            add_cards_to_page(cards);
            console.log(cards.length);
        },
        error: function(a, b, c) {
            console.log(a.status + " " + b + " " + c);
        }
    });
}

function add_cards_to_page(cards)
{
    if(cards === null)
        return;

    $.ajax({
        url: '/get_card_template',
        type: "GET",
        success: function(result) {
            console.log(result);
            let card_template = result;

            cards.forEach((element) =>{
                console.log(element);
                console.log(element.bank_name)
                let card = card_template;

                card = card.replaceAll("bank_name_value", element.bank_name);
                card = card.replaceAll("card_number_value", element.number);

                if(element.is_blocked === true)
                   card = card.replaceAll("vaild_thru_value", "BLOCKED")
                else
                    card = card.replaceAll("vaild_thru_value", "21/14")

                document.getElementById("container_stack").innerHTML += card;
            })
        },
        error: function(a, b, c) {
            console.log(a.status + " " + b + " " + c);
        }
    });
}



