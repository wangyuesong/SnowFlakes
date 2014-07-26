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
        <title>View History Doc</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <link href="/SnowFlakes/css/datepicker.css" rel="stylesheet">

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
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg')">
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script src="/SnowFlakes/js/jqBootstrapValidation.js"></script>            
        <script src="/SnowFlakes/js/bootstrap-datepicker.js"></script>            

        <div id="header" class="nav navbar-default" style="background: white" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="/SnowFlakes/img/logo-title.png"/>
                        </a>  
                    </div>

                    <ul class="nav navbar-nav">
                        <li><a href="projectManagement"><span class="glyphicon glyphicon-list"></span> Project</a></li>

                        <li><a href="personalCenter"><span class="glyphicon glyphicon-user"></span><span class="badge pull-right">2</span> Personal Center&nbsp;</a></li>

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

            <div class="panel panel-default" style="background-image: url('/SnowFlakes/img/bg.png')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li><a href="show">Project</a></li>
                        <li class="active">ViewHistoryDoc</li>

                    </ol>

                    <div class="tab-content" id="historyList">


                        <table class="table table2" >
                           
                            <tr  style="background: silver; font-weight: lighter">
                                <th id="Date">Date</th>
                                <th id="Author">Author</th>
                                <th id="Title">Title</th>
                                
                            </tr>
                             <c:forEach var="item" items="${docList}">
                            <tr>

                                
                                <td>${item.date}</td>
                                <td>${item.author}</td>
                                <td><a href="viewDocument?doc_id=${item.doc_id}&type=${item.type}">${item.title}</a></td>
                                <c:if test="${session_user.id == item.id}">
                                    <td><button type="button" data-toggle="modal" data-target="#deletedialog" class="close" aria-hidden="true">&times;</button></td>
                                </c:if>
                            </tr>
                             </c:forEach>

                            
                        </table>


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



        <!--footer-->
        <div id="footer"  class=" navbar-fixed-bottom" style="padding-top: 100px;" >
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
                                    <input class="form-control" required data-validation-regex-regex="(\w{0,32})" data-validation-regex-message="The maximum length can not exceed 32" type="text" id="name" name="name" >
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label for="name">Description:</label>
                                <div class="controls">
                                    <textarea class="form-control" data-validation-regex-regex="(\w{0,128})" data-validation-regex-message="The maximum length can not exceed 128" type="text" id="description:" name="description:" ></textarea>
                                </div>
                            </div>
                            <div class="form-group control-group">
                                <label for="name">Expected Due Date:*</label>
                                <div class="controls" id="duedate-div">
                                    <input class=" form-control" required type="text" id="duedate" name="duedate" >
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                            <button type="submit" id="deletion-confirm" class="btn btn-primary">Confirm</button>
                        </div>
                    </form>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->

    </body>
</html>
