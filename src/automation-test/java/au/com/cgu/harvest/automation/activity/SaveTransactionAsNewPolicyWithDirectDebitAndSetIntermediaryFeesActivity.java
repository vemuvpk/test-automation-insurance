package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Save Transaction as New Policy with MOnthly Credit Card and Set Intermediary fees in FinishPage Activity" )
public class SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity extends
    AbstractActivity< FinishPage >
{
    private FinishPage _page;

    public SaveTransactionAsNewPolicyWithDirectDebitAndSetIntermediaryFeesActivity( FinishPage page )
    {
        _page = page;
    }

    @Override
    protected FinishPage execute()
    {
        _page.setSaveTransactionAs( "Policy" );
        _page.setPolicyDeclaration( "Yes" );
        _page.setPremiumPaymentMethod( "Monthly Credit Card" );
        _page.setIntermediaryFee( "150" );
        _page.setDirectDebitAuthorisation( "Verbal Authority" );
        return new FinishPage( getWebDriver() );

    }
}
