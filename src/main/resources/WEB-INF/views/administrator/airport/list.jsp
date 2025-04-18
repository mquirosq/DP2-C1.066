<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.airport.list.label.IATACode" path="IATACode" width="15%"/>
	<acme:list-column code="administrator.airport.list.label.scope" path="scope" width="15%"/>
	<acme:list-column code="administrator.airport.list.label.name" path="name" width="70%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button code="administrator.airport.list.button.create" action="/administrator/airport/create"/>