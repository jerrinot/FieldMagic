FieldMagic - (Very) Experimental Field Extractor
=======
A hacky approach to private fields values extracting - 0% reflection, 100% running with scissors.

### Example ###
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

### Known Issues ###
* Primitive values cannot be extracted. That's no. 1 item in my TODO list
* Error-handling is virtually non-existing
* Poor test coverage

### Credits ###
Thanks to [Chris](https://github.com/noctarius) and [Peter](https://github.com/peter-lawrey) for the idea!
