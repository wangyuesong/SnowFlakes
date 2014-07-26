<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link href='/SnowFlakes/fullCalendar/fullcalendar.css' rel='stylesheet' />
        <link href='/SnowFlakes/fullCalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
        <script src='/SnowFlakes/fullCalendar/jquery.min.js'></script>
        <script src='/SnowFlakes/fullCalendar/jquery-ui.custom.min.js'></script>
        <script src='/SnowFlakes/fullCalendar/fullcalendar.min.js'></script>
        <link href="/SnowFlakes/fancybox/fancybox.css" rel="stylesheet" type="text/css"/>
        <script src="/SnowFlakes/fancybox/jquery.fancybox-1.3.1.pack.js" type="text/javascript"></script>
        <script src="/SnowFlakes/jqueryLayer/layer.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="/SnowFlakes/js/bootstrap.js"></script>
        <link href="/SnowFlakes/css/bootstrap.css" rel="stylesheet"/>
        <link href="/SnowFlakes/css/bootstrap-v2.css" rel="stylesheet"/>
        <style>
            .event
            {
                height: 40px;
                font-size:  medium;
                font-weight:  300;
                color:   black;
                border-style:   outset;
                text-align: center;
                padding-top:10px;
                text-shadow: -1px -1px 0 #fff,1px 1px 0 #333,1px 1px 0 #444;

            }

        </style>
        <script>
            function timeStamp2String(time) {

                var datetime = new Date(time);
                var year = datetime.getFullYear();

                var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;

                var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();

                return year + "-" + month + "-" + date;

            }

            $(document).ready(function() {

                var url = "ajaxGetProjectInfo";
                var datas = [];
                var calendar = $('#calendar').fullCalendar({
                    header: {
                        left: 'prev,next today',
                        center: 'title',
                        right: 'month,agendaWeek,agendaDay'
                    },
                    selectable: true,
                    selectHelper: true,
                    select: function(start, end, allDay) {
                        $('#from').val(timeStamp2String(start));
                        $('#to').val(timeStamp2String(end));
                        $('#myModal').modal('show');

//                        $.fancybox({
////                            'type': 'ajax',
//                            'href': 'popupCalendarEvent?start='+start+'&end='+end
//                        });

//                        var title = prompt('Event Title:');
//                        if (title) {
//                            calendar.fullCalendar('renderEvent',
//                                    {
//                                        title: title,
//                                        start: start,
//                                        end: end,
//                                        allDay: allDay
//                                    },
//                            true // make the event "stick"
//                                    );
//                        }
                        calendar.fullCalendar('unselect');
                    },
                    editable: true,
                    events: {
                        url: 'ajaxGetProjectInfo',
                        type: 'GET',
                        error: function() {
                            alert('there was an error while fetching events!');
                        },
                        success: function(data)
                        {
                            datas = [];
                            //cleardatas
                            $(data.events).each(
                                    function()
                                    {
                                        function getRandomColor() {
                                            var array = new Array();
                                            array[0] = "#F2DCDC";
                                            array[1] = "#DDC5F7";
                                            array[2] = "#89E5F1";
                                            array[3] = "#89F1B4";
                                            array[4] = "#9BEB5D";
                                            array[5] = "#EBBE5D";
                                            array[6] = "#ED7234";
                                            return array[Math.floor(Math.random() * (3 + 1))];
                                        }
                                        ;
                                        var data = {};
                                        data["title"] = this.description + ":" + this.assignee;
                                        data["start"] = new Date(this.starttime);
                                        data["end"] = new Date(this.finishtime);
                                        data["color"] = getRandomColor();
                                        data["className"] = "event";
                                        datas.push(data);

                                    }

                            )
                            return datas;
                        }
                    }
                  
                });

            });

        </script>
        <style>

            body {
                margin-top: 40px;
                text-align: center;
                font-size: 14px;
                font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
            }

            #calendar {
                width: 900px;
                margin: 0 auto;
            }

        </style>
    </head>
    <body style="background:url('/SnowFlakes/img/bgimg.jpg'); background-size: contain">
          <div id="header" class="nav navbar-default navbar-fixed-top" style="background: white" role="navigation">
            <div class="row">
                <div style="margin: 0 auto; display: table; width: auto">
                    <div class="navbar-header" >
                        <a class="navbar-brand" href="index" style=" color: white">
                            <img  style="width: 50%;height:50%" src="/SnowFlakes/img/logo-title.png"/>
                        </a>  
                    </div>

                    <ul class="nav navbar-nav">
                        <li><a href="team/show"><span class="glyphicon glyphicon-briefcase"></span> Team </a></li>
                        <li><a href="/SnowFlakes/projectManagement"><span class="glyphicon glyphicon-list"></span> Project</a></li>

                        <li><a href="/SnowFlakes/personalCenter"><span class="glyphicon glyphicon-user"></span><span class="badge pull-right"></span> Personal Center&nbsp;</a></li>

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
        
        <div >
            <hr>
            <a href="show"><h4><span class="glyphicon glyphicon-arrow-left"></span> Back</h4></a>
             <hr>
        <div id='calendar' class="container wrap" style="background: url('/SnowFlakes/img/bg.png'); padding: 20px"></div>
        
        <div class="modal fade" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title">Assign A Task </h4>
                    </div>

                    <form method="post" action="addOneTask?from=cal">
                        <div class="modal-body">
                            <div class="form-group">
                                <label >Content</label>
                                <textarea required class="form-control"  name="content" cols="40" rows="10"></textarea>
                            </div>
                            <div class ="form-group">
                                <div class="row">
                                    <div class="col-md-2"><label>Assginee:</label> </div>
                                    <div class="col-md-6">
                                        <select class="form-control"  name="target">
                                            <c:forEach items="${memberCollection}" var="member">
                                                <option value="${member.id}" label="${member.name}">${member.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                            </div>
                            <div class="form-group">
                                <div class="row">
                                    <div class="col-md-2"><label>Period:</label> </div>
                                    <div class="col-md-3">
                                        <input type="text" id="from" name="begin_time"/>
                                    </div>
                                    <div class="col-md-2">
                                        <center>  <strong> TO </strong> </center>
                                    </div>
                                    <div class="col-md-3">
                                        <input type="text" id="to" name="finish_time"/>
                                    </div>
                                </div>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </form>
                   
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
</div>
    </body>
</html>
