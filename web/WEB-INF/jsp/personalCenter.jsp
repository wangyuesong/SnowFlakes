<%-- 
    Document   : personalCenter
    Created on : 2014-4-27, 22:42:52
    Author     : j
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Personal Center</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link rel="stylesheet" href="css/datepicker.css">


        <style>
            #Due-date, #Assign-date, #projects:hover{
                cursor:  pointer
            }
            #myTab{
                background: #BBBBBB;-moz-border-radius: 4px;-webkit-border-radius: 4px;border-radius:4px;
            }

            #infoTab > li > a {
                border-color:transparent;
                color: #000;

            }
            #infoTab > li > a:hover {
                border-color: transparent;
                color: #333333
            }

            #infoTab > li.active > a,
            #infoTab > li.active > a:hover,
            #infoTab > li.active > a:focus {
                color: #ffffff;
                cursor: default;
                background-color: #333366;
                border:transparent;
                border-bottom-color: transparent;
            }

            #taskTab > li > a {
                border-color:transparent;
                color: #000;

            }
            #taskTab > li > a:hover {
                border-color: transparent;
                color: #333333
            }

            #taskTab > li.active > a,
            #taskTab > li.active > a:hover,
            #taskTab > li.active > a:focus {
                color: #ffffff;
                cursor: default;
                background-color: #333366;
                border:transparent;
                border-bottom-color: transparent;
            }

            .table3,.table2,.table2-2{
                background: #666699;-moz-border-radius: 4px;-webkit-border-radius: 4px;border-radius:4px;

            }
            .table3 a{
                color:  #000;
                font-weight: lighter;
            }
            .table2 a,.table2-2 a{
                color: #000;
            }

            .help-block{
                color: #d2322d
            }
            .panel label,.panel a,.panel p{
                margin-left:5px;

            }
        </style>
    </head>
    <body style=" background: url('img/bgimg.jpg');">
        <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/sortElements.js"></script>
        <script src="js/jqBootstrapValidation.js"></script>  

        <script>



            $(function() {
      
                $("#SettingChange").click(function(){
                    var url = "personalCenter/settingChange";
                    var message = 1;
                    var email = 1;
                    if(!document.getElementById("messageSetting").checked)
                        var message = 0;
                    if(!document.getElementById("emailSetting").checked)
                        var email = 0;
                    $.ajax({
                     type: "post",
                     dataType: "",
                     url: url,
                     data: {message : message, email: email},
                     
                     success : function() {
                         alert("Success!");
                     },
                     error : function() {
                         alert("Fail! Please try again latter.");
                     }
                    });
                });
                
                $(".myAccept").click(function() {
                    var id = $(this).attr("myValue");
                    var url = "personalCenter/acceptInvitation/" + id;
                    var dele = $(this);
                    $.ajax({
                     type: "post",
                     dataType: "",
                     url: url,
                     data: "",
                     success : function() {
                         dele.parent().parent().remove();
                         alert("Success! You are now a member of the team!");
                     },
                     error : function() {
                         alert("No network connected!");
                     }
                    });
                    //$(this).parent().parent().remove();
                });
                $(".myRefuse").click(function() {
                    var id = $(this).attr("myValue");
                    var url = "personalCenter/refuseInvitation/" + id;
                    var dele = $(this);
                    $.ajax({
                     type: "post",
                     dataType: "",
                     url: url,
                     data: "",
                     success : function() {
                         dele.parent().parent().remove();
                         alert("You refused the invitation successfully!");
                     },
                     error : function() {
                         alert("No network connected!");
                     }
                    });
                    //$(this).parent().parent().remove();
                });

                $.post("script/getNewMCount.jsp", {type: "userId", userId:${myInfo.id}}, function(data) {
                    if (data == "0") {
                        $('#NmessageN').hide();
                        $('#NmessageN2').hide();
                    }
                    else {
                        $('#NmessageN').html(data);
                        $('#NmessageN2').html(data);
                        $('#NmessageN').show();
                        $('#NmessageN2').show();
                    }
                });
                //$('#NmessageN').css("display","block");
                //$('#NmessageN').hide();
                //$('#NmessageN').show();
                //$('#NmessageN').html("100");
                //$('#NmessageN2').html("10");
                //$('#NmessageN').val("3");
                function sortTable(table) {
                    $('#Due-date, #Assign-date,#projects')
                            .wrapInner('<span title="sort this column"/>')
                            .each(function() {

                                var th = $(this),
                                        thIndex = th.index(),
                                        inverse = false;

                                th.click(function() {

                                    table.find('td').filter(function() {

                                        return $(this).index() === thIndex;

                                    }).sortElements(function(a, b) {

                                        if ($.text([a]) == $.text([b]))
                                            return 0;

                                        return $.text([a]) > $.text([b]) ?
                                                inverse ? -1 : 1
                                                : inverse ? 1 : -1;

                                    }, function() {

                                        // parentNode is the element we want to move
                                        return this.parentNode;

                                    });

                                    inverse = !inverse;

                                });

                            });
                }
                sortTable($('.table2'));
                sortTable($('.table2-2'));




                $('#myTab a').click(function(e) {
                    e.preventDefault();//阻止a链接的跳转行为 
                    $(this).tab('show');//显示当前选中的链接及关联的content 
                });


                var username;
                var phone;
                var birthday;
                var address1;
                var address2;
                $("#edit").click(function() {
                    if ($(".editable").attr("readOnly")) {

                        username = $('#username').val();
                        phone = $('#phone').val();
                        birthday = $('#birthday').val();
                        address1 = $('#address1').val();
                        address2 = $('#address2').val();
                        $(this).html("Cancel");
                        $(".editable").attr("readOnly", false);
                        $(".editable").attr("disabled", false);
                        $("#save").attr("disabled", false);
                    }
                    else {
                        $('#username').val(username);
                        $('#phone').val(phone);
                        $('#birthday').val(birthday);
                        $('#address1').val(address1);
                        $('#address2').val(address2);
                        $(this).html("Edit");

                        $(".editable").attr("readOnly", true);
                        $(".editable").attr("disabled", true);

                        $("#save").attr("disabled", true);
                        $('#username').parent().find('.duplicate').remove();

                        $('#username').parent().parent().removeClass("has-error");
                        $('#phone').parent().parent().removeClass("has-error");

                    }


                });

                $('#infoTab a:first').tab('show');//初始化显示哪个tab 

                $('#infoTab a').click(function(e) {
                    e.preventDefault();//阻止a链接的跳转行为 
                    $(this).tab('show');//显示当前选中的链接及关联的content 
                });

                $('#taskTab a').click(function(e) {
                    e.preventDefault();
                    $(this).tab('show');
                });

                $('#birthday').datepicker();
                $('#birthday').bind("keydown", function(e) {
                    return false;
                });

                $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();

                $('#username').blur(function() {
                    $('#save').attr('disabled', "false");
                    var $input_field = $(this);
                    var username = $(this).val();
                    $.post("script/is-duplicated.jsp", {type: "modiUsername", username: username, pname: "${myInfo.name}"}, function(data) {
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

                $('#username').focus(function() {

                    $(this).parent().find('.duplicate').remove();
                    $('#save').attr('disabled', "true");
                });

                $('#modiPInfo').find('input').blur(function() {

                    if ($('#modiPInfo').find('.help-block  ul').length != 0) {
                        $('#save').attr('disabled', "true");
                    }
                    else {
                        $('#save').removeAttr("disabled");
                    }
                });
                $('#save').click(function() {
                    $('#modiPInfo').find('input').trigger("blur");

                });

                $('#modi-password').find('input').blur(function() {

                    if ($('#modi-password').find('.help-block  ul').length != 0) {
                        $('#modi-pwd').attr('disabled', "true");
                    }
                    else {
                        $('#modi-pwd').removeAttr("disabled");
                    }
                });

                $('#modi-pwd').click(function() {
                    $('#modi-password').find('input').trigger("blur");

                });

                $('#old_password').blur(function() {
                    $('#modi-pwd').attr('disabled', "false");
                    var $input_field = $(this);
                    var opw = $(this).val();
                    $.post("script/password-resure.jsp", {type: "pwresure", pid: "${myInfo.id}", pw: opw}, function(data) {
                        if (data == "false") {

                            if ($input_field.parent().find('.duplicate').length == 0) {
                                var div = "<div class='help-block wrong'><ul role='alert'><li>Wrong password!</li></ul></div>";
                                $(div).appendTo($input_field.parent());

                            }
                            $input_field.parent().parent().removeClass("has-success");
                            $input_field.parent().parent().addClass("has-error");

                        }
                        else {
                            $input_field.parent().find('.wrong').remove();

                            $input_field.parent().parent().removeClass("has-error");
                            $input_field.parent().parent().addClass("has-success");
                        }
                    });
                });
                $('#old_password').focus(function() {

                    $(this).parent().find('.wrong').remove();
                    $('#modi-pwd').attr('disabled', "true");
                });

                $('a.taskDetail').click(function()
                {
                    var id = $(this).attr("id");
                    var url = "personalCenter/getTaskDetail/" + id;
                    //首先更改模态框提交地址
                    //$("#taskDetail").attr("action", "updateOneTask/" + id);
                    var dele = $(this);

                    $.ajax({
                        type: "get",
                        dataType: "",
                        url: url,
                        data: "",
                        success: function(json) {

                            var j = json.replace(/'/g, "\"")
                            j = "[" + j + "]";
                            j = eval("(" + j + ")");

                            $("#taskDetail-content").text(
                                    j[0]["description"]
                                    );
                            $("#taskDetail-BDate").text(j[0]["begindate"]);
                            $("#taskDetail-FDate").text(j[0]["finishdate"]);
                            $("#taskDetail-Pname").text(j[0]["projectname"]);
                            
                       },
                        error: function() {
                            //window.location="redirect:personalCenter";
                            $("#showTaskDetail").modal('toggle');
                            alert("No network or the task has been deleted!");
                            dele.parent().parent().remove();
                        }
                    })
                });

                $('a.messageDetail').click(function()
                {
                    var id = $(this).attr("id");
                    var url = "personalCenter/getMessageDetail/" + id;
                    //首先更改模态框提交地址
                    //$("#taskDetail").attr("action", "updateOneTask/" + id);
                    var dele = $(this);

                    $.ajax({
                        type: "get",
                        dataType: "",
                        url: url,
                        data: "",
                        success: function(json) {

                            var j = json.replace(/'/g, "\"")
                            j = "[" + j + "]";
                            j = eval("(" + j + ")");

                            $("#messageDetail-details").text(
                                    j[0]["details"]
                                    );
                            $("#messageDetail-date").text(j[0]["date"]);
                            
                        },
                        error: function() {
                            $("#showMessageDetail").modal('toggle');
                            alert("操作失败");
                            dele.parent().parent().remove();
                        }
                    })
                });
                var trigger_message;
                var removed_message;
                $('#showMessageDetail').on('show.bs.modal', function(e) {
                    trigger_message = e.relatedTarget;
                });

                $('#showMessageDetail').on('hide.bs.modal', function(e) {
                    if ($(trigger_message).parent().parent().parent().parent().parent().attr("id") == "new-info") {
                        removed_message = $(trigger_message).parent().parent();
                        removed_message.remove();
                        removed_message.appendTo($('#old-info').find("table"));
                        if (Number($("#NmessageN2").html()) <= 1)
                            $("#NmessageN2").hide();
                        $("#NmessageN2").html(Number($("#NmessageN2").html()) - 1);
                    }

                });




            });




        </script>



        <div id="header" class="nav navbar-default" style="background: white; background-size: contain;opacity: 0.9" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="img/logo-title.png"/>
                        </a>  
                    </div>

                    <ul class="nav navbar-nav" >
                        <li><a href="projectManagement" ><span class="glyphicon glyphicon-list"></span> Project</a></li>
                        <li class="active"><a><span class="glyphicon glyphicon-user" ></span><span id="NmessageN"  class="badge pull-right" ></span> Personal Center&nbsp;</a></li>

                        <li><a href="index?log_out=true" ><span class="glyphicon glyphicon-off"> </span> Exit</a></li>

                        <li><a >|</a></li>

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

        <div class="container" style="padding-top: 20px;">
            <div class="panel " style="background-image: url('img/bg.png')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li class="active">PersonalCenter</li>
                    </ol>
                    <div class="col-md-2" style=" padding-top: 17px">

                        <ul class="nav nav-pills nav-stacked" id="myTab">
                            <li class="active"><a href="#table-1">Basic Info</a></li>
                            <li><a href="#table-2">Task List</a></li>
                            <li><a href="#table-3"></span><span id="NmessageN2" class="badge pull-right"></span>Message Box</a></li>
                            <li><a href="#table-6">Invitation</a></li>
                            <li><a href="#table-4">Modify Password</a></li>

                            <li><a href="#table-5">Setting</a></li>
                        </ul>
                    </div>

                    <div class="col-md-8" >
                        <div class="tab-content">

                            <div class="tab-pane active" id="table-1" >
                                <div class="panel" style="background: transparent;">
                                    <div class="panel-body" id="modiPInfo">

                                        <form method="post" enctype="multipart/form-data">
                                            <div class="form-group">
                                                <img src="<c:url value='${headPic}'/>" class="img img-thumbnail" style="width: 140px;height: 140px">
                                                <br/>
                                                <br/>

                                                <input type="file" name="file"  id="exampleInputFile" accept="image/gif, image/jpeg, image/jpg" >

                                                <button id="modipic" name="modifyPic" type="submit" class="btn btn-success">Change</button>
                                            </div>
                                        </form>
                                        <form method="post">

                                            <div class="form-group control-group">
                                                <label for="account">Email</label>

                                                <input type="email" class="form-control" name="email" id="account" placeholder="Put in your account here" disabled value="${myInfo.email}">

                                            </div>
                                            <div class="form-group control-group">
                                                <label for="username">Username</label>
                                                <div class="controls">
                                                    <input type="text" class="form-control editable" name="name" required id="username" placeholder="Fill in your nickname here" readonly="readonly" value="${myInfo.name}">
                                                </div>
                                            </div>
                                            <div class="form-group control-group">
                                                <label for="phone">Phone</label>
                                                <div class="controls">
                                                    <input name="phone" type="tel" pattern="((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)" title="invalid phone format!" class="form-control editable" id="phone" placeholder="Fill in your phone number here" readonly="readonly" value="${myInfo.phone}">
                                                </div>
                                            </div> 
                                            <div class="form-group">
                                                <label for="birthday">Birthday</label>
                                                <input name="bir" class="form-control editable" type="text" id="birthday" placeholder="Fill in your birthday here" disabled value="${myInfo.birthday}">
                                            </div>
                                            <div class="form-group">
                                                <label for="address1">Address1</label>
                                                <input name="address1" type="text" class="form-control editable" id="address1" placeholder="Fill in your address here" readonly="readonly" value="${myInfo.address1}">
                                            </div>
                                            <div class="form-group">
                                                <label for="address2">Address2</label>
                                                <input name="address2" type="text" class="form-control editable" id="address2" placeholder="Fill in your address here" readonly="readonly" value="${myInfo.address2}">
                                            </div>

                                            <div class="form-group">
                                                <p  style="color: red">Please click "Edit" to update your profile, and "Save" to save changes.</p>     

                                                <button id="edit" type="button" class="btn btn-info" >Edit</button>&nbsp;&nbsp;&nbsp;&nbsp;
                                                <button  id="save" name="modifyInfo" type="submit" class="btn btn-success" disabled>Save</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>


                            <div class="tab-pane" id="table-2">
                                <div class="panel" style="background: transparent;">
                                    <div class="panel-body">
                                        <ul class="nav nav-tabs"  id="taskTab">
                                            <li class="active"><a href="#pending-task">Pending Task</a></li>
                                            <li><a href="#history-task">History Task</a></li>
                                        </ul>
                                        <div class="tab-content" id="table2-tab" style=" overflow: scroll; max-height: 500px">

                                            <div class="tab-pane active" id="pending-task" style=" overflow: scroll; max-height: 500px">

                                                <table class="table table2" >
                                                    <tr  style="background: silver; font-weight: lighter">
                                                        <th>Task Id</th>
                                                        <th id="projects">Project</th>
                                                        <th id="Due-date">Due Date</th>
                                                        <th id="Assign-date">Assign Date</th>      
                                                    </tr>

                                                    <c:forEach var="item"  items="${PendingTaskList}">
                                                        <tr>
                                                            <td><a class="taskDetail" id="${item.title}" data-target="#showTaskDetail" data-toggle="modal" href>${item.title}</a></td>
                                                            <td>${item.projectName}</td>
                                                            <td>${item.dueTime}</td>
                                                            <td>${item.assignTime}</td>                  

                                                        </tr>
                                                    </c:forEach>


                                                </table>

                                            </div>
                                            <div class="tab-pane" id="history-task" style=" overflow: scroll; max-height: 500px" >
                                                <table class="table table2-2" >
                                                    <tr style="background: silver; font-weight: lighter">
                                                        <th>Task Id</th>
                                                        <th id="projects">Project</th>
                                                        <th id="Due-date">Due Date</th>
                                                        <th id="Assign-date">Assign Date</th>       
                                                    </tr>

                                                    <c:forEach var="item"  items="${HistoryTaskList}">
                                                        <tr>
                                                            <td><a class="taskDetail" id="${item.title}" data-target="#showTaskDetail" data-toggle="modal" href>${item.title}</a></td>
                                                            <td>${item.projectName}</td>
                                                            <td>${item.dueTime}</td>
                                                            <td>${item.assignTime}</td>       
                                                        </tr>
                                                    </c:forEach>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>


                            <div class="tab-pane" id="table-3" >
                                <div class="panel" style="background: transparent;" style=" overflow: scroll; max-height: 500px">
                                    <div class="panel-body">
                                        <ul class="nav nav-tabs"  id="infoTab">
                                            <li class="active"><a href="#new-info">New Message</a></li>
                                            <li><a href="#old-info">History Message</a></li>
                                        </ul>

                                        <div class="tab-content" id="table3-tab">

                                            <div class="tab-pane active" id="new-info">
                                                <table  class="table table3">
                                                    <c:forEach var="item"  items="${PendingMessageList}">
                                                        <tr>
                                                            <th><a class="messageDetail" id="${item.id}" data-target="#showMessageDetail" data-toggle="modal" href>${item.details}</a></th>
                                                        </tr>
                                                    </c:forEach>
                                                </table>
                                            </div>

                                            <div class="tab-pane" id="old-info">
                                                <table class="table table3">

                                                    <c:forEach var="item"  items="${HistoryMessageList}">
                                                        <tr>

                                                            <th><a class="messageDetail" id="${item.id}" data-target="#showMessageDetail" data-toggle="modal" href>${item.details}</a></th>
                                                        </tr>
                                                    </c:forEach>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>


                            <div class="tab-pane" id="table-4">
                                <div class="panel" style="background: transparent;">
                                    <div class="panel-body" id="modi-password">
                                        <form method="post">
                                            <div class="form-group control-group">
                                                <label for="old_password">Present Password</label>
                                                <div class="controls">
                                                    <input type="password" class="form-control" required id="old_password" name="old_password" placeholder="Put in your present password here" value="${modipw.old_password}">
                                                </div>
                                            </div>
                                            <div class="form-group control-group">
                                                <label for="new_password1">New Password</label>
                                                <div class="controls">

                                                    <input class="form-control" data-validation-regex-regex="(\w{6,32})" data-validation-regex-message="The password length must between 6 and 32" 
                                                           required id="new_password1" placeholder="Put in your new password here" value="${modipw.new_password1}" name="new_password1" type="password">
                                                </div>
                                            </div>
                                            <div class="form-group control-group">
                                                <label for="new_password2">Confirm Password</label>
                                                <div class="controls">

                                                    <input class="form-control" data-validation-matches-match="new_password1" data-validation-matches-message="Must match password entered above" required
                                                           id="new_repassword2" name="new_password2" type="password" placeholder="Confirm your new password here" value="${modipw.new_password2}">
                                                </div>
                                            </div>

                                            <button  type="submit" id="modi-pwd" name="modi-pwd" class="btn btn-success" >Submit</button>

                                        </form>
                                    </div>
                                </div>
                            </div>


                            <div class="tab-pane" id="table-6" style=" overflow: scroll; max-height: 500px" >
                                <div class="panel" style="background: transparent;">
                                    <div class="panel-body">
                                        <table  class="table table3">
                                            <tr  style="background: silver; font-weight: lighter">
                                                <th>Project Name</th>
                                                <th>Project ID</th>
                                                <th>Inviter Name</th>
                                                <th>Date</th>
                                                <th></th>

                                            </tr>
                                            <c:forEach var="item"  items="${Invitation}">
                                                <tr>
                                                    <th>${item.project_name}</th>
                                                    <th>${item.project_id}</th>
                                                    <th>${item.owner_name}</th>
                                                    <th>${item.time}</th>
                                                    <th><button id="accetpInvitation" name="accetpInvitation" type="button" class="btn btn-success btn-xs  myAccept"  myValue = "${item.id}">Accept</button>
                                                        <button id="refuseInvitation" type="button" class="btn btn-danger btn-xs myRefuse" myValue = "${item.id}" >Refuse</button></th>   
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </div>



                            <div class="tab-pane" id="table-5">
                                <div class="panel" style="background: transparent;">

                                    <div class="panel-body">
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" id="messageSetting" <c:if test="${myInfo.notification_settings[0] eq 1}">checked="checked" </c:if> > Send me a message I get a new task
                                            </label>
                                        </div>

                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox" id="emailSetting" <c:if test="${myInfo.notification_settings[1] eq 1}">checked="checked" </c:if> > Send me an Email when I get a new task
                                            </label>
                                        </div>
                                        <br/>
                                        <button  id="SettingChange" class="btn btn-default"  >Save</button>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>


                </div>
            </div>




        </div>



        <!--footer-->
        <div id="footer"  class="navbar-fixed-bottom " >
            <a class="form-control "><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>



        <div class="modal fade" id="showTaskDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form id="taskDetail">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myModalLabel">Task Details</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Project Name:</label>
                                <text id="taskDetail-Pname"></text>
                            </div>

                            <div class="form-group">
                                <label >Detailed Description:</label>
                                <textarea id="taskDetail-content" readonly="readonly" class="form-control"  name="content" cols="40" rows="10"></textarea>
                            </div>
                            <div class="form-group">
                                <label>Assign Date:</label>
                                <text id="taskDetail-BDate"></text>
                            </div>
                            <div class="form-group">
                                <label>Due Date:</label>
                                <text id="taskDetail-FDate"></text>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Back</button>

                        </div>
                    </div>
                </form>
            </div>
        </div>


        <div class="modal fade" id="showMessageDetail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <form id="messageDetail">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title" id="myModalLabel">Message Details</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label>Date:</label>
                                <text id="messageDetail-date"></text>
                            </div>

                            <div class="form-group">
                                <label >Details:</label>
                                <textarea id="messageDetail-details" readonly="readonly" class="form-control"  name="content" cols="40" rows="10"></textarea>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" id="message-back-btn" class="btn btn-default" data-dismiss="modal">Back</button>

                        </div>
                    </div>
                </form>
            </div>
        </div>



    </body>
</html>
