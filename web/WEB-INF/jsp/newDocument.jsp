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
        <title>New Document</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="/SnowFlakes/css/bootstrap.css">
        <link href="/SnowFlakes/css/datepicker.css" rel="stylesheet">
        <link href="/SnowFlakes/css/bootstrap.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/bootstrap-wysihtml5.css"/>

        <link href="/SnowFlakes/css/bootstrap-v2.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/prettify.css"/>
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
                        <li><a href="/SnowFlakes/personalCenter"><span class="glyphicon glyphicon-user"></span><span class="badge pull-right"></span> Personal Center&nbsp;</a></li>

                        <li><a href="/SnowFlakes/index" ><span class="glyphicon glyphicon-off"> </span> Exit</a></li>

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

            <div class="panel panel-default" style="background-image: url('/SnowFlakes/img/bg.jpg')">
                <div class="panel-body">
                    <ol class="breadcrumb" style="background: transparent">
                        <li><a href="index">Home</a></li>
                        <li><a href="projectMainView">Project</a></li>
                        <li class="active">NewDocument</li>

                    </ol>
                    <form method="post">
                    <div class="form-group">
                        <label for="title">&nbsp;&nbsp;Title</label>
                        <input type="text" class="form-control editable" name="title" value="${newdocu.title}" style="width: 50%;" id="title" placeholder="Fill in your title here">
                    </div>
                    <div class="form-group">
                        <label for="description">&nbsp;&nbsp;Description</label>
                        <input type="text" class="form-control editable"  name="description" value="${newdocu.description}" id="description" placeholder="Fill in your description here">
                    </div>
                    <div class="form-group">
                        <label for="version">&nbsp;&nbsp;Version Id</label>
                        <input type="text" class="form-control editable"  name="versionId" value="${newdocu.versionId}" id="version" placeholder="Fill in your version id here">
                    </div>
                    <div>
                        <textarea id="newDoc" placeholder="Enter text ..." name="content" class="form-control"  style="height: 600px" >${newdocu.content}</textarea>
                    </div>
                    <br/>
                    <div>
                        <button type="submit" id="vsave" name="save" class="btn btn-primary" >Save</button>

                        <a  href="projectMainView" class="btn btn-danger" onclick="location.href = 'projectMainView'">Cancel</a>

                    </div>
                    </form>






                </div>
            </div>
        </div>




        <!--footer-->
        <div id="footer"  class=" navbar-fixed-bottom"  >
            <a class="form-control "><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>


        <script>
            $('#newDoc').wysihtml5();
            
            $(function(){
                $('#version').blur(function(){
                            var patt1=new RegExp("^\\d+$");

                            if(!patt1.test($("#version").val())){
                               $('#version').val("");
                            }
                        });
            });
                
        </script>
        <script type="text/javascript" charset="utf-8">
            $(prettyPrint);
        </script>
    </body>
</html>
