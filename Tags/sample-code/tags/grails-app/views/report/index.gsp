<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Reports</title>
</head>

<body>

<div class="nav" role="navigation">
    <ul>
        <li>
            <a class="home" href="${createLink(uri: '/')}">
                <g:message code="default.home.label"/>
            </a>
        </li>
    </ul>
</div>

<div class="content" role="main">
    <h1>Reports</h1>

    <p><g:link controller="report" action="counts">Counts</g:link></p>

</div>
</body>
</html>
