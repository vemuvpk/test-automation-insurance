package au.com.cgu.harvest.automation.scenario;

import org.junit.extensions.cpsuite.ClasspathSuite.BaseTypeFilter;
import org.junit.extensions.cpsuite.ClasspathSuite.ClassnameFilters;
import org.junit.runner.RunWith;

import au.com.cgu.harvest.automation.ConcurrentClasspathSuite;

@RunWith( ConcurrentClasspathSuite.class )
@ClassnameFilters(
{
    ".*farmmotor.*Scenario", "!.*pat.*Scenario", "!.*sunriseimport.*Scenario"
} )
@BaseTypeFilter( AbstractScenario.class )
public class FarmMotorSuite
{

}
