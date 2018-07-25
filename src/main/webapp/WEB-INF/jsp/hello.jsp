<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link href="https://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css" rel="stylesheet" type="text/css">

    <script>
        $( function() {
            $( "#datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' });
        } );
    </script>

</head>
<body>
<%
    //String prefix ="au%3A";
   // String postfix="&x=57&y=18&time=8&startdate=1%2F4%2F1999&enddate=11%2F24%2F2016&freq=1&compidx=aaaaa%3A0&comptemptext=&comp=none&ma=3&maval=50&uf=0&lf=268435456&lf2=2&lf3=0&type=1&style=320&size=4&timeFrameToggle=false&compareToToggle=false&indicatorsToggle=false&chartStyleToggle=false&state=11";
   // String src="http://bigcharts.marketwatch.com/advchart/frames/frames.asp?show=&insttype=Stock&symb=";
   // String postfix2="&x=23&y=11&time=7&startdate=1%2F4%2F1999&enddate=11%2F24%2F2016&freq=1&compidx=aaaaa%3A0&comptemptext=&comp=none&ma=3&maval=20&uf=0&lf=268435456&lf2=2&lf3=0&type=1&style=320&size=4&timeFrameToggle=false&compareToToggle=false&indicatorsToggle=false&chartStyleToggle=false&state=11";



%>
<c:set var="prefix" value="au%3A" />
<c:set var="postfix" value="&x=57&y=18&time=8&startdate=1%2F4%2F1999&enddate=11%2F24%2F2016&freq=1&compidx=aaaaa%3A0&comptemptext=&comp=none&ma=3&maval=50&uf=0&lf=268435456&lf2=2&lf3=0&type=1&style=320&size=4&timeFrameToggle=false&compareToToggle=false&indicatorsToggle=false&chartStyleToggle=false&state=11" />
<c:set var="src" value="http://bigcharts.marketwatch.com/advchart/frames/frames.asp?show=&insttype=Stock&symb=" />
<c:set var="postfix2" value="&x=23&y=11&time=7&startdate=1%2F4%2F1999&enddate=11%2F24%2F2016&freq=1&compidx=aaaaa%3A0&comptemptext=&comp=none&ma=3&maval=20&uf=0&lf=268435456&lf2=2&lf3=0&type=1&style=320&size=4&timeFrameToggle=false&compareToToggle=false&indicatorsToggle=false&chartStyleToggle=false&state=11" />



<div class="container">
    <form action="search" method="post">

    <div class="row">
        <div class="col-md-6">

            <p>Date: <input type="text" id="datepicker" name="MyNoteDate" value="${date}"> search: <input type="text" name="search"><input type="submit" name="Yes" value="Yes"></p>

        </div>
    </div>

    <div class="row">
        <p style="color: blue">hello world </p>
    </div>




    <div class="row">
        <div class="col-md-12" style="background: gray">
            <p >
            <div class="checkbox">
                <label><input type="checkbox" name="checksearch" value="low" onChange="this.form.submit()">LOW</label>
                <label><input type="checkbox" name="checksearch" value="large" onChange="this.form.submit()">LARGEST</label>
                <label><input type="checkbox" name="checksearch" value="expand" onChange="this.form.submit()">EXPAND</label>
                <label><input type="checkbox" name="checksearch" value="marketshare" onChange="this.form.submit()">EXPAND</label>
                <label><input type="checkbox" name="checksearch" value="change management" onChange="this.form.submit()">CHANGE MANAGE</label>
                <label><input type="checkbox" name="checksearch" value="growth" onChange="this.form.submit()">GROWTH</label>
                <label><input type="checkbox" name="checksearch" value="optimistic" onChange="this.form.submit()">OPTIMISTIC</label>
                <label><input type="checkbox" name="checksearch" value="Innovation" onChange="this.form.submit()">INNOVATION</label>


        </div>

            </p>
        </div>


    </div>

        <div class="row">
            <div class="col-md-2" >
                <p >Date</p>
            </div>
            <div class="col-md-2" >
                <p >Code</p>
            </div>
            <div class="col-md-6" >
                <p >Title</p>
            </div>
            <div class="col-md-2" >
                <p >Chart</p>
            </div>

        </div>

        <hr>

        <div class="row">

            <c:forEach items = "${data}" var = "news">




            <div class="col-md-2" >
                <p ><c:out value = "${news.date}"/></p>
            </div>
            <div class="col-md-2" >
                <p ><c:out value = "${news.code}"/></p>
            </div>
            <div class="col-md-6" >
                <p >
                <a href="<c:out value = "${news.link}"/>" >
                        <c:out value = "${news.title}"/>
                </a>
               </p>
            </div>
                <div class="col-md-2" >
                    <p >
                        <a href=" ${src}${prefix}${news.code}${postfix2}"  target="_blank" >Chart</a>

                    </p>
                </div>
            </c:forEach>

        </div>

    </form>

</div>
</body>
</html>
