
package acme.features.administrator.recommendation;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Administrator;
import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.Recommendation;

@GuiController
public class AdministratorRecommendationController extends AbstractGuiController<Administrator, Recommendation> {

	// Internal state ---------------------------------------------------------

	private final AdministratorRecommendationPerformService recommendationService;


	@Autowired
	public AdministratorRecommendationController(final AdministratorRecommendationPerformService recommendationService) {
		this.recommendationService = recommendationService;
	}

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("perform", this.recommendationService);
	}

}
