
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>


<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<html>
    <head>
        <title>SnowFlakes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/MainView.css">
        <link rel="stylesheet" href="css/bootstrap-notify.css">

        <style>
            .help-block{
                color: #d2322d
            }
            .panel label,.panel a,.panel p{
                margin-left:5px;

            }
            #register-tab > li.active > a,
            #register-tab > li.active > a:hover,
            #register-tab > li.active > a:focus {
                color: #ffffff;
                cursor: default;
                background-color: #666699;
                border:transparent;
                border-bottom-color: transparent;
            }
            .auth{
                height: 34px;
                padding: 6px 12px;
                font-size: 14px;
                line-height: 1.428571429;
                color: #555555;
                vertical-align: middle;
                background-color: #ffffff;
                background-image: none;
                border: 1px solid #cccccc;
                border-radius: 4px;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
                -webkit-transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
                transition: border-color ease-in-out 0.15s, box-shadow ease-in-out 0.15s;
            }
        </style>


    </head>
    <body style="background-image: url('img/bgimg.jpg')">
        
        <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script src="js/jqBootstrapValidation.js"></script>    
        <script src="js/jquery.cookie.js"></script>    
        <script src="js/bootstrap-notify.js"></script>    

        <script type="text/javascript">
            $(function() {
                var has_got_code = "false";
                var code;
                $('#register-btn').attr('disabled', "true");
                var login_error = '<%=request.getAttribute("login_error")%>';
                var register_duplicated = '<%=request.getAttribute("register_duplicated")%>';
                if (login_error == "true") {
                    $('.notifications').notify({
                        message: {text: 'The Email or Password that you provided is Wrong!'},
                        type: 'danger',
                    }).show();
                    window.scrollBy(0,1000);
                }
                $('#username').blur(function() {
                    var $input_field = $(this);
                    var username = $(this).val();
                    $.post("script/is-duplicated.jsp", {type: "username", username: username}, function(data) {
                        if (data == "true") {

                            if ($input_field.parent().find('.duplicate').length == 0) {
                                var div = "<div class='help-block duplicate'><ul role='alert'><li>This username has been used!</li></ul></div>";
                                $(div).appendTo($input_field.parent());

                            }
                            $input_field.parent().parent().removeClass("has-success");
                            $input_field.parent().parent().addClass("has-error");
                            $('#register-btn').attr('disabled', "true");
                        }
                        else {
                            $input_field.parent().find('.duplicate').remove();

                            $input_field.parent().parent().removeClass("has-error");
                            $input_field.parent().parent().addClass("has-success");
                        }



                    });
                });

                $('#emailadd').blur(function() {
                    var $input_field = $(this);
                    var email = $(this).val();
                    $.post("script/is-duplicated.jsp", {type: "email", email:email}, function(data) {
                        if (data == "true") {

                            if ($input_field.parent().find('.duplicate').length == 0) {
                                var div = "<div class='help-block duplicate'><ul role='alert'><li>This username has been used!</li></ul></div>";
                                $(div).appendTo($input_field.parent());

                            }
                            $input_field.parent().parent().removeClass("has-success");
                            $input_field.parent().parent().addClass("has-error");
                            $('#register-btn').attr('disabled', "true");
                        }
                        else {
                            $input_field.parent().find('.duplicate').remove();

                            $input_field.parent().parent().removeClass("has-error");
                            $input_field.parent().parent().addClass("has-success");
                        }



                    });
                });


                var email = $.cookie('tjcollaborationteam-email');
                var password = $.cookie('tjcollaborationteam-password');
                if (email != undefined) {
                    $('#login').find('#email').val(email);
                }
                if (password != undefined) {
                    $('#login').find('#password').val(password);
                }

                $('#login-btn').click(function() {
                    if ($('#rememberme').attr("checked")) {
                        $.cookie('tjcollaborationteam-email', $('#login').find('#email').val());
                        $.cookie('tjcollaborationteam-password', $('#login').find('#password').val());
                    }
                });
                var code;
                
                    $('#code').attr("src", "./script/img.jsp?time="+new Date());
                    setTimeout(function(){
                        
                        $.post("script/getcode.jsp", function(data) {
                        code = data.replace(/[\r\n]/g, '');
                        code = code.replace(/[ ]/g, '');

                        });
                    },500);
                    

                $('a[data-toggle="tab"]').on('shown.bs.tab', function(e) {
                    window.scrollBy(0, 1000);

                });
                $('#verification').tooltip('hide');
                $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
                $("#code").click(function() {
                        
                        $('#code').attr("src", "script/img.jsp?time="+new Date());
                        
                        setTimeout(function(){
                        
                        $.post("./script/getcode.jsp", function(data) {
                            code = data.replace(/[\r\n]/g, '');
                            code = code.replace(/[ ]/g, '');
                            if ($('#checking-img').val() != code) {
                                $('#verify-message').show();
                                $('#login-btn').attr("disabled", "true");
                            }
                        });
                        
                        },500);
                        
                        

              
                });
                $('#checking-img').blur(function() {
                    if ($(this).val() != code) {
                        $('#verify-message').show();
                        $('#login-btn').attr("disabled", "true");
                    }
                });
                $('#checking-img').focus(function() {
                    $('#verify-message').hide();
                    $('#login-btn').removeAttr("disabled");
                });
                
                $('#register').find('input').blur(function() {
                    if ($('#register').find('.help-block li').length != 0) {
                        $('#register-btn').attr('disabled', "true");
                    }
                    else {
                        $('#register-btn').removeAttr("disabled");
                    }
                });
               
                 document.onkeydown = function(){
                   
                     if (window.event && window.event.keyCode == 13) {
                          $('#checking-img').trigger("blur");
                          if ($('#checking-img').val() != code) {
                        $('#verify-message').show();
                        $('#login-btn').attr("disabled", "true");
                         window.event.returnValue = false;
                    }
                            
                             else
                             window.event.returnValue = true;
                         }
                         
                }
                
                var authcode_timer;
                var start_count = 20;
                
                $('#veri-btn').click(function() {
                        $(this).attr("disabled", "true");
                        $('#submit').attr("disabled", "true");
                        $.post("mail/check-valid-mail.jsp", {email: $('#emailadd').val()}, function(data) {
                            if (data == "false") {
                                alert("The email address is invalid!");
                            }
                            else {
                                has_got_code = "true";
                                code = data;
                            }

                        });
                        authcode_timer = setInterval(function(){
                            $('#veri-btn').html("Get Auth Code Via Email"+" "+start_count);
                    start_count--;
                    if(start_count==-1){
                        $('#veri-btn').removeAttr("disabled");
                        $('#veri-btn').html("Get Auth Code Via Email");
                        start_count=20;
                        clearInterval(authcode_timer);
                    }
                        },1000);
                });
                
                $('#register-btn').click(function() {
                    
                    $('#register').find('input').trigger("blur");
                   if (has_got_code=="false") {
                        
                        return false;
                    }
                    if (code != $('#validation').val()) {
                        alert("Authentication code does not match!");
                        return false;
                    }
                   
                });
            });
            
        </script>

        <!--header-->
        <div id="header" class="nav navbar-inverse navbar-fixed-top" style="opacity: 0.9" role="navigation">
            <div class="row" style="margin: 0 auto; display: table; width: auto;">
                <div>
                    <div class="navbar-header">
                        <a class="navbar-brand" href="index" style="color: white;">
                            SnowFlakes   
                        </a>
                    </div>

                    <ul class="nav navbar-nav">
                        <li class="active"><a><span class="glyphicon glyphicon-home"></span> Home</a></li>
                        <li><a href="introduction" ><span class="glyphicon glyphicon-eye-open"></span> Introduction</a></li>

                        <li><a href="#" ><span class="glyphicon glyphicon-question-sign"> </span> Help</a></li>
                        <li><a href="about" ><span class="glyphicon glyphicon-info-sign"> </span> About</a></li>
                        <li><a href="/SpringMVC_Mobile/index" ><span class="glyphicon glyphicon-user"> </span> Mobile</a></li>
                        <li><a style=" color: white">|</a></li>
                        <li><a href="register" >Register</a></li>

                        <li><a href="#panel" >Login</a></li>
                        <li>
                            <form class="navbar-form navbar-right" role="search">
                                <div class="form-group">
                                    <input type="text" class="form-control" placeholder="Search">
                                </div>&nbsp;&nbsp;&nbsp;&nbsp;
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"> </span> Search</button>
                            </form>
                        </li>
                    </ul>

                </div>
            </div>
        </div>
        <br/>
        <br/>
        <br/>

        <!--body-->
        <div class="container" >    

            <div class="panel panel-collapse">
                <div class="panel-body">
                    <div class="nav navbar" role="navigation">


                        <div class="navbar-header">
                            <div class="row">
                                <div class=" col-md-2">

                                    <a class="navbar-brand">
                                        <img class=" img-rounded" style="width: 120%;height: 120%; " src="<c:url value='img/logo.png'/>">
                                    </a>
                                    <div>
                                        <img class="img-rounded col-md-offset-2" style="position: relative;left:5px;width: 80%;height: 80%;" src="<c:url value='img/logo-title.png'/>">

                                    </div>
                                </div>
                                <div class="col-md-10">
                                    <br/>
                                    <div id="carousel-title" class="carousel slide" data-ride="carousel">
                                        <!-- Indicators -->


                                        <!-- Wrapper for slides -->
                                        <div class="carousel-inner">
                                            <div class="item active">
                                                <img  src="<c:url value='img/network.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3 style=" color: black">Connection</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='img/comm.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3 style=" color: black">Communication</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='img/teamwork.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Management</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='img/uml.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Sharing</h3>
                                                </div>

                                            </div>
                                            <div class="item">
                                                <img  src="<c:url value='img/creativity.jpg'/>" >

                                                <div class="carousel-caption">
                                                    <h3>Creativity</h3>
                                                </div>

                                            </div>

                                        </div>                 
                                    </div>
                                </div>
                            </div>



                        </div>


                    </div>
                </div>
            </div>
            <div class="panel panel-default">

                <div class="jumbotron" style="background-image:  url('img/bg-2.jpeg'); z-index: 2">
                    <h1>Welcome to SnowFlakes!</h1><br/>

                    <p class="text-primary">SnowFlakes is a non-profitable online team collaboration tool dedicating to software project
                        development. The website supports Project-Oriented team organization, and try to 
                        provide customers with the most user-friendly GUI as well as the clearest functions and services. 
                        For more details, please regard to introduction. Would you like to
                        experience an unprecedented teamwork process? Get set and start building your project now! It might work beyond your expectation! 
                        Click<a href="register" class="text-danger"> Register</a> to begin your project management trip.
                    </p>    

                    <p><a class="btn btn-primary btn-lg" href="#">Learn More</a></p>
                </div></div>


            <br/>        

            <div id="panel" class="row">

                <div class="col-md-offset-4 col-md-4">
                    <div class="panel panel-default text-info" style=" background-image: url('img/bg.png')">
                        <div class="panel-body">
                            <ul class="nav nav-tabs" id="register-tab" >
                                <li class="active"><a href="#login" data-toggle="tab">Login</a></li>
                                <li ><a href="#register" id="reg-panel" data-toggle="tab">Register</a></li>

                            </ul><br/>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="login">
                                    <form method="post">
                                        <div class="form-group">
                                            <label for="email">Email:</label>
                                            <input value="${login_info.email}" required class="form-control" name="email" id="email" type="email">
                                        </div>
                                        <div class="form-group">
                                            <label for="reg-password-1">Password:</label>
                                            <input value="${login_info.password}" required class="form-control" name="password" id="password" type="password">
                                        </div>  
                                        <div class="form-group">
                                            <label for="checking-img">Verification Code:</label>
                                            <div class="row">
                                                <div class="col-md-7">
                                                    <input required class="form-control" id="checking-img" type="text">
                                                </div>
                                                <div class="col-md-5">

                                                    <h5 data-toggle="tooltip" id="verification" data-placement="top" data-trigger="hover" title="Click to Change Code" >
                                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <img id="code"  border=0 >
                                                    </h5>
                                                    <!--<img src="img/logo.png"/>-->
                                                </div>
                                            </div>
                                        </div>  
                                        <div class="form-group">
                                            <label class="text-danger" id="verify-message" style="display: none">The Verification Code Does not Match!</label>
                                        </div>
                                        <div class="form-group">
                                            <a class="text-warning" href="#">Want to get familiar with the usage first?</a>
                                            <div class="checkbox">
                                                <label>
                                                    <input name="rememberme" id="rememberme" type="checkbox"> Remember me
                                                </label>
                                            </div>
                                            <div class="notifications"></div>
                                        </div>
                                        
                                        <div class="form-group">
                                            <button type="submit" name="login-btn" id="login-btn" class="form-control btn btn-success">Sign in</button>
                                        </div>    
                                    </form>
                                </div>
                                <div class="tab-pane fade" id="register">
                                    <form  name="myinfo" method="post" >
                                        <div class="form-group ">
                                            <p class="text-danger">Hint: Username, Password field <em>only</em> accept A-Z, a-z, 0-9 for each character.</p>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="username">Username:</label>
                                            <div class="controls">
                                                <input class="form-control" value="${myinfo.name}" name="name" data-validation-regex-regex="(\w{0,32})" data-validation-regex-message="The maximum length can not exceed 32" required id="username" type="text">

                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label for="emailadd">Email:</label>
                                            <input class="form-control" value="${myinfo.email}" name="email" required id="emailadd" type="email">
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="reg-password">Password:</label>
                                            <div class="controls">
                                                <input class="form-control" data-validation-regex-regex="(\w{6,32})" data-validation-regex-message="The password length must between 6 and 32" 
                                                       required id="reg-password" value="${myinfo.password}" name="password" type="password">
                                            </div>
                                        </div>
                                        <div class="form-group control-group">
                                            <label for="reg-repassword" class="control-label">Re-Enter Password:</label>
                                            <div class="controls">
                                                <input class="form-control" data-validation-matches-match="password" data-validation-matches-message="Must match password entered above" 
                                                       id="reg-repassword" name="reg-repassword" type="password">
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button  type="submit"  name="continue-register" value="submit" class="btn btn-link text-warning">Want to fill in more information?</button>
                                            <div class="notifications"></div>
                                            
                                        </div>
                                        <div class="form-group">                                    
                                    <label for="validation">Authentication Code:</label>                                    
                                    <div>
                                    <input style="width:100px" class="auth" id="validation" type="text">


                                    <button style=" margin-left: 20px;" type="button" id="veri-btn" class="btn btn-primary">Get Auth Code Via Email</button>
                                    </div>
                                </div>    
                                        <div class="form-group">
                                            <button id="register-btn" type="submit" name="register-btn" value="submit" class="btn btn-success form-control">Register</button>
                                        </div>    
                                    </form>
                                </div>

                            </div>



                        </div>
                    </div>
                    

                </div>

            </div>





            <!--
            <div class="row"  >

                <div class="col-md-2">This is a demo </div>
                <div class="col-md-4">
                    <p>
                        <em>Git</em><strong>Linus Torvalds</strong>编写，用作Linux内核代码的管理。在推出后，Git在其它项目中也取得了很大成功，尤其是在Ruby社区中。
                    </p>
                    <h3>
                        h3. 这是一套可视化布局系统.
                    </h3>
                </div>
                <div class="col-md-4">
                    <p>
                        <em>aaa</em>是一个分布式的版本控制系统，最初由<strong>Linus Torvalds</strong>编写，用作Linux内核代码的管理。在推出后，Git在其它项目中也取得了很大成功，尤其是在Ruby社区中。
                    </p>
                    <h3 >
                        h3. 这是一套可视化布局系统.
                    </h3>
                </div>


                <table class="table">

                    <tr  class="success">
                        <td>#</td>
                        <td>Name</td>
                        <td>ID</td>
                        <td>Age</td>
                        <td>Address</td>
                    </tr>
                    <tr class="danger">
                        <td >1</td>
                        <td>jk</td>
                        <td>1152801</td>
                        <td>20</td>
                        <td>Caoan highway4800</td>
                    </tr>
                    <tr class="warning">
                        <td>2</td>
                        <td>cj</td>
                        <td>1152803</td>
                        <td>21</td>
                        <td>Caoan this is a highway4800</td>
                    </tr>
                </table>

                
                <ul class="nav nav-tabs">
                    <li class="active"><a >Home</a></li>
                    <li ><a href="http://www.baidu.com">Project</a></li>
                    <li><a href="http://www.baidu.com">Member</a></li>

                </ul>

                <ol class="breadcrumb">
                    <li><a href="#">Home</a></li>
                    <li><a href="#">Library</a></li>
                    <li class="active">Data</li>
                </ol>

                <ul class="pagination">
                    <li><a href="#">&laquo;</a></li>
                    <li><a href="#">1</a></li>
                    <li><a href="#">2</a></li>
                    <li><a href="#">3</a></li>
                    <li><a href="#">4</a></li>
                    <li><a href="#">5</a></li>
                    <li><a href="#">&raquo;</a></li>
                </ul>



            </div><br/>
            -->
            <!--
            <form role="form" method="post">
                <div class="form-group">
                    <label for="exampleInputEmail1">Email</label>
                    <input name="email"  class="form-control" type="email" placeholder="Enter Email"/> 
                </div>
                <div class="form-group">
                    <label for="exampleInputFile">File input</label>
                    <input type="file"  id="exampleInputFile"><br/>
                    <img src="img/img.png" class="img-thumbnail" >
                    <p class="help-block">Example block-level help text here.</p>

                    <textarea name="description"  rows="3"  class="form-control" ></textarea>     

                </div>

                <div  class="form-group ">
                    <div class="row">
                        <div class="col-md-2">
                            <select name="city" class="form-control">
          
        </select>
    </div>
