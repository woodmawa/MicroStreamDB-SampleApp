package com.softwood.datastore.entity

import com.softwood.DataRoot
import org.codehaus.groovy.runtime.metaclass.ThreadManagedMetaBeanProperty

import java.lang.reflect.Constructor
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

trait RepositoryTrait<ID, T> {

    static ConcurrentHashMap references = new ConcurrentHashMap<>()
    static AtomicLong idGenerator = new AtomicLong(0)


    Map<ID, T> dictionary () {

        String name = this.getClass().canonicalName
        String actualClassName = name - "Repository"

        List classInstanceList = Database.getEntityByTypeList(actualClassName)

        assert references == classInstanceList

        //list of saved instances of appropriate concrete repository class
        references
    }

    T newInstance (Map params) {
        String name = this.getClass().canonicalName
        String actualClassName = name - "Repository"

        Class clazz = Class.forName(actualClassName)

        def constructors = clazz?.getDeclaredConstructors()

        Constructor mapConstructor = constructors.find{it.parameterTypes[0] == Map && it.parameterTypes.size() == 1}

        T instance =  mapConstructor.newInstance(params) as T
        instance?.@id = idGenerator.incrementAndGet()

        instance
    }

    T newInstance () {
        String name = this.getClass().canonicalName
        String actualClassName = name - "Repository"

        Class clazz = Class.forName(actualClassName)

        def constructors = clazz?.getDeclaredConstructors()
        Constructor constructor = constructors[0]
        Class[] paramTypes = constructor.parameterTypes

        T instance
        if (paramTypes.contains(Map)) {
            //allows for @MapConstructor case
            instance = constructor.newInstance([:]) as T
        } else if (paramTypes.size() == 0)
            instance = constructor.newInstance() as T
        instance?.@id = idGenerator.incrementAndGet()
        instance as T
    }

    T save(T entity) {
        if (entity.id == 0) {
            entity.@id = idGenerator.incrementAndGet()      //update field not via setter/getter
        }

        long success = Database.save (entity)
        if (success > 0) {
            references.putIfAbsent(entity.id, entity)
        }
        entity
    }

    void saveAllRefs (Collection<T> entityList) {

        def unsaved = entityList.findAll{entity -> entity.id == 0}

        unsaved.each {entity -> entity.@id = idGenerator.incrementAndGet()
            references.putIfAbsent(entity.id, entity)
        }
        Database.updateEntityClassDictionary()

    }

    def findOne(ID primaryKey) {

    }

    Iterable findAll() {
        references.elements().iterator() as Iterable
    }

    Long count() {
        references.size()
    }

    void delete(T entity) {

        if (references.containsKey(entity.id)) {
            references.remove(entity.id)
        }

    }

    boolean exists(ID primaryKey) {
        references.containsKey(primaryKey)
    }


}