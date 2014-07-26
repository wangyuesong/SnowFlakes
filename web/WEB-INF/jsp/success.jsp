<%-- 
    Document   : success
    Created on : Apr 23, 2014, 9:55:29 PM
    Author     : coodoo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
      <meta charset="utf-8">
  <title>jQuery UI Draggable - Default functionality</title>

          <link href="css/bootstrap.css"  type="text/css" rel="stylesheet"/>
        
        <link rel="stylesheet" type="text/css" href="css/prettify.css"></link>
        <link rel="stylesheet" type="text/css" href="css/bootstrap-wysihtml5.css"></link>
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-30181385-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
</head>
<body>
    <div>
        adsffadsjkkajsf
 <div >
     asdfkjlfdsjas
<ul id="draggable" >
    <li><a type="button">Drag me around</button></a></li>
    <li><button >Drag me around</button></li>
    <li><button >Drag me around</button></li>
</ul>
 </div>
    </div>
    <div class="container">

    <div class="hero-unit" style="margin-top:40px">
    
	<textarea class="textarea" placeholder="Enter text ..." style="width: 810px; height: 200px"></textarea>
    	</div>
    </div>        
    
    
    <script type="text/javascript" src="js/wysihtml5-0.3.0.js"></script>       
        <script type="text/javascript" src="js/jquery-1.11.0.js"></script>
        <script type="text/javascript" src="js/bootstrap.js"></script>

            <script type="text/javascript" src="js/bootstrap-wysihtml5.js"></script>
            <script>
	$('.textarea').wysihtml5({
            "font-styles": true, //Font styling, e.g. h1, h2, etc. Default true
	"emphasis": true, //Italics, bold, etc. Default true
	"lists": true, //(Un)ordered lists, e.g. Bullets, Numbers. Default true
	"html": true, //Button which allows you to edit the generated HTML. Default false
	"link": true, //Button to insert a link. Default true
	"image": true, //Button to insert an image. Default true,
	"color": true //Button to change color of font  
        });
</script>


    </body>
    
</html>
