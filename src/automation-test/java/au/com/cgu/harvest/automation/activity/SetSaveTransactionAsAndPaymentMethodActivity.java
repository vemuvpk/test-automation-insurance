package au.com.cgu.harvest.automation.activity;

import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Set Save Transaction asand payment Method in FinishPage Activity" )
public class SetSaveTransactionAsAndPaymentMethodActivity extends
    AbstractActivity< FinishPage >
{
    private FinishPage _page;
    private String _saveTransaction;
    private String _paymentMethod;

    public SetSaveTransactionAsAndPaymentMethodActivity( FinishPage page, String saveTransaction,
        String paymentMethod )
    {
        _page = page;
        _saveTransaction = saveTransaction;
        _paymentMethod = paymentMethod;
    }

    @Override
    protected FinishPage execute()
    {
        _page.setSaveTransactionAs( _saveTransaction );
        _page.setPremiumPaymentMethod( _paymentMethod );
        return new FinishPage( getWebDriver() );

    }
}
