
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.Airline;
import acme.entities.AirlineRepository;
import acme.helpers.ValidatorHelper;

@Validator
public class AirlineValidator extends AbstractValidator<ValidAirline, Airline> {

	private final AirlineRepository repository;


	@Autowired
	public AirlineValidator(final AirlineRepository repository) {
		this.repository = repository;
	}

	@Override
	protected void initialise(final ValidAirline annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Airline airline, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (airline == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else if (airline.getIATACode() != null) {
			Airline existingAirline = this.repository.getByIATACode(airline.getIATACode());
			boolean uniqueIATACode = ValidatorHelper.checkUniqueness(airline, existingAirline);
			super.state(context, uniqueIATACode, "IATACode", "acme.validation.airline.IATACode.message");
		}

		result = !super.hasErrors(context);

		return result;
	}

}
