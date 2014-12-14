package uk.co.rockstable.experiements.codegen.reflection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.profile.LinuxPerfProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;

@State(Scope.Benchmark)
public class ReflectionTest {
    private static final int NO_OF_OBJECTS = 1_000_000;


    private List<DomainObject> objects;

    @Setup
    public void setUp() {
        objects = new ArrayList<>(NO_OF_OBJECTS);
        for (int i = 0; i < NO_OF_OBJECTS; i++) {
            objects.add(RandomDomainObjectFactory.create());
        }
    }

    @Benchmark
    public int directMatcher() {
        int hits = 0;
        Matcher<DomainObject> matcher = new DirectMatcher();
        for (DomainObject o : objects) {
            if (matcher.isMatching(o)) {
                hits++;
            }
        }
        return hits;
    }

    @Benchmark
    public int cachingReflectionMatcher() {
        int hits = 0;
        Matcher<DomainObject> matcher = new CachingReflectionMatcher();
        for (DomainObject o : objects) {
            if (matcher.isMatching(o)) {
                hits++;
            }
        }
        return hits;
    }

    @Benchmark
    public int naiveReflectionMatcher() {
        int hits = 0;
        Matcher<DomainObject> matcher = new NaiveReflectionMatcher();
        for (DomainObject o : objects) {
            if (matcher.isMatching(o)) {
                hits++;
            }
        }
        return hits;
    }

    public static void main(String[] args) throws Exception{
                Options opt = new OptionsBuilder().include(".*" + ReflectionTest.class.getSimpleName() + ".*")
                .warmupIterations(10)
//                .warmupTime(TimeValue.seconds(10))
                .measurementIterations(20)
//                .timeUnit(TimeUnit.MILLISECONDS)
                .threads(1)
                .forks(1)
//                .jvmArgsPrepend("-XX:+UnlockDiagnosticVMOptions", "-XX:+TraceClassLoading", "-XX:+LogCompilation", "-XX:+PrintAssembly")
                .addProfiler(LinuxPerfProfiler.class)
                .build();

        new Runner(opt).run();
    }
}
