package uoc.ds.pr.util;

public class ResourceUtil {

    public static byte getFlag(byte flagVolunteers, byte flagPublicSecurity, byte flagPrivateSecurity, byte flagBasicLifeSupport) {
        return (byte) (flagVolunteers | flagPublicSecurity | flagPrivateSecurity | flagBasicLifeSupport);
    }

    public static byte getFlag(byte flagPublicSecurity, byte flagPrivateSecurity, byte flagBasicLifeSupport) {
        return (byte) (flagPublicSecurity | flagPrivateSecurity | flagBasicLifeSupport);
    }

    public static byte getFlag(byte flagPublicSecurity, byte flagVolunteers) {
        return (byte) (flagVolunteers | flagPublicSecurity);
    }

    public static byte getFlag(byte flagAllOpts) {
        return flagAllOpts;
    }

    public static boolean hasPublicSecurity(byte resource) {
        return (resource & 2) == 2;
    }

    public static boolean hasPrivateSecurity(byte resource) {
        return (resource & 1) == 1;
    }

    public static boolean hasBasicLifeSupport(byte resource) {
        return (resource & 4) == 4;
    }

    public static boolean hasVolunteers(byte resource) {
        return (resource & 8) == 8;
    }

    public static boolean hasAllOpts(byte resource) {
        return (resource & 15) == 15;
    }


}
