package com.softwood

import org.apache.groovy.util.concurrent.concurrentlinkedhashmap.ConcurrentLinkedHashMap

class DataRoot extends Expando {  //this works
    String name
    static List entities = []
    //map with class name as key and List or saved entities of this type
    static Map entityTypeDictionary  = new ConcurrentLinkedHashMap<String, List>()

    DataRoot() {
        super()
    }

    String toString () {
        return "Root : " + name
    }

}
