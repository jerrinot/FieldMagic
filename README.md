codegen - Experimental Field Extractor
=======
A hacky approach to field values extracting.

```
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