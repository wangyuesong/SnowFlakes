<%-- 
    Document   : newjsp
    Created on : Apr 24, 2014, 9:19:54 PM
    Author     : coodoo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SnowFlakes register</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link href="css/datepicker.css" rel="stylesheet">

        <style>
            .help-block{
                color: #d2322d
            }
            .panel label,.panel a,.panel p{
                margin-left:5px;

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

        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jqBootstrapValidation.js"></script>    


        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script>
            $(function() {
                
                
                $('#submit').attr('disabled', "true");
                $('#register').find('input').blur(function() {
                    if ($('#register').find('.help-block  ul').length != 0) {
                        $('#submit').attr('disabled', "true");
                    }
                    else {
                        $('#submit').removeAttr("disabled");
                    }
                });



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

                        }
                        else {
                            $input_field.parent().find('.duplicate').remove();

                            $input_field.parent().parent().removeClass("has-error");
                            $input_field.parent().parent().addClass("has-success");
                        }



                    });
                });


                var has_got_code = "false";
                var code;

                $('#username').focus(function() {
                    $(this).parent().find('.duplicate').remove();
                });

                $('#emailadd').focus(function() {
                    $(this).parent().find('.duplicate').remove();
                });

                $('#emailadd').blur(function() {
                    var $input_field = $(this);
                    var email = $(this).val();
                    $.post("script/is-duplicated.jsp", {type: "email", email: email}, function(data) {
                        if (data == "true") {

                            if ($input_field.parent().find('.duplicate').length == 0) {
                                var div = "<div class='help-block duplicate'><ul role='alert'><li>This email has been used!</li></ul></div>";
                                $(div).appendTo($input_field.parent());

                            }
                            $input_field.parent().parent().removeClass("has-success");
                            $input_field.parent().parent().addClass("has-error");

                        }
                        else {
                            $input_field.parent().find('.duplicate').remove();

                            $input_field.parent().parent().removeClass("has-error");
                            $input_field.parent().parent().addClass("has-success");
                        }


                    });


                    

                });
                
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
                            $('#veri-btn').html("Get Authentication Code via Email"+" "+start_count);
                    start_count--;
                    if(start_count==-1){
                        $('#veri-btn').removeAttr("disabled");
                        $('#veri-btn').html("Get Authentication Code via Email");
                        start_count=20;
                        clearInterval(authcode_timer);
                    }
                        },1000);
                });
                
                
                
              
                $('#birthday').datepicker();

                $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();
                $('#birthday').bind("keydown", function(e) {
                    return false;
                });
                $('#submit').click(function() {
                    
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
        <div id="header" class="nav navbar-inverse navbar-fixed-top" style="opacity: 0.9" role="navigation">
            <div class="row" style="margin: 0 auto; display: table; width: auto;">
                <div>
                    <div class="navbar-header">
                        <a class="navbar-brand" href="index" style="color: white;">
                            SnowFlakes   
                        </a>
                    </div>

                    <ul class="nav navbar-nav ">
                        <li><a href="index"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                        <li><a href="introduction" ><span class="glyphicon glyphicon-eye-open"></span> Introduction</a></li>

                        <li><a href="#" ><span class="glyphicon glyphicon-question-sign"> </span> Help</a></li>
                        <li><a href="about" ><span class="glyphicon glyphicon-info-sign"> </span> About</a></li>
                        <li><a href="#" ><span class="glyphicon glyphicon-user"> </span> Mobile</a></li>
                        <li><a style=" color: white">|</a></li>
                        <li class="active"><a>Register</a></li>

                        <li><a href="index#panel" >Login</a></li>
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

        <div class="container">
            <div class="panel panel-collapse">
                <div class="panel-body">
                    <div class="nav navbar" role="navigation">


                        <div class="navbar-header">
                            <div class="row">
                                <div class="col-md-2">

                                    <a class="navbar-brand">
                                        <img class="img-rounded" style="width: 120%;height: 120%; " src="<c:url value='img/logo.png'/>">
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

            <br/>
            <div class="panel panel-default" style="background-image: url('img/bg.jpg')">
                <div class="panel-body">
                    <ul class="nav nav-tabs" >
                        <li class="active"><a href="#register" data-toggle="tab">Register</a></li>
                    </ul><br/>
                    <div class="tab-content">
                        <div class="tab-pane active" id="register">
                            <form method="post" >
                                <div class="form-group">
                                    <p class="text-danger">Hint: Username, Password field <em>only</em> accept A-Z, a-z, 0-9 for each character.</p>
                                </div>
                                <div class="form-group control-group">
                                    <label for="username">Username:*</label>
                                    <div class="controls">
                                        <input value="${myinfo.name}" name="name" required class="form-control" data-validation-regex-regex="(\w{0,32})" data-validation-regex-message="The maximum length can not exceed 32, and each character must be valid!" id="username" type="text">
                                    </div>

                                </div>
                                <div class="form-group control-group">
                                    <label for="emailadd">Email:*</label>
                                    <div class="controls">

                                        <input value="${myinfo.email}" name="email" required class="form-control" id="emailadd"  type="email">
                                    </div>
                                </div>
                                <div class="form-group control-group">
                                    <label >Password:*</label>
                                    <div class="controls">
                                        <input value="${myinfo.password}" name="password" data-validation-regex-regex="(\w{6,32})" data-validation-regex-message="The password length must between 6 and 32" required class="form-control"  type="password">
                                    </div>
                                </div>
                                <div class="form-group control-group">
                                    <label for="reg-repassword" class="control-label">Re-Enter Password:*</label>
                                    <div class="controls">
                                        <input value="${myinfo.password}" name="reg-repassword"  data-validation-matches-match="password" data-validation-matches-message="Must match password entered above"  
                                               class="form-control " id="reg-repassword" type="password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="birthday">Birthday:</label>
                                    <input value="${myinfo.birthday}" name="birthday" placeholder="Click for calendar"
                                           class="form-control" type="text" id="birthday" >
                                </div>
                                <div class="form-group">
                                    <label for="address1">Address 1:</label>
                                    <input value="${myinfo.address1}" name="address1" placeholder="Street Address" data-validation-regex-regex="([\w\W]{0,255})" data-validation-regex-message="The maximum length can not exceed 255" class="form-control" id="address1" type="text">
                                </div>
                                <div class="form-group">
                                    <label for="address2">Address 2:</label>
                                    <input value="${myinfo.address2}" name="address2" data-validation-regex-regex="([\w\W]{0,255})" data-validation-regex-message="The maximum length can not exceed 255" placeholder="City, State/Province Zipcode" class="form-control" id="address2" type="text">
                                </div>

                                <div class="form-group control-group">
                                    <label for="phone">Phone:</label>
                                    <div class="controls">
                                        <input value="${myinfo.phone}" name="phone" pattern="((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)" title="invalid phone format!"
                                               data-validation-regex-regex="(\w{0,16})" data-validation-regex-message="The maximum length can not exceed 16" class="form-control" id="phone" type="text">
                                    </div>
                                </div>
                                <div class="form-group">                                    
                                    <label for="validation">Authentication Code:</label>                                    
                                    <div>
                                    <input style="width:100px" class="auth" id="validation" type="text">


                                    <button style=" margin-left: 20px;" type="button" id="veri-btn" class="btn btn-primary">Get Authentication Code via Email</button>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="legal">Legal</label>&nbsp;&nbsp;&nbsp;


                                    <div class="checkbox-inline">
                                        <input  id="legal" type="checkbox" required  data-validation-required-message="You must agree to the terms and conditions"> 

                                        I agree to the <a href="#">terms and conditions</a>

                                    </div>
                                </div>
                                <div class="form-group">

                                    <Button id="submit" type="submit" name="finish-register" class="btn btn-success" >Submit</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>                                      





        <!--footer-->
        <div id="footer" style="opacity: 0.8">
            <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>
    </body>
</html>
