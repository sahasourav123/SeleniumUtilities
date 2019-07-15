# Selenium Utilities 
![](https://img.shields.io/badge/build-passing-success.svg) ![](https://img.shields.io/badge/Java->=8(update%20181)-blue.svg) ![](https://img.shields.io/badge/maintenance-weekly-brightgreen.svg) ![](https://img.shields.io/badge/license-Apache%202.0-blue.svg) ![](https://img.shields.io/badge/version-1.1.4-blue.svg)

Supporting utilities to develop automation suite with Java/Selenium.
Web Automation and File Automation related basic reusable building blocks are put together for faster prototyping.
Test Automation Framework as API.

## Get Ready for Rapid Prototyping.

### Just Add this inside pom.xml
```
<repositories>
  <repository>
    <id>SeleniumUtilities-mvn-repo</id>
    <url>https://raw.github.com/sahasourav123/SeleniumUtilities/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>

<dependencies>
  <dependency>
    <groupId>automation</groupId>
    <artifactId>SeleniumUtilities</artifactId>
    <version>1.1.4</version>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>3.8.1</version>
      <configuration>
        <source>1.8</source>
        <target>1.8</target>
      </configuration>
    </plugin>
  </plugins>
</build>
```

### Example
[See Example](https://github.com/sahasourav123/SeleniumUtilities/blob/master/src/test/java/example/Examples.java) using this Utility Library.
[See Example](https://github.com/sahasourav123/SeleniumUtilities/blob/master/src/test/java/example/KnowElementExample.java) to dump all element in the page.

### Get Compatible Driver
Download compatible driver.exe.
Place it in project folder.
* [32-bit IE Driver](https://goo.gl/9Cqa4q)
* [64-bit IE Driver](https://goo.gl/AtHQuv)
* [Chrome Drivers](https://sites.google.com/a/chromium.org/chromedriver/downloads)
* [Gecko Drivers(Firefox)](https://github.com/mozilla/geckodriver/releases/)

### Create Broswser Instance
```
WebDriver driver;
String url = "https://github.com/sahasourav123/SeleniumUtilities";

// Create Chrome Driver Instance with initial URL
driver = new GetDriver().chromeDriver(url);

// Create IE Driver Instance with Initial URL
driver = new GetDriver().ieDriver(url);

// Create Firefox Driver Instance without Initial URL
driver = new GetDriver().firefoxDriver();
```

DriverSupport Object handles interaction with WebElement 
 ```
DriverSupport support = new DriverSupport(driver);
```
Rapid Data Entry Object (Wrapper on DriverSupport Class)
```
DriverAction action = new DriverAction(driver);
```
SendKeys, SelectDropdownOption & SelectRadioButton using DriverAction (all One-liner)
```
action.setText(Locator.Name, "first_name", "Sourav");
action.selectDropdownOption(Locator.Name, "state", "Texas");
action.selectRadioOption(Locator.Name, "hosting", "No");
support.jsClick(Locator.Xpath, "//button[@type='submit']");
```

Take Screenshot easily
```
support.takeScreenShot(ScreenShotType.FullPage);
```

Dump all page elements
```
new KnowElements(driver).fetchBot();
```

 Find a WebElement or perform an action quickly using Locator enum and :
 
 ![](https://img.shields.io/badge/-Name-yellowgreen.svg) ![](https://img.shields.io/badge/-NameContains-yellowgreen.svg) ![](https://img.shields.io/badge/-NameStartWith-yellowgreen.svg) ![](https://img.shields.io/badge/-NameEndWith-yellowgreen.svg)
 
![](https://img.shields.io/badge/-Xpath-yellowgreen.svg) ![](https://img.shields.io/badge/-Id-yellowgreen.svg) ![](https://img.shields.io/badge/-IdContains-yellowgreen.svg) ![](https://img.shields.io/badge/-IdStartWith-yellowgreen.svg) ![](https://img.shields.io/badge/-IdEndWith-yellowgreen.svg)

 ![](https://img.shields.io/badge/-CSSSelector-yellowgreen.svg) ![](https://img.shields.io/badge/-ClassName-yellowgreen.svg) ![](https://img.shields.io/badge/-LinkText-yellowgreen.svg) ![](https://img.shields.io/badge/-PartialLinkText-yellowgreen.svg) ![](https://img.shields.io/badge/-TagName-yellowgreen.svg)
 
 ```
By byElement = support.customLocator(Locator.IdStartWith, "Quote");
```
Check if Element present in dom
```
boolean isElement = support.isElement(byElement);
```
Click on the element if present
```
support.jsElement(byElement);
```

CustomWait object handle different types of wait on WebElement, iFrame and WindowsHandle
```
CustomWait wait = new CustomWait(driver);
```
Wait up-to specified seconds for a element and return boolean status
```
boolean status;
status = wait.explicitWait(8, byElement);
status = wait.waitForInvisibilityOfElement(5, Locator.IdContains, "Partial_id_of_element");
status = wait.waitForStalenessOfElement(5, Locator.Xapth, "XPath_of_element");
```
### How the architecture looks like
![Architecture](architecture.png)
