<%-- 
    Document   : introduction
    Created on : 2014-4-28, 1:05:14
    Author     : boion
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>SnowFlakes</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="css/bootstrap.css">
    </head>
    <body style="background-image: url('/SnowFlakes/img/bgimg.jpg');background-size: contain" >
        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script type="text/javascript">
            $(function() {
                $('#verification').tooltip('hide');
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

                    <ul class="nav navbar-nav">
                        <li ><a href="index"><span class="glyphicon glyphicon-home"></span> Home</a></li>
                        <li class="active"><a ><span class="glyphicon glyphicon-eye-open"></span> Introduction</a></li>

                        <li><a href="#" ><span class="glyphicon glyphicon-question-sign"> </span> Help</a></li>
                        <li><a href="about" ><span class="glyphicon glyphicon-info-sign"> </span> About</a></li>
                        <li><a href="#" ><span class="glyphicon glyphicon-user"> </span> Mobile</a></li>
                        <li><a style=" color: white">|</a></li>
                        <li><a href="register" >Register</a></li>

                        <li><a href="index#login" >Login</a></li>
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

            <br/>        



            <div class="jumbotron" style="background-image:  url('img/bg-2.jpeg');background-size: cover ">
                <h1>Introcuction</h1><br/>
                <p class="text-primary">As everyone knows, teamwork plays an essential role in finishing a project, as 
                    a tough problem can be divided into smaller parts to be implemented by different group members. 
                    They can focus on individual part of the project and thus makes a project much easier to accomplish.
                    Also, by making a schedule to decide when and in what sequence the individual parts should be done, the
                    whole project can be in well control and finished on time. Therefore, the business goal of our project 
                    is to help people to manage their work in a team in terms of progress control and task assignment, and also
                    to bridge the gap between people who are geographically separated in the real world.
                </p>       
                <p class="text-primary"> Our system website and the corresponding mobile app are mainly designed for the area of software
                    development, especially under the condition that software projects can be divided into several separate 
                    tasks which can be finished by different people at the same time. And our target end users are those 
                    small teams with no more than 20 people because we think usually this kind of relatively small scale
                    team may not have suitable place for them all to get together all the time, and it is often the case 
                    that a development team number will not exceed the number of 20. Their projects usually are more likely 
                    to be divided into individual tasks. 
                    In addition, it can be used for some other simple projects like for a family, family members can make 
                    good use of it to prepare for a wedding.
                </p>
            </div>


            <div class="row">
                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/online-doc.jpg'/>" style=" width: 350px;height: 200px">

                        <div class="caption">
                            <h3 style="color: goldenrod">Document online</h3>
                            <p>Editable online document enables everyone to express their ideas on the document openly and efficiently.</p>                                           
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/sharedfile.jpg'/>" style=" width: 350px;height: 200px">

                        <div class="caption">
                            <h3 style="color: goldenrod">Share file </h3>
                            <p>File sharing, including Files upload, download and delete, guarantees the collaboration of team where one's work is depending on the other's.</p>                                           
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/Project-Management.jpg'/>" style=" width: 350px;height: 200px">

                        <div class="caption">
                            <h3 style="color: goldenrod">Project management </h3>
                            <p>Review project anytime, view the task schedule, arrange follow-up during any phases, and inform team member of important notifications.</p>                                           
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/personnel-management.jpg'/>" style=" width: 350px;height: 200px">

                        <div class="caption">

                            <h3 style="color: goldenrod">Personnel management </h3>
                            <p>There are members where is a project,add new members and management.</p>                                           
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/task-management.png'/>" style=" width: 350px;height: 200px">

                        <div class="caption">
                            <h3 style="color: goldenrod">Task management </h3>
                            <p>Creat new tasks and assign them to participants by message in time.</p>                                           
                        </div>
                    </div>
                </div>

                <div class="col-sm-6 col-md-4">
                    <div class="thumbnail">
                        <img src="<c:url value='img/message-system.jpg'/>" style=" width: 350px;height: 200px">

                        <div class="caption">
                            <h3 style="color: goldenrod">Message System</h3>
                            <p>Built-in message and Email Service allow members to send or receive messages upon important events.  </p>                                           
                        </div>
                    </div>
                </div>


            </div>


        </div>  

        <div id="footer">
            <a class="form-control"><center>Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.</center></a>
        </div>

    </body>
</html>
