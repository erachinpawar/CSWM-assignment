<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">

<head>
    <title>Credit-Suisse WM Login</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/login.css}"/>
    <link rel="icon"
	href="http://logok.org/wp-content/uploads/2014/10/Credit_Suisse_Logo-880x600.png">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>
<form th:action="@{/registration}" method="get">
    <button class="btn btn-md btn-login btn-block" type="Submit">Go To Registration Page</button>
</form>


<div class="container">
    <img th:src="@{/images/login.jpg}" class="img-responsive center-block" width="300" height="200" alt="Logo"/>
    <form th:action="@{/login}" method="POST" class="form-signin">
        <h3 class="form-signin-heading" th:text="Welcome"></h3>
        <br/>

        <input type="text" id="email" name="email" th:placeholder="Email"
               class="form-control"/> <br/>
        <input type="password" th:placeholder="Password"
               id="password" name="password" class="form-control"/> <br/>

        <div align="center" th:if="${param.error}">
            <p style="font-size: 20; color: #FF1C19;">Email or Password invalid, please verify</p>
        </div>
        <button class="btn btn-lg btn-primary btn-block" name="Submit" value="Login" type="Submit"
                th:text="Login"></button>
    </form>
</div>
</body>
</html>