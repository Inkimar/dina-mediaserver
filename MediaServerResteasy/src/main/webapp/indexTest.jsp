<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <title>Demo, upload file [returns a UUID]</title>
    </head>
    <body>
        <h1>Media</h1>
        <form action="media/testing" method="post" enctype="multipart/form-data"  accept-charset="utf-8" >
            <p>
                1: <input type="text" size="24" name="taglist" />
            </p>
            <p>
                2:<input type="text" size="24" name="taglist" />
            </p>
            <p>
                3: <input type="text" size="24" name="taglist" />
            </p>

            <input type="submit" value="Upload" />
        </form>
    </body>
</html>