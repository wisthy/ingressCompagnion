package be.shoktan.ingressCompagnion.config;

import java.util.Properties;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages={"be.shoktan.ingressCompagnion.bean", "be.shoktan.ingressCompagnion.repository.hibernate"},	excludeFilters={
		@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
public class RootConfig {
	static final Logger logger = LoggerFactory.getLogger(RootConfig.class);
	
//	@Profile(value = "development")
//	@Bean
//	public DataSource embeddedDataSource(){
//		return new EmbeddedDatabaseBuilder()
//		.setType(EmbeddedDatabaseType.H2)
//		.build();
//	}
//	
	@Profile(value = "localhost")
	@Bean
	public DataSource postgressDataSource(){
		org.postgresql.Driver driver = new org.postgresql.Driver();
		String connectionString = "jdbc:postgresql://localhost/ingressCompagnion";
		String login = "ingressComp";
		String password = "ingressCompagnion";
		SimpleDriverDataSource bean = new SimpleDriverDataSource(driver, connectionString, login, password);
		return bean;
	}

	@Profile(value = "localhost")
	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource);
		sfb.setPackagesToScan("be.shoktan.ingressCompagnion.bean");
		Properties props = new Properties();
		props.setProperty("dialect", "org.hibernate.dialect.PostgreSQLDialect");
		sfb.setHibernateProperties(props);
		return sfb;
	}
	
//	@Profile(value = "development")
//	@Bean
//	public LocalSessionFactoryBean sessionFactoryH2(DataSource dataSource){
//		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
//		sfb.setDataSource(dataSource);
//		sfb.setPackagesToScan("be.shoktan.ingressCompagnion.bean");
//		Properties props = new Properties();
//		props.setProperty("dialect", "org.hibernate.H2Dialect");
//		sfb.setHibernateProperties(props);
//		return sfb;
//	}

	@Bean
	public BeanPostProcessor persistenceTranslation(){
		return new PersistenceExceptionTranslationPostProcessor();
	}


	public static class WebPackage extends RegexPatternTypeFilter {
		public WebPackage() {
			super(Pattern.compile("be\\.shoktan\\.ingressCompagnion\\.web"));
		}
	}
}