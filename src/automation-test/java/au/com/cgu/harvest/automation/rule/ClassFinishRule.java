package au.com.cgu.harvest.automation.rule;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.annotations.VisibleForTesting;

/**
 * This TestWatcher com.dynacrongroup.webtest.rule notes when the last test method has finished. It
 * can be extended to
 * provide custom behavior when a class finishes.
 * User: yurodivuie
 * Date: 3/9/12
 * Time: 8:52 AM
 */
public class ClassFinishRule extends TestWatcher
{

    @VisibleForTesting
    Integer methodsRemaining = null;
    private static final Logger LOG = Logger.getLogger( ClassFinishRule.class );

    /**
     * Tracks the number of test methods remaining and calls "classFinished" if none are left.
     * Rules that extend this class should call this method.
     */
    @Override
    protected void finished( Description description )
    {
        if ( methodsRemaining == null )
        {
            methodsRemaining = countTestMethods( description.getTestClass() );
        }

        methodsRemaining--;

        // Test class is complete. Check less than, since it's possible that there were no methods
        // to start with.
        if ( methodsRemaining <= 0 )
        {
            LOG.trace( String.format( "Class %s has no methods remaining", description
                .getTestClass().getSimpleName() ) );
            classFinished( description );
        }
    }

    /**
     * Override this rule to provide custom behavior for when a test class has finished running.
     * 
     * @param description
     */
    protected void classFinished( Description description )
    {
        LOG.debug( String.format( "Test class %s finished running all test methods.", description
            .getTestClass().getName() ) );
    }

    /**
     * This method counts the number of test methods. This counter is used to
     * help shut down the browsers when the test is complete.
     */
    @VisibleForTesting
    static int countTestMethods(
        @SuppressWarnings( "rawtypes" ) Class testClass )
    {

        int count = 0;
        for ( Method m : testClass.getMethods() )
        {
            if ( m.getAnnotation( Test.class ) != null
                && m.getAnnotation( Ignore.class ) == null )
            {
                count++;
            }
        }
        return count;
    }
}
