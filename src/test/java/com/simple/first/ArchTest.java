package com.simple.first;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.simple.first");

        noClasses()
            .that()
                .resideInAnyPackage("com.simple.first.service..")
            .or()
                .resideInAnyPackage("com.simple.first.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.simple.first.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
