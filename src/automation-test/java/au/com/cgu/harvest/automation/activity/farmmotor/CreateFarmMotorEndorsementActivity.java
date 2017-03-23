// package au.com.cgu.harvest.automation.activity.farmmotor;
//
// import au.com.cgu.harvest.api.PolicyDetailPage;
// import au.com.cgu.harvest.automation.activity.AbstractActivity;
// import au.com.cgu.harvest.automation.activity.Activity;
//
// @Activity( "Create a Farm Motor Endorsement Transaction with Single PMV" )
// public class CreateFarmMotorEndorsementActivity extends AbstractActivity< PolicyDetailPage >
// {
//
// @Override
// protected PolicyDetailPage execute()
// {
// WelcomePage welcomePage =
// performActivity( new LoginToExecutiveActivity() );
//
// PolicyDetailPage policyDetailPage =
// performActivity( new LaunchFarmMotorActivity( welcomePage ) );
//
// policyDetailPage =
// performActivity( new PolicyDetailsActivity( policyDetailPage ) );
//
// InsuranceHistoryPage insuranceHistoryPage =
// performActivity( new InsuranceHistoryActivity( policyDetailPage ) );
//
// PrivateMotorVehiclePage vehiclePage =
// performActivity( new CreatePrivateMotorVehicleActivity(
// insuranceHistoryPage ) );
//
// NewBusinessPage newBusinessPage =
// performActivity( new FinishPolicyAsNewPolicyActivity( vehiclePage ) );
//
// newBusinessPage =
// performActivity( new GetAcceptanceActivity( newBusinessPage ) );
//
// policyDetailPage =
// performActivity( new ModifyAndModifyRiskDetailsActivity( newBusinessPage ) );
//
// return policyDetailPage;
// }
// }
