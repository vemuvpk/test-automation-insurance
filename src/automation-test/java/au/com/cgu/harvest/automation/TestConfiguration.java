package au.com.cgu.harvest.automation;

import org.apache.log4j.Logger;

import au.com.cgu.harvest.pages.sunrise.IntermediaryDetails;

public class TestConfiguration
{
    private static Logger LOGGER = Logger.getLogger( TestConfiguration.class );

    private static String PROPERTY_EXEC_URL = "sunrise.exec.url";
    private static String BUILD_MODE = "build.mode";
    private static String PROPERTY_FARM_MOTOR_PRODUCT = "farmmotor.product";
    private static String PROPERTY_COUNTRYPAK_PRODUCT = "countrypak.product";
    private static String PROPERTY_IS_HOSTED_MODE = "hosted.mode";
    private static String PROPERTY_REMOTE_DRIVER_HOST = "remote.driver.host";

    private static String PROPERTY_COMPANY_ID = "company.id";
    private static String PROPERTY_USER_ID = "user.id";
    private static String PROPERTY_PASSWORD = "password";
    private static String PROPERTY_CLIENT_ID = "client.id";
    private static String PROPERTY_INSURED_ID = "insured.id";

    private String _sunriseExecutiveUrl;
    private String _farmMotorProduct;
    private String _countrypakProduct;
    private HostedMode _hostedMode;
    private DriverMode _driverMode;
    private String _remoteDriverHost;
    private DriverOperatingSystem _driverOperatingSystem;
    private String _buildMode;
    private IntermediaryDetails _intermediaryDetails;

    private static TestConfiguration _currentTestConfiguration;

    static
    {
        initialiseTestConfiguration();
    }

    /**
     * LOCAL DEVELOPMENT OPTIONS: LocalJettyConfiguration: Runs against Jetty without the Selenium
     * Server LocalHostedModeConfiguration: Runs against Hosted Mode without the Selenium Server
     * RemoteDriverLocalJettyConfiguration: Runs against Jetty using a local Selenium Server
     * LOGGER.info( "Starting WebDriver using non-JVM Test Configuration" );
     * _activeTestConfiguration = TestConfiguration.getWebsphereSeleniumConfiguration();
     * _logger.info( "Configuration: " + _activeTestConfiguration.getClass().getName() );
     */
    private static void initialiseTestConfiguration()
    {
        if ( jvmArgsProvided() )
        {
            LOGGER.debug( "RUNNING REMOTE MODE using jvm args" );
            _currentTestConfiguration = getRemoteConfiguration();
        }
        else
        {
            LOGGER.debug( "RUNNING LOCAL MODE using default login and client details hardcoded" );
            LOGGER.debug( "ALL JVM args not provided so switching to local mode" );
            _currentTestConfiguration = getRemoteDriverLocalJettyConfiguration();
        }
    }

    public enum HostedMode
    {
        TRUE,
        FALSE
    }

    public enum DriverMode
    {
        LOCAL,
        REMOTE
    }

    public enum DriverOperatingSystem
    {
        WINDOWS,
        UNIX
    }

    private TestConfiguration( String sunriseExecutiveUrl, String farmMotorProduct,
        String countrypakProduct, HostedMode hostedMode,
        DriverOperatingSystem driverOperatingSystem, IntermediaryDetails intermediaryDetails )
    {
        this( sunriseExecutiveUrl, farmMotorProduct, countrypakProduct, hostedMode,
            driverOperatingSystem, DriverMode.LOCAL, intermediaryDetails );
    }

    private TestConfiguration( String sunriseExecutiveUrl, String farmMotorProduct,
        String countrypakProduct, HostedMode hostedMode, String remoteDriverHost,
        DriverOperatingSystem remoteDriverOperatingSystem, String buildMode,
        IntermediaryDetails intermediaryDetails )
    {
        this( sunriseExecutiveUrl, farmMotorProduct, countrypakProduct, hostedMode,
            remoteDriverOperatingSystem, DriverMode.REMOTE, intermediaryDetails );
        _remoteDriverHost = remoteDriverHost;
        _buildMode = buildMode;

    }

    public String getSunriseExecutiveUrl()
    {
        return _sunriseExecutiveUrl;
    }

    public String getFarmMotorProduct()
    {
        return _farmMotorProduct;
    }

    public String getCountrypakProduct()
    {
        return _countrypakProduct;
    }

    public HostedMode getHostedMode()
    {
        return _hostedMode;
    }

    public DriverMode getDriverMode()
    {
        return _driverMode;
    }

    public DriverOperatingSystem getDriverOperatingSystem()
    {
        return _driverOperatingSystem;
    }

    public String getRemoteDriverHost()
    {
        return _remoteDriverHost;
    }

    public boolean isCiBuild()
    {
        return "continuous-integration".equals( _buildMode );
    }

    public static TestConfiguration getCurrentTestConfiguration()
    {
        return _currentTestConfiguration;
    }

    private static TestConfiguration getLocalHostedModeConfiguration()
    {
        _currentTestConfiguration = new TestConfiguration(
            "http://localsunrise:5150/saffron/www/intermediary/Login.jsp",
            "Farm Motor", "Countrypak", HostedMode.TRUE,
            DriverOperatingSystem.WINDOWS, createIntermediaryDetails() );

        return _currentTestConfiguration;
    }

