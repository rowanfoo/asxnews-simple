<%@taglibprefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglibprefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

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
            <div class="col-md-8" >
                <p >Title</p>
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
            <div class="col-md-8" >
                <p >
                <a href="<c:out value = "${news.link}"/>" >
                        <c:out value = "${news.title}"/>
                </a>
               </p>
            </div>

            </c:forEach>

        </div>

    </form>

</div>
</body>
</html>
