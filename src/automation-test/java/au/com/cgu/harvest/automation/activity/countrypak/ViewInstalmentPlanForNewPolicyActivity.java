package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CreditCardDetailsPopup;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "View Policy Totals in Instalment plan Activity" )
public class ViewInstalmentPlanForNewPolicyActivity extends
    AbstractActivity< InstalmentPlanPopup >
{
    private HarvestPage _page;
    private WebDriver Driver;

    public ViewInstalmentPlanForNewPolicyActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected InstalmentPlanPopup execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPolicyDeclaration( "Yes" );
        finishPage.setPremiumPaymentMethod( "Monthly Credit Card" );
        finishPage.setIntermediaryFee( "150" );

        CreditCardDetailsPopup creditCardPopup = finishPage.addCreditCardDetails();
        creditCardPopup.setCreditCardNumber( "4242424242424242" );
        creditCardPopup.setCreditCardName( "Credit Card Name" );
        creditCardPopup.setExpiryDate( "6", "2015" );
        creditCardPopup.clickCaptureCreditCardDetails();
        finishPage.setDirectDebitAuthorisation( "Written/Electronic Request" );

        InstalmentPlanPopup instalmentPlanPopup =
            finishPage.viewInstalmentPlan();
        instalmentPlanPopup.getIntermediaryFees();

        return instalmentPlanPopup;
    }
}
