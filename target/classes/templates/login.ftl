<#ftl encoding='UTF-8'>
<html>
<head>
    <link href="/css/styles.css" rel="stylesheet">
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body>
<#if error??>
    <div class="alert alert-danger" role="alert">Login or password is incorrect</div>
</#if>
<div class="container">
    <div class="form-style-2">
        <div class="form-style-2-heading">
            Please Login!
        </div>
        <form method="post" action="/login">
            <label for="login">Login
                <input class="input-field" type="text" id="login" name="login">
            </label>
            <br>
            <label for="password">Password
                <input class="input-field" type="password" id="password" name="password">
            </label>
            <a href="/signUp">Sign Up</a>
            <br>
            <label for="remember-me">
                <input type="checkbox" id="remember-me" name="remember-me">Remember-me</label>
            <input type="submit" value="Login">
        </form>
    </div>
</div>
</body>
</html>
