<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
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
  <form action="/signUp" method="post" style="min-width: 50%">
    <h1 style="text-align: center" class="mt-3">SIGN UP</h1>
    <div class="form-group row mt-5">
      <label for="name" class="col-sm-1 col-form-label col-form-label-lg">Name</label>
      <div class="col-sm-11" style="display: flex; justify-content: center">
        <input type="text" class="form-control form-control-lg" name="name" id="name" placeholder="Unique name">
      </div>
    </div>
    <div class="form-group row mt-2">
      <label for="password" class="col-sm-1 col-form-label col-form-label-lg">Password</label>
      <div class="col-sm-11" style="display: flex; justify-content: center">
        <input type="password" class="form-control form-control-lg" name="password" id="password" placeholder="Password">
      </div>
    </div>
    <div class="row mt-4">
      <div style="display: flex; justify-content: center; ">
        <button type="submit" class="btn btn-warning w-l">Sign-up</button>
      </div>
    </div>
  </form>
</div>
</body>
</html>