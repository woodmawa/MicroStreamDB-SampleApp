package com.softwood

import com.softwood.datastore.entity.RepositoryTrait

class SiteRepository <ID, E> implements RepositoryTrait<ID, E> {

    SiteRepository() {

        String name = this.getClass().canonicalName
        String actualClassName = name - "Repository"

        DataRoot.entityTypeDictionary.putIfAbsent(actualClassName, references)

    }
}
