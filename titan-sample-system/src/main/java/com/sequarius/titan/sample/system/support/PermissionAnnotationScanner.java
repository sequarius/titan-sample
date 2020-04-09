package com.sequarius.titan.sample.system.support;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * project erp-bundle
 *
 * @author Sequarius *
 * @since 02/03/2020
 */
public class PermissionAnnotationScanner extends ClassPathBeanDefinitionScanner {

    public PermissionAnnotationScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(RestController.class));
        addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinition : beanDefinitions) {
            System.out.println(beanDefinition.getBeanName());
        }
        return beanDefinitions;
    }

//    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//        return true;
////        return super.isCandidateComponent(beanDefinition) && (beanDefinition.getMetadata()
////                .hasAnnotation(Controller.class.getName())||beanDefinition.getMetadata()
////                .hasAnnotation(RestController.class.getName()));
//    }

}