start();

function swap(e)
{
    let stack = document.querySelector(".stack");
    let children = stack.children;
    if (children.length <= 1 || e.target !== children[children.length - 1])
        return;

    children[children.length - 1].style.animation = "swap 700ms forwards";

    setTimeout(()=>
    {
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

function start()
{
    document.querySelector(".stack").addEventListener("click", swap);
    let btns = [document.getElementById("pay_button"),
        document.getElementById("deposit_button") ];
    console.log(btns.length)
    for (let i = 0; i < btns.length; i++)
    {
        btns[i].addEventListener("click", side_bar_display);
    }
}