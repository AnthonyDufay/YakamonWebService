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
public class DtoTest {

    @ArchTest
    public static final ArchRule dtoPackageName = classes().that()
                                                           .areAnnotatedWith(DtoRequest.class)
                                                           .or()
                                                           .areAnnotatedWith(DtoResponse.class)
                                                           .should().resideInAPackage("..controller..");

    @ArchTest
    public static final ArchRule dtoRequestClassSuffix = classes().that()
                                                                  .areAnnotatedWith(DtoRequest.class)
                                                                  .and()
                                                                  .areNotAssignableTo(DtoRequest.class)
                                                                  .should()
                                                                  .haveSimpleNameEndingWith("Request");

    @ArchTest
    public static final ArchRule dtoResponseClassSuffix = classes().that()
                                                                   .areAnnotatedWith(DtoResponse.class)
                                                                   .should()
                                                                   .haveSimpleNameEndingWith("Response");

    @ArchTest
    public static final ArchRule nonDtoResponseClassSuffix = noClasses().that().areNotAnnotatedWith(DtoResponse.class)
                                                                        .and().areNotAssignableTo(DtoResponse.class)
                                                                        .should().haveSimpleNameEndingWith("Response");

    @ArchTest
    public static final ArchRule nonDtoRequestClassSuffix = noClasses().that().areNotAnnotatedWith(DtoRequest.class)
                                                                       .and().areNotAssignableTo(DtoRequest.class)
                                                                       .should().haveSimpleNameEndingWith("Request");

    @ArchTest
    public static final ArchRule noReferencesToEntities = noClasses().that()
                                                                     .areAnnotatedWith(DtoRequest.class)
                                                                     .or()
                                                                     .areAnnotatedWith(DtoResponse.class)
                                                                     .should()
                                                                     .dependOnClassesThat()
                                                                     .areAnnotatedWith(Entity.class);
    @ArchTest
    public static final ArchRule noReferencesToModels = noClasses().that()
                                                                   .areAnnotatedWith(DtoRequest.class)
                                                                   .or()
                                                                   .areAnnotatedWith(DtoResponse.class)
                                                                   .should()
                                                                   .dependOnClassesThat()
                                                                   .areAnnotatedWith(Model.class);


}
