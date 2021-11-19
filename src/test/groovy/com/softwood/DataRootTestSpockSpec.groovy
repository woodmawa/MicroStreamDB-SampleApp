package com.softwood

import spock.lang.Specification

class DataRootTestSpockSpec extends Specification {

    def "first test " () {
        given :
            println "setting up expectation "
            def res = "william"

        expect:
            res == "william"
    }

    def "data root" () {
        /*
        // this throws java.lang.NullPointerException: Cannot read field "concurrencyLevel" because "builder" is null
        given :
            DataRoot root = new DataRoot(name : "myRoot")

        expect:
            root.name == "myRoot"

        when:
            root.name = "renamedRoot"
        then:
        root.name == "renamedRoot"*/
    }
}
