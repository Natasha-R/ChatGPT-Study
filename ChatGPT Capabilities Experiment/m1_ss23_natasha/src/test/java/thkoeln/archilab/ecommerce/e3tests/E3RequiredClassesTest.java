package thkoeln.archilab.ecommerce.e3tests;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class E3RequiredClassesTest {


    private static final String[] REQUIRED_CLASSES = {
            "Customer",
            "CustomerRepository",
            "Address",
            "Order",
            "OrderRepository",
            "ShoppingBasket",
            "ShoppingBasketRepository",
            "Thing",
            "ThingRepository"
    };

    @Test
    public void testRequiredClassesDeclared() throws IOException {
        String basePackage = "thkoeln.archilab.ecommerce";
        String packageSearchPath = "classpath*:" + basePackage.replace( '.', '/' ) + "/**/*.class";

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory( resolver );

        Set<String> foundClasses = new HashSet<>();

        Arrays.stream( resolver.getResources( packageSearchPath ) ).forEach( resource -> {
            try {
                MetadataReader reader = readerFactory.getMetadataReader( resource );
                foundClasses.add( reader.getClassMetadata().getClassName() );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } );

        Arrays.stream(REQUIRED_CLASSES).forEach(requiredClass -> {
            boolean found = foundClasses.stream().anyMatch(className ->
                    className.startsWith(basePackage) && className.endsWith("." + requiredClass)
            );
            assertTrue(found, "Expected class not found: " + basePackage + ".*." + requiredClass);
        });
    }
}
