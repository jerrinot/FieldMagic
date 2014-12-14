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
        DomainObject domainObject = new DomainObject(generatedName, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "name");
        String extractedName = (String) extractor.extract(domainObject);

        assertEquals(generatedName, extractedName);
    }

    @Test
    public void testExtractor_nullStillWorks() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "name");
        String extractedName = (String) extractor.extract(domainObject);

        assertEquals(null, extractedName);
    }



    @Test
    public void testExtractor_autoboxingInt() {
        DomainObject domainObject = new DomainObject(null, Integer.MAX_VALUE);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "id");
        Integer extractedId = (Integer) extractor.extract(domainObject);

        assertEquals((Integer)Integer.MAX_VALUE, extractedId);
    }

    @Test
    public void testExtractor_autoboxingBool() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_boolean");
        Boolean extractedBool = (Boolean) extractor.extract(domainObject);

        assertEquals(true, extractedBool);
    }

    @Test
    public void testExtractor_autoboxingChar() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_char");
        Character extractedChar = (Character) extractor.extract(domainObject);

        assertEquals((Character)Character.MAX_VALUE, extractedChar);
    }

    @Test
    public void testExtractor_autoboxingByte() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_byte");
        Byte extractedByte = (Byte) extractor.extract(domainObject);

        assertEquals((Byte)Byte.MAX_VALUE, extractedByte);
    }

    @Test
    public void testExtractor_autoboxingShort() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_short");
        Short extractedShort = (Short) extractor.extract(domainObject);

        assertEquals((Short)Short.MAX_VALUE, extractedShort);
    }

    @Test
    public void testExtractor_autoboxingLong() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_long");
        Long extractedLong = (Long) extractor.extract(domainObject);

        assertEquals((Long)Long.MAX_VALUE, extractedLong);
    }

    @Test
    public void testExtractor_autoboxingFloat() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_float");
        Float extractedFloat = (Float) extractor.extract(domainObject);

        assertEquals((Float)Float.MAX_VALUE, extractedFloat);
    }

    @Test
    public void testExtractor_autoboxingDouble() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor extractor = new MagicExtractorFactory().create(DomainObject.class, "_double");
        Double extractedDouble = (Double) extractor.extract(domainObject);

        assertEquals((Double)Double.MAX_VALUE, extractedDouble);
    }





}
