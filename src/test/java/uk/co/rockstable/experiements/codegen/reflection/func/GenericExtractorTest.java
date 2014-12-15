package uk.co.rockstable.experiements.codegen.reflection.func;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.MagicExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.ReflectionExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.UnsafeExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.func.domain.DomainObject;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(value = Parameterized.class)
public class GenericExtractorTest {
    private Class<? extends ExtractorFactory> factoryClass;
    private ExtractorFactory factory;

    public GenericExtractorTest(Class factoryClass) {
        this.factoryClass = factoryClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getFactories() {
        return Arrays.asList(new Object[][] {
                {UnsafeExtractorFactory.class},
                {MagicExtractorFactory.class},
                {ReflectionExtractorFactory.class}
        });
    }

    @Before
    public void setUp() throws IllegalAccessException, InstantiationException {
        factory = factoryClass.newInstance();
    }

    @Test
    public void testStringExtractor() {
        String generatedName = RandomStringUtils.randomAlphanumeric(10);
        DomainObject domainObject = new DomainObject(generatedName, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "name");
        String extractedName = extractor.extract(domainObject);

        assertEquals(generatedName, extractedName);
    }

    @Test
    public void testExtractor_nullStillWorks() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "name");
        String extractedName = extractor.extract(domainObject);

        assertEquals(null, extractedName);
    }



    @Test
    public void testExtractor_autoboxingInt() {
        DomainObject domainObject = new DomainObject(null, Integer.MAX_VALUE);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "id");
        Integer extractedId = extractor.extract(domainObject);

        assertEquals((Integer)Integer.MAX_VALUE, extractedId);
    }

    @Test
    public void testExtractor_autoboxingBool() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_boolean");
        Boolean extractedBool = extractor.extract(domainObject);

        assertEquals(true, extractedBool);
    }

    @Test
    public void testExtractor_autoboxingChar() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_char");
        Character extractedChar = extractor.extract(domainObject);

        assertEquals((Character)Character.MAX_VALUE, extractedChar);
    }

    @Test
    public void testExtractor_autoboxingByte() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_byte");
        Byte extractedByte = extractor.extract(domainObject);

        assertEquals((Byte)Byte.MAX_VALUE, extractedByte);
    }

    @Test
    public void testExtractor_autoboxingShort() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_short");
        Short extractedShort = extractor.extract(domainObject);

        assertEquals((Short)Short.MAX_VALUE, extractedShort);
    }

    @Test
    public void testExtractor_autoboxingLong() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_long");
        Long extractedLong = extractor.extract(domainObject);

        assertEquals((Long)Long.MAX_VALUE, extractedLong);
    }

    @Test
    public void testExtractor_autoboxingFloat() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_float");
        Float extractedFloat = extractor.extract(domainObject);

        assertEquals((Float)Float.MAX_VALUE, extractedFloat);
    }

    @Test
    public void testExtractor_autoboxingDouble() {
        DomainObject domainObject = new DomainObject(null, 0);

        Extractor<DomainObject> extractor = factory.create(DomainObject.class, "_double");
        Double extractedDouble = extractor.extract(domainObject);

        assertEquals((Double)Double.MAX_VALUE, extractedDouble);
    }

    @Test
    public void testExtractor_primitiveArraysAreNotSupported() {
        try {
            factory.create(DomainObject.class, "primitiveIntArray");
            if (factoryClass != ReflectionExtractorFactory.class) { //reflection factory supports arrays
                fail();
            }
        } catch (UnsupportedOperationException e) {}
    }

    @Test
    public void testExtractor_objectArraysAreNotSupported() {
        try {
            new MagicExtractorFactory().create(DomainObject.class, "integerArray");
            if (factoryClass != ReflectionExtractorFactory.class) { //reflection factory supports arrays
                fail();
            }
        } catch (UnsupportedOperationException e) { }
    }

}
