package com.ryosm.core.com.ryosm.comms.RoboSpice;

import android.app.Application;

import com.octo.android.robospice.persistence.ObjectPersisterFactory;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.ryosm.core.com.ryosm.Configs;
import com.ryosm.core.com.ryosm.utils.Security.SecurityUtils;

public class MemoryGsonObjectPersisterFactory extends ObjectPersisterFactory {

    public MemoryGsonObjectPersisterFactory(Application application) throws CacheCreationException {
        super(application);
    }

    @Override
    public <DATA> MemoryGsonObjectPersister<DATA> createObjectPersister(Class<DATA> clazz)
            throws CacheCreationException {
        String seed = null;
        if (Configs.ENCRYPT_CACHE) {
            seed = SecurityUtils.generateSeedForCache();
        }
        return new MemoryGsonObjectPersister<DATA>(getApplication(), clazz, seed);
    }

}
