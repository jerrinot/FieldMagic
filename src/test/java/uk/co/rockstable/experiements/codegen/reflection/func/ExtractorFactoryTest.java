package uk.co.rockstable.experiements.codegen.reflection.func;

import org.junit.Test;
import uk.co.rockstable.experiements.codegen.reflection.extractors.Extractor;
import uk.co.rockstable.experiements.codegen.reflection.extractors.ExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.MagicExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.ReflectionExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.extractors.impl.UnsafeExtractorFactory;
import uk.co.rockstable.experiements.codegen.reflection.func.domain.DomainObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ExtractorFactoryTest {

    @Test
    public void testNewInstance_magic() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.MAGIC, false);
        assertEquals(MagicExtractorFactory.class, factory.getClass());
    }

    @Test
    public void testNewInstance_reflection() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.REFLECTION, false);
        assertEquals(ReflectionExtractorFactory.class, factory.getClass());
    }

    @Test
    public void testNewInstance_unsafe() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.UNSAFE, false);
        assertEquals(UnsafeExtractorFactory.class, factory.getClass());
    }

    @Test
    public void testNewInstance_cachingEnabledByDefault() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.REFLECTION);
        Extractor firstNameExtractor = factory.create(DomainObject.class, "name");
        Extractor secondNameExtractor = factory.create(DomainObject.class, "name");

        assertSame(firstNameExtractor, secondNameExtractor);
    }

    @Test
    public void testNewInstance_cachingDisabled() {
        ExtractorFactory factory = ExtractorFactory.newInstance(ExtractorFactory.Type.REFLECTION, false);
        Extractor firstNameExtractor = factory.create(DomainObject.class, "name");
        Extractor secondNameExtractor = factory.create(DomainObject.class, "name");

        assertNotSame(firstNameExtractor, secondNameExtractor);
    }
}
