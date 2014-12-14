package uk.co.rockstable.experiements.codegen.reflection;

import org.junit.Test;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.func.domain.DomainObject;

import static org.junit.Assert.*;

public class Example {

    @Test
    public void basicExample() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.MAGIC);

        Extractor nameExtractor = factory.create(DomainObject.class, "name");
        DomainObject domainObject = new DomainObject("hello world", 0);

        String extractedString = (String) nameExtractor.extract(domainObject);
        assertEquals("hello world", extractedString);
    }
}
