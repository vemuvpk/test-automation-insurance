package au.com.cgu.harvest.automation.activity.farmmotor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.Element;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.farmmotor.CertificateOfCurrencyGrid;
import au.com.cgu.harvest.pages.farmmotor.CertificateOfCurrencyGridRow;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a New policy" )
public class AssertCertificateOfCurrencyOnFinishPageActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public AssertCertificateOfCurrencyOnFinishPageActivity( HarvestPage page )
    {
        _page = page;

    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
        finishPage.setCertificateOfCurrency( "Yes" );
        finishPage.setPremiumPaymentMethod( "Intermediary Statement" );

        Wait< WebDriver > wait = new WebDriverWait( getWebDriver(), Element.MAX_WAIT_SECS );
        wait.until( Element.tableHasRows( By.xpath( CertificateOfCurrencyGrid.TABLE_LOCATOR
            + "/tbody/tr" ) ) );

        finishPage.getCertificateofCurrency().getNumberOfRows();

        CertificateOfCurrencyGridRow row1 =
            finishPage.getCertificateofCurrency().getCertificateAtRow( 1 );
        row1.setVehicleForCertificateOfCurrency();
        row1.hasValues( "AAA-999 HOLDEN BARINA", "Hire Purchase",
            "Vemu Finance", "59-61 Good Street SYDNEY NSW 2000" );

        CertificateOfCurrencyGridRow row2 =
            finishPage.getCertificateofCurrency().getCertificateAtRow( 2 );
        row2.hasValues( "AAA-998 Caterpillar XSD", "Other: Money Lender",
            "Vemu Finance", "59-61 Good Street SYDNEY NSW 2000" );

        finishPage.setPolicyDeclaration( "Yes" );
        return finishPage.finish();
    }
}
