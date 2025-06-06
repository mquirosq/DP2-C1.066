
package acme.features.flightCrewMember.activityLog;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.ActivityLog;
import acme.entities.FlightAssignment;
import acme.features.flightCrewMember.flightAssignment.FlightCrewMemberFlightAssignmentRepository;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberActivityLogCreateService extends AbstractGuiService<FlightCrewMember, ActivityLog> {

	@Autowired
	FlightCrewMemberActivityLogRepository				repository;

	@Autowired
	private FlightCrewMemberFlightAssignmentRepository	flightAssignmentRepository;


	@Override
	public void authorise() {
		boolean authorised = true;

		int flightAssignmentId;
		String requestFlightAssignmentId;
		FlightAssignment flightAssignment;

		if (super.getRequest().hasData("masterId")) {
			requestFlightAssignmentId = super.getRequest().getData("masterId", String.class);
			try {
				flightAssignmentId = Integer.parseInt(requestFlightAssignmentId);
				flightAssignment = this.flightAssignmentRepository.findFlightAssignmentById(flightAssignmentId);
				authorised = flightAssignment != null && flightAssignment.getAllocatedFlightCrewMember() != null && super.getRequest().getPrincipal().hasRealm(flightAssignment.getAllocatedFlightCrewMember());
			} catch (NumberFormatException e) {
				authorised = false;
			}
		} else
			authorised = false;
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		ActivityLog activityLog = new ActivityLog();
		super.getBuffer().addData(activityLog);
	}

	@Override
	public void bind(final ActivityLog activityLog) {
		int requestFlightAssignmentId = super.getRequest().getData("masterId", int.class);
		FlightAssignment flightAssignment = this.flightAssignmentRepository.findFlightAssignmentById(requestFlightAssignmentId);

		super.bindObject(activityLog, "typeOfIncident", "description", "severityLevel");
		activityLog.setAssignment(flightAssignment);
		activityLog.setRegistrationMoment(MomentHelper.getCurrentMoment());
		activityLog.setPublished(false);
	}

	@Override
	public void validate(final ActivityLog activityLog) {
		;
	}

	@Override
	public void perform(final ActivityLog activityLog) {
		this.repository.save(activityLog);
	}

	@Override
	public void unbind(final ActivityLog activityLog) {
		Dataset dataset;
		int masterId = super.getRequest().getData("masterId", int.class);
		dataset = super.unbindObject(activityLog, "registrationMoment", "typeOfIncident", "description", "severityLevel", "published");
		dataset.put("readonly", false);
		super.getResponse().addData(dataset);
		super.getResponse().addGlobal("masterId", masterId);
	}
}
