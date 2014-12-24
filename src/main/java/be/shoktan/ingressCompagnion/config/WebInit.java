package be.shoktan.ingressCompagnion.config;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

//public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getRootConfigClasses()
//	 */
//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return new Class<?>[] { RootConfig.class };
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer#getServletConfigClasses()
//	 */
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class<?>[] { WebConfig.class };
//	}
//	
//	/*
//	 * (non-Javadoc)
//	 * @see org.springframework.web.servlet.support.AbstractDispatcherServletInitializer#getServletMappings()
//	 */
//	@Override
//	protected String[] getServletMappings() {
//		return new String[] { "/" };
//	}
//}

public class WebInit extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}
}