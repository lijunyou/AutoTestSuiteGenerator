import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest0 {

    public static boolean debug = false;

    @Test
    public void test1() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test1");
        try {
            int[] intArray1 = LC_4.stringToIntegerArray("hi!");
            org.junit.Assert.fail("Expected exception of type java.lang.NumberFormatException; message: For input string: \"i\"");
        } catch (java.lang.NumberFormatException e) {
            org.junit.Assert.fail("Expected exception of type java.lang.NumberFormatException; message: For input string: \"i\"");
        }
    }

    @Test
    public void test2() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test2");
        java.lang.Object obj0 = new java.lang.Object();
        java.lang.Class<?> wildcardClass1 = obj0.getClass();
        org.junit.Assert.assertNotNull(wildcardClass1);
    }

    @Test
    public void test3() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test3");
        int[] intArray1 = new int[] { '4' };
        int[] intArray2 = new int[] {};
        double double3 = LC_4.findMedianSortedArrays(intArray1, intArray2);
        org.junit.Assert.assertNotNull(intArray1);
        org.junit.Assert.assertNotNull(intArray2);
        org.junit.Assert.assertTrue("'" + double3 + "' != '" + 52.0d + "'", double3 == 52.0d);
    }
}

