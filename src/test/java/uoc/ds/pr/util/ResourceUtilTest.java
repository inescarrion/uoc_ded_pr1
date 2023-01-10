package uoc.ds.pr.util;

import org.junit.Assert;
import org.junit.Test;

import static uoc.ds.pr.SportEvents4Club.*;

public class ResourceUtilTest {


    @Test
    public void hasFlagTest1() {
        byte resource = ResourceUtil.getFlag(FLAG_PUBLIC_SECURITY, FLAG_PRIVATE_SECURITY, FLAG_BASIC_LIFE_SUPPORT);

        Assert.assertTrue(ResourceUtil.hasPublicSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasPrivateSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasBasicLifeSupport(resource));
        Assert.assertFalse(ResourceUtil.hasVolunteers(resource));
        Assert.assertFalse(ResourceUtil.hasAllOpts(resource));
    }

    @Test
    public void hasFlagTest2() {
        byte resource = ResourceUtil.getFlag(FLAG_PUBLIC_SECURITY, FLAG_VOLUNTEERS);

        Assert.assertTrue(ResourceUtil.hasPublicSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasVolunteers(resource));
        Assert.assertFalse(ResourceUtil.hasPrivateSecurity(resource));
        Assert.assertFalse(ResourceUtil.hasBasicLifeSupport(resource));
        Assert.assertFalse(ResourceUtil.hasAllOpts(resource));
    }

    @Test
    public void hasFlagTest3() {
        byte resource = ResourceUtil.getFlag(FLAG_ALL_OPTS);

        Assert.assertTrue(ResourceUtil.hasPublicSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasVolunteers(resource));
        Assert.assertTrue(ResourceUtil.hasPrivateSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasBasicLifeSupport(resource));
        Assert.assertTrue(ResourceUtil.hasAllOpts(resource));
    }

    @Test
    public void hasFlagTest4() {
        byte resource = ResourceUtil.getFlag(FLAG_VOLUNTEERS, FLAG_PUBLIC_SECURITY, FLAG_PRIVATE_SECURITY, FLAG_BASIC_LIFE_SUPPORT);

        Assert.assertTrue(ResourceUtil.hasPublicSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasVolunteers(resource));
        Assert.assertTrue(ResourceUtil.hasPrivateSecurity(resource));
        Assert.assertTrue(ResourceUtil.hasBasicLifeSupport(resource));
        Assert.assertTrue(ResourceUtil.hasAllOpts(resource));
    }

}
