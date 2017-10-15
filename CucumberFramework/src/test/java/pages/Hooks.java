package pages;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.runtime.FeatureBuilder;
import wrappers.WeatherMethods;

public class Hooks extends WeatherMethods{
	
	@Before
	public void launchBrowser(Scenario sc) throws InterruptedException {
		invokeApp("chrome");		
		startTestCase(sc.getName(), sc.getId());
	}
	
	@After
	public void executeAfterScenario() {
		quitBrowser();
	}




}
