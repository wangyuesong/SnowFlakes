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
        <title>View Document</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <link href="/SnowFlakes/css/datepicker.css" rel="stylesheet">
        <link href="/SnowFlakes/css/bootstrap.css" rel="stylesheet"/>
        <link href="/SnowFlakes/css/bootstrap-v2.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/bootstrap-wysihtml5.css"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/prettify.css"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/wysiwyg-color.css"/>

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
            .popover{
                background-color: darkseagreen;
            }
            .popover.right .arrow:after{
                border-right-color:darkseagreen;
            }
        </style>
    </head>
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg')" onunload="leavePage()">
        <script type="text/javascript" src="/SnowFlakes/js/wysihtml5-0.3.0.min.js"></script>       

        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/prettify.js"></script>

        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap-wysihtml5.js"></script>


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

                        <li><a href="/SnowFlakes/projectManagement"><span class="glyphicon glyphicon-list"></span> Project</a></li>
                        <li><a href="/SnowFlakes/personalCenter"><span class="glyphicon glyphicon-user"></span><span class="badge pull-right">2</span> Personal Center&nbsp;</a></li>

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

        <div class="container" style="padding-top: 30px">

            <div class="panel panel-default" style="background-image: url('/SnowFlakes/img/bg.png')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li><a href="projectMainView">Project</a></li>
                        <li class="active">ViewDocument</li>

                    </ol>
                    <form>
                        <div class="form-group">
                            <label for="vtitle">&nbsp;Title</label>
                            <input type="text" class="form-control editable" style="width: 500px;height: 40px" name="title" id="vtitle" disabled placeholder="Fill in your title here" value="${doc.title}">
                        </div>
                        <div class="form-group">
                            <label for="description">&nbsp;Description</label>
                            <input type="text" class="form-control editable" style="width: 500px;height: 40px" id="description" name="description" disabled placeholder="Fill in your title here" value="${doc.description}">
                        </div>
                        <div class="form-group">
                            <label for="versionId">&nbsp;Version ID</label>
                            <input name="versionId" type="text" class="form-control editable" style="width: 500px;height: 40px" id="versionId" disabled placeholder="Fill in your title here" value="${doc.versionId}">
                        </div>
                        <div>
                            <textarea id="viewDoc" placeholder="Enter text ..." class="form-control" name="content" style="height: 600px">${doc.content}</textarea>
                        </div>
                        <br/>
                        <div> 
                            <button type="button" id="myEdit" class="btn btn-primary"  data-toggle="popover" data-content="Someone is editting" style="margin-right:200px;">Edit</button>
                            <button  id="vsave" type="submit" class="btn btn-primary" disabled name="save" style="padding-right:50px">Save</button>
                            <c:if test="${(type eq 'initial') and (is_locked eq 'false')}">
                                <button   name="initial" type="button" data-toggle="modal" data-target="#deletedialog" class="btn btn-danger delete"  aria-hidden="true">Delete</button>
                            </c:if>
                            <c:if test="${(type eq 'copy') and (is_locked eq 'false')}">
                                <button  name="copy" type="button" data-toggle="modal" data-target="#deletedialog" class="btn btn-danger delete" aria-hidden="true">Delete</button>
                            </c:if>      
                            <a id="cancel"  href="show" class="btn btn-success" style="padding-right:50px">Cancel</a>
                            <a  id="vhistory" href="viewHistory" class="btn btn-warning" style="padding-right:50px">View History</a>
                            
                        </div>
                    </form>
                </div>
                <br/>
                <br/>
                <br/>
            </div>
        </div>
        <div class="modal fade" id="deletedialog" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title text-danger" id="myModalLabel">Delete</h4>
                    </div>
                    <div class="modal-body">
                        Are you sure to delete this online Document?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary" id="confirmDelete" data-dismiss="modal" >Confirm</button>
                    </div>
                </div>
            </div>
            
        </div>



        <!--footer-->
        <div id="footer"  class=" navbar-fixed-bottom"  >
            <a class="form-control "><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>

        <script type="text/javascript">
            var can_cancel = false;
            var delete_doc_type;
            var type = '<%=request.getParameter("type")%>';
            var id = '<%=request.getParameter("doc_id")%>';
               $('#cancel').click(function(event) {
                    if(can_cancel)
                        $.post('../../script/release-lock.jsp', {type: type, id: id});
                });
                
            function leavePage(){
                    if(can_cancel)
                        $.post('../../script/release-lock.jsp', {type: type, id: id});
               
            }
            $(function() {
               
                $('#viewDoc').wysihtml5();
                //    var $content=$('#viewDoc').parent().find("iframe");
                var sign = false;
               // var temp = document.getElementsByTagName("iframe")[0].contentDocument.firstElementChild.firstElementChild.nextSibling;
                


                var temp=$(document.getElementsByTagName("iframe")[0].contentDocument.lastChild.lastChild);
                temp.focus(function(){
                    if(!can_cancel)
                          temp.attr("contentEditable", "false");       
                      else
                          temp.attr("contentEditable", "true");                            
                }); 

                $("#myEdit").click(function() {
                    //$(this).attr("disabled",true);
                    $.post('../../script/check-doc-status.jsp', {type: type, id: id}, function(data) {
                        if (data === "accepted") {
                            $('#myEdit').popover("hide");
                            $("#vsave").removeAttr("disabled");
                            $("#vtitle").removeAttr("disabled");
                            $("#description").removeAttr("disabled");
                            $('#versionId').removeAttr("disabled");
                            $('.delete').attr("disabled","true");                    
                            can_cancel=true;
                            temp.trigger("focus");
                            temp.attr("contentEditable", "true");                            
                            
                            
                        }
                        if (data === "declined") {
                            $('#myEdit').popover("toggle");
                            can_cancel=false;
                        }
                    });
                });                                                  
                $('#deletedialog').on('show.bs.modal', function (e) {
                       delete_doc_type = e.relatedTarget;
                });
                
                $('#confirmDelete').click(function(){
                    if($(delete_doc_type).attr("name")=="initial")
                        window.location = "show?id="+id+"&type1=true";
                    if($(delete_doc_type).attr("name")=="copy")    
                        window.location = "show?id="+id+"&type2=true";
                });
                //  $('#edit_status').popover('hide');
                //  $('#myEdit').click(function(){
                //      $('#edit_status').popover('show');
                //  });
                 $('#versionId').blur(function(){
                            var patt1=new RegExp("^\\d+$");

                            if(!patt1.test($("#versionId").val())){
                               $('#versionId').val("");
                            }
                 });

            });

        </script>

        <script type="text/javascript" charset="utf-8">
            $(prettyPrint);
        </script>

    </body>
</html>
