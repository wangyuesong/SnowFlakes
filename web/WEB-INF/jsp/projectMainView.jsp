<%-- 
    Document   : ProjectMainView
    Created on : May 3, 2014, 3:07:12 PM
    Author     : coodoo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Project Main View</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="/SnowFlakes/css/bootstrap.css" rel="stylesheet"/>
        <link href="/SnowFlakes/css/bootstrap-v2.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/bootstrap-wysihtml5.css"/>
        <link href="/SnowFlakes/css/MainView.css" rel="stylesheet"/>
        <link rel="stylesheet" type="text/css" href="/SnowFlakes/css/prettify.css"/>
        <link href="/SnowFlakes/css/datepicker.css" rel="stylesheet">
        <link href="/SnowFlakes/progressbar/jquery.percentageloader-0.1.css" rel="stylesheet" type="text/css"/>

        <!--HOVER STYLE-->

        <style>
            hr{
                border: 1px lightgrey solid
            }
            #Due-date, #Assign-date, #Assignee:hover{
                cursor:  pointer
            }
        </style>
    </head>
    <body style="background:url('/SnowFlakes/img/bgimg.jpg'); background-size: contain">
        <script type="text/javascript" src="/SnowFlakes/js/wysihtml5-0.3.0.min.js"></script>       

        <script type="text/javascript" src="/SnowFlakes/js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/prettify.js"></script>

        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap-wysihtml5.js"></script>

        <script type="text/javascript" src="/SnowFlakes/js/sortElements.js"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap-datepicker.js"></script>
        <link href="/SnowFlakes/fancybox/fancybox.css" rel="stylesheet" type="text/css"/>
        <script src="/SnowFlakes/fancybox/jquery.fancybox-1.3.1.pack.js" type="text/javascript"></script>
        <script src="/SnowFlakes/progressbar/jquery.percentageloader-0.1.min.js" type="text/javascript"></script>
        <script type="text/javascript">

            //Progres Bar jquery
            //

            $(function() {
                $.post("/SnowFlakes/script/getNewMCount.jsp", {type: "userId", userId:${session_user.id}}, function(data) {
                    if(data == "0"){
                        $('#NmessageN').hide();
                    }
                    else{
                        $('#NmessageN').html(data);
                        $('#NmessageN').show();
                    }
                });

                var $file_list = $('#collapse3').children('.file');
                var $minicard_list = $('#collapse2').children('.minicard');
                var minicard_count = 0;
                var file_count = 0;
                
                
                function see_more($list, count, $trigger) { 
                    
                    $list.each(function() {
                        count++;
                        if (count > 3) {
                            $(this).hide();
                        }
                        
                    });
                    
                    if(count<=3){
                        $trigger.hide();
                    }
                    
                    $trigger.click(function() {
                        $list.each(function() {
                            $(this).show();

                        });

                    });
                }
                
                see_more($minicard_list, minicard_count, $('#see-more-doc'));
                see_more($file_list, file_count, $('#see-more-file'));
                var $topLoader = $("#topLoader").percentageLoader({width: 200, height: 200, controllable: false, progress: 0, onProgressUpdate: function(val) {
                        $topLoader.setValue(Math.round(val * 20.0));
                        alert("shit");
                    }});
//  <label style=" display: none" id="totalTaskNumber">{projectInfo.totalTasks}</label>
//                                             <label style=" display: none" id="projectPercentage">{projectInfo.percentage}</label>
                var topLoaderRunning = false;
                $("#animateButton").click(function() {
                    if (topLoaderRunning) {
                        return;
                    }
                    topLoaderRunning = true;
                    $topLoader.setProgress(0);
                    $topLoader.setValue('0 Tasks');
                    var now = 0;
                    var totalTasks = ${projectInfo.totalTasks};
                    var animateFunc = function() {
                        now += 0.1;
                        $topLoader.setProgress(now / totalTasks);
                        $topLoader.setValue(parseInt(now) + "/" + totalTasks + ' Tasks');
                        if (now < ${projectInfo.finishedTasks}) {
                            setTimeout(animateFunc, 25);
                        } else {
                            topLoaderRunning = false;
                        }
                    }

                    setTimeout(animateFunc, 25);
                    $("#fadeinLogo").fadeIn(5000);
                });
            });
            //Scroll Jquery
            (function($) {

                $.fn.myScroll = function(options) {
                    //默认配置
                    var defaults = {
                        speed: 100, //滚动速度,值越大速度越慢
                        rowHeight: 24 //每行的高度
                    };
                    var opts = $.extend({}, defaults, options), intId = [];
                    function marquee(obj, step) {

                        obj.find("ul").animate({
                            marginTop: '-=1'
                        }, 0, function() {
                            var s = Math.abs(parseInt($(this).css("margin-top")));
                            if (s >= step) {
                                $(this).find("li").slice(0, 1).appendTo($(this));
                                $(this).css("margin-top", 0);
                            }
                        });
                    }

                    this.each(function(i) {
                        var sh = opts["rowHeight"], speed = opts["speed"], _this = $(this);
                        intId[i] = setInterval(function() {
                            if (_this.find("ul").height() <= _this.height()) {
                                clearInterval(intId[i]);
                            } else {
                                marquee(_this, sh);
                            }
                        }, speed);
                        _this.hover(function() {
                            clearInterval(intId[i]);
                        }, function() {
                            intId[i] = setInterval(function() {
                                if (_this.find("ul").height() <= _this.height()) {
                                    clearInterval(intId[i]);
                                } else {
                                    marquee(_this, sh);
                                }
                            }, speed);
                        });
                    });
                }

            })(jQuery);
            $(function() {

                $("div.ranklist").myScroll({
                    speed: 40,
                    rowHeight: 300
                });
            });
            //Invoke Fancy box


            $(function() {
                $("a[rel=group]").fancybox({
                    'titlePosition': 'over',
                    'cyclic': true,
                    'centerOnScroll': true,
                    'titleFormat': function(title, currentArray, currentIndex, currentOpts) {
                        return '<span id="fancybox-title-over">' + (currentIndex + 1) +
                                ' / ' + currentArray.length + (title.length ? '   ' + title : '') + '</span>';
                    }
                });
            });
            $(function() {
//                            $( "#" ).datepicker({ dateFormat: "yy-mm-dd" });
                $("#duedate").datepicker();
                $("#startdate").datepicker();
                $('.datepicker').css("z-index", "9999");
            });

