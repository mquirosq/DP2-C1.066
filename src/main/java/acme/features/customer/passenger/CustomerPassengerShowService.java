
package acme.features.customer.passenger;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.Passenger;
import acme.realms.Customer;

@GuiService
public class CustomerPassengerShowService extends AbstractGuiService<Customer, Passenger> {

	// Internal state ---------------------------------------------------------

	private final CustomerPassengerRepository repository;


	@Autowired
	public CustomerPassengerShowService(final CustomerPassengerRepository repository) {
		this.repository = repository;
	}

	// AbstractGuiService interface -------------------------------------------

	@Override
	public void authorise() {
		Boolean authorised;
		String rawId;
		int passengerId;
		Passenger passenger;

		try {
			rawId = super.getRequest().getData("id", String.class);
			passengerId = Integer.parseInt(rawId);
			passenger = this.repository.findPassengerById(passengerId);

			authorised = passenger != null && passenger.getCustomer().equals(super.getRequest().getPrincipal().getActiveRealm());
		} catch (NumberFormatException | AssertionError e) {
			authorised = false;
		}

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Passenger passenger;
		int id;

		id = super.getRequest().getData("id", int.class);
		passenger = this.repository.findPassengerById(id);

		super.getBuffer().addData(passenger);
	}

	@Override
	public void unbind(final Passenger passenger) {
		Dataset dataset;

		dataset = super.unbindObject(passenger, "fullName", "email", "passportNumber", "birthDate", "specialNeeds", "draftMode");

		super.getResponse().addGlobal("readonly", true);
		super.getResponse().addData(dataset);
	}

}
