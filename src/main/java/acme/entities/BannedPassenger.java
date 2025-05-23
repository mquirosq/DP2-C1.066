
package acme.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidBannedPassenger;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidBannedPassenger
public class BannedPassenger extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				fullName;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				dateOfBirth;

	@Mandatory
	@ValidString(pattern = "^[A-Z0-9]{6,9}$", message = "{acme.validation.bannedPassenger.passportNumberPattern.message}")
	@Automapped
	private String				passportNumber;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				nationality;

	@Mandatory
	@ValidString(min = 1, max = 255)
	@Automapped
	private String				reason;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				issuedAt;

	@Optional
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				liftedAt;

}
