package com.softwood

import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap

import java.util.concurrent.ConcurrentHashMap

class DataRoot extends Expando {  //this works
    String name
    static List entities = []
    //map with class name as key and List of saved entities of this type
    static Map entityTypeDictionary  = new ConcurrentHashMap<String, List>()

    DataRoot() {
        super()
    }

    String toString () {
        return "Root (name : $name)"
    }

}
