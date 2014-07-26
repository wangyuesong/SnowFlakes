s<%-- 
    Document   : personalCenter
    Created on : 2014-4-27, 22:42:52
    Author     : j
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Project Management</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.css">
        <link href="css/datepicker.css" rel="stylesheet">

        <style>
            .close{
                visibility: hidden;
            }
            #projectTable > li > a {
                border-color:transparent;
                color: #333333;

            }
            #projectTable > li > a:hover {
                border-color: transparent;
                color: #333333
            }

            #projectTable > li.active > a,
            #projectTable > li.active > a:hover,
            #projectTable > li.active > a:focus {
                color: #ffffff;
                cursor: default;
                background-color: #666699;
                border:transparent;
                border-bottom-color: transparent;
            }
            .help-block{
                color: #d2322d
            }
            .datepicker:hover{
                cursor: pointer;
            }
        </style>
    </head>
    <body style="background-image: url('img/bgimg.jpg')">
        <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>
        <script src="js/jqBootstrapValidation.js"></script>            
        <script src="js/bootstrap-datepicker.js"></script>            
        <script type="text/javascript">
            $(function() {
                $.post("script/getNewMCount.jsp", {type: "userId", userId:${session_user.id}}, function(data) {
                    if(data == "0"){
                        $('#NmessageN').hide();
                    }
                    else{
                        $('#NmessageN').html(data);
                        $('#NmessageN').show();
                    }
                });
                
                
                $('#verification').tooltip('hide');
                var $deleteitem;
                var delete_item_id;
                
                
        
                $('#deletedialog').on('show.bs.modal', function(e) {
                    $deleteitem = $(e.relatedTarget);


                });
                $('#confirmDelete').click(function() {
                    delete_item_id = $deleteitem.attr("name");
                    $.post("script/delete-project.jsp", {id: delete_item_id}, function() {
                        $deleteitem.parent().remove();

                    });
                });
                $('#duedate').datepicker();
                $('.datepicker').css("z-index", "9999");
                $('#projectTable a:first').tab('show');//初始化显示哪个tab 

                $('#projectTable a').click(function(e) {
                    e.preventDefault();//阻止a链接的跳转行为 
                    $(this).tab('show');//显示当前选中的链接及关联的content 
                });
                $("input,select,textarea").not("[type=submit]").jqBootstrapValidation();

        

                $('.profile').hover(function() {
                    $(this).parent().parent().find('.close').css("visibility", "visible");

                });
                $('.profile-tree').mouseleave(function() {
                    $(this).find('.close').css("visibility", "hidden");

                });
                $('#duedate').bind("keydown", function(e) {
                    return false;
                });




            });
        </script>




        <div id="header" class="nav navbar-default" style="background: white" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="img/logo-title.png"/>
                        </a>  
                    </div>

                    <ul class="nav navbar-nav">
                        <li class="active"><a><span class="glyphicon glyphicon-list"></span> Project</a></li>

                        <li><a href="personalCenter"><span class="glyphicon glyphicon-user"></span><span id="NmessageN" class="badge pull-right">2</span> Personal Center&nbsp;</a></li>

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

        <div class="container" style="padding-top: 50px">

            <div class="panel panel-default" style="background-image: url('img/bg.png')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li class="active">ProjectManagement</li>

                    </ol>
                    <div>
                        <button type="button" id="create-btn" data-toggle="modal" name="${session_user.id}" data-target="#createdialog" class="btn btn-info pull-right">New Project</button>

                    </div><br><br><br>




                    <div  id="projectList">
                        <ul class="nav nav-tabs" id="projectTable">
                            <li class="active"><a href="#myProject"><span class="glyphicon glyphicon-tower">&nbsp;Manager</span></a></li>
                            <li><a href="#otherProject"><span class="glyphicon glyphicon-user"></span>&nbsp;Participant</a></li>
                        </ul>

                        <div class="tab-content">

                            <div class="tab-pane active" id="myProject">
                                <ul>
                                    <c:forEach var="item"  items="${ManagerProjectList}">
                                        <li class="btn btn-link profile-tree" >
                                            <button type="button" data-toggle="modal" name="${item.id}" data-target="#deletedialog" class="close" aria-hidden="true">&times;</button>
                                            <div class= "column">
                                                <a  class="profile" href="projectMainView/${item.id}/show"><img class="img img-rounded" style="width: 150px;height: 150px" src="img/projects_folder.png"/></a>  
                                                <p >${item.name}</p>
                                                <p> <span class="glyphicon glyphicon-th-list"></span> &nbsp;${item.category}</p>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>

                            </div>

                            <div class="tab-pane" id="otherProject">
                                <ul>
                                    <c:forEach var="item"  items="${MemberProjectList}">
                                        <li class="btn btn-link profile-tree" >
                                           
                                            <div class= "column">
                                                <a  class="profile" href="projectMainView/${item.id}/show"><img class="img img-rounded" style="width: 150px;height: 150px" src="img/projects_folder.png"/></a>  
                                                <p >${item.name}</p>
                                            </div>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>

                    </div>




                </div>
            </div>
        </div>

        <div class="modal fade" id="deletedialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title text-danger" id="myModalLabel">Delete</h4>
                    </div>
                    <div class="modal-body">
                        Are you sure to delete the whole project?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="confirmDelete" data-dismiss="modal" >Confirm</button>
                    </div>
                </div>
            </div>
        </div>


<br/><br/><br/>
        <!--footer-->
        <div id="footer"  class=" navbar-fixed-bottom" >
            <a class="form-control "><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>

        <div class="modal fade" id="createdialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form role="form" method="post">

                        <div class="modal-header">
                            <h4 class="modal-title text-primary" id="myModalLabel">Create Project</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group control-group">
                                <label for="name">Name:*</label>
                                <div class="controls">
                                    <input class="form-control" required data-validation-regex-regex="([\w\W]{0,32})" data-validation-regex-message="The maximum length can not exceed 32" type="text" id="name" value="${create_project.name}" name="name" >
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label for="category">Category:</label>
                                <div class="controls">
                                    <input class="form-control" value="${create_project.category}" data-validation-regex-regex="([\w\W]{0,255})" data-validation-regex-message="The maximum length can not exceed 255" type="text" id="category" name="category" >
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label for="description:">Description:</label>
                                <div class="controls">
                                    <textarea class="form-control" data-validation-regex-regex="([\w\W]{0,255})" data-validation-regex-message="The maximum length can not exceed 255" id="description" name="description" >${create_project.description}</textarea>
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label for="goal">Goal:</label>
                                <div class="controls">
                                    <textarea class="form-control" data-validation-regex-regex="([\w\W]{0,255})" data-validation-regex-message="The maximum length can not exceed 255"  id="goal" name="goal" >${create_project.goal}</textarea>
                                </div>
                            </div>
                            
                            <div class="form-group control-group">
                                <label for="duedate">Expected Due Date:</label>
                                <div class="controls" id="duedate-div">
                                    <input class=" form-control" value="${create_project.dueTime}"  type="text" id="duedate" name="dueTime" >
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" id="create-confirm" name="create" class="btn btn-primary">Confirm</button>
                        </div>
                    </form>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </body>
</html>
