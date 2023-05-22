$(".user_page_element").remove();
$(".admin_page_element").remove();

let account_manipulation_classes = ["btn","btn-outline-light","me-2", "main_page_element"];

create_element(
    "button",
    account_manipulation_classes,
    "loginBtn",
    "Login",
    "authorization_btns_holder").onclick = function ()
{
    window.location.href = "/login";
};
create_element(
    "button",
    account_manipulation_classes,
    "signUpBtn",
    "Sign up",
    "authorization_btns_holder").onclick = function ()
{
    window.location.href = "/register";
};