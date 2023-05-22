function create_element(type, classList = null, id, innerHtml, parentId = null)
{
    let element = document.createElement(type);
    if(classList !== null)
        element.classList.add(...classList);

    element.id = id;
    element.innerHTML = innerHtml;

    if(parent !== null)
        $("#" + parentId).append(element);
    else
        document.body.appendChild(element);

    return element;
}