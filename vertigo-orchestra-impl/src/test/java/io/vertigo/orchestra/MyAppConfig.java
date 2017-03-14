package io.vertigo.orchestra;

import io.vertigo.app.config.AppConfig;
import io.vertigo.app.config.AppConfigBuilder;
import io.vertigo.app.config.ModuleConfigBuilder;
import io.vertigo.commons.impl.CommonsFeatures;
import io.vertigo.commons.plugins.cache.memory.MemoryCachePlugin;
import io.vertigo.core.param.Param;
import io.vertigo.core.plugins.resource.classpath.ClassPathResourceResolverPlugin;
import io.vertigo.core.plugins.resource.url.URLResourceResolverPlugin;
import io.vertigo.dynamo.impl.DynamoFeatures;
import io.vertigo.dynamo.impl.database.vendor.h2.H2DataBase;
import io.vertigo.dynamo.plugins.database.connection.c3p0.C3p0ConnectionProviderPlugin;
import io.vertigo.dynamo.plugins.kvstore.delayedmemory.DelayedMemoryKVStorePlugin;
import io.vertigo.dynamo.plugins.store.datastore.sql.SqlDataStorePlugin;
import io.vertigo.orchestra.boot.DataBaseInitializer;
import io.vertigo.orchestra.util.monitoring.MonitoringServices;
import io.vertigo.orchestra.util.monitoring.MonitoringServicesImpl;
import io.vertigo.orchestra.webservices.WsDefinition;
import io.vertigo.orchestra.webservices.WsExecution;
import io.vertigo.orchestra.webservices.WsExecutionControl;
import io.vertigo.orchestra.webservices.data.user.TestUserSession;
import io.vertigo.orchestra.webservices.data.user.WsTestLogin;
import io.vertigo.persona.impl.security.PersonaFeatures;
import io.vertigo.vega.VegaFeatures;

public final class MyAppConfig {
	public static final int WS_PORT = 8088;

	public static AppConfigBuilder createAppConfigBuilder() {
		return new AppConfigBuilder().beginBoot()
				.withLocales("fr_FR")
				.addPlugin(ClassPathResourceResolverPlugin.class)
				.addPlugin(URLResourceResolverPlugin.class)
				.silently()
				.endBoot()
				.addModule(new CommonsFeatures()
						.withCache(MemoryCachePlugin.class)
						.withScript()
						.build())
				.addModule(new DynamoFeatures()
						.withKVStore()
						.addKVStorePlugin(DelayedMemoryKVStorePlugin.class,
								Param.create("collections", "tokens"),
								Param.create("timeToLiveSeconds", "120"))
						.withStore()
						.addDataStorePlugin(SqlDataStorePlugin.class,
								Param.create("dataSpace", "orchestra"),
								Param.create("connectionName", "orchestra"),
								Param.create("sequencePrefix", "SEQ_"))
						.withSqlDataBase()
						.addSqlConnectionProviderPlugin(C3p0ConnectionProviderPlugin.class,
								Param.create("name", "orchestra"),
								Param.create("dataBaseClass", H2DataBase.class.getName()),
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

	public static void addVegaEmbeded(final AppConfigBuilder appConfigBuilder) {
		appConfigBuilder
				.addModule(new PersonaFeatures()
						.withUserSession(TestUserSession.class)
						.build())
				.addModule(new VegaFeatures()
						.withTokens("tokens")
						.withSecurity()
						.withMisc()
						.withEmbeddedServer(WS_PORT)
						.build());
	}

	public static void addWebServices(final AppConfigBuilder appConfigBuilder) {
		appConfigBuilder
				.addModule(new ModuleConfigBuilder("orchestra-ws")
						.withNoAPI()
						.addComponent(WsDefinition.class)
						.addComponent(WsExecution.class)
						.addComponent(WsExecutionControl.class)
						.addComponent(WsTestLogin.class)
						.build());
	}

	public static AppConfig config() {
		// @formatter:off
		return createAppConfigBuilder().build();
	}

	public static AppConfig configWithVega() {
		// @formatter:off
		final AppConfigBuilder builder = createAppConfigBuilder();
		addVegaEmbeded(builder);
		addWebServices(builder);
		return builder.build();
	}



}
