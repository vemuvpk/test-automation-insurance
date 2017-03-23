package au.com.cgu.harvest.automation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.runners.model.RunnerScheduler;

public class ParallelScenarioRunner extends DescriptivelyParameterized
{
    private static class ThreadPoolScheduler implements RunnerScheduler
    {
        private final ExecutorService _executor;

        public ThreadPoolScheduler()
        {
            _executor = Executors.newFixedThreadPool( 5 );
        }

        @Override
        public void schedule( Runnable childStatement )
        {
            _executor.submit( childStatement );
        }

        @Override
        public void finished()
        {
            _executor.shutdown();
            try
            {
                _executor.awaitTermination( 2, TimeUnit.HOURS );
            }
            catch ( InterruptedException e )
            {
                throw new RuntimeException( "FAILED TO TERMINATE THREAD", e );
            }
        }
    }

    public ParallelScenarioRunner( Class< ? > type ) throws Throwable
    {
        super( type );
        // PH-1473: Temporarily remove multi-browser support
        // setScheduler( new ThreadPoolScheduler() );
    }

}
