package au.com.cgu.harvest.automation.activity.sunrise;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.automation.sunriseimport.SunrisePolicyImport;
import au.com.cgu.harvest.pages.sunrise.ImportPolicyPage;
import au.com.cgu.harvest.pages.sunrise.NewBusinessPage;

@Activity( "Input a Policy Number and search for the policy to import" )
public class ImportPolicyActivity extends
    AbstractActivity< NewBusinessPage >
{
    private ImportPolicyPage _importPolicyPage;
    private SunrisePolicyImport _policyToImport;

    public ImportPolicyActivity( ImportPolicyPage importPolicyPage,
        SunrisePolicyImport policyToImport )
    {
        _importPolicyPage = importPolicyPage;
        _policyToImport = policyToImport;
    }

    @Override
    protected NewBusinessPage execute()
    {
        selectProduct();
        _importPolicyPage.setPolicyNumber( _policyToImport.getPolicyNumber() );
        return _importPolicyPage.importPolicyFromSearchResults();
    }

    private void selectProduct()
    {
        if ( _policyToImport.isFarmMotor() )
        {
            _importPolicyPage.selectProductByCode( "FMCONV" );
        }
        else
        {
            _importPolicyPage.selectProductByCode( "CCCONV" );
        }
    }

}
