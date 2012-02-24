<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <title>Counts</title>
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
    <h1>Incident Counts for the year ${year}</h1>

    <p>Total:  ${total}</p>

</div>
</body>
</html>
