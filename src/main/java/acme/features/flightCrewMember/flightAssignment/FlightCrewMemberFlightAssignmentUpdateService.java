
package acme.features.flightCrewMember.flightAssignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.datatypes.CurrentStatus;
import acme.datatypes.Duty;
import acme.entities.FlightAssignment;
import acme.entities.FlightLeg;
import acme.realms.FlightCrewMember;

@GuiService
public class FlightCrewMemberFlightAssignmentUpdateService extends AbstractGuiService<FlightCrewMember, FlightAssignment> {

	@Autowired
	FlightCrewMemberFlightAssignmentRepository repository;


	@Override
	public void authorise() {
		boolean authorised = true;

		int flightLegId;
		String requestFlightLegId;
		FlightLeg leg;

		int flightAssignmentId;
		String requestFlightAssignmentId;
		FlightAssignment flightAssignment;

		if (super.getRequest().hasData("leg")) {
			requestFlightLegId = super.getRequest().getData("leg", String.class);
			try {
				flightLegId = Integer.parseInt(requestFlightLegId);
			} catch (NumberFormatException e) {
				flightLegId = -1;
			}
			if (flightLegId != 0) {
				leg = this.repository.findByLegId(flightLegId);
				authorised = leg != null && !leg.getDraftMode();
			}
		}

		if (super.getRequest().hasData("id")) {
			requestFlightAssignmentId = super.getRequest().getData("id", String.class);
			try {
				flightAssignmentId = Integer.parseInt(requestFlightAssignmentId);
				flightAssignment = this.repository.findFlightAssignmentById(flightAssignmentId);
				authorised &= flightAssignment != null && !flightAssignment.getPublished() && super.getRequest().getPrincipal().hasRealm(flightAssignment.getAllocatedFlightCrewMember());
			} catch (NumberFormatException e) {
				authorised = false;
			}
		} else
			authorised = false;
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		FlightAssignment flightAssignment = this.repository.findFlightAssignmentById(id);
		super.getBuffer().addData(flightAssignment);
	}

	@Override
	public void bind(final FlightAssignment flightAssignment) {
		int flightLegId;
		FlightLeg flightLeg;

		int flightCrewMemberId;
		FlightCrewMember flightCrewMember;

		flightLegId = super.getRequest().getData("leg", int.class);
		flightLeg = this.repository.findByLegId(flightLegId);

		flightCrewMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		flightCrewMember = this.repository.findByFlightCrewMemberId(flightCrewMemberId);

		super.bindObject(flightAssignment, "duty", "currentStatus", "remarks");
		flightAssignment.setLeg(flightLeg);
		flightAssignment.setAllocatedFlightCrewMember(flightCrewMember);
	}

	@Override
	public void validate(final FlightAssignment flightAssignment) {
		;
	}

	@Override
	public void perform(final FlightAssignment flightAssignment) {
		flightAssignment.setMoment(MomentHelper.getCurrentMoment());
		this.repository.save(flightAssignment);
	}

	@Override
	public void unbind(final FlightAssignment flightAssignment) {
		Dataset dataset;
		SelectChoices legChoices, statusChoices, dutyChoices;

		Collection<FlightLeg> flightLegs = this.repository.findAllPublishedLegs();

		legChoices = SelectChoices.from(flightLegs, "identifier", flightAssignment.getLeg());
		statusChoices = SelectChoices.from(CurrentStatus.class, flightAssignment.getCurrentStatus());
		dutyChoices = SelectChoices.from(Duty.class, flightAssignment.getDuty());

		dataset = super.unbindObject(flightAssignment, "moment", "remarks", "published");
		dataset.put("legs", legChoices);
		dataset.put("leg", legChoices.getSelected().getKey());
		dataset.put("duties", dutyChoices);
		dataset.put("duty", dutyChoices.getSelected().getKey());
		dataset.put("statusChoices", statusChoices);
		dataset.put("currentStatus", statusChoices.getSelected().getKey());

		super.getResponse().addData(dataset);
	}

}
