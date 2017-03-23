package au.com.cgu.harvest.automation.activity.countrypak;

import org.junit.Assert;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.scenario.AbstractScenario;
import au.com.cgu.harvest.pages.countrypak.SituationDetailPage;

@Activity( "Assert Suburb, State and Postcode in Situation executive" )
public class AssertSuburbStatePostcodeInSituationActivity extends
    AbstractActivity< SituationDetailPage >
{
    private SituationDetailPage _page;
    private String _suburb;
    private String _state;
    private String _postcode;

    public AssertSuburbStatePostcodeInSituationActivity( SituationDetailPage page, String suburb,
        String state, String postcode )
    {
        _page = page;
        _suburb = suburb;
        _state = state;
        _postcode = postcode;
    }

    @Override
    protected SituationDetailPage execute()
    {
        _page.getGarageSuburb();
        AbstractScenario.getScenarioLogger().trace( "Suburb is:" + _page.getGarageSuburb() );
        Assert.assertEquals( _suburb, _page.getGarageSuburb() );

        _page.getGarageStateCode();
        AbstractScenario.getScenarioLogger().trace( "State is:" + _page.getGarageStateCode() );
        Assert.assertEquals( _state, _page.getGarageStateCode() );

        _page.getGaragePostcode();
        AbstractScenario.getScenarioLogger().trace( "PostCode is:" + _page.getGaragePostcode() );
        Assert.assertEquals( _postcode, _page.getGaragePostcode() );
        return _page;
    }
}
