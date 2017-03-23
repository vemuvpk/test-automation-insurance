package au.com.cgu.harvest.automation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.extensions.cpsuite.ClasspathSuite;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.junit.runners.model.RunnerScheduler;

public class ConcurrentClasspathSuite extends ClasspathSuite
{
    private static class ThreadPoolScheduler implements RunnerScheduler
    {
        private final ExecutorService _executor;

        public ThreadPoolScheduler()
        {
            // _executor = Executors.newFixedThreadPool( 3 );
            _executor = Executors.newCachedThreadPool();
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

    public ConcurrentClasspathSuite( Class< ? > suiteClass, RunnerBuilder builder )
        throws InitializationError
    {
        super( suiteClass, builder );
        setScheduler( new ThreadPoolScheduler() );
    }

}
