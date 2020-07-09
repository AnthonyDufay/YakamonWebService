package com.epita.assistants.yakamon.arch;

import com.epita.assistants.yakamon.Yakamon;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.type;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * Arch tests.
 *
 * @author dumeig_a (antoine.dumeige@epita.fr)
 * @since 1.0
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.epita")
public class LayersTest {

    @ArchTest
    public static final ArchRule layers = layeredArchitecture()
            .layer("Main").definedBy(type(Yakamon.class))
            .layer("Controller").definedBy(annotatedWith(Controller.class))
            .layer("Service").definedBy(annotatedWith(Service.class))
            .layer("Persistence").definedBy(annotatedWith(Repository.class))
            .layer("DTO").definedBy(annotatedWith(DtoResponse.class).or(annotatedWith(DtoRequest.class)))
            .layer("Model").definedBy(annotatedWith(Model.class))
            .layer("Entity").definedBy(annotatedWith(Entity.class))
            .layer("Tests").definedBy(annotatedWith(TestSuite.class))

            .whereLayer("Controller").mayOnlyBeAccessedByLayers("Main")
            .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service", "Main")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "Persistence", "Main")
            .whereLayer("DTO").mayOnlyBeAccessedByLayers("Controller", "Tests")
            .whereLayer("Entity").mayOnlyBeAccessedByLayers("Controller", "Service")
            .whereLayer("Model").mayOnlyBeAccessedByLayers("Persistence", "Service");

}
