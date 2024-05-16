package com.theodoro.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

@Sql({ "/cleanup.sql", "/dataset.sql"})
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnabledIf(expression = "#{environment['spring.profiles.active'] == null}")
public class ApplicationTests<T extends ApplicationTests<?>> {

    private final YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();

	@Autowired
	protected MockMvc mockMvc;

	protected ApplicationTests() {
		super();
        T controllerTest = (T) this;
        yamlProperties.setResources(
				new ClassPathResource("scenarios/controllers/" + controllerTest.getClass().getSimpleName() + ".yml"));
	}

	protected String getScenarioBody(final String scenario) {
		return Objects.requireNonNull(yamlProperties.getObject()).getProperty(scenario + ".body");
	}

}
