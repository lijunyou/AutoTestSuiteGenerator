import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegressionTest01 {

    public static boolean debug = false;

    @Test
    public void test1() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test1");
        int[] intArray4 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray5 = new int[] {};
        double double7 = LC_3.findMedianSortedArrays(intArray4, intArray5, "");
        int[] intArray12 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray13 = new int[] {};
        double double15 = LC_3.findMedianSortedArrays(intArray12, intArray13, "");
        double double17 = LC_3.findMedianSortedArrays(intArray4, intArray13, "hi!");
        java.lang.Class<?> wildcardClass18 = intArray4.getClass();
        org.junit.Assert.assertNotNull(intArray4);
        org.junit.Assert.assertNotNull(intArray5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + (-1.0d) + "'", double7 == (-1.0d));
        org.junit.Assert.assertNotNull(intArray12);
        org.junit.Assert.assertNotNull(intArray13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + (-1.0d) + "'", double15 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(wildcardClass18);
    }

    @Test
    public void test2() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test2");
        int[] intArray0 = null;
        int[] intArray1 = new int[] {};
        try {
            double double3 = LC_3.findMedianSortedArrays(intArray0, intArray1, "");
            org.junit.Assert.fail("Expected exception of type java.lang.ArrayIndexOutOfBoundsException; message: 0");
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            org.junit.Assert.fail("Expected exception of type java.lang.ArrayIndexOutOfBoundsException; message: 0");
        }
        org.junit.Assert.assertNotNull(intArray1);
    }

    @Test
    public void test3() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test3");
        int[] intArray0 = new int[] {};
        int[] intArray5 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray6 = new int[] {};
        double double8 = LC_3.findMedianSortedArrays(intArray5, intArray6, "");
        int[] intArray13 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray14 = new int[] {};
        double double16 = LC_3.findMedianSortedArrays(intArray13, intArray14, "");
        double double18 = LC_3.findMedianSortedArrays(intArray5, intArray14, "hi!");
        try {
            double double20 = LC_3.findMedianSortedArrays(intArray0, intArray14, "hi!");
            org.junit.Assert.fail("Expected exception of type java.lang.ArrayIndexOutOfBoundsException; message: 0");
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            org.junit.Assert.fail("Expected exception of type java.lang.ArrayIndexOutOfBoundsException; message: 0");
        }
        org.junit.Assert.assertNotNull(intArray0);
        org.junit.Assert.assertNotNull(intArray5);
        org.junit.Assert.assertNotNull(intArray6);
        org.junit.Assert.assertTrue("'" + double8 + "' != '" + (-1.0d) + "'", double8 == (-1.0d));
        org.junit.Assert.assertNotNull(intArray13);
        org.junit.Assert.assertNotNull(intArray14);
        org.junit.Assert.assertTrue("'" + double16 + "' != '" + (-1.0d) + "'", double16 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double18 + "' != '" + (-1.0d) + "'", double18 == (-1.0d));
    }

    @Test
    public void test4() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test4");
        LC_3 lC_3_0 = new LC_3();
    }

    @Test
    public void test5() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test5");
        int[] intArray3 = new int[] { 'a', (byte) 100, (byte) 10 };
        int[] intArray8 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray9 = new int[] {};
        double double11 = LC_3.findMedianSortedArrays(intArray8, intArray9, "");
        int[] intArray16 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray17 = new int[] {};
        double double19 = LC_3.findMedianSortedArrays(intArray16, intArray17, "");
        double double21 = LC_3.findMedianSortedArrays(intArray8, intArray17, "hi!");
        double double23 = LC_3.findMedianSortedArrays(intArray3, intArray17, "");
        org.junit.Assert.assertNotNull(intArray3);
        org.junit.Assert.assertNotNull(intArray8);
        org.junit.Assert.assertNotNull(intArray9);
        org.junit.Assert.assertTrue("'" + double11 + "' != '" + (-1.0d) + "'", double11 == (-1.0d));
        org.junit.Assert.assertNotNull(intArray16);
        org.junit.Assert.assertNotNull(intArray17);
        org.junit.Assert.assertTrue("'" + double19 + "' != '" + (-1.0d) + "'", double19 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double21 + "' != '" + (-1.0d) + "'", double21 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double23 + "' != '" + 100.0d + "'", double23 == 100.0d);
    }

    @Test
    public void test6() throws Throwable {
        if (debug)
            System.out.format("%n%s%n", "RegressionTest0.test6");
        int[] intArray4 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray5 = new int[] {};
        double double7 = LC_3.findMedianSortedArrays(intArray4, intArray5, "");
        int[] intArray12 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray13 = new int[] {};
        double double15 = LC_3.findMedianSortedArrays(intArray12, intArray13, "");
        double double17 = LC_3.findMedianSortedArrays(intArray4, intArray13, "hi!");
        int[] intArray22 = new int[] { 100, (short) -1, (short) -1, (short) 0 };
        int[] intArray23 = new int[] {};
        double double25 = LC_3.findMedianSortedArrays(intArray22, intArray23, "");
        java.lang.Class<?> wildcardClass26 = intArray23.getClass();
        double double28 = LC_3.findMedianSortedArrays(intArray4, intArray23, "");
        java.lang.Class<?> wildcardClass29 = intArray4.getClass();
        org.junit.Assert.assertNotNull(intArray4);
        org.junit.Assert.assertNotNull(intArray5);
        org.junit.Assert.assertTrue("'" + double7 + "' != '" + (-1.0d) + "'", double7 == (-1.0d));
        org.junit.Assert.assertNotNull(intArray12);
        org.junit.Assert.assertNotNull(intArray13);
        org.junit.Assert.assertTrue("'" + double15 + "' != '" + (-1.0d) + "'", double15 == (-1.0d));
        org.junit.Assert.assertTrue("'" + double17 + "' != '" + (-1.0d) + "'", double17 == (-1.0d));
        org.junit.Assert.assertNotNull(intArray22);
        org.junit.Assert.assertNotNull(intArray23);
        org.junit.Assert.assertTrue("'" + double25 + "' != '" + (-1.0d) + "'", double25 == (-1.0d));
        org.junit.Assert.assertNotNull(wildcardClass26);
        org.junit.Assert.assertTrue("'" + double28 + "' != '" + (-1.0d) + "'", double28 == (-1.0d));
        org.junit.Assert.assertNotNull(wildcardClass29);
    }
}

