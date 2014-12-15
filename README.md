FieldMagic - (Very) Experimental Field Extractor
=======
A hacky approach to private fields values extracting - 0% reflection, 100% running with scissors.

### Example ###
```java
public class DomainObject {
    private final String name;
    private final int id;

    public DomainObject(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
```

```java
public class Example {

    @Test
    public void basicExample() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.MAGIC);
        Extractor nameExtractor = factory.create(DomainObject.class, "name");
        Extractor idExtractor = factory.create(DomainObject.class, "id");

        DomainObject domainObject = new DomainObject("hello world", 1);

        String extractedString = nameExtractor.extract(domainObject);
        Integer extractedId = idExtractor.extract(domainObject);

        assertEquals("hello world", extractedString);
        assertEquals(1, extractedId);
    }
}
```

### Why? ###
But I can! Here are some numbers based in [this](https://github.com/jerrinot/FieldMagic/blob/baeba79327b22fc858880392015ae96d6e55d9d4/src/test/java/uk/co/rockstable/experiements/codegen/reflection/perf/PerformanceTest.java) test:
```
Benchmark                         (type)   Mode  Samples     Score    Error   Units
benchmarkIntegerExtraction        DIRECT  thrpt       40   356.823 ±  3.781  ops/us
benchmarkIntegerExtraction    REFLECTION  thrpt       40   141.894 ±  3.747  ops/us
benchmarkIntegerExtraction         MAGIC  thrpt       40   356.025 ±  1.877  ops/us
benchmarkIntegerExtraction        UNSAFE  thrpt       40   318.227 ±  1.145  ops/us

benchmarkQueryLikeWorkload        DIRECT  thrpt       40  1428.623 ±  8.243   ops/s
benchmarkQueryLikeWorkload    REFLECTION  thrpt       40   511.562 ± 11.229   ops/s
benchmarkQueryLikeWorkload         MAGIC  thrpt       40  1450.806 ± 20.139   ops/s
benchmarkQueryLikeWorkload        UNSAFE  thrpt       40  1078.089 ± 22.838   ops/s

benchmarkStringExtraction         DIRECT  thrpt       40   355.263 ±  5.818  ops/us
benchmarkStringExtraction     REFLECTION  thrpt       40   142.848 ±  2.002  ops/us
benchmarkStringExtraction          MAGIC  thrpt       40   357.490 ±  2.528  ops/us
benchmarkStringExtraction         UNSAFE  thrpt       40   315.061 ±  5.595  ops/us
```
Apparently the Magic method is even faster than Unsafe.getXXX(object, offset). I was quite surprised by this finding as I expected Unsafe to be the fastest method. 


### Known Issues ###
* Primitive values are always boxed to objects. Support for extracting primitive types without boxing is the no. 1 item in my TODO list.
* Arrays are not supported.
* No auto-detection if the the Magic is available.
* Poor test coverage.

### Credits ###
Thanks to [Chris](https://github.com/noctarius) and [Peter](https://github.com/peter-lawrey) for the idea!
