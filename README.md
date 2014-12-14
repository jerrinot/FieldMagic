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

        String extractedString = (String) nameExtractor.extract(domainObject);
        Integer extractedId = (Integer) idExtractor.extract(domainObject);

        assertEquals("hello world", extractedString);
        assertEquals(1, extractedId);
    }
}
```

### Known Issues ###
* Primitive values are always autoboxed. Support for extracting primitive type without boxing is the no. 1 item in my TODO list
* No auto-detection if the the Magic is available.
* Poor test coverage

### Credits ###
Thanks to [Chris](https://github.com/noctarius) and [Peter](https://github.com/peter-lawrey) for the idea!
