package pojo;

import utility.DriverSupport.Locator;

public interface iDriverAction {
	
	public boolean selectRadioOption(Locator locatorType, String locatorValue, String optionValue);
	
	public boolean selectDropdownOption(Locator locatorType, String locatorValue, String optionValue);
	
	public boolean setText(Locator locatorType, String locatorValue, String text);

}
