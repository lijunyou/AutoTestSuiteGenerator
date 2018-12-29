import org.junit.Test;
import randoop.*;
import randoop.main.GenTests;
import randoop.main.Main;

public class RandoopTest
{
    @Test
    public void testLeetCode()
    {
        Main a = new Main();
        String[] args = {"gentests","--testclass=LC_3","--output-limit=10","--time-limit=60"};
        a.nonStaticMain(args);
    }

}
