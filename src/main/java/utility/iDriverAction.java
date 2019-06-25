package utility;

import pojo.Keywords;

public interface iDriverAction {
	
	public boolean selectRadioOption(Keywords locatorType, String locatorValue, String optionValue);
	
	public boolean selectDropdownOption(Keywords locatorType, String locatorValue, String optionValue);
	
	public boolean setText(Keywords locatorType, String locatorValue, String text);

}
