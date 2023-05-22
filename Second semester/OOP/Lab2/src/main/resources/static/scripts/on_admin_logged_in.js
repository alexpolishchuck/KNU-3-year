$(".main_page_element").remove();
$(".user_page_element").remove();

let account_manipulation_classes = ["btn","btn-outline-light","me-2", "admin_page_element"];

create_element(
    "button",
    account_manipulation_classes,
    "unblockBtn",
    "Unblock card",
    "authorization_btns_holder").onclick = function ()
{
    window.location.href = "/changeCardStatus";
};

create_element(
    "button", account_manipulation_classes, "logoutBtn", "Logout", "authorization_btns_holder")

$("#logoutBtn").on("click",function ()
{
    window.location.href = "/logout";
});