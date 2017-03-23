package au.com.cgu.harvest.automation.rule;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.annotations.VisibleForTesting;

public class MethodTimer extends TestWatcher
{

    @VisibleForTesting
    Timing timer;

    public MethodTimer()
    {
    }

    @Override
    protected void starting( Description description )
    {
        String timerName = String.format( "%s.%s",
            description.getClassName(),
            description.getMethodName() );
        timer = new Timing( timerName );
        timer.start();
    }

    @Override
    protected void finished( Description description )
    {
        timer.stop();
    }
}
