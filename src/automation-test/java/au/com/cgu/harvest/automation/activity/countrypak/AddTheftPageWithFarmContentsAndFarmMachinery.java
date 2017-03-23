package au.com.cgu.harvest.automation.activity.countrypak;

import au.com.cgu.harvest.automation.activity.AbstractActivity;
import au.com.cgu.harvest.automation.activity.Activity;
import au.com.cgu.harvest.pages.HarvestPage;
import au.com.cgu.harvest.pages.countrypak.CountrypakSection;
import au.com.cgu.harvest.pages.countrypak.SectionType;
import au.com.cgu.harvest.pages.countrypak.TheftPage;

@Activity( "Add Theft - farm Contents value, Farm contents and Farm Machinery Activity" )
public class AddTheftPageWithFarmContentsAndFarmMachinery extends AbstractActivity< TheftPage >
{
    private HarvestPage _page;

    public AddTheftPageWithFarmContentsAndFarmMachinery( HarvestPage page )
    {
        _page = page;
    }

    @Override
    protected TheftPage execute()
    {
        TheftPage theftPage =
            _page.getNavigationTree().navigateToTheft();

        theftPage.setFarmContentsValue( "10,000" );
        theftPage.getSpecifiedFarmContentsGrid().addRow( "Farm content-1", "100" );
        theftPage.getSpecifiedFarmContentsGrid().addRow( "Farm content-2", "200" );
        theftPage.getSpecifiedFarmMachineryGrid().addRow( "Farm Machinery-1", "100" );
        theftPage.getSpecifiedFarmMachineryGrid().addRow( "Farm Machinery-2", "200" );

        CountrypakSection section =
            _page.getNavigationTree().getSection( SectionType.THEFT );
        section.isTaken();
        return theftPage;
    }

}
