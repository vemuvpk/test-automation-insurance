package au.com.cgu.harvest.automation;

import org.junit.rules.TestRule;
import org.openqa.selenium.WebDriver;

public interface WebDriverManager extends TestRule
{
    WebDriver getDriver();

    String getJobUrl();

    String getJobId();
}
