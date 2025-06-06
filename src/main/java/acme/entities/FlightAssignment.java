
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.datatypes.CurrentStatus;
import acme.datatypes.Duty;
import acme.realms.FlightCrewMember;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = {
	@Index(columnList = "allocated_flight_crew_member_id"), @Index(columnList = "leg_id")
})
public class FlightAssignment extends AbstractEntity {

	public static final long	serialVersionUID	= 1L;

	@Mandatory
	@Valid
	@Automapped
	private Duty				duty;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@Mandatory
	@Valid
	@Automapped
	private CurrentStatus		currentStatus;

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String				remarks;

	@Mandatory
	@Valid
	@Automapped
	private Boolean				published;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private FlightCrewMember	allocatedFlightCrewMember;

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private FlightLeg			leg;
}
