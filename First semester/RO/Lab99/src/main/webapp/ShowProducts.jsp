<%@ page import="java.util.ArrayList" %>
<%@ page import="db.vegetable" %>
<%@ page import="utility.CrudMethod" %><%--
  Created by IntelliJ IDEA.
  User: oleksandr.polishchuk
  Date: 16/12/2022
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
  <link rel="stylesheet" type="text/css" href="stylesheets/GeneralStylesheet.css">
  <title>JSP - Hello World</title>
  <style>
    .del{

    }
    .edit{

    }
  </style>
</head>
<body>
<header class="p-3 topbar-wrapper">
  <div class="container">
    <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
      <h3>PRODUCTS</h3>

      <ul class="nav col-6 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
        <li><span><a class="custom_link px-2" href="index">Home</a></span></li>
        <li><span><a class="custom_link px-2" href="">Show products</a></span></li>
      </ul>
      <ul class="nav secondary-nav">
        <li class="menu">
          <a href="#" class="menu custom_link px-4" id="menu_btn">Menu</a>
          <ul class="menu-dropdown" style="list-style: none;display: none;">
            <li><span><a class="custom_link px-2 " href="AddProduct">Add product</a></span></li>
            <li><span><a class="custom_link px-2 " href="EditProduct">Edit product</a></span></li>

          </ul>
        </li>
      </ul>
    </div>
  </div>
</header>

<div style="margin-top: 40px; padding: 0px 20px; display: flex; flex-direction: column; align-items: center;">
  <h1 style="width: fit-content;text-align: center;color: #20B2AA; margin-top: 40px">PRODUCTS</h1>
  <form action="CrudController" method="GET" id="form" name="form" style="width: 100%; height: fit-content;">
  <table class="table table-striped" id="vegetables" style="margin-top: 40px; ">
    <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">Name</th>
      <th scope="col">Category</th>
      <th style="text-align: left;"><span><a class="btn btn-primary edit" href=EditProduct>Edit</a></span>
        <span><a class="btn btn-primary del" href="DeleteProduct">Delete</a></span>
      </th>
    </tr>
    </thead>
    <tbody>
    <% ArrayList<vegetable> vegs = (ArrayList<vegetable>)request.getAttribute("Elements");
    int i=1;
    if(vegs != null)
    for(vegetable veg:vegs)
    {
    %>

    <tr>
      <th scope="row"><%= i %></th>
      <td style="text-overflow: ellipsis; overflow: hidden; "><span><%= veg.getName() %></span></td>
      <td style="text-overflow: ellipsis; overflow: hidden; "><%= veg.getCategory() %></td>
      <td style="text-align: left;"></td>
    </tr>
    <%
      i++;
    }
    %>

    </tbody>
  </table>
  </form>
</div>


</body>
<script>
  allmenus = document.getElementsByClassName("menu");
  let len = allmenus.length;

  for (let i=0; i<len; i++)
  {
    allmenus[i].onclick = function (){onclick_function(allmenus[i])};
  }
  function onclick_function(obj)
  {
    let children_len = obj.children.length;
    let contains_open = obj.classList.contains("open");
    let children = obj.children;
    for(let j =0; j< children_len; j++)
    {
      if(children[j].classList.contains("menu-dropdown") )
      {
        if(!contains_open)
        {
          children[j].style.display = "block";
          obj.classList.add("open");
        } else
        {
          children[j].style.display = "none";
          obj.classList.remove("open");
        }
      }
    }
  }


</script>
</html>
