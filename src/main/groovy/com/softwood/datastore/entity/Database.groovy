package com.softwood.datastore.entity

import com.softwood.DataRoot
import one.microstream.concurrency.XThreads
import one.microstream.storage.embedded.types.EmbeddedStorageManager
import one.microstream.storage.embedded.types.EmbeddedStorage

import java.nio.file.Paths
import java.nio.file.Path

class Database {

    // Initialize a storage manager ("the database") with the given directory.
    static EmbeddedStorageManager storage
    static private Path DEFAULT_STORAGE_PATH = Paths.get("data")
    static private DataRoot root = new DataRoot()
    static private long rootObjId
    static List errors = []

    static def create() {

        println "start storageManager"
        storage = EmbeddedStorage.start (
                root,                 // root object
                DEFAULT_STORAGE_PATH  // storage directory created as 'data' as root directory node in app
        )

        if (storage.root() == null) {
            println "no existing DB, creating a new one "

            root = new DataRoot()
            rootObjId = storage.storeRoot(root )
        } else {
            println "existing database found in <project root>/" + DEFAULT_STORAGE_PATH

            root = storage.root() as DataRoot
        }
        root
    }

    static long clearDatabase () {
        println "clearing all entities list from data store "

        root.entities.clear()
        storage.store(root.getEntities())

        //clear by class instance dictionary records from root
        root.entityTypeDictionary.clear()
        storage.store(root.entityTypeDictionary)

        assert root.entities.size() == 0
        assert root.entityTypeDictionary.size() == 0

        rootObjId
    }

    static getErrors () {
        errors
    }

    static long save (def entity) {

        long entitiesResultId = 0

        if (errors) errors.clear()  //clear any outstanding errors before save

        if (root.entities.contains(entity)) {
            //already in the entities list
        } else {
            root.entities << entity
        }

        XThreads.executeSynchronized(() -> {
            entitiesResultId = storage.store(root.entities)
            }
        )

        entitiesResultId
    }

    static long updateEntityClassDictionary () {

        long entitiesDictionaryId = 0

        if (errors) errors.clear()  //clear any outstanding errors before save

         XThreads.executeSynchronized(() -> {
             entitiesDictionaryId = storage.store(root.entityTypeDictionary)
            }
        )

        entitiesDictionaryId
    }

    static List getEntities () {
        root.entities
    }

    static List getEntityByTypeList (String typeName) {

        //todo should we set errors if requested class type doesn't exist
        root.entityTypeDictionary[typeName] ?: []
    }

    static boolean shutdown() {
        println "shutting down storage manager"

        //storage.close()
        storage.shutdown()
    }
}
