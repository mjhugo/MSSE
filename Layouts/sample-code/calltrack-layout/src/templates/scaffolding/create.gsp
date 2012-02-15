<% import grails.persistence.Event %>
<% import org.codehaus.groovy.grails.plugins.PluginManagerHolder %>
<%=packageName%>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="kickstart" />
	<g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
	<title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>

<section id="create" class="first">

	<g:hasErrors bean="\${${propertyName}}">
	<div class="alert alert-error">
		<g:renderErrors bean="\${${propertyName}}" as="list" />
	</div>
	</g:hasErrors>
	
	<g:form action="save" class="form-horizontal" <%= multiPart ? ' enctype="multipart/form-data"' : '' %>>
				<%  excludedProps = Event.allEvents.toList() << 'version' << 'id' << 'dateCreated' << 'lastUpdated'
					persistentPropNames = domainClass.persistentProperties*.name
					props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
					Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
					display = true
					boolean hasHibernate = PluginManagerHolder.pluginManager.hasGrailsPlugin('hibernate')
					props.each { p ->
						if (!Collection.class.isAssignableFrom(p.type)) {
							if (hasHibernate) {
								cp = domainClass.constrainedProperties[p.name]
								display = (cp ? cp.display : true)
							}
							if (display) { %>
							<div class="control-group \${hasErrors(bean: ${propertyName}, field: '${p.name}', 'error')}">
								<label for="${p.name}" class="control-label"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></label>
				            	<div class="controls">
									${renderEditor(p)}
									<span class="help-inline">\${hasErrors(bean: ${propertyName}, field: '${p.name}', 'error')}</span>
								</div>
							</div>
				<%  }   }   } %>
		<div class="form-actions">
			<g:submitButton name="create" class="save btn-primary" value="\${message(code: 'default.button.create.label', default: 'Create')}" />
            <button class="btn" type="reset">Cancel</button>
		</div>
	</g:form>
	
</section>
		
</body>

</html>
