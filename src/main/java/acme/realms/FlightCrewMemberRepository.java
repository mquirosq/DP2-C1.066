
package acme.realms;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface FlightCrewMemberRepository extends AbstractRepository {

	@Query("select f from FlightCrewMember f where f.employeeCode = :employeeCode")
	FlightCrewMember findByEmployeeCode(String employeeCode);
}
