/**
 *
 */
package io.vertigo.orchestra.boot.initializer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import io.vertigo.commons.cache.CacheManager;
import io.vertigo.core.Home;
import io.vertigo.core.spaces.component.ComponentInitializer;
import io.vertigo.dynamo.domain.metamodel.DataType;
import io.vertigo.dynamo.domain.metamodel.DtDefinition;
import io.vertigo.dynamo.domain.metamodel.DtField;
import io.vertigo.dynamo.domain.model.DtListURIForMasterData;
import io.vertigo.dynamo.domain.model.DtObject;
import io.vertigo.dynamo.domain.util.DtObjectUtil;
import io.vertigo.dynamo.store.StoreManager;

/**
 * Initialisation des listes de références.
 *
 * @author jmforhan
 */
public class StoreManagerInitializer implements ComponentInitializer<StoreManager> {

    private static final String SI_ACTIF = "";
    private static final String ACTIF_DATA_CODE = "";
    private static final String ALL_DATA_CODE = "";
    private static final int CACHE_DURATION_LONG = 3600;
    // Durée du cache court en secondes. Pour les listes de références modifiables. nécessaire du fait du contexte
    // multi
    // web apps
    private static final int CACHE_DURATION_SHORT = 600;
    private static final Map<Class<? extends DtObject>, Set<String>> MDL_MAP = new HashMap<>();

    /** {@inheritDoc} */
    @Override
    public void init(final StoreManager component) {
        registerAllMasterData(component);
    }

    private void registerAllMasterData(final StoreManager storeManager) {
        // TODO : ajouter les mdl
    }

    private <O extends DtObject> void registerMasterData(final StoreManager storeManager,
            final Class<O> dtObjectClass) {
        registerMasterData(storeManager, dtObjectClass, null, true);
    }

    private <O extends DtObject> void registerMasterData(final StoreManager storeManager, final Class<O> dtObjectClass,
            final Integer duration, final boolean reloadItemByList) {
        final DtDefinition dtDefinition = DtObjectUtil.findDtDefinition(dtObjectClass);
        memorizeMdl(dtObjectClass, dtDefinition.getName());
        // Si la durée dans le cache n'est pas précisé, on se base sur le type de la clé primaire pour déterminer la
        // durée
        final int cacheDuration;
        if (duration == null) {
            final DtField primaryKey = dtDefinition.getIdField().get();
            if (primaryKey.getDomain().getDataType() == DataType.String) {
                cacheDuration = CACHE_DURATION_LONG;
            } else {
                cacheDuration = CACHE_DURATION_SHORT;
            }
        } else {
            cacheDuration = duration;
        }
        storeManager.getBrokerConfig().registerCacheable(dtDefinition, cacheDuration, reloadItemByList);
        // on enregistre le filtre actif
        final DtListURIForMasterData uriActif = new DtListURIForMasterData(dtDefinition, ACTIF_DATA_CODE);
        try {
            // On teste si le champ actif est présent ou pas. si ce n'est pas le cas, une nullpointerexception est levée
            dtDefinition.getField(SI_ACTIF);
            // on enregistre la définition avec filtre sur actif
            storeManager.getMasterDataConfig().register(uriActif, SI_ACTIF, Boolean.TRUE);
        } catch (final NullPointerException e) {
            // On est dans le cas ou le champ n'est pas présent
            storeManager.getMasterDataConfig().register(uriActif);
        }
        // On enregistre la liste globale
        final DtListURIForMasterData uri = new DtListURIForMasterData(dtDefinition, ALL_DATA_CODE);
        storeManager.getMasterDataConfig().register(uri);
    }

    private static void memorizeMdl(final Class<? extends DtObject> dtObjectClass, final String cacheName) {
        Set<String> set = MDL_MAP.get(dtObjectClass);
        if (set == null) {
            set = new HashSet<>();
            MDL_MAP.put(dtObjectClass, set);
        }
        set.add(cacheName);
    }

    /**
     * Vide le cache sur les listes de références.
     */
    public static void clearMdlCache() {
        final CacheManager manager = Home.getComponentSpace().resolve(CacheManager.class);
        final String debutContext = "DataCache:";
        for (final Set<String> set : MDL_MAP.values()) {
            for (final String mdl : set) {
                manager.clear(debutContext + mdl);
            }
        }
    }

    /**
     * Vide le cache de façon spécifique sur une liste de référence.
     *
     * @param dtObjectClass classe de lal iste de référence
     */
    public static void clearMdlCache(final Class<? extends DtObject> dtObjectClass) {
        final CacheManager manager = Home.getComponentSpace().resolve(CacheManager.class);
        final String debutContext = "DataCache:";
        final Set<String> set = MDL_MAP.get(dtObjectClass);
        if (set != null) {
            for (final String mdl : set) {
                manager.clear(debutContext + mdl);
            }
        }
    }
}
