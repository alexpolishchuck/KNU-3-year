<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns:th="https://www.thymeleaf.org" lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
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
    input
    {
      max-width: 50%;
    }
  </style>
</head>
<body>
<div class="container" style="display: flex; flex-direction: column; align-items: center;">
  <form action="#" method="post" style="min-width: 50%">
    <h1 style="text-align: center" class="mt-3">ADD CARD</h1>
    <div class="form-group row mt-5">
      <label for="numberField" class="col-sm-1 col-form-label col-form-label-lg">Card number</label>
      <div class="col-sm-11" style="display: flex; justify-content: center">
        <input type="text" class="form-control form-control-lg"
               id="numberField" placeholder="Unique card number">
      </div>
    </div>
    <div class="row mt-4">
      <div style="display: flex; justify-content: center; ">
        <button type="submit" class="btn btn-warning w-l">Save</button>
      </div>
    </div>
  </form>
</div>
<script>
  $(document).ready(function () {
    $("form").submit(function (event) {
      var formData = {
        number: $("#numberField").val()
      };

      $.ajax({
        type: "POST",
        url: "add_card",
        data: formData,
        encode: true,
        async: false,
        beforeSend: function(xhr) {
          xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem("token"));
        },
        complete: function ()
        {
          window.location.href = "/userPage";
        }
      });
      event.preventDefault();

    });//form.submit
  });//document.ready
</script>
</body>
</html>