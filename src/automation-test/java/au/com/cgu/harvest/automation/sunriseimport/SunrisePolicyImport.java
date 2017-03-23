package au.com.cgu.harvest.automation.sunriseimport;

public class SunrisePolicyImport
{
    private String _policyNumber;
    private String _intermediary;

    public String getPolicyNumber()
    {
        return _policyNumber;
    }

    public void setPolicyNumber( String policyNumber )
    {
        _policyNumber = policyNumber;
    }

    public String getIntermediary()
    {
        return _intermediary;
    }

    public void setIntermediary( String intermediary )
    {
        _intermediary = intermediary;
    }

    public boolean isFarmMotor()
    {
        return _policyNumber.startsWith( "24B" );
    }

}
