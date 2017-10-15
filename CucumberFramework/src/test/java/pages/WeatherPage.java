package pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import cucumber.api.java.en.*;
import wrappers.WeatherMethods;
public class WeatherPage extends WeatherMethods {

	public WeatherPage() {
		PageFactory.initElements(getDriver(), this);
	}

	@FindBy(id="city")
	private WebElement city;

	@When("Enter city name (.*)")
	public void enterUserName(String name){
		type(city, name);
	}

	@When("Hit Enter key")
	public void hitEnter(){
		pressEnter(city);
	}

	@FindAll({@FindBy(className="name")})
	private List<WebElement> dayElements;

	@When("Verify 5 day weather forecast displayed")
	public void verifyNumOfDays(){
		verifyCountOfElements(dayElements, 5);
	}

	@FindBy(xpath="//div[@data-test='error']")
	private WebElement error;	

	@When("Verify Error Message (.*)")
	public void verifyErrorMsg(String errorMsg){
		verifyText(error, errorMsg);
	}

	@When("Select Day (.*)")
	public void clickDay(int day){
		click(dayElements.get(day-1));
	}

	@Then("Verify 3 hourly forecast (.*)")
	public void verifyHourlyForecast(int day) {
		String hourXpath = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']";
		verifyIntervalOfElements(hourXpath, 3);
	}

	@Then("Verify hourly forecast is hidden (.*)")
	public void isHourlyForecastHidden(int day) {
		String hourXpath = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']";
		isHidden(hourXpath);
	}


	@Then("Verify Most dominant condition (.*)")
	public void isMostDominant(int day) {
		String rowIcon = "(//span[@class='name'])["+day+"]/following::*[@data-test='description-"+day+"']";
		String firstHourIcon = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']/following::*[contains(@data-test,'description')]";
		verifyTwoElementsAttribute(rowIcon, firstHourIcon);


	}

	@Then("Verify Most dominant Wind Speed (.*)")
	public void isMostDominantWindSpeed(int day) {
		String rowIcon = "(//span[@class='name'])["+day+"]/following::*[@data-test='speed-"+day+"']";
		String firstHourIcon = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']/following::*[contains(@data-test,'speed')]";
		verifyTwoElementsText(rowIcon, firstHourIcon);


	}

	@Then("Verify Aggregate rainfall (.*)")
	public void verifyAggregateRainfall(int day) {
		String rowIcon = "(//span[@class='name'])["+day+"]/following::*[@data-test='rainfall-"+day+"']";
		String hourXpath = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']";
		String hourIcon = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']/following::*[contains(@data-test,'rainfall')]";

		// Get all the rainfall values (only numbers) using regex + find the average
		int average = getAverageRainfall(hourXpath, hourIcon);
 
		// verify with the header
		verifyContents(rowIcon, average);

	}

	@Then("Verify Max and Min Temp (.*)")
	public void verifyMaxAndMinTemp(int day) {
		// Get all the temperatures from first row		
		String maxIcon = "(//span[@class='name'])["+day+"]/following::*[@data-test='maximum-"+day+"']";
		String hourXpath = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']";
		String maxTempIcon = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']/following::*[contains(@data-test,'maximum')]";

		// find the max
		int maxTemp = getMaxTemp(hourXpath, maxTempIcon);
		
		// verify with the header
		verifyContents(maxIcon, maxTemp);

		// Get all the temperatures from second row
		String minIcon = "(//span[@class='name'])["+day+"]/following::*[@data-test='minimum-"+day+"']";
		String minTempIcon = "((//span[@class='name'])["+day+"]/following::div[@class='details'])[1]//span[@class='hour']/following::*[contains(@data-test,'minimum')]";

		// find the min
		int minTemp = getMinTemp(hourXpath, minTempIcon);
		
		// verify with the header
		verifyContents(minIcon, minTemp);

	}
	
	private int getAverageRainfall(String hourXpath, String hourRainXpath) {
		List<WebElement> hours = getDriver().findElementsByXPath(hourXpath);
		List<WebElement> rains = getDriver().findElementsByXPath(hourRainXpath);
		int rainText = 0;
		for (int i = 0; i < hours.size(); i++) {
			rainText = rainText + Integer.parseInt(rains.get(i).getText().replaceAll("\\D", ""));
		}
		return rainText;
	}
	
	private int getMaxTemp(String tempXpath, String hourtempXpath) {
		List<WebElement> hours = getDriver().findElementsByXPath(tempXpath);
		List<WebElement> temps = getDriver().findElementsByXPath(hourtempXpath);
		List<Integer> tempList = new ArrayList<>();
		for (int i = 0; i < hours.size(); i++) {
			tempList.add(Integer.parseInt(temps.get(i).getText().replaceAll("\\D", "")));
		}
		Collections.sort(tempList);
		return tempList.get(tempList.size()-1);
	}
	
	private int getMinTemp(String tempXpath, String hourtempXpath) {
		List<WebElement> hours = getDriver().findElementsByXPath(tempXpath);
		List<WebElement> temps = getDriver().findElementsByXPath(hourtempXpath);
		List<Integer> tempList = new ArrayList<>();
		for (int i = 0; i < hours.size(); i++) {
			tempList.add(Integer.parseInt(temps.get(i).getText().replaceAll("\\D", "")));
		}
		Collections.sort(tempList);
		return tempList.get(0);
	}

	private void verifyTwoElementsAttribute(String xpath, String xpath1) {
		if(getAttribute(xpath,"aria-label").equals(getAttribute(xpath1,"aria-label"))) {
			reportStep("Most dominant (or current) condition matches with value "+getAttribute(xpath,"aria-label"), "PASS");
		}else {
			reportStep("Most dominant (or current) condition did not matches with value "+getAttribute(xpath1,"aria-label"), "WARN");

		}
	}

	private void verifyTwoElementsText(String xpath, String xpath1) {
		if(getTextByXpath(xpath).equals(getTextByXpath(xpath1))) {
			reportStep("Most dominant (or current) condition matches with value "+getTextByXpath(xpath), "PASS");
		}else {
			reportStep("Most dominant (or current) condition did not matches with value "+getTextByXpath(xpath1), "WARN");

		}
	}
	
	

	private boolean verifyIntervalOfElements(String xpath, int count) {
		boolean bIntervalMatch = true;
		List<WebElement> eles = getDriver().findElementsByXPath(xpath);
		for (int i = 1; i < eles.size(); i++) {
			int prevHour = Integer.parseInt(eles.get(i-1).getText()) + (count*100);			
			int presentHour = Integer.parseInt(eles.get(i).getText()) ;
			if(prevHour != presentHour) {
				bIntervalMatch = false;
				reportStep("The interval between hours "+prevHour+" and the following does not match.", "WARN");
				break;
			}
		}		
		if(bIntervalMatch)
			reportStep("The interval between all hours are within "+count+" hours.", "PASS");

		return false;
	}
	
	private boolean verifyContents(String xpath, int actualVal) {
		int expectedVal = Integer.parseInt(getTextByXpath(xpath).replaceAll("\\D", ""));
		if(expectedVal == actualVal) {
			reportStep("Expected value matches with actual value "+actualVal , "PASS");
			return true;
		}else {
			reportStep("Expected value :"+expectedVal+" does not matches with actual value "+actualVal , "PASS");
			return false;
		}
	}

}
