package com.epita.assistants.yakamon.arch;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Arch tests.
 *
 * @author dumeig_a (antoine.dumeige@epita.fr)
 * @since 1.0
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.epita")
public class ModelTest {

    @ArchTest
    public static final ArchRule modelPackageName = classes().that()
                                                             .areAnnotatedWith(Model.class)
                                                             .should().resideInAPackage("..model..");
    @ArchTest
    public static final ArchRule modelClassSuffix = classes().that()
                                                             .areAnnotatedWith(Model.class)
                                                             .should().haveSimpleNameEndingWith("Model");
    @ArchTest
    public static final ArchRule nonModelClassSuffix = noClasses().that()
                                                                  .areNotAnnotatedWith(Model.class)
                                                                  .and().areNotAssignableTo(Model.class)
                                                                  .should().haveSimpleNameEndingWith("Model");

    @ArchTest
    public static final ArchRule noReferencesToEntities = noClasses().that().areAnnotatedWith(Model.class).should()
                                                                     .dependOnClassesThat()
                                                                     .areAnnotatedWith(Entity.class);

    @ArchTest
    public static final ArchRule noReferencesToDtos = noClasses().that()
                                                                 .areAnnotatedWith(Model.class)
                                                                 .should()
                                                                 .dependOnClassesThat()
                                                                 .areAnnotatedWith(DtoRequest.class)
                                                                 .orShould()
                                                                 .dependOnClassesThat()
                                                                 .areAnnotatedWith(DtoResponse.class);

}
