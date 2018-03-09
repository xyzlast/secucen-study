<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title"/></title>
    <link href="/node_modules/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
    <script src="/node_modules/jquery/dist/jquery.min.js"></script>
    <script src="/node_modules/bootstrap/dist/js/bootstrap.min.js"></script>
</head>
<body>
    <h2>freemarker list view</h2>
    <div class="example-form">
        <h3>Book list</h3>
        <table class="table table-striped table-bordered table-hover">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>status</th>
                    <th>Description</th>
                </tr>
            </thead>

            <tbody>
                <#list books as book>
                    <tr>
                    <td>${book.name}</td>
                    <#if book.status == "NORMAL">
                        <td>일반</td>
                    <#elseif book.status == "RENTNOW">
                        <td>대여중</td>
                    <#else>
                        <td>분실</td>
                    </#if>
                    <td>${book.author}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>
</body>
</html>
