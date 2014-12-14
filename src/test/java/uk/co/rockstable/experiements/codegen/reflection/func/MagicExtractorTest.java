package uk.co.rockstable.experiements.codegen.reflection.func;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.MagicExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.func.domain.DomainObject;

import static org.junit.Assert.*;

public class MagicExtractorTest {

    @Test
    public void testStringExtractor() {
        String generatedName = RandomStringUtils.randomAlphanumeric(10);
        DomainObject domainObject = new DomainObject(generatedName);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "name");
        String extractedName = (String) extractor.extract(domainObject);

        assertEquals(generatedName, extractedName);
    }

    @Test
    public void testExtractor_nullStillWorks() {
        DomainObject domainObject = new DomainObject(null);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "name");
        String extractedName = (String) extractor.extract(domainObject);

        assertEquals(null, extractedName);
    }


}
