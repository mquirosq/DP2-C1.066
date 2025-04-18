
package acme.entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface BookingRepository extends AbstractRepository {

	@Query("select b from Booking b where b.locatorCode = :locatorCode")
	Booking getSameLocatorCode(String locatorCode);

	@Query("select count(br) from BookingRecord br where br.booking.id = :bookingId")
	Long getNumberOfPassengers(int bookingId);
}
