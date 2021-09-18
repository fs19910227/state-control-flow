package com.doubao.statemachine.spring.config;

import java.util.Set;

import com.doubao.statemachine.spring.annotation.StateMachineScan;
import com.doubao.statemachine.extend.annotation.MachineConfig;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * scan config package and registry
 *
 * @author doubao
 * @date 2021/4/30
 */
public class ConfigRegistrar implements ImportBeanDefinitionRegistrar {

	private static class Scanner extends ClassPathBeanDefinitionScanner {
		public Scanner(BeanDefinitionRegistry registry) {
			super(registry);
			this.addIncludeFilter(new AnnotationTypeFilter(MachineConfig.class));
		}

		@Override
		protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
			Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
			return beanDefinitionHolders;
		}
	}


	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
			importingClassMetadata.getAnnotationAttributes(StateMachineScan.class.getName()));
		String[] packagePaths = annotationAttributes.getStringArray("value");
		Scanner scanner = new Scanner(registry);
		scanner.scan(packagePaths);
	}

}
