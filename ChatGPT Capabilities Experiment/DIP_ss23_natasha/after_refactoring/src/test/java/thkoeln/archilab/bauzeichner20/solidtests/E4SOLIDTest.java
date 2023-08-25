package thkoeln.archilab.bauzeichner20.solidtests;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.SliceAssignment;
import com.tngtech.archunit.library.dependencies.SliceIdentifier;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class E4SOLIDTest {
    private JavaClasses importedClasses = new ClassFileImporter().importPackages("thkoeln.archilab.bauzeichner20.solution");

    private SliceAssignment inAllClasses() {
        return new SliceAssignment() {
            @Override
            public String getDescription() {
                return "every class in it's own slice";
            }

            @Override
            public SliceIdentifier getIdentifierOf(JavaClass javaClass) {
                return SliceIdentifier.of("In-All-Classes", javaClass.getPackageName(), javaClass.getName());
            }
        };
    }

    @Test
    public void testNoCyclicDependenciesBetweenPackages() {
        ArchRule rule = slices().matching("..solution.(*)..").should().beFreeOfCycles();
        rule.check(importedClasses);
    }
}
