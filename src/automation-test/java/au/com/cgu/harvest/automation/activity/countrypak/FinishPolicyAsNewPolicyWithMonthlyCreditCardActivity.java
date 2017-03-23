package au.com.cgu.harvest.automation.activity.countrypak;

import org.openqa.selenium.WebDriver;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.CreditCardDetailsPopup;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Countrypak Policy as a New policy With Monthly Credit Card Activity" )
public class FinishPolicyAsNewPolicyWithMonthlyCreditCardActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;
    private WebDriver Driver;

    public FinishPolicyAsNewPolicyWithMonthlyCreditCardActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "Policy" );
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
        instalmentPlanPopup.assertNonZeroPremium();
        finishPage = instalmentPlanPopup.closeInstalmentPlan();
        finishPage.finish();
        return new NewBusinessPage( getWebDriver() );
    }
}
