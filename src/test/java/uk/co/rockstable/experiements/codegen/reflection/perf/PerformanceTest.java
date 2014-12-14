package uk.co.rockstable.experiements.codegen.reflection.perf;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.MagicExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.ReflectionExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.perf.direct.DirectExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.perf.domain.DomainObject;
import uk.co.rockstable.experiements.codegen.reflection.perf.domain.RandomDomainObjectFactory;
import uk.co.rockstable.experiements.codegen.reflection.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class PerformanceTest {
    private static final int NO_OF_OBJECTS = 100_000;

    @Param({"DIRECT", "REFLECTION", "MAGIC"})
    private String type;
    private List<DomainObject> objects;

    private Extractor timestampExtractor;
    private Extractor positionExtractor;
    private Extractor nameExtractor;

    private DomainObject domainObject;

    @Setup
    public void setUp() {
        prepareData();
        createExtractors();

        domainObject = RandomDomainObjectFactory.create();
    }

    @Benchmark
    public int benchmarkQueryLikeWorkload() {
        int hits = 0;
        for (DomainObject o : objects) {
            Long timestamp = timestampExtractor.extract(o);
            Integer position = positionExtractor.extract(o);
            String name = nameExtractor.extract(o);

            if (Utils.isEven(timestamp) && position == 0 && "foo".equals(name)) {
                hits++;
            }
        }
        return hits;
    }

    @Benchmark
    public String benchmarkStringExtraction() {
        return nameExtractor.extract(domainObject);
    }

    @Benchmark
    public Integer benchmarkIntegerExtraction() {
        return positionExtractor.extract(domainObject);
    }

    public static void main(String[] args) throws Exception{
                Options opt = new OptionsBuilder().include(".*" + PerformanceTest.class.getSimpleName() + ".*")
                .warmupIterations(10)
                .measurementIterations(20)
                .threads(1)
                .forks(1)
                .timeUnit(TimeUnit.MICROSECONDS)
                .build();

        new Runner(opt).run();
    }

    private void createExtractors() {
        ExtractorFactory extractorFactory;
        switch (type) {
            case "DIRECT":
                extractorFactory = new DirectExtractorFactory();
                break;
            case "REFLECTION":
                extractorFactory = new ReflectionExtractorFactory();
                break;
            case "MAGIC":
                extractorFactory = new MagicExtractorFactory();
                break;
            default:
                throw new IllegalArgumentException("Unknown extractor factory " + type);
        }

        timestampExtractor = extractorFactory.create(DomainObject.class, "timestamp");
        positionExtractor = extractorFactory.create(DomainObject.class, "position");
        nameExtractor = extractorFactory.create(DomainObject.class, "name");
    }


    private void prepareData() {
        objects = new ArrayList<>(NO_OF_OBJECTS);
        for (int i = 0; i < NO_OF_OBJECTS; i++) {
            objects.add(RandomDomainObjectFactory.create());
        }
    }
}
