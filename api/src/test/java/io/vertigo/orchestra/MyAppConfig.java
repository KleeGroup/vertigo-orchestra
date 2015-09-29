package io.vertigo.orchestra;

import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.commons.plugins.resource.java.ClassPathResourceResolverPlugin;
import io.vertigo.core.config.AppConfig;
import io.vertigo.core.config.AppConfigBuilder;
import io.vertigo.core.environment.EnvironmentManager;
import io.vertigo.core.impl.environment.EnvironmentManagerImpl;
import io.vertigo.core.impl.locale.LocaleManagerImpl;
import io.vertigo.core.impl.resource.ResourceManagerImpl;
import io.vertigo.core.locale.LocaleManager;
import io.vertigo.core.resource.ResourceManager;
import io.vertigo.dynamo.database.SqlDataBaseManager;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.SqlDataBaseManagerImpl;
import io.vertigo.dynamo.impl.database.vendor.postgresql.PostgreSqlDataBase;
import io.vertigo.dynamo.impl.transaction.VTransactionAspect;
import io.vertigo.dynamo.plugins.database.connection.mock.MockConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.java.AnnotationLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.loaders.kpr.KprLoaderPlugin;
import io.vertigo.dynamo.plugins.environment.registries.domain.DomainDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.environment.registries.task.TaskDynamicRegistryPlugin;
import io.vertigo.dynamo.plugins.store.datastore.postgresql.PostgreSqlDataStorePlugin;

public final class MyAppConfig {
	@SuppressWarnings("deprecation")
	private static AppConfigBuilder createAppConfigBuilder() {
		// @formatter:off
		return new AppConfigBuilder()
			.beginBootModule()
				.beginComponent(LocaleManager.class, LocaleManagerImpl.class)
					.addParam("locales", "fr")
				.endComponent()
				.addComponent(ResourceManager.class, ResourceManagerImpl.class)
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addComponent(EnvironmentManager.class, EnvironmentManagerImpl.class)
				.addPlugin(KprLoaderPlugin.class)
				.addPlugin(AnnotationLoaderPlugin.class)
				.addPlugin(DomainDynamicRegistryPlugin.class)
				.addPlugin(TaskDynamicRegistryPlugin.class)
			.endModule()
			.beginBoot()
				.silently()
			.endBoot()
			.beginModule(CommonsFeatures.class)
				.withCache(MemoryCachePlugin.class)
				.withScript()
			.endModule()
			.beginModule(DynamoFeatures.class)
				.withStore()
				.getModuleConfigBuilder()
				.addComponent(SqlDataBaseManager.class, SqlDataBaseManagerImpl.class)
				.beginPlugin(MockConnectionProviderPlugin.class)
					.addParam("dataBaseClass", PostgreSqlDataBase.class.getName())
					.addParam("jdbcDriver", org.postgresql.Driver.class.getName())
					.addParam("jdbcUrl", "jdbc:postgresql://mlaroche-port:5432/orchestra?user=orchestra&password=orchestra")
				.endPlugin()
				.beginPlugin(PostgreSqlDataStorePlugin.class)
					.addParam("sequencePrefix","SEQ_")
				.endPlugin()
				.addAspect(VTransactionAspect.class)
//				.addPlugin(LuceneIndexPlugin.class)
				.endModule()
			//.beginModule(PersonaFeatures.class).withUserSession(TestUserSession.class).endModule()
			//.beginModule(VegaFeatures.class).withEmbeddedServer(8080).endModule()
			.beginModule(OrchestraFeatures.class).endModule();
		// @formatter:on
	}

	public static AppConfig config() {
		// @formatter:off
		return createAppConfigBuilder().build();
	}



}
