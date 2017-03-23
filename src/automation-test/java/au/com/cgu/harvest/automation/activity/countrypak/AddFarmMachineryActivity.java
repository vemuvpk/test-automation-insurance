package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.FarmMachineryAndWorkingDogsPage;
import au.com.cgu.harvest.pages.countrypak.SectionType;

@Activity( "Assert Premiums in farm Machinery and Working Dogs page Activity" )
public class AddFarmMachineryActivity extends AbstractActivity< FarmMachineryAndWorkingDogsPage >
{
    private HarvestPage _page;

    public AddFarmMachineryActivity( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected FarmMachineryAndWorkingDogsPage execute()
    {
        FarmMachineryAndWorkingDogsPage farmMachineryAndWorkingDogsPage =
            _page.getNavigationTree().navigateToFarmMachinery();

        farmMachineryAndWorkingDogsPage.setUnspecifiedFarmMachinerySumInsured( "5000" );
        farmMachineryAndWorkingDogsPage.getFarmMachineryGrid().addRow( "Machinery-1", "500" );
        farmMachineryAndWorkingDogsPage.getFarmMachineryGrid().addRow( "machinery-2", "550" );
        farmMachineryAndWorkingDogsPage.getFarmDogsGrid().addRow( "Dog-1", "800" );
        farmMachineryAndWorkingDogsPage.getFarmDogsGrid().addRow( "Dog-2", "600" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.FARM_MACHINERY );
        section.isTaken();

        return farmMachineryAndWorkingDogsPage;
    }
}
