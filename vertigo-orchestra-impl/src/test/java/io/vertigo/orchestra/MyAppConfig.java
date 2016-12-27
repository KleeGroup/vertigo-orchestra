package io.vertigo.orchestra;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.vendor.h2.H2Database;
import io.vertigo.dynamo.plugins.database.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.task.TaskDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.store.datastore.postgresql.PostgreSqlDataStorePlugin;
import io.vertigo.orchestra.boot.DataBaseInitializer;
import io.vertigo.orchestra.monitoring.MonitoringServices;
import io.vertigo.orchestra.monitoring.MonitoringServicesImpl;

public final class MyAppConfig {
	private static AppConfigBuilder createAppConfigBuilder() {
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
						.addDataStorePlugin(PostgreSqlDataStorePlugin.class,
								Param.create("name", "orchestra"),
								Param.create("connectionName", "orchestra"),
								Param.create("sequencePrefix", "SEQ_"))
						.withSqlDataBase()
						.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
								Param.create("name", "orchestra"),
								Param.create("dataBaseClass", H2Database.class.getName()),
								Param.create("jdbcDriver", org.h2.Driver.class.getName()),
								Param.create("jdbcUrl", "jdbc:h2:mem:database"))
						.build())
				// we build h2 mem
				.addModule(new ModuleConfigBuilder("databaseInitializer").withNoAPI().addComponent(DataBaseInitializer.class).build())
				//
				.addModule(new OrchestraFeatures()
						.withDataBase("NODE_TEST_1", 1, 3, 60)
						.withMemory(1)
						.build())
				.addModule(new ModuleConfigBuilder("orchestra-test")
						//---Services
						.addComponent(MonitoringServices.class, MonitoringServicesImpl.class)
						.build());
	}

	public static AppConfig config() {
		// @formatter:off
		return createAppConfigBuilder().build();
	}



}
