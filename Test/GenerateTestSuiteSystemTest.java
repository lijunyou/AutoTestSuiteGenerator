import org.evosuite.*;
import org.evosuite.coverage.CoverageCriteriaAnalyzer;
import org.evosuite.coverage.FitnessFunctions;
import org.evosuite.coverage.TestFitnessFactory;
import org.evosuite.coverage.dataflow.DefUseCoverageSuiteFitness;
import org.evosuite.ga.metaheuristics.GeneticAlgorithm;
import org.evosuite.junit.JUnitAnalyzer;
import org.evosuite.regression.RegressionSuiteMinimizer;
import org.evosuite.result.TestGenerationResult;
import org.evosuite.rmi.ClientServices;
import org.evosuite.rmi.service.ClientState;
import org.evosuite.statistics.RuntimeVariable;
import org.evosuite.statistics.StatisticsSender;
import org.evosuite.strategy.TestGenerationStrategy;
import org.evosuite.symbolic.DSEStats;
import org.evosuite.testcase.ConstantInliner;
import org.evosuite.testcase.TestCase;
import org.evosuite.testcase.TestChromosome;
import org.evosuite.testcase.TestFitnessFunction;
import org.evosuite.testcase.execution.ExecutionResult;
import org.evosuite.testcase.execution.TestCaseExecutor;
import org.evosuite.testsuite.RegressionSuiteSerializer;
import org.evosuite.testsuite.TestSuiteChromosome;
import org.evosuite.testsuite.TestSuiteMinimizer;
import org.evosuite.testsuite.TestSuiteSerialization;
import org.evosuite.utils.ArrayUtil;
import org.evosuite.utils.LoggingUtils;
import org.junit.Assert;
import org.junit.Test;


