<%@ page import="advancedorm.Customer" %>
<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'customer.label', default: 'Customer')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-customer" class="skip" tabindex="-1">
    <g:message code="default.link.skip.label" default="Skip to content&hellip;"/>
</a>

<div class="nav" role="navigation">
    <ul>
        <li>
            <a class="home" href="${createLink(uri: '/')}">
                <g:message code="default.home.label"/>
            </a>
        </li>
        <li>
            <g:link class="list" action="list">
                <g:message code="default.list.label" args="[entityName]"/>
            </g:link>
        </li>
        <li>
            <g:link class="create" action="create">
                <g:message code="default.new.label"
                           args="[entityName]"/>
            </g:link>
        </li>
    </ul>
</div>

<div id="show-customer" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list customer">

        <g:if test="${customerInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="customer.name.label"
                                                                        default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${customerInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

    %{--
    ******************************************************************
    ******************************************************************
    ******************************************************************
    CODE EXAMPLE STARTS HERE

    look at the classes being added to the span tag in the link to service level

    ******************************************************************
    ******************************************************************
    ******************************************************************
    --}%


        <g:if test="${customerInstance?.serviceLevel}">
            <li class="fieldcontain">
                <span id="serviceLevel-label" class="property-label">
                    <g:message code="customer.serviceLevel.label"
                               default="Service Level"/>
                </span>

                <span class="property-value" aria-labelledby="serviceLevel-label">
                    <g:link controller="serviceLevel"
                            action="show"
                            id="${customerInstance?.serviceLevel?.id}">

                    %{--The hard way--}%
                        <span class="servicelevel ${customerInstance?.serviceLevel?.name?.toLowerCase()?.replaceAll(" ", "")}">${customerInstance?.serviceLevel?.encodeAsHTML()}</span>

                    %{--the easy way--}%
                    %{--<sl:renderWithColor serviceLevel="${customerInstance.serviceLevel}"/>--}%
                    </g:link>
                </span>

            </li>
        </g:if>


    %{--
    ******************************************************************
    ******************************************************************
    ******************************************************************
    CODE EXAMPLE ENDS HERE
    ******************************************************************
    ******************************************************************
    ******************************************************************
    --}%




        <g:if test="${customerInstance?.accountNumber}">
            <li class="fieldcontain">
                <span id="accountNumber-label" class="property-label"><g:message code="customer.accountNumber.label"
                                                                                 default="Account Number"/></span>

                <span class="property-value" aria-labelledby="accountNumber-label"><g:fieldValue
                        bean="${customerInstance}" field="accountNumber"/></span>

            </li>
        </g:if>

        <g:if test="${customerInstance?.phone}">
            <li class="fieldcontain">
                <span id="phone-label" class="property-label"><g:message code="customer.phone.label"
                                                                         default="Phone"/></span>

                <span class="property-value" aria-labelledby="phone-label"><g:fieldValue bean="${customerInstance}"
                                                                                         field="phone"/></span>

            </li>
        </g:if>

        <g:if test="${customerInstance?.active}">
            <li class="fieldcontain">
                <span id="active-label" class="property-label">
                    <g:message code="customer.active.label"
                               default="Active"/></span>

                <span class="property-value" aria-labelledby="active-label">
                    <g:formatBoolean
                            boolean="${customerInstance?.active}"/></span>

            </li>
        </g:if>

        <g:if test="${customerInstance?.address}">
            <li class="fieldcontain">
                <span id="address-label" class="property-label"><g:message code="customer.address.label"
                                                                           default="Address"/></span>

                <span class="property-value" aria-labelledby="address-label"><g:link controller="address" action="show"
                                                                                     id="${customerInstance?.address?.id}">${customerInstance?.address?.encodeAsHTML()}</g:link></span>

            </li>
        </g:if>

        <g:if test="${customerInstance?.dateCreated}">
            <li class="fieldcontain">
                <span id="dateCreated-label" class="property-label"><g:message code="customer.dateCreated.label"
                                                                               default="Date Created"/></span>

                <span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate
                        date="${customerInstance?.dateCreated}"/></span>

            </li>
        </g:if>

        <g:if test="${customerInstance?.incidents}">
            <li class="fieldcontain">
                <span id="incidents-label" class="property-label"><g:message code="customer.incidents.label"
                                                                             default="Incidents"/></span>

                <g:each in="${customerInstance.incidents}" var="i">
                    <span class="property-value" aria-labelledby="incidents-label"><g:link controller="incident"
                                                                                           action="show"
                                                                                           id="${i.id}">${i?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

        <g:if test="${customerInstance?.products}">
            <li class="fieldcontain">
                <span id="products-label" class="property-label"><g:message code="customer.products.label"
                                                                            default="Products"/></span>

                <g:each in="${customerInstance.products}" var="p">
                    <span class="property-value" aria-labelledby="products-label"><g:link controller="product"
                                                                                          action="show"
                                                                                          id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
                </g:each>

            </li>
        </g:if>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${customerInstance?.id}"/>
            <g:link class="edit" action="edit" id="${customerInstance?.id}"><g:message code="default.button.edit.label"
                                                                                       default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
