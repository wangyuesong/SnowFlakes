<%-- 
    Document   : team
    Created on : Apr 27, 2014, 6:37:43 PM
    Author     : coodoo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Snowflakes team</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <style>
            .close{
                visibility: hidden;
            }
            .profile-tree p{
                position: relative;
                left: -8px;
            }
        </style>
        <script src="/SnowFlakes/jquery-ui-1.10.3.js"></script>


    </head>
    <body style="background-image: url('img/bgimg.jpg')">
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script src="/SnowFlakes/fullCalendar/jquery-ui-1.10.3.js"></script>

        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script>
            $(function() {
                $.post("/SnowFlakes/script/getNewMCount.jsp", {type: "userId", userId:${session_user.id}}, function(data) {
                    if (data == "0") {
                        $('#NmessageN').hide();
                    }
                    else {
                        $('#NmessageN').html(data);
                        $('#NmessageN').show();
                    }
                });

                var $invite = $('#invite');
                var $member = $('#member');
                $("li", $invite).draggable({
                    revert: "invalid",
                    containment: "document",
                });

                $member.droppable({
                    accept: "#invite li",
                    drop: function(event, ui) {
                        addImage(ui.draggable);
                    }
                });




                function addImage($item) {
                    $item.fadeOut(function() {
                        $item.find(".close").html("&times;").css("visibility", "hidden").attr("data-toggle", "modal").attr("data-target", "#deletedialog")
                                .end().appendTo($member).fadeIn();
                    });
                }



                $("#invite").find(".close").click(function() {
                    var $item = $(this).parent();
                    addImage($item);
                    return true;
                });

                $("#searchmember").find(".close").click(function() {
                    var $item = $(this).parent();
                    addImage($item);
                    return true;
                });

                $('.profile').hover(function() {
                    $(this).parent().find('.close').css("visibility", "visible");

                });
                $('.profile-tree').mouseleave(function() {
                    $(this).find('.close').css("visibility", "hidden");

                });

                var $deleteitem;

                $('#deletedialog').on('show.bs.modal', function(e) {
                    $deleteitem = e.relatedTarget;



                });

                $('#deletion-confirm').click(function() {
                    var $li = $deleteitem.parentNode;
                    deleteImage($li);
                });

                function deleteImage($item) {
                    $item.remove();
                }

            });

            $(function() {
                $("#ajaxSearch").click(function() {
//                    alert($("input[name='searchString']").val());
                    $('#resultDiv').load('search/' + $("input[name='searchString']").val());

                })
            });

            $(function() {
                $('.close').click(function()
                {
                    var id = $(this).attr("id");

                    var url = "deleteInvitation/" + id ;
                    //首先更改模态框提交地址
//                    alert(url);
                    $("#deleteMemberForm").attr("action", url);
                });
            });
        </script>

        <!--header-->
        <div id="header" class="nav navbar-default navbar-fixed-top" style="background: white" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="/SnowFlakes/img/logo-title.png"/>
                        </a>  
                    </div>

                    <ul class="nav navbar-nav">
                        <li><a href="/SnowFlakes/projectMainView/${project.id}/show"><span class="glyphicon glyphicon-arrow-left"></span> Back </a></li>
                        <li><a href="/SnowFlakes/projectManagement"><span class="glyphicon glyphicon-list"></span> Project</a></li>

                        <li><a href="/SnowFlakes/personalCenter"><span class="glyphicon glyphicon-user"></span><span id="NmessageN" class="badge pull-right">2</span> Personal Center&nbsp;</a></li>

                        <li><a href="/SnowFlakes/index?log_out=true" ><span class="glyphicon glyphicon-off"> </span> Exit</a></li>

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
        <br/>
        <br/>
        <br/>

        <div class="container">





            <div class="panel panel-default" style="background-image: url('/SnowFlakes/img/bg.jpg')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li><a href="#">Project</a></li>
                        <li class="active">Team</li>
                    </ol>
                    <ul class="nav nav-tabs" >
                        <li class="active"><a href="#team" data-toggle="tab">Team</a></li>
                    </ul><br/>
                    <div class="tab-content">
                        <div class="tab-pane active" id="team">
                            <div class="leader text-center panel" style=" background: transparent;">
                                <h4 class="text-info">Manager</h4>
                                <a><img class="img img-circle img-responsive img-thumbnail" style="height: 80px; width: 80px"  src="/SnowFlakes/headPic/${owner.headpic}"></a>
                                <p class=" text-muted">${owner.name}</p>


                            </div>


                            <div class="member list-group list-inline leader text-center panel" style="background: transparent;">
                                <h4 class="text-info text-center">Group Members</h4>
                                <c:forEach var="member" items="${allMembers}">
                                    <ul class="btn-group" id="member" >
                                        <li class="btn btn-link profile-tree">  
                                            <c:if test="${owner.id==session_user.id}">
                                            <button type="button" id="${member.id}" data-toggle="modal" data-target="#deletedialog"
                                                    class="close" aria-hidden="true">&times;</button>
                                                    </c:if>
                                            <a class="profile" ><img class="img img-circle img-responsive img-thumbnail"  style=" height: 80px; width: 80px" src="/SnowFlakes/headPic/${member.headpic}"/></a>
                                            <p class="text-muted">${member.name}</p>
                                        </li>
                                    </ul>
                                </c:forEach>
                                <br/><br/>
                            </div>
                            <div class="panel-group text-center" id="accordion" >
                                <div class="panel panel-success" >
                                    <div class=" panel-heading" >
                                        <h3 class="panel-title" >
                                            <a  data-toggle="collapse" data-parent="#accordion" href="#item1">Invite old member
                                            </a></h3>                                       
                                    </div>
                                    <div id="item1" class="panel-collapse collapse in">
                                        <div class="text-center panel" style=" background: transparent;">
                                            <div class="row">
                                                <h5 class="text-info">Here are the users you have worked with: </h5>
                                            </div>
                                           
                                            <c:forEach items="${otherUsers}" var="user">
                                            <div class='col-md-3 col-md-offset-1' style= 'border-style: groove;  border-width:  thin;padding:10px; margin-bottom: 20px; '>
                                                <center>
                                                    <img src="/SnowFlakes/headPic/${user.headpic}" 
                                                         class='img-rounded img-responsive img-thumbnail' style='height:80px; width:80px'/>
                                                </center>
                                                <hr/>
                                                <center>
                                                    <a href='#'>${user.name}</a><hr/>
                                                </center>
                                                <hr/>
                                                <a class='btn btn-default' href='sendInvitation/${user.id}' 
                                                   onclick="alert('An Invitation has been sent!')">Invite</a>
                                            </div>
                                             </c:forEach>
                                        </div>
                                    </div>
                                    <div class="panel panel-success" >
                                        <div class=" panel-heading">
                                            <h3 class="panel-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#item2">Search new member
                                                </a></h3>                                                
                                        </div>
                                        <div id="item2" class=" panel-collapse collapse">
                                            <div class="panel-body">

                                                <div class="form-group">
                                                    <div class="row">
                                                        <div class="col-sm-7 col-sm-offset-2">
                                                            <input class="form-control" id="search" type="text" name="searchString" placeholder="Input user's Email/Name">
                                                        </div>
                                                        <div class="col-sm-1">

                                                            <button type="submit" id="ajaxSearch" class="btn btn-warning">Search</button>

                                                        </div>
                                                    </div>
                                                    <div class="searchresult">
                                                        <h5 class="text-info">Here are the people you may be looking for:</h5>
                                                        <hr>

                                                        <div id="resultDiv" class="row">

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
                </div>

            </div>                                      
            <form id="deleteMemberForm">
                <div class="modal fade" id="deletedialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title text-danger" id="myModalLabel">Alert</h4>
                            </div>
                            <div class="modal-body">
                                Do you really want to remove this person from your team?
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button type="submit" id="deletion-confirm" class="btn btn-primary">Confirm</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </div><!-- /.modal-dialog -->
                </div><!-- /.modal -->
            </form>





            <!--footer-->
            <div id="footer" class="navbar-fixed-bottom" style="opacity: 0.8">
                <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
            </div>

    </body>


</html>
