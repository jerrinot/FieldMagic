codegen - Experimental Field Extractor
=======
A hacky approach to field values extracting.

```java
public class DomainObject {
    private final String name;

    public DomainObject(String name) {
        this.name = name;
    }
}
```

```java
public class Example {

    @Test
    public void basicExample() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.MAGIC);

        Extractor nameExtractor = factory.create(DomainObject.class, "name");
        DomainObject domainObject = new DomainObject("hello world");

        String extractedString = (String) nameExtractor.extract(domainObject);
        assertEquals("hello world", extractedString);
    }
}
```
