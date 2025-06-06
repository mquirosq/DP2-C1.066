
package acme.features.customer.bookingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.datatypes.Money;
import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.Booking;
import acme.entities.BookingRecord;
import acme.entities.Passenger;
import acme.realms.Customer;

@GuiService
public class CustomerBookingRecordCreateService extends AbstractGuiService<Customer, BookingRecord> {

	// Internal state ---------------------------------------------------------

	private final CustomerBookingRecordRepository repository;


	@Autowired
	public CustomerBookingRecordCreateService(final CustomerBookingRecordRepository repository) {
		this.repository = repository;
	}

	// AbstractService<Customer, BookingRecord> -------------------------------------

	@Override
	public void authorise() {
		boolean authorised;
		String rawId;
		int bookingId;
		Booking booking;
		Collection<Passenger> legalPassengers = null;

		try {
			rawId = super.getRequest().getData("masterId", String.class);
			bookingId = Integer.parseInt(rawId);
			booking = this.repository.findBookingById(bookingId);
			authorised = booking != null;
			if (authorised) {
				Customer customer = booking.getCustomer();
				legalPassengers = this.repository.findMyPassengersNotAlreadyInBooking(super.getRequest().getPrincipal().getActiveRealm().getId(), bookingId);
				// Cannot execute missing branch of following statement
				authorised = booking.isDraftMode() && super.getRequest().getPrincipal().getActiveRealm().equals(customer);
			}
		} catch (NumberFormatException | AssertionError e) {
			authorised = false;
		}

		String passengerIdRaw;
		int passengerId;
		Passenger passenger;

		if (super.getRequest().hasData("passenger")) {
			passengerIdRaw = super.getRequest().getData("passenger", String.class);

			try {
				passengerId = Integer.parseInt(passengerIdRaw);
			} catch (NumberFormatException e) {
				passengerId = -1;
				authorised = false;
			}

			if (passengerId != 0) {
				passenger = this.repository.findPassengerById(passengerId);
				authorised &= passenger != null && legalPassengers != null && legalPassengers.contains(passenger);
			}
		}

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		BookingRecord bookingRecord;

		bookingRecord = new BookingRecord();

		int bookingId = super.getRequest().getData("masterId", int.class);
		Booking booking = this.repository.findBookingById(bookingId);

		bookingRecord.setBooking(booking);

		super.getBuffer().addData(bookingRecord);
		super.getResponse().addGlobal("bookingId", bookingId);
	}

	@Override
	public void bind(final BookingRecord bookingRecord) {
		super.bindObject(bookingRecord, "passenger");
	}

	@Override
	public void validate(final BookingRecord bookingRecord) {
		// Intentionally left empty: no extra validation needed for BookingRecord in this context.
	}

	@Override
	public void perform(final BookingRecord bookingRecord) {
		this.repository.save(bookingRecord);

		Booking booking = bookingRecord.getBooking();
		Money price = booking.getPrice();
		price.setAmount(price.getAmount() + booking.getFlight().getCost().getAmount());
		booking.setPrice(price);
		this.repository.save(booking);
	}

	@Override
	public void unbind(final BookingRecord bookingRecord) {
		Collection<Passenger> passengers;
		SelectChoices choices;
		Dataset dataset;

		int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		int bookingId = bookingRecord.getBooking().getId();
		passengers = this.repository.findMyPassengersNotAlreadyInBooking(customerId, bookingId);
		choices = SelectChoices.from(passengers, "identifier", bookingRecord.getPassenger());

		dataset = super.unbindObject(bookingRecord);
		dataset.put("passenger", choices.getSelected().getKey());
		dataset.put("passengers", choices);

		super.getResponse().addData(dataset);
	}
}
