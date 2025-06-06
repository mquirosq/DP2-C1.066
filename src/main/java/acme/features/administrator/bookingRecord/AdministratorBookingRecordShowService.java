
package acme.features.administrator.bookingRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.Booking;
import acme.entities.BookingRecord;
import acme.entities.Passenger;

@GuiService
public class AdministratorBookingRecordShowService extends AbstractGuiService<Administrator, BookingRecord> {

	private final AdministratorBookingRecordRepository repository;


	@Autowired
	public AdministratorBookingRecordShowService(final AdministratorBookingRecordRepository repository) {
		this.repository = repository;
	}

	@Override
	public void authorise() {
		boolean authorised;
		int id;
		String rawId;
		Booking booking;
		BookingRecord bookingRecord;

		try {
			rawId = super.getRequest().getData("id", String.class);
			id = Integer.parseInt(rawId);
			bookingRecord = this.repository.findBookingRecordById(id);
			booking = this.repository.findBookingOfBookingRecordById(id);
			authorised = bookingRecord != null && booking != null && !booking.isDraftMode();
		} catch (NumberFormatException | AssertionError e) {
			authorised = false;
		}

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		BookingRecord bookingRecord;
		int id;

		id = super.getRequest().getData("id", int.class);
		bookingRecord = this.repository.findBookingRecordById(id);

		super.getBuffer().addData(bookingRecord);
		super.getResponse().addGlobal("bookingId", bookingRecord.getBooking().getId());
	}

	@Override
	public void unbind(final BookingRecord bookingRecord) {
		Collection<Passenger> passengers;
		SelectChoices choices;
		Dataset dataset;

		int customerId = bookingRecord.getBooking().getCustomer().getId();
		passengers = this.repository.findPassengersByCustomerId(customerId);
		choices = SelectChoices.from(passengers, "identifier", bookingRecord.getPassenger());

		dataset = super.unbindObject(bookingRecord);
		dataset.put("passenger", bookingRecord.getPassenger().getId());
		dataset.put("passengers", choices);

		super.getResponse().addData(dataset);
	}
}
