
package acme.features.administrator.service;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Administrator;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.Service;

@GuiService
public class AdministratorServiceShowService extends AbstractGuiService<Administrator, Service> {

	@Autowired
	private AdministratorServiceRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Service service;
		int id;

		id = super.getRequest().getData("id", int.class);
		service = this.repository.findServiceById(id);

		super.getBuffer().addData(service);
	}

	@Override
	public void unbind(final Service service) {
		Dataset dataset;

		dataset = super.unbindObject(service, "name", "pictureLink", "avgDwellTime", "promotionCode", "promotionDiscount");

		super.getResponse().addData(dataset);
	}

}
