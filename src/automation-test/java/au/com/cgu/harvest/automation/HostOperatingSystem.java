package au.com.cgu.harvest.automation;

import java.io.File;

public class HostOperatingSystem
{
    public static boolean isWindows()
    {
        return File.pathSeparator.equals( ";" );
    }
}
