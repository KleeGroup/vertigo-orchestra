package io.vertigo.orchestra;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.database.SqlDataBaseManager;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.SqlDataBaseManagerImpl;
import io.vertigo.dynamo.impl.database.vendor.postgresql.PostgreSqlDataBase;
import io.vertigo.dynamo.plugins.database.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.task.TaskDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.store.datastore.postgresql.PostgreSqlDataStorePlugin;
import io.vertigo.orchestra.monitoring.MonitoringServices;
import io.vertigo.orchestra.monitoring.MonitoringServicesImpl;

public final class MyAppConfig {
	private static AppConfigBuilder createAppConfigBuilder() {
		// @formatter:off
		return new AppConfigBuilder().beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(KprLoaderPlugin.class)
				.addPlugin(AnnotationLoaderPlugin.class)
				.addPlugin(DomainDynamicRegistryPlugin.class)
				.addPlugin(TaskDynamicRegistryPlugin.class)
				.silently()
			.endBoot()
			.addModule(new CommonsFeatures()
				.withCache(MemoryCachePlugin.class)
				.withScript()
				.build())
			.addModule(new DynamoFeatures()
				.withStore()
				.getModuleConfigBuilder()
				.addComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
				.beginPlugin(C3p0ConnectionProviderPlugin.class)
					.addParam("name", "orchestra")
					.addParam("dataBaseClass", PostgreSqlDataBase.class.getName())
					.addParam("jdbcDriver", org.postgresql.Driver.class.getName())
					.addParam("jdbcUrl", "jdbc:postgresql://localhost:5432/orchestra?user=orchestra&password=orchestra")
				.endPlugin()
				.beginPlugin(PostgreSqlDataStorePlugin.class)
					.addParam("name", "orchestra")
					.addParam("connectionName", "orchestra")
					.addParam("sequencePrefix","SEQ_")
				.endPlugin()
//				.addPlugin(LuceneIndexPlugin.class)
				.build())
			//.beginModule(PersonaFeatures.class).withUserSession(TestUserSession.class).endModule()
			//.beginModule(VegaFeatures.class).withEmbeddedServer(8080).endModule()
			.addModule(new OrchestraFeatures().build())
			.addModule(new ModuleConfigBuilder("orchestra-test")
					//---Services
					.addComponent(MonitoringServices.class, MonitoringServicesImpl.class)
					.build());
		// @formatter:on
	}

	public static AppConfig config() {
		// @formatter:off
		return createAppConfigBuilder().build();
	}



}
