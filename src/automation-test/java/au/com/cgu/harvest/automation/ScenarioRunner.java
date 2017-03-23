// package au.com.cgu.harvest.automation;
//
// import java.util.List;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.TimeUnit;
//
// import org.junit.runner.Description;
// import org.junit.runner.notification.RunNotifier;
// import org.junit.runners.BlockJUnit4ClassRunner;
// import org.junit.runners.model.FrameworkMethod;
// import org.junit.runners.model.InitializationError;
// import org.junit.runners.model.RunnerScheduler;
// import org.junit.runners.model.Statement;
//
// import au.com.cgu.harvest.automation.scenario.AbstractScenario;
//
// public class ScenarioRunner extends BlockJUnit4ClassRunner
// {
// private static class ThreadPoolScheduler implements RunnerScheduler
// {
// private ExecutorService _executor;
//
// public ThreadPoolScheduler()
// {
// _executor = Executors.newCachedThreadPool();
// }
//
// @Override
// public void finished()
// {
// _executor.shutdown();
// try
// {
// _executor.awaitTermination( 10, TimeUnit.MINUTES );
// }
// catch ( InterruptedException exc )
// {
// throw new RuntimeException( exc );
// }
// }
//
// @Override
// public void schedule( Runnable childStatement )
// {
// _executor.submit( childStatement );
// }
// }
//
// AbstractScenario _scenario;
//
// public ScenarioRunner( Class< ? > scenarioClass )
// throws InitializationError
// {
// super( ScenarioTestClass.class );
// setScheduler( new ThreadPoolScheduler() );
// _scenario = createScenario( scenarioClass );
// }
//
// @Override
// protected Description describeChild( FrameworkMethod method )
// {
// return Description.createTestDescription( _scenario.getClass(),
// testName( method ), method.getAnnotations() );
// }
//
// @Override
// public Object createTest() throws Exception
// {
// return getTestClass().getOnlyConstructor().newInstance( _scenario );
// }
//
// private AbstractScenario createScenario( Class< ? > type )
// {
// try
// {
// return ( AbstractScenario ) type.newInstance();
// }
// catch ( Exception e )
// {
// e.printStackTrace();
// throw new RuntimeException( "Failed to create scenario.", e );
// }
// }
//
// @Override
// protected String getName()
// {
// return _scenario.getClass().getName();
// }
//
// @Override
// protected String testName( final FrameworkMethod method )
// {
// return String.format( "%s[%s]", "execute:",
// _scenario.getDescription() );
// }
//
// @Override
// protected void validateConstructor( List< Throwable > errors )
// {
// validateOnlyOneConstructor( errors );
// }
//
// @Override
// protected void validateTestMethods( List< Throwable > errors )
// {
// // TESTING: should check that the class is a derivative of scenario maybe?
// }
//
// @Override
// protected Statement classBlock( RunNotifier notifier )
// {
// return childrenInvoker( notifier );
// }
// }
