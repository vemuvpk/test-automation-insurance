package au.com.cgu.harvest.automation.activity.farmmotor;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.PremiumPage;
import au.com.cgu.harvest.pages.PremiumRow;

@Activity( "Override the Adjustment percentage at Risk Level" )
public class OverrideAdjustmentPercentageAtRiskLevelActivity extends AbstractActivity< PremiumPage >
{
    private PremiumPage _page;
    public String _riskLabel;
    private PremiumRow _rowNumber;

    public OverrideAdjustmentPercentageAtRiskLevelActivity( PremiumPage page,
        PremiumRow premiumRow, String riskLabel )
    {
        _page = page;
        _riskLabel = riskLabel;
        _rowNumber = premiumRow;
    }

    @Override
    protected PremiumPage execute()
    {
        _rowNumber.setAdjustmentPercentageAtRiskLevel( _riskLabel );
        return _page;
    }
}
