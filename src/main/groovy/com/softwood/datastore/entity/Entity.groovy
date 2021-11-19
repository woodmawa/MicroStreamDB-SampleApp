package com.softwood.datastore.entity

import com.softwood.DataRoot
import one.microstream.storage.embedded.types.EmbeddedStorageManager

trait Entity<E> implements EntityApi<E> {

    //need to inject this
    EmbeddedStorageManager storage

    boolean dirty

    boolean instanceOf (Class clazz) {
        this.instanceOf(clazz)
    }

    E save () {

        DataRoot root = storage.root()
        String key = self().getClass().name
        root.entities.putIfAbsent(key, new ArrayList<E>())
        List entList =  root.entities[key]

        entList <<  this.$delegate

        long id = storage.store(root.entities)
        assert id
        this.dirty = false
        this
    }

    List<E> getAll() {
        DataRoot root = storage.root()
        String key = self().getClass().name

        root.getEntities()[key]
    }

    boolean isDirty() {
        dirty == true
    }

    E self () {
        this
    }
}