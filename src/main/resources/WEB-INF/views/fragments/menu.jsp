<%--
- menu.jsp
-
- Copyright (C) 2012-2025 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:menu-bar>
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.maria-favourite-link" action="http://www.google.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.guillermo-favourite-link" action="https://www.github.com"/>
			<acme:menu-suboption code="master.menu.anonymous.alejandro-favourite-link" action="https://magic.wizards.com/en"/>
			<acme:menu-suboption code="master.menu.anonymous.daniel-favourite-link" action="https://www.taylorswift.com"/>
			<acme:menu-suboption code="master.menu.anonymous.ignacio-favourite-link" action="https://www.youtube.com"/>
			<acme:menu-suboption code="master.menu.anonymous.list-reviews" action="/any/review/list" />
			<acme:menu-suboption code="master.menu.anonymous.list-services" action="/any/service/list" />
			<acme:menu-suboption code="master.menu.anonymous.list-airports" action="/any/airport/list" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.list-reviews" action="/any/review/list" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRealm('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.list-user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-airports" action="/administrator/airport/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-airlines" action="/administrator/airline/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-aircrafts" action="/administrator/aircraft/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-bookings" action="/administrator/booking/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.list-services" action="/administrator/service/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.recommendation" action="/administrator/recommendation/perform" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.show-systemConfiguration" action="/administrator/system-configuration/show" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.show-dashboard" action="/administrator/administrator-dashboard/show" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-initial" action="/administrator/system/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-db-sample" action="/administrator/system/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-system-down" action="/administrator/system/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code = "master.menu.assistance-agent" access = "hasRealm('AssistanceAgent')">
			<acme:menu-suboption code = "master.menu.assistance-agent.list-claims" action = "/assistance-agent/claim/list"/>
		</acme:menu-option>
		
		<acme:menu-option code = "master.menu.technician" access = "hasRealm('Technician')">
			<acme:menu-suboption code = "master.menu.technician.list-maintenance-records" action = "/technician/maintenance-record/list"/>
			<acme:menu-suboption code = "master.menu.technician.list-tasks" action = "/technician/task/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.customer" access="hasRealm('Customer')">
			<acme:menu-suboption code="master.menu.customer.list-bookings" action="/customer/booking/list"/>
			<acme:menu-suboption code="master.menu.customer.list-passengers" action="/customer/passenger/list?all=${false}"/>
			<acme:menu-suboption code="master.menu.customer.list-recommendations" action="/customer/recommendation/list"/>
			<acme:menu-suboption code="master.menu.customer.show-dashboard" action="/customer/customer-dashboard/show" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.airline-manager" access="hasRealm('AirlineManager')">
			<acme:menu-suboption code="master.menu.airline-manager.list-flights" action="/airline-manager/flight/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.flightCrewMember" access="hasRealm('FlightCrewMember')">
			<acme:menu-suboption code="master.menu.flightCrewMember.list-completed" action="/flight-crew-member/flight-assignment/list-completed"/>
			<acme:menu-suboption code="master.menu.flightCrewMember.list-planned" action="/flight-crew-member/flight-assignment/list-planned"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-profile" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-customer" action="/authenticated/customer/create" access="!hasRealm('Customer')"/>
			<acme:menu-suboption code="master.menu.user-account.customer-profile" action="/authenticated/customer/update" access="hasRealm('Customer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-technician" action="/authenticated/technician/create" access="!hasRealm('Technician')"/>
			<acme:menu-suboption code="master.menu.user-account.technician-profile" action="/authenticated/technician/update" access="hasRealm('Technician')"/>
		</acme:menu-option>
	</acme:menu-right>
</acme:menu-bar>