import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GenerateTestSuiteSystemTest extends SystemTestBase {

    @Test
    public void testLC_4() {
        EvoSuite evosuite = new EvoSuite();
        String targetClass = LC_3.class.getCanonicalName();
//        TestSuiteGenerator ts = new TestSuiteGenerator();
//        ts.generateTestSuite();

        Properties.TARGET_CLASS = targetClass;

        String[] command = new String[] { "-generateTests", "-class", targetClass ,"-projectCP","out/production/LeetCode"};

        Object result = evosuite.parseCommandLine(command);
        GeneticAlgorithm<?> ga = getGAFromResult(result);
        TestSuiteChromosome best = (TestSuiteChromosome) ga.getBestIndividual();
//        postProcessTests(best);

        System.out.println("EvolvedTestSuite:\n" + best);

    }

    protected GeneticAlgorithm<?> getGAFromResult(Object result) {
        assert(result instanceof List);
        List<List<TestGenerationResult>> results = (List<List<TestGenerationResult>>)result;
        assert(results.size() == 1);
        //return results.iterator().next().getGeneticAlgorithm();
        return results.get(0).get(0).getGeneticAlgorithm();
    }

    protected void postProcessTests(TestSuiteChromosome testSuite) {
        testSuite.getTestChromosomes().removeIf((t) -> {
            return t.getLastExecutionResult() != null && (t.getLastExecutionResult().hasTimeout() || t.getLastExecutionResult().hasTestException());
        });
        if (Properties.CTG_SEEDS_FILE_OUT != null) {
            TestSuiteSerialization.saveTests(testSuite, new File(Properties.CTG_SEEDS_FILE_OUT));
        } else if (Properties.TEST_FACTORY == Properties.TestFactory.SERIALIZATION) {
            TestSuiteSerialization.saveTests(testSuite, new File(Properties.SEED_DIR + File.separator + Properties.TARGET_CLASS));
        }

        List<TestFitnessFunction> goals = new ArrayList();
        Iterator var3 = getFitnessFactories().iterator();

        while(var3.hasNext()) {
            TestFitnessFactory<?> ff = (TestFitnessFactory)var3.next();
            goals.addAll(ff.getCoverageGoals());
        }

        var3 = testSuite.getCoveredGoals().iterator();

        while(var3.hasNext()) {
            TestFitnessFunction f = (TestFitnessFunction)var3.next();
            if (!goals.contains(f)) {
                testSuite.removeCoveredGoal(f);
            }
        }

        if (Properties.INLINE) {
            ClientServices.getInstance().getClientNode().changeState(ClientState.INLINING);
            ConstantInliner inliner = new ConstantInliner();
            inliner.inline(testSuite);
        }

        double coverage;
        if (Properties.MINIMIZE) {
            ClientServices.getInstance().getClientNode().changeState(ClientState.MINIMIZATION);
            if (!TimeController.getInstance().hasTimeToExecuteATestCase()) {
                LoggingUtils.getEvoLogger().info("* Skipping minimization because not enough time is left");
                ClientServices.track(RuntimeVariable.Result_Size, testSuite.size());
                ClientServices.track(RuntimeVariable.Minimized_Size, testSuite.size());
                ClientServices.track(RuntimeVariable.Result_Length, testSuite.totalLengthOfTestCases());
                ClientServices.track(RuntimeVariable.Minimized_Length, testSuite.totalLengthOfTestCases());
            } else if (Properties.isRegression()) {
                RegressionSuiteMinimizer minimizer = new RegressionSuiteMinimizer();
                minimizer.minimize(testSuite);
            } else {
                coverage = testSuite.getFitness();
                TestSuiteMinimizer minimizer = new TestSuiteMinimizer(getFitnessFactories());
                LoggingUtils.getEvoLogger().info("* Minimizing test suite");
                minimizer.minimize(testSuite, true);
                double after = testSuite.getFitness();
                if (after > coverage + 0.01D) {
                    throw new Error("EvoSuite bug: minimization lead fitness from " + coverage + " to " + after);
                }
            }
        } else {
            if (!TimeController.getInstance().hasTimeToExecuteATestCase()) {
                LoggingUtils.getEvoLogger().info("* Skipping minimization because not enough time is left");
            }

            ClientServices.track(RuntimeVariable.Result_Size, testSuite.size());
            ClientServices.track(RuntimeVariable.Minimized_Size, testSuite.size());
            ClientServices.track(RuntimeVariable.Result_Length, testSuite.totalLengthOfTestCases());
            ClientServices.track(RuntimeVariable.Minimized_Length, testSuite.totalLengthOfTestCases());
        }

        if (Properties.COVERAGE) {
            ClientServices.getInstance().getClientNode().changeState(ClientState.COVERAGE_ANALYSIS);
            CoverageCriteriaAnalyzer.analyzeCoverage(testSuite);
        }

        coverage = testSuite.getCoverage();
        if (!ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.MUTATION) && ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.STRONGMUTATION)) {
            ;
        }

        StatisticsSender.executedAndThenSendIndividualToMaster(testSuite);
        LoggingUtils.getEvoLogger().info("* Generated " + testSuite.size() + " tests with total length " + testSuite.totalLengthOfTestCases());
        if (!Properties.ANALYSIS_CRITERIA.isEmpty()) {
            CoverageCriteriaAnalyzer.analyzeCriteria(testSuite, Properties.ANALYSIS_CRITERIA);
        }

        if (Properties.CRITERION.length > 1) {
            LoggingUtils.getEvoLogger().info("* Resulting test suite's coverage: " + NumberFormat.getPercentInstance().format(coverage) + " (average coverage for all fitness functions)");
        } else {
            LoggingUtils.getEvoLogger().info("* Resulting test suite's coverage: " + NumberFormat.getPercentInstance().format(coverage));
        }

        if (ArrayUtil.contains(Properties.CRITERION, Properties.Criterion.DEFUSE) && Properties.ANALYSIS_CRITERIA.isEmpty()) {
            DefUseCoverageSuiteFitness.printCoverage();
        }

        DSEStats.getInstance().trackConstraintTypes();
        DSEStats.getInstance().trackSolverStatistics();
        if (Properties.DSE_PROBABILITY > 0.0D && Properties.LOCAL_SEARCH_RATE > 0 && Properties.LOCAL_SEARCH_PROBABILITY > 0.0D) {
            DSEStats.getInstance().logStatistics();
        }

        if (Properties.FILTER_SANDBOX_TESTS) {
            Iterator var13 = testSuite.getTestChromosomes().iterator();

            while(var13.hasNext()) {
                TestChromosome test = (TestChromosome)var13.next();
                ExecutionResult result = test.getLastExecutionResult();
                if (result == null) {
                    result = TestCaseExecutor.runTest(test.getTestCase());
                }

                if (result.hasSecurityException()) {
                    int position = result.getFirstPositionOfThrownException();
                    if (position > 0) {
                        test.getTestCase().chop(position);
                        result = TestCaseExecutor.runTest(test.getTestCase());
                        test.setLastExecutionResult(result);
                    }
                }
            }
        }

        if (Properties.ASSERTIONS && !Properties.isRegression()) {
            LoggingUtils.getEvoLogger().info("* Generating assertions");
            ClientServices.getInstance().getClientNode().changeState(ClientState.ASSERTION_GENERATION);
            if (!TimeController.getInstance().hasTimeToExecuteATestCase()) {
                LoggingUtils.getEvoLogger().info("* Skipping assertion generation because not enough time is left");
            } else {
                TestSuiteGeneratorHelper.addAssertions(testSuite);
            }

            StatisticsSender.sendIndividualToMaster(testSuite);
        }

        if (Properties.NO_RUNTIME_DEPENDENCY) {
            LoggingUtils.getEvoLogger().info("* Property NO_RUNTIME_DEPENDENCY is set to true - skipping JUnit compile check");
            LoggingUtils.getEvoLogger().info("* WARNING: Not including the runtime dependencies is likely to lead to flaky tests!");
        } else if (Properties.JUNIT_TESTS && Properties.JUNIT_CHECK) {
            compileAndCheckTests(testSuite);
        }

        if (Properties.SERIALIZE_REGRESSION_TEST_SUITE) {
            RegressionSuiteSerializer.appendToRegressionTestSuite(testSuite);
        }

        if (Properties.isRegression() && Properties.KEEP_REGRESSION_ARCHIVE) {
            RegressionSuiteSerializer.storeRegressionArchive();
        }

    }

    public static List<TestFitnessFactory<? extends TestFitnessFunction>> getFitnessFactories() {
        List<TestFitnessFactory<? extends TestFitnessFunction>> goalsFactory = new ArrayList();

        for(int i = 0; i < Properties.CRITERION.length; ++i) {
            goalsFactory.add(FitnessFunctions.getFitnessFactory(Properties.CRITERION[i]));
        }

        return goalsFactory;
    }

    private void compileAndCheckTests(TestSuiteChromosome chromosome) {
        LoggingUtils.getEvoLogger().info("* Compiling and checking tests");
        if (!JUnitAnalyzer.isJavaCompilerAvailable()) {
            String msg = "No Java compiler is available. Make sure to run EvoSuite with the JDK and not the JRE.You can try to setup the JAVA_HOME system variable to point to it, as well as to make sure that the PATH variable points to the JDK before any JRE.";
            throw new RuntimeException(msg);
        } else {
            ClientServices.getInstance().getClientNode().changeState(ClientState.JUNIT_CHECK);
            boolean junitSeparateClassLoader = Properties.USE_SEPARATE_CLASSLOADER;
            Properties.USE_SEPARATE_CLASSLOADER = false;
            int numUnstable = 0;
            if (!TimeController.getInstance().isThereStillTimeInThisPhase()) {
                Properties.USE_SEPARATE_CLASSLOADER = junitSeparateClassLoader;
            } else {
                List<TestCase> testCases = chromosome.getTests();
                JUnitAnalyzer.removeTestsThatDoNotCompile(testCases);
                long start = System.currentTimeMillis();
                Iterator iter = testCases.iterator();

                while(iter.hasNext() && TimeController.getInstance().hasTimeToExecuteATestCase()) {
                    TestCase tc = (TestCase)iter.next();
                    List<TestCase> list = new ArrayList();
                    list.add(tc);
                    numUnstable += JUnitAnalyzer.handleTestsThatAreUnstable(list);
                    if (list.isEmpty()) {
                        iter.remove();
                    }
                }

                long delta = System.currentTimeMillis() - start;
                numUnstable += checkAllTestsIfTime(testCases, delta);
                if (testCases.size() > 1) {
                    Collections.reverse(testCases);
                    numUnstable += checkAllTestsIfTime(testCases, delta);
                }

                chromosome.clearTests();
                Iterator var10 = testCases.iterator();

                while(var10.hasNext()) {
                    TestCase testCase = (TestCase)var10.next();
                    chromosome.addTest(testCase);
                }

                boolean unstable = numUnstable > 0;
                if (!TimeController.getInstance().isThereStillTimeInThisPhase()) {
                    ;
                }

                ClientServices.track(RuntimeVariable.HadUnstableTests, unstable);
                ClientServices.track(RuntimeVariable.NumUnstableTests, numUnstable);
                Properties.USE_SEPARATE_CLASSLOADER = junitSeparateClassLoader;
            }
        }
    }

    private static int checkAllTestsIfTime(List<TestCase> testCases, long delta) {
        return TimeController.getInstance().hasTimeToExecuteATestCase() && TimeController.getInstance().isThereStillTimeInThisPhase(delta) ? JUnitAnalyzer.handleTestsThatAreUnstable(testCases) : 0;
    }

}