//Open date picker
            $(function() {


                var $file_list = $('#collapse3').children('.file');
                var $minicard_list = $('#collapse2').children('.minicard');
                var minicard_count = 0;
                var file_count = 0;
                $('a.updateClass').click(function()
                {
                    var id = $(this).attr("id");
                    var url = "ajaxGetOneTaskInfo/" + id;
                    //首先更改模态框提交地址
                    $("#updateForm").attr("action", "updateOneTask/" + id);
                    $.ajax({
                        type: "get",
                        dataType: "",
                        url: url,
                        data: "",
                        success: function(json) {
                            var j = json.replace(/'/g, "\"")
                            j = "[" + j + "]";
                            j = eval("(" + j + ")");
                            $("#update-content").text(
                                    j[0]["description"]
                                    );
                            $("#update-begindate").val(j[0]["begindate"]);
                            $("#update-enddate").val(j[0]["finishdate"]);
                        },
                        error: function() {
                            alert("操作失败");
                        }
                    })
                });
                
                
                function see_more($list, count, $trigger) {
                    $list.each(function() {
                        count++;
                        if (count > 3) {
                            $(this).hide();
                        }
                    });
                    $trigger.click(function() {
                        $list.each(function() {
                            $(this).show();
                        });
                    });
                }

                see_more($minicard_list, minicard_count, $('#see-more-doc'));
                see_more($file_list, file_count, $('#see-more-file'));


                $('#select-duedate').change(function() {
                    var selected_value = $(this).find("option:selected").text();
                    var $list = $("#taskTable td:nth-child(1)");
                    if (selected_value === "All") {
                        $list.each(function() {
                            $(this).parent().show();
                        });
                    }
                    else {
                        $list.each(function() {
                            var date = Date.parse($(this).html());
                            var dif = (date - new Date().getTime()) / (3600 * 1000 * 24);
                            if (selected_value === "Processing")
                            {
                                if (dif >= 0)
                                    $(this).parent().show();
                                else
                                    $(this).parent().hide();
                            }
                            else if (selected_value === "History")
                            {
                                if (dif < 0)
                                {
                                    $(this).parent().show();
                                }
                                else
                                    $(this).parent().hide();
                            }
                            else if (selected_value === "Within 1 week") {
                                if (0 <= dif && dif < 7) {
                                    $(this).parent().show();
                                }
                                else {
                                    $(this).parent().hide();
                                }

                            }
                            else if (selected_value === "Within 2 weeks") {
                                if (0 <= dif && dif < 14) {
                                    $(this).parent().show();
                                }
                                else {
                                    $(this).parent().hide();
                                }
                            }
                            else if (selected_value === "Within 4 weeks") {
                                if (0 <= dif && dif < 28) {
                                    $(this).parent().show();
                                }
                                else {
                                    $(this).parent().hide();
                                }
                            }
                        });
                    }
                });
                
                $('#select-assignee').change(function() {
                    var selected_value = $(this).find("option:selected").text();
                    var $list = $("#taskTable td:nth-child(3)");
                    if (selected_value === "All people") {
                        $list.each(function() {

                            $(this).parent().show();
                        });
                    }
                    else {
                        $list.each(function() {
                            if ($(this).html() !== selected_value) {
                                $(this).parent().hide();
                            }
                            else {
                                $(this).parent().show();
                            }
                        });
                    }
                });
                $('#select-status').change(function() {
                    var selected_value = $(this).find("option:selected").text();
                    var $list = $("#taskTable td:nth-child(5)");
//                    alert(selected_value);
                    if (selected_value === "All") {
                        $list.each(function() {

                            $(this).parent().show();
                        });
                    }
                    else if (selected_value === "Pending")
                    {
                        $list.each(function() {
//                            alert($(this).text());
                            if ($(this).html() === selected_value) {

                                $(this).parent().show();
                            }
                            else {
                                $(this).parent().hide();
                            }
                        });
                    }
                    else if (selected_value === "Finished")
                    {
                        $list.each(function() {
                            if ($(this).html() === selected_value) {
                                $(this).parent().show();
                            }
                            else {
                                $(this).parent().hide();
                            }
                        });
                    }
                });
                
                function sortTable(table) {
                    $('#Due-date, #Assign-date,#Assignee')
                            .wrapInner('<span title="sort this column"/>')
                            .each(function() {

                                var th = $(this),
                                        thIndex = th.index(),
                                        inverse = false;
                                th.click(function() {

                                    table.find('td').filter(function() {

                                        return $(this).index() === thIndex;
                                    }).sortElements(function(a, b) {

                                        if ($.text([a]) === $.text([b]))
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
                ;
                sortTable($('#taskTable'));
                $('#collapse1-toggle').click(function() {

                    if ($(this).find("span").hasClass("glyphicon-chevron-up")) {
                        $(this).find("span").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
                    }
                    else {
                        $(this).find("span").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
                    }
                    return true;
                });
                $('#collapse2-toggle').click(function() {
                    if ($(this).find("span").hasClass("glyphicon-chevron-up")) {
                    $(this).find("span").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
                    }
                    else {
                    $(this).find("span").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
                    }
                    return true;
                });
                $('#collapse3-toggle').click(function() {
                    if ($(this).find("span").hasClass("glyphicon-chevron-up")) {
                        $(this).find("span").removeClass("glyphicon-chevron-up").addClass("glyphicon-chevron-down");
                        $('#collapse3').hide(200);
                    }
                    else {
                        $(this).find("span").removeClass("glyphicon-chevron-down").addClass("glyphicon-chevron-up");
                        $('#collapse3').show(200, function() {
                            window.scrollBy(0, 1000);
                        });
                    }
                    return true;
                });
                 var delete_doc_type;
                
                $('#deletedialog').on('show.bs.modal', function (e) {
                       delete_doc_type = e.relatedTarget;
                });
                
                $('#confirmDelete').click(function(){
                    var old_location;
                    if($(delete_doc_type).hasClass("close-initial")){
                        old_location=window.location.href;
                    //    if(old_location.indexOf("#") >= 0){
                     //       old_location = old_location.replace(/#/, "");
                     //   }
                        window.location = old_location+"?type1=delete-initial&id="+$(delete_doc_type).attr("name");
                    }
                    if($(delete_doc_type).hasClass("close-copy")){
                        old_location=window.location.href;
                    //    if(old_location.indexOf("#") >= 0){
                   //         old_location = old_location.replace(/#/, "");
                   //     }
                        window.location = old_location+"?type2=delete-copy&id="+$(delete_doc_type).attr("name");
                    }
                });
                });
                                
        
        </script>


        <!--/*//Progress Bar*/-->
        <style type="text/css">
            #topLoader {
                width: 256px;
                height: 256px;
                margin-bottom: 32px;
            }
            #animateButton {
                width: 256px;
            }
            #container {
                width: 940px;
                padding: 10px;
                margin-left: auto;
                margin-right: auto;
            }


            /*<!--ScrollCSS-->*/

            a,img{border:0;}
            a,a:visited{color:#5e5e5e; text-decoration:none;}
            a:hover{color:#b52725;text-decoration:underline;}
            .clear{display:block;overflow:hidden;clear:both;height:0;line-height:0;font-size:0;}
            body{font:12px/180% Arial, Helvetica, sans-serif;}
            .demo{width:600px;margin:20px auto;border:solid 1px #ddd;padding:0 10px;}
            .demo h2{font-size:14px;color:#333;height:30px;line-height:30px;padding:15px 0;color:#3366cc;}
            /* ranklist */
            .ranklist{height:200px;overflow:hidden;}
            .ranklist li{height:16px;line-height:16px;overflow:hidden;position:relative;padding:0 70px 0 30px;margin:0 0 10px 0;}
            .ranklist li em{background:url(images/mun.gif) no-repeat;width:20px;height:16px;overflow:hidden;display:block;position:absolute;left:0;top:0;text-align:center;font-style:normal;color:#333;}
            .ranklist li em{background-position:0 -16px;}
            .ranklist li.top em{background-position:0 0;color:#fff;}
            .ranklist li .num{position:absolute;right:0;top:0;color:#999;}
        </style>



        <div id="header" class="nav navbar-default" style="background: white" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="/SnowFlakes/img/logo-title.png"/>
                        </a>  
                    </div>
 
                    <ul class="nav navbar-nav">
                        <c:if test="${session_user.id == project.owner.id}">
                        <li><a href="team/show"><span class="glyphicon glyphicon-briefcase"></span> Team </a></li>
                        </c:if>
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
        <br/> <br/>
        <div class="container wrap" style="background: url('/SnowFlakes/img/bg.png');background-size: cover">

            <div class="row">
                <div class="col-md-7">
                   
                    <img src='/SnowFlakes/img/projects_folder.png' class="clear-surronding"  height="50" width="50">
                   
                    <h1 class="clear-surronding"><strong>${project.name}</strong></h1>
                    <h2 class=" clear-surronding"><small>${project.description}</small></h2>
                </div>
                <div class="col-md-5">
                    <br/>

                    <div class="clear-between-rows"></div>
                    <span class="text-warning"> <strong>Due Date:</strong></span>
                    <span class="text-info"><fmt:formatDate value="${project.dueTime}" pattern="yyyy-MM-dd"/></span>
                    &nbsp;  &nbsp;  &nbsp;  &nbsp;  &nbsp;
                    <button class="btn btn-link" data-target="#progress-report" data-toggle="modal" id="animateButton">Progress Report</button>
                    <br/>
                    <br/>
                    <br/>
                    <div class="progress">
                        <div class="progress-bar" role="progressbar" aria-valuenow="${projectInfo.percentage}" aria-valuemin="0" aria-valuemax="100" style="width: ${projectInfo.percentage}%;">
                            <span class="">${projectInfo.percentage}% Complete</span>
                        </div>
                    </div>

                </div>
                <hr/>
                <em><h5><label style=" font-weight:  900"><span style="color:red" class="glyphicon glyphicon-screenshot"></span>&nbsp;Goal:</label> &nbsp;${project.goal}</h5></em>
            </div>
            <hr/>
            <div class="row">
                <h3 class="clear-surronding"><span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;Task List 
                    <a type="button" id="collapse1-toggle" class="btn btn-link btn-sm" data-toggle="collapse" href="#collapse1">
                        <span class="glyphicon glyphicon-chevron-up"></span>
                    </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <c:if test="${session_user.id == project.owner.id}">
                        <button type="button" data-target="#create-task" data-toggle="modal" class="btn btn-primary">
                            Create Task 
                        </button>     

                    </c:if>

                </h3>
            </div>
            <hr/>

            <div class="row panel-collapse collapse in" id="collapse1">
                <div class="col-md-8 well clear-surronding" style="min-height: 200px;">
                    <table class="table table-responsive " id="taskTable" >
                        <tr  style="background: silver; font-weight: lighter">

                            <th id="Assign-date">Due Date</th>
                            <th id="Due-date">Assign Date</th>
                            <th id="Assignee">Assignee</th>
                            <th>Title</th>
                            <th>Status</th>
                            <th>Operations</th>
                        </tr>

                        <c:forEach items="${taskCollection}" var="task">

                            <tr>

                                <td><fmt:formatDate value="${task.finishTime}" pattern="yyyy-MM-dd"/></td>
                                <td><fmt:formatDate value="${task.beginTime}" pattern="yyyy-MM-dd"/></td>
                                <td>${task.userId.name}</td>
                                <td><a href="#"><label style="width:100px;overflow:scroll">${task.description}</label></a></td>
                                <td><c:if test="${task.status == 1}">Pending</c:if><c:if test="${task.status == 0}">Finished</c:if></td>
                                <c:if test="${session_user.id == project.owner.id}">
                                    <td><a href="deleteOneTask/${task.id}">Delete</a> |
                                        <a class="updateClass" id="${task.id}"  data-target="#update-task"  data-toggle="modal"  href="#">Update</a>
                                    </c:if>
                                    <c:if test="${session_user.id != project.owner.id}">
                                    <td>
                                        <!--这个用户的未完成的任务-->
                                        <c:if test="${(session_user.id == task.userId.id) && (task.status == 1)}">
                                            <a href="finishOneTask/${task.id}">Finish</a>
                                        </c:if>   
                                        <!--这个用户的完成的任务-->
                                        <c:if test="${(session_user.id == task.userId.id) && (task.status == 0)}">
                                            <a href="redoOneTask/${task.id}">Redo</a>
                                        </c:if>
                                    </td>
                                </c:if>




                            </tr>
                        </c:forEach>

                    </table>

                </div>

                <div class="col-md-2 col-md-offset-1"><h5><span class="glyphicon glyphicon-user"></span> Assignee:</h5>
                    <form role="form">
                        <select id="select-assignee" class="form-control" >
                            <option>All people</option>
                            <c:forEach items="${memberCollection}" var="member">
                                <option>${member.name}</option>
                            </c:forEach>
                        </select>

                    </form>
                    <h5><span class="glyphicon glyphicon-sound-5-1"></span>&nbsp;Due Date:</h5>
                    <select id="select-duedate" class="form-control">
                        <option>All</option>
                        <option>Processing</option>
                        <option>History</option>
                        <option>Within 1 week</option>
                        <option>Within 2 weeks</option>
                        <option>Within 4 weeks</option>
                    </select>
                    <h5><span class="glyphicon glyphicon-ok"></span>&nbsp;Status:</h5>
                    <select id="select-status" class="form-control">
                        <option>All</option>
                        <option>Pending</option>
                        <option>Finished</option>
                    </select>
                    <hr/>
                    <h5><a href="cal"><span class="glyphicon glyphicon-calendar"></span>&nbsp;Calendar View</a></h5>
                    <img src="/SnowFlakes/img/calendaricon.png" class="img-responsive"/>
                </div>

            </div>
            <div class="row">
                <div class="col-md-1">
                    <center>
                        <h4><center>Action<p></p>History<p></p>List</center></h4>

                    </center>

                </div>
                <div class="col-md-11">
                    <div class="ranklist">
                        <ul>
                            <li></li>
                            <li></li>
                            <li></li>
                            <li></li><li></li>
                            <li></li>
                            <li></li>
                            <li></li>

                            <c:forEach items="${actionCollection}"  var="action" varStatus="status" begin="0" end="19">
                                <c:if test="${status.count}<3">
                                    <li class="top">
                                    </c:if>
                                    <c:if test="${status.count}>=3">
                                    <li>
                                    </c:if>
                                    <div class="row">
                                        <div class="col-md-1">  <em>${status.count}</em></div>
                                        <div class="col-md-5">  <a href="#" target="_blank"><em>${action.userId.name}</em> : <strong>${action.message}</strong></a></div>
                                        <div class="col-md-2"><fmt:formatDate value="${action.time}" pattern="yyyy-MM-dd"/> </div>

                                    </div>
                                </li>

                            </c:forEach>
                        </ul>
                    </div>
                </div>



            </div>

            <hr/>

            <h3 class="clear-surronding"><span class="glyphicon glyphicon-pencil"></span>&nbsp;&nbsp;Online Documents
                <a  id="collapse2-toggle" class="btn btn-link btn-sm" data-toggle="collapse" href="#collapse2">
                    <span class="glyphicon glyphicon-chevron-up"></span>
                </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <a  class="btn btn-primary" href="newDocument?project_id=${project.id}">Create Document</a>

            </h3>
            <hr/>
            <div class="panel-collapse collapse in" id="collapse2">
                <c:forEach var="item" items="${onlineDocList}">
                    
                    
                    <div class="col-md-3 minicard doc" style="min-height: 222px;">
                        <br/>
                        <strong>          
                            <c:choose>
                            <c:when test="${(item.path eq 'initial') or (item.path eq '_deleteable-initial')}">
                                <a style="display:inline;font-size:18px" href="viewDocument?doc_id=${item.id}&type=initial">&nbsp;${item.title}</a>      
                            </c:when>
                            <c:otherwise>
                                <a style="display:inline;font-size:18px" href="viewDocument?doc_id=${item.id}&type=copy">&nbsp;${item.title}</a>      
                            </c:otherwise>
                            </c:choose>
                        </strong>
                        <c:if test="${item.path eq '_deleteable-initial'}">
                            <button  style="display:inline" name="${item.id}" type="button" data-toggle="modal" data-target="#deletedialog" class="close close-initial" aria-hidden="true">&times;</button>
                        </c:if>
                        <c:if test="${item.path eq '_deleteable-copy'}">
                              <button  style="display:inline" name="${item.id}" type="button" data-toggle="modal" data-target="#deletedialog" class="close close-copy" aria-hidden="true">&times;</button>
                        </c:if>      
                        <hr/>&nbsp;
                        ${item.description}
                    </div>
                 
                </c:forEach>
                <div class="row">
                    <br/>

                    <a href="#" id="see-more-doc">See More...</a>
                </div>
            </div>

            <hr/>
            <div class="row clear-surronding" >
                <h3><span class="glyphicon glyphicon-picture"></span>&nbsp;&nbsp;Shared Files
                    <a type="button" id="collapse3-toggle" class="btn btn-link btn-sm">
                        <span class="glyphicon glyphicon-chevron-up"></span>
                    </a>
                </h3>


                <!--UPLOADFILE-->
                <form   method="post" action="upload" enctype="multipart/form-data">
                    <div class="form-group">
                        <h6 style="display:inline"> 1.Select A file</h6>
                        <input  type="file"  name="file"  style=" display: inline">
                        <h6 style="display:inline"> 2.Give A Name(Description) to the file:</h6>
                        <input type="text" name="description" style="min-width: 200px;"/>

                        <input type="submit" value="Submit" onclick="alert(\"Success\")" class="btn btn-primary btn-sm"/>
                    </div>

                </form>
            </div>

            <div class="panel-collapse collapse in" id="collapse3">

                <c:forEach items="${fileCollection}" var="file">
                    <div class="col-sm-6  col-md-3 file">
                        <div class="thumbnail">
                            <a rel="group" href="/SnowFlakes/uploaded/${project.id}/${file.name}.${file.ext}" title="">
                                <img alt="/SnowFlakes/img/alt.jpg" class="img-responsive" src="/SnowFlakes/uploaded/${project.id}/${file.name}.${file.ext}" /></a>
                                           <!--<img src="/SnowFlakes/uploaded/${project.id}/${file.name}.${file.ext}">-->
                            <div class="caption">
                                ${file.description}   <a href="download/${file.name}/${file.ext}">Download</a>: ${file.downloadCounts} Times
                            </div>
                        </div>
                    </div>
                </c:forEach>
                <br/>
                <div class="row">
                    <a href="#" id="see-more-file">See More Files...</a>
                </div>

            </div>

            <hr/>
            <div class="row clear-surronding" >
                <h3><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;Group Members:
                    <!--<a type="button" id="collapse3-toggle" class="btn btn-link btn-sm">-->
                        <!--<span class="glyphicon glyphicon-chevron-up"></span>-->
                    <!--</a>-->
                </h3>

            </div>
            <hr>
            <div class="row">
                <c:forEach items="${memberCollection}" var="member">
                    <div class="col-sm-6  col-md-3">
                        <center>
                        <img src="/SnowFlakes/headPic/${member.headpic}" style=" max-width: 100px; max-height: 100px; margin-bottom: 20px" class="img-circle">
                        
                        </center>
                        <center>
                            <strong>${member.name}</strong>
                        </center>
                    </div>
                </c:forEach>
            </div>
            <hr/>
            <a class="form-control">
                <center>
                    Copyright © 2014 SnowFlakes Inc. All Rights Reserved. User Agreement. Privacy and Cookies.
                </center>
            </a>
            <!--创建task的模态-->
            <div class="modal fade" id="create-task" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <form method="post" action="addOneTask?from=main">

                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">Create Task</h4>
                            </div>

                            <div class="modal-body">
                                <div class="form-group">
                                    <label >Content</label>
                                    <textarea required class="form-control"  name="content" cols="40" rows="10"></textarea>
                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>Assginee</label> </div>
                                        <div class="col-md-6">
                                            <select class="form-control"  name="target">
                                                <c:forEach items="${memberCollection}" var="member">
                                                    <option value="${member.id}" label="${member.name}">${member.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>BeginDate</label> </div>
                                        <div class="col-md-6">
                                            <input required class="form-control" id="startdate" name="begin_time" type="text" data-date-format="yyyy-mm-dd">

                                        </div>
                                    </div>

                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>DueDate</label> </div>
                                        <div class="col-md-6">
                                            <input required class="form-control" id="duedate" name="finish_time" type="text"  data-date-format="yyyy-mm-dd">

                                        </div>
                                    </div>
                                </div>


                            </div>
                            <br/>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button type="submit"  class="btn btn-primary">Confirm</button>
                            </div>
                        </div><!-- /.modal-content -->
                    </form>

                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->


            <!--模态框2-->

            <form id="updateForm" action="">
                <div class="modal fade" id="update-task" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">Edit Task</h4> <h3 id="editHint"></h3>
                            </div>

                            <div class="modal-body">
                                <div class="form-group">
                                    <label >Content</label>
                                    <textarea required class="form-control" id="update-content"  name="content" cols="40" rows="10"></textarea>
                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>Assginee</label> </div>
                                        <div class="col-md-6">
                                            <select class="form-control"  name="target">
                                                <c:forEach items="${memberCollection}" var="member">
                                                    <option value="${member.id}" label="${member.name}">${member.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>BeginDate</label> </div>
                                        <div class="col-md-6">
                                            <input required class="form-control" id="update-begindate" name="begin_time" type="text"/>

                                        </div>
                                    </div>
                                </div>
                                <div class ="form-group">
                                    <div class="row">
                                        <div class="col-md-2"><label>DueDate</label> </div>
                                        <div class="col-md-6">
                                            <input required class="form-control" id="update-enddate" name="finish_time" type="text"/>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <br/>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                <button type="submit"  class="btn btn-primary">Confirm</button>
                            </div>
                        </div>
                    </div>     
                </div>  
            </form>

            <form id="updateForm" action="">
                <div class="modal fade" id="progress-report" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">

                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel">Progress Report of <em>${project.name}</em></h4> <h3 id="editHint"></h3>
                            </div>

                            <div class="modal-body">
                                <div class="row">
                                    <div  class="col-md-4">
                                        <div id="topLoader">      
                                        </div>
                                    </div>  
                                    <div class="col-md-6 col-md-offset-1">
                                        <h2>Total Tasks: <em>${projectInfo.totalTasks}</em></h2>
                                        <h2>Finished Tasks: <em>${projectInfo.finishedTasks}</em></h2>
                                        <h2>Progress: <em>${projectInfo.percentage} %</em></h2>
                                        <h2>GroupMember: <em>${projectInfo.memberNumbers}</em></h2>
                                        <label style=" display: none" id="totalTaskNumber">${projectInfo.totalTasks}</label>
                                        <label style=" display: none" id="projectPercentage">${projectInfo.percentage}</label>
                                    </div>
                                </div>
                                <div class="row" id="fadeinLogo" style="display:none">
                                    <hr>
                                    <center>  <h2>Powered By:  <em style=" color: blue">Snow Flakes </em></h2> </center>
                                    <hr>
                                </div>
                            </div>


                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Confirm</button>
                            </div>
                        </div>
                    </div>     
                </div>  
            </form>
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
              
                                    
        <script>
                    $('#editor').wysihtml5();</script>
        <script type="text/javascript" charset="utf-8">
                    $(prettyPrint);
                    $('#datepicker').datepicker()

        </script>
    </body>
</html>
