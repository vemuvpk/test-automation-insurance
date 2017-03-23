package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.PremiumPage;

@Activity( "Add cancellation Reason int he premium page Activity" )
public class AddCancellationReasonActivity extends
    AbstractActivity< PremiumPage >
{
    private PremiumPage _page;

    public AddCancellationReasonActivity( PremiumPage page )
    {
        _page = page;
    }

    @Override
    protected PremiumPage execute()
    {
        _page.setCancellationReason( "Insured Elsewhere" );

        return new PremiumPage( getWebDriver() );
    }
}
