# Selenium Utilities
Supporting utilities to develop automation suite with Java/Selenium.
Web Automation and File Automation related basic reusable building blocks are put together for faster prototyping.

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
    <version>1.0.1</version>
  </dependency>
</dependencies>
```

### Create Broswser Instance
```
WebDriver driver;
driver = new GetDriver().chromeDriver();
driver = new GetDriver().ieDriver("https://github.com/sahasourav123/SeleniumUtilities");
driver = new GetDriver().firefoxDriver();
```
