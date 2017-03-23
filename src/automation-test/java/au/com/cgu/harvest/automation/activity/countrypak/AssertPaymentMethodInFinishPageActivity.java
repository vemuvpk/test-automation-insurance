package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.farmmotor.FinishPage;

@Activity( "Assert Payment Method in Finish Page" )
public class AssertPaymentMethodInFinishPageActivity extends AbstractActivity< FinishPage >
{
    private FinishPage _page;
    private String _paymentMethod;

    public AssertPaymentMethodInFinishPageActivity( FinishPage page, String paymentMethod )
    {
        _page = page;
        _paymentMethod = paymentMethod;
    }

    @Override
    protected FinishPage execute()
    {
        _page.getPremiumPaymentMethodDisabled();
        AbstractScenario.getScenarioLogger()
            .trace( "Payment Method is: " + _page.getPremiumPaymentMethod() );
        Assert.assertEquals( _paymentMethod, _page.getPremiumPaymentMethod() );
        return _page;
    }
}
