<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <title>Admin Console | Monitor Console</title>
  <link href="/resources/console/css/bootstrap.min.css" rel="stylesheet">
  <link href="/resources/console/css/font-awesome.min.css" rel="stylesheet">
  <link href="/resources/console/css/web.css" rel="stylesheet">
</head>

<body>
  <section>
    <div class="lockedpanel">
        <div class="locked">
            <i class="fa fa-lock"></i>
        </div>
        <div class="loginuser">
            <img src="/resources/console/images/img/loggeduser.png" alt="" />
        </div>
        <form method="post"  action="/console/account/login">
            <div class="form-group">
			    <input type="text" name="account" class="form-control" placeholder="请输入账号" />
			</div>
            <div class="form-group">
			    <input type="password" name="password" class="form-control" placeholder="请输入密码" />
			</div>
            <button class="btn btn-success btn-block">登陆</button>
        </form>
    </div>
</section>


<script src="/resources/console/js/jquery-1.11.1.min.js"></script>
<script src="/resources/console/js/bootstrap.min.js"></script>
<script>
    jQuery(document).ready(function(){       
       
        
    });
</script>
</body>
</html>
