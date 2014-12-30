package be.shoktan.ingressCompagnion.config;

import java.io.IOException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableTransactionManagement
@ComponentScan(excludeFilters={
		@Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)})
public class RepositoryTestConfig {
	static final Logger logger = LoggerFactory.getLogger(RepositoryTestConfig.class);
	
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder edb = new EmbeddedDatabaseBuilder();
		edb.setType(EmbeddedDatabaseType.H2);
		edb.addScript("schema.sql");
		edb.addScript("test-data.sql");
		EmbeddedDatabase embeddedDatabase = edb.build();
		return embeddedDatabase;
	}
	
	@Bean
	public SessionFactory sessionFactoryBean() {
		try {
			LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
			lsfb.setDataSource(dataSource());
			lsfb.setPackagesToScan("be.shoktan.ingressCompagnion.bean");
			Properties props = new Properties();
			props.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
			//props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
			lsfb.setHibernateProperties(props);
			lsfb.afterPropertiesSet();
			SessionFactory object = lsfb.getObject();
			return object;
		} catch (IOException e) {
			return null;
		}
	}
}
