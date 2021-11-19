package com.softwood

import com.softwood.datastore.entity.RepositoryTrait

class CustomerRepository<ID, E> implements RepositoryTrait<ID, E>{

    CustomerRepository() {

        String name = this.getClass().canonicalName
        String actualClassName = name - "Repository"

        DataRoot.entityTypeDictionary.putIfAbsent(actualClassName, references)

    }


    @Override E save (E entity) {
        RepositoryTrait.super.save (entity) //call default implementation
        SiteRepository srepo = new SiteRepository()
        srepo.saveAllRefs(entity.sites)

    }


}
