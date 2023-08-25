package thkoeln.archilab.bauzeichner20.dddtests;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.domain.PackageMatchers;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTag;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;

import static com.tngtech.archunit.core.domain.JavaClass.Functions.GET_PACKAGE_NAME;
import static com.tngtech.archunit.core.domain.JavaMember.Predicates.declaredIn;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@ArchTag("layerRules")
@AnalyzeClasses(packages = "thkoeln.archilab.bauzeichner20.solution")
public class E3DDDRulesTest {

    @ArchTest
    static final ArchRule domainClassesMayOnlyCallOtherDomainClasses =
            classes().that().resideInAPackage("..domain..")
                    .should().onlyCallMethodsThat( areDeclaredInDomainLayer());

    @ArchTest
    static final ArchRule entitiesMustResideInADomainPackage =
            classes().that().areAnnotatedWith( Entity.class ).should().resideInAPackage("..domain..")
                    .as("Entities must reside in a package '..domain..'");

    @ArchTest
    static final ArchRule repositoriesMustResideInADomainPackage =
            classes().that().areAssignableTo( CrudRepository.class ).should().resideInAPackage("..domain..")
                    .as("Repositories must reside in a package '..domain..'");

    @ArchTest
    static final ArchRule repositoryNamesMustHaveProperSuffix =
            classes().that().areAssignableTo( CrudRepository.class )
                    .should().haveSimpleNameEndingWith("Repository");

    @ArchTest
    static final ArchRule servicesMustResideInAnApplicationPackage =
            classes().that().areAnnotatedWith( Service.class ).should().resideInAPackage("..application..")
                    .as("Application Services must reside in a package '..application..'");

    @ArchTest
    static final ArchRule serviceNamesMustHaveProperSuffix =
            classes().that().areAnnotatedWith( Service.class )
                    .should().haveSimpleNameEndingWith("Service");


    private static DescribedPredicate<JavaMember> areDeclaredInDomainLayer() {
        DescribedPredicate<JavaClass> aPackageController = GET_PACKAGE_NAME.is(PackageMatchers.of("..domain..", "java.."))
                .as("a package '..domain..'");
        return are(declaredIn(aPackageController));
    }

}
