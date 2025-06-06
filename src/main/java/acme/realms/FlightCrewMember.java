
package acme.realms;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import acme.client.components.basis.AbstractRole;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidFlightCrewMember;
import acme.constraints.ValidPhoneNumber;
import acme.datatypes.AvailabilityStatus;
import acme.entities.Airline;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidFlightCrewMember
@Table(indexes = {
	@Index(columnList = "employeeCode", unique = true)
})
public class FlightCrewMember extends AbstractRole {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidString(pattern = "^[A-Z]{2,3}\\d{6}$", message = "{acme.validation.realm.identifierPattern.message}")
	@Automapped
	private String				employeeCode;

	@Mandatory
	@ValidPhoneNumber
	@Automapped
	private String				phoneNumber;

	@Mandatory
	@ValidString(min = 1, max = 255)
	@Automapped
	private String				languageSkills;

	@Mandatory
	@Valid
	@Automapped
	private AvailabilityStatus	availabilityStatus;

	@Mandatory
	@ValidMoney
	@Automapped
	private Money				salary;

	@Optional
	@ValidNumber(min = 0, max = 120)
	@Automapped
	private Integer				yearsOfExperience;

	// Relationships ------------------------------------------------

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Airline				airline;

}
