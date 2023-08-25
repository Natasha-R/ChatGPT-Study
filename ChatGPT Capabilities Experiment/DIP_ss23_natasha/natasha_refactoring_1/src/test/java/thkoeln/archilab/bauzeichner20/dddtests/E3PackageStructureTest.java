package thkoeln.archilab.bauzeichner20.dddtests;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class E3PackageStructureTest {

    private static final String[] REQUIRED_PACKAGES = {"canvas", "drawingelement"};
    private static final String BASE_PACKAGE = "thkoeln.archilab.bauzeichner20.solution";
    private JavaClasses importedClasses;


    @BeforeEach
    public void setUp() {
        importedClasses = new ClassFileImporter().importPackages( BASE_PACKAGE );
    }

    @Test
    void testRequiredPackagesAreThere() {
        for ( String requiredPackage : REQUIRED_PACKAGES ) {
            String packageName = BASE_PACKAGE + "." + requiredPackage;
            assertThat( importedClasses.containPackage( packageName ) )
                    .as( "Expected package %s not found!", packageName )
                    .isTrue();
        }
    }

    @Test
    void testRequiredDomainPackagesAreThere() {
        for ( String requiredPackage : REQUIRED_PACKAGES ) {
            String packageName = BASE_PACKAGE + "." + requiredPackage + ".domain";
            assertThat( importedClasses.containPackage( packageName ) )
                    .as( "Expected domain sub-package %s not found!", packageName )
                    .isTrue();
        }
    }

    @Test
    void testRequiredApplicationPackagesAreThere() {
        for ( String requiredPackage : REQUIRED_PACKAGES ) {
            String packageName = BASE_PACKAGE + "." + requiredPackage + ".application";
            assertThat( importedClasses.containPackage( packageName ) )
                    .as( "Expected application sub-package %s not found!", packageName )
                    .isTrue();
        }
    }

}
