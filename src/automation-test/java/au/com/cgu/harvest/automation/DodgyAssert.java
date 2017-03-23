package au.com.cgu.harvest.automation;

import java.math.BigDecimal;

import au.com.cgu.harvest.automation.scenario.AbstractScenario;

public class DodgyAssert
{
    private static final BigDecimal THREE_CENTS = new BigDecimal( "0.03" );
    private static final String ASSERTION_MESSAGE = "%s is not within 3 cents of %s";
    private static final String WARNING_MESSAGE =
        "WARNING: %s is not equal, but is within 3 cents of %s";

    public static void assertNearlyEquals( String expected, String actual )
    {
        if ( isEmpty( expected ) && isEmpty( actual ) )
        {
            return;
        }

        BigDecimal actualNumber = getDecimalFromCurrencyString( actual );
        BigDecimal expectedNumber = getDecimalFromCurrencyString( expected );

        if ( expectedNumber.equals( actualNumber ) )
        {
            return;
        }
        if ( isBetweenZeroAndThreeCents( expectedNumber.subtract( actualNumber ) )
            || isBetweenZeroAndThreeCents( actualNumber.subtract( expectedNumber ) ) )
        {
            AbstractScenario.getScenarioLogger()
                .trace( String.format( WARNING_MESSAGE, actual, expected ) );
        }
        else
        {
            throw new AssertionError( String.format( ASSERTION_MESSAGE, actual, expected ) );
        }

    }

    private static BigDecimal getDecimalFromCurrencyString( String value )
    {
        if ( isEmpty( value ) )
        {
            value = "0.00";
        }
        return new BigDecimal( value.replace( "$", "" ).replace( ",", "" ) );
    }

    static boolean isBetweenZeroAndThreeCents( BigDecimal amount )
    {
        return amount.compareTo( new BigDecimal( "-0.03" ) ) >= 0
            && amount.compareTo( new BigDecimal( "0.03" ) ) <= 0;
    }

    public static boolean isEmpty( String s )
    {
        return s == null || s.length() == 0;
    }
}
