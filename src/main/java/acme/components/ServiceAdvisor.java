
package acme.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import acme.entities.Service;
import acme.entities.ServiceRepository;

@ControllerAdvice
public class ServiceAdvisor {

	// Internal state --------------------------------------------------------

	private final ServiceRepository repository;


	@Autowired
	public ServiceAdvisor(final ServiceRepository repository) {
		this.repository = repository;
	}

	// Beans -----------------------------------------------------------------

	@ModelAttribute("service")
	public Service getService() {
		Service result;

		try {
			result = this.repository.findRandomService();
		} catch (final Throwable oops) {
			result = null;
		}

		return result;
	}

}
