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
    <h1>Counts</h1>

    <g:each in="${counts.keySet()}" var="key">
        <p>${key} - ${counts[key]}</p>
    </g:each>

</div>
</body>
</html>