    public static TestConfiguration getRemoteDriverLocalJettyConfiguration()
    {
        _currentTestConfiguration =
            new TestConfiguration(
                "http://localsunrise:5150/saffron/www/intermediary/Login.jsp",
                "Farm Motor Jetty", "Countrypak Jetty",
                HostedMode.FALSE,
                "http://localdev:6161/wd/hub", DriverOperatingSystem.WINDOWS, "local",
                createIntermediaryDetails() );
        return _currentTestConfiguration;
    }

    public static TestConfiguration getRemoteDriverWebsphereSeleniumConfiguration()
    {
        _currentTestConfiguration =
            new TestConfiguration(
                "http://sdc1ws187:5150/saffron/www/intermediary/Login.jsp",
                "Farm Motor Selenium Test D99280040", "Countrypak Selenium Test D99280040",
                HostedMode.FALSE,
                "http://localdev:6161/wd/hub", DriverOperatingSystem.WINDOWS, "local",
                createIntermediaryDetails() );
        return _currentTestConfiguration;
    }

    private static TestConfiguration getRemoteConfiguration()
    {
        String url = System.getProperty( PROPERTY_EXEC_URL );
        String buildMode = System.getProperty( BUILD_MODE );
        String farmMotorProduct = System.getProperty( PROPERTY_FARM_MOTOR_PRODUCT );
        String countrypakProduct = System.getProperty( PROPERTY_COUNTRYPAK_PRODUCT );
        String isHostedMode = System.getProperty( PROPERTY_IS_HOSTED_MODE );
        String remoteDriverHost = System.getProperty( PROPERTY_REMOTE_DRIVER_HOST );

        LOGGER.debug( "Attempting to load JVM Test Configuration" );
        _currentTestConfiguration =
            new TestConfiguration( url, farmMotorProduct, countrypakProduct,
                HostedMode.valueOf( isHostedMode.toUpperCase() ),
                remoteDriverHost, DriverOperatingSystem.WINDOWS,
                buildMode, createIntermediaryDetails() );
        return _currentTestConfiguration;

    }

    private static IntermediaryDetails createIntermediaryDetails()
    {
        return new IntermediaryDetails( getValue( PROPERTY_COMPANY_ID, "BROKER" ),
            getValue( PROPERTY_USER_ID, "BROKER" ),
            getValue( PROPERTY_PASSWORD, "password" ),
            getValue( PROPERTY_CLIENT_ID, "1" ),
            getValue( PROPERTY_INSURED_ID, "1" ) );
    }

    private static String getValue( String key, String defaultValue )
    {
        return System.getProperty( key ) == null ? defaultValue : System.getProperty( key );
    }

    public static boolean jvmArgsProvided()
    {
        String url = System.getProperty( PROPERTY_EXEC_URL );
        String buildMode = System.getProperty( BUILD_MODE );
        String farmMotorProduct = System.getProperty( PROPERTY_FARM_MOTOR_PRODUCT );
        String countrypakProduct = System.getProperty( PROPERTY_COUNTRYPAK_PRODUCT );
        String isHostedMode = System.getProperty( PROPERTY_IS_HOSTED_MODE );
        String remoteDriverHost = System.getProperty( PROPERTY_REMOTE_DRIVER_HOST );

        String companyId = System.getProperty( PROPERTY_COMPANY_ID );
        String userId = System.getProperty( PROPERTY_USER_ID );
        String password = System.getProperty( PROPERTY_PASSWORD );
        String clientId = System.getProperty( PROPERTY_CLIENT_ID );
        String insuredId = System.getProperty( PROPERTY_INSURED_ID );

        LOGGER.debug( "WEBDRIVER JVM ARGS -------------------------------------------" );
        LOGGER.debug( "Sunrise URL: " + url );
        LOGGER.debug( "Farm Motor Product: " + farmMotorProduct );
        LOGGER.debug( "Countrypak Product: " + countrypakProduct );
        LOGGER.debug( "Hosted Mode: " + isHostedMode );
        LOGGER.debug( "Remote Driver Host: " + remoteDriverHost );
        LOGGER.debug( "Build Mode: " + buildMode );
        LOGGER.debug( "Company Id: " + companyId );
        LOGGER.debug( "User Id: " + userId );
        LOGGER.debug( "Password: " + password );
        LOGGER.debug( "Client Id: " + clientId );
        LOGGER.debug( "Insured Id: " + insuredId );
        LOGGER.debug( "--------------------------------------------------------------" );

        boolean valid = url != null && farmMotorProduct != null && countrypakProduct != null
            && isHostedMode != null && remoteDriverHost != null
            && !remoteDriverHost.equals( "" )
            && buildMode != null
            && companyId != null
            && userId != null
            && password != null
            && clientId != null
            && insuredId != null;
        LOGGER.debug( "Validating from jvmArgsProvided and are : " + valid );

        return valid;
    }

    private TestConfiguration( String sunriseExecutiveUrl, String farmMotorProduct,
        String countrypakProduct, HostedMode hostedMode,
        DriverOperatingSystem driverOperatingSystem, DriverMode driverMode,
        IntermediaryDetails intermediaryDetails )
    {
        _sunriseExecutiveUrl = sunriseExecutiveUrl;
        _farmMotorProduct = farmMotorProduct;
        _countrypakProduct = countrypakProduct;
        _hostedMode = hostedMode;
        _driverMode = driverMode;
        _driverOperatingSystem = driverOperatingSystem;
        _intermediaryDetails = intermediaryDetails;
    }

    public IntermediaryDetails getIntermediaryDetails()
    {
        return _intermediaryDetails;
    }
}
