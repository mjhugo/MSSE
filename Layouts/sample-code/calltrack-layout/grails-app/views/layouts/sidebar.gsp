<!doctype html>
<head>
    <title><g:layoutTitle default="Grails"/></title>
    <g:layoutHead/>
    <r:layoutResources/>

    <style>
    .left {
        float:left;
        width:100px;
    }

    .right {
        float:right;
        width:400px;
    }

    </style>
</head>
<body>
<div style="background-color: gray;">
    Header - from layouts/sidebar.gsp
</div>

<div class="left"><g:pageProperty name="page.mySidebar"/></div>
<div class="right"><g:layoutBody/></div>

<div style="background-color: gray; clear:both;">
    Footer - from layouts/sidebar.gsp
</div>
<r:layoutResources/>
</body>
</html>