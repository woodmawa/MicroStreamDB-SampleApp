package com.softwood

import com.softwood.datastore.entity.AnyTrait
import groovy.transform.ToString

@ToString
class TestEntityWithTrait implements AnyTrait{

    String name
    long entityId
}
