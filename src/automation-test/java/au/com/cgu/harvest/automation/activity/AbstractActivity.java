package au.com.cgu.harvest.automation.activity;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import au.com.cgu.harvest.automation.scenario.AbstractScenario;

import com.google.common.io.Files;

public abstract class AbstractActivity< PAGE >
{
    private AbstractScenario _scenario;
    private final static String SUNRISE_EXEC_ERROR_DATA_LOCATOR = "//td[@class='ColErrData']";

    public void setScenario( AbstractScenario scenario )
    {
        _scenario = scenario;
    }

    protected abstract PAGE execute();

    public PAGE perform()
    {
        PAGE result = null;
        beforeActivity();

        try
        {
            result = execute();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
            logUnexpectedSunriseError( e );
            throw new RuntimeException( "Failed to perform activity [" + getDescription() + "]", e );
        }
        finally
        {
            afterActivity();
        }
        return result;
    }

    private void logUnexpectedSunriseError( Exception exception )
    {
        WebElement error;
        try
        {
            error =
                _scenario.getWebDriver().findElement( By.xpath( SUNRISE_EXEC_ERROR_DATA_LOCATOR ) );
        }
        catch ( Exception e )
        {

            AbstractScenario.getScenarioLogger().error( "No sunrise executive errors displayed" );
            return;
        }

        throw new RuntimeException( "Sunrise Executive Error: " + error.getText(), exception );
    }

    protected WebDriver getWebDriver()
    {
        return _scenario.getWebDriver();
    }

    private void beforeActivity()
    {
        try
        {
            writeDescription( getDescription() );
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

    }

    private String getDescription()
    {
        Activity activity = getClass().getAnnotation( Activity.class );
        if ( activity == null )
        {
            throw new RuntimeException(
                "Activities must be annotated with a description using the @Activity annotation" );
        }

        return activity.value();
    }

    private void afterActivity()
    {

    }

    private void takeScreenshot( String when )
    {
        TakesScreenshot driver = ( TakesScreenshot ) _scenario.getWebDriver();
        byte[] bytes = driver.getScreenshotAs( OutputType.BYTES );
        File file =
            new File( getClass().getSimpleName() + "-" + when + "-" + UUID.randomUUID().toString()
                + ".png" );
        try
        {
            Files.write( bytes, file );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private void writeDescription( String description )
    {
        AbstractScenario.getScenarioLogger().trace( "h4. Activity: " + description );
    }

}
