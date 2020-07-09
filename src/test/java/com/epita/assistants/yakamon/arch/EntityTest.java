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
public class EntityTest {

    @ArchTest
    public static final ArchRule entityPackageName = classes().that()
                                                              .areAnnotatedWith(Entity.class)
                                                              .should().resideInAPackage("..entity..");
    @ArchTest
    public static final ArchRule entityClassSuffix = classes().that()
                                                              .areAnnotatedWith(Entity.class)
                                                              .should().haveSimpleNameEndingWith("Entity");
    @ArchTest
    public static final ArchRule nonEntityClassSuffix = noClasses().that()
                                                                   .areNotAnnotatedWith(Entity.class)
                                                                   .and().areNotAssignableTo(Entity.class)
                                                                   .should().haveSimpleNameEndingWith("Entity");

    @ArchTest
    public static final ArchRule noReferencesToModels = noClasses().that().areAnnotatedWith(Entity.class).should()
                                                                   .dependOnClassesThat()
                                                                   .areAnnotatedWith(Model.class);

    @ArchTest
    public static final ArchRule noReferencesToDtos = noClasses().that()
                                                                 .areAnnotatedWith(Entity.class)
                                                                 .should()
                                                                 .dependOnClassesThat()
                                                                 .areAnnotatedWith(DtoRequest.class)
                                                                 .orShould()
                                                                 .dependOnClassesThat()
                                                                 .areAnnotatedWith(DtoResponse.class);

}
