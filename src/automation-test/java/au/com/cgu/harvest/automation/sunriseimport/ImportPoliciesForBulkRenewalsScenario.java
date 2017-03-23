package au.com.cgu.harvest.automation.sunriseimport;

import java.io.FileReader;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.JavascriptExecutor;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.cgu.harvest.automation.ParallelScenarioRunner;
import au.com.cgu.harvest.automation.TestConfiguration;
import au.com.cgu.harvest.automation.activity.farmmotor.FinishPolicyImportActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ImportPolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.ImportRiskDetailsActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LaunchImportPolicyActivity;
import au.com.cgu.harvest.automation.activity.sunrise.LoginToExecutiveToImportPoliciesActivity;
import au.com.cgu.harvest.automation.activity.sunrise.SaveAndAcceptActivity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.automation.scenario.Scenario;
import au.com.cgu.harvest.pages.PolicyDetailPage;
import au.com.cgu.harvest.pages.sunrise.ImportPolicyPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;
import au.com.cgu.harvest.pages.sunrise.WelcomePage;

@Ignore
@RunWith( ParallelScenarioRunner.class )
@Scenario( "Import Policies for Bulk Renewals scenario" )
public class ImportPoliciesForBulkRenewalsScenario extends AbstractScenario
{
    @Test
    public void execute()
    {
        List< SunrisePolicyImport > policiesToImport = loadPoliciesToImport();

        for ( SunrisePolicyImport policyToImport : policiesToImport )
        {
            try
            {
                WelcomePage welcomePage =
                    performActivity( new LoginToExecutiveToImportPoliciesActivity( policyToImport
                        .getIntermediary(), policyToImport.getIntermediary(),
                        "password" ) );

                ImportPolicyPage importPolicyPage =
                    performActivity( new LaunchImportPolicyActivity( welcomePage ) );

                NewBusinessPage newBusinessPage =
                    performActivity( new ImportPolicyActivity( importPolicyPage, policyToImport ) );

                PolicyDetailPage policyDetailPage =
                    performActivity( new ImportRiskDetailsActivity( newBusinessPage ) );

                newBusinessPage =
                    performActivity( new FinishPolicyImportActivity( policyDetailPage ) );

                newBusinessPage = performActivity( new SaveAndAcceptActivity( newBusinessPage ) );
            }
            catch ( Exception e )
            {
                AbstractScenario.getScenarioLogger().trace(
                    String.format( "Could not import policy %s [%s]",
                        policyToImport.getPolicyNumber(), policyToImport.getIntermediary() ) );

                ( ( JavascriptExecutor ) getWebDriver() )
                        .executeScript( "window.onbeforeunload = function(e){};" );

                getWebDriver().get(
                    TestConfiguration.getCurrentTestConfiguration().getSunriseExecutiveUrl() );
            }
        }
    }

    private List< SunrisePolicyImport > loadPoliciesToImport()
    {
        ColumnPositionMappingStrategy< SunrisePolicyImport > columnMappingStrategy =
                createColumnMappingStrategy();

        return readPoliciesToImportFromCsv( columnMappingStrategy );
    }

    private List< SunrisePolicyImport > readPoliciesToImportFromCsv(
        ColumnPositionMappingStrategy< SunrisePolicyImport > columnMappingStrategy )
    {
        try
        {
            CSVReader reader =
                new CSVReader( new FileReader(
                    "../test-automation/src/automation-test/resources/import.csv" ),
                    CSVParser.DEFAULT_SEPARATOR, CSVParser.DEFAULT_QUOTE_CHARACTER,
                    CSVParser.DEFAULT_ESCAPE_CHARACTER, 1 );

            CsvToBean< SunrisePolicyImport > csv = new CsvToBean< SunrisePolicyImport >();

            List< SunrisePolicyImport > list = csv.parse( columnMappingStrategy, reader );
            return list;
        }
        catch ( Exception e )
        {
            throw new RuntimeException( e );
        }

    }

    private ColumnPositionMappingStrategy< SunrisePolicyImport > createColumnMappingStrategy()
    {
        ColumnPositionMappingStrategy< SunrisePolicyImport > strat =
            new ColumnPositionMappingStrategy< SunrisePolicyImport >();
        strat.setType( SunrisePolicyImport.class );
        String[] columns = new String[]
        {
            "policyNumber", "intermediary"
        };
        strat.setColumnMapping( columns );
        return strat;
    }
}
