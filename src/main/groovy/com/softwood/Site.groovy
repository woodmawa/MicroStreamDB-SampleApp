package com.softwood

import groovy.transform.EqualsAndHashCode
import groovy.transform.MapConstructor
import groovy.transform.ToString

@ToString
@EqualsAndHashCode
@MapConstructor
class Site {

    long id
    String name

    long getId() {
        id
    }

    void setId (long id) {
        println "no can do, site id is readonly"
    }

    Customer customer

}