</div>
</div>
<div class="form-group">

<button type="submit" class="btn btn-danger">Submit</button>
</div>
<div class="form-group">
<div class="btn-group">
    <button type="button" class="btn btn-default">1</button>
    <button type="button" class="btn btn-default">2</button>
    <div class="dropdown btn-group">
        <button type="button" id="dropdownMenu1" data-toggle="dropdown" class="btn btn-danger dropdown-toggle">
            Toggle Dropdown
            <span class="caret"></span>

        </button>
        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
            <li role="presentation" class="dropdown-header">Category 1</li>
            <li role="presentation"><a href="http://www.baidu.com" role="menuitem">Action1</a></li>
            <li role="presentation"><a href="http://www.baidu.com" role="menuitem">Action2</a></li>
            <li role="presentation" class="divider"></li>
            <li role="presentation" class="dropdown-header">Category 2</li>
            <li role="presentation"><a href="http://www.baidu.com" role="menuitem">Action3</a></li>

        </ul>
    </div>
</div>
</div>

<div class="input-group">
<span class="input-group-addon">
    Do
</span>
<input type="text" class="form-control">

<span class="input-group-btn">      
    <button class="btn btn-default" type="button">Search</button>
</span>
</div>





</form>
            -->
        </div>
        <br/><br/>
        <!--footer-->
        <div id="footer" class=" navbar-fixed-bottom">
            <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>



    </body>
</html>
