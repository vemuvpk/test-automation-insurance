package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.InstalmentPlanPopup;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Finish Policy as a Endorsement policy With Monthly Direct Debit Activity" )
public class FinishPolicyAsEndorsementPolicyWithMonthlyDirectDebitActivity extends
    AbstractActivity< NewBusinessPage >
{
    private HarvestPage _page;

    public FinishPolicyAsEndorsementPolicyWithMonthlyDirectDebitActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected NewBusinessPage execute()
    {
        FinishPage finishPage = _page.getNavigationTree().navigateToFinish();
        finishPage.setSaveTransactionAs( "In Force" );
        finishPage.setPremiumPaymentMethod( "Monthly Direct Debit" );
        finishPage.setIntermediaryFee( "150" );
        finishPage.setBankDetails( "012345", "123456", "Don Corleone" );

        InstalmentPlanPopup instalmentPlan = finishPage.viewInstalmentPlan();
        instalmentPlan.getIntermediaryFees();
        instalmentPlan.closeInstalmentPlan();
        finishPage.setDirectDebitAuthorisation( "Verbal Authority" );
        return finishPage.finish();
    }
}
