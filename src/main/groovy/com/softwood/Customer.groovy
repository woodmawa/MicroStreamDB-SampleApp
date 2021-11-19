package com.softwood

import groovy.transform.EqualsAndHashCode
import groovy.transform.MapConstructor

@EqualsAndHashCode
@MapConstructor
class Customer {

    String name
    private long id

    /*Customer () {
        super()
    }*/

    long getId() {
        id
    }

    void setId (long id) {
        println "no can do, customer id is readonly"
    }

    List<Site> sites = []

    Customer leftShift (Site site) {
        assert site.id

        sites.contains(site) ? false : sites.add(site)
        site.customer = this
        this
    }

    Customer leftShift (List<Site> siteList) {

        addSites(siteList)
        this
    }

    boolean addSite (Site site) {
        sites.contains (site) ? false : sites.add(site)
    }

    void addSites (List<Site> sitesList) {
        //remove any duplicates before adding
        List<Site> dedupSublist = sitesList - sites.intersect(sitesList)
        dedupSublist.each {it.customer=this}        //set owning customer for each
        sites = sites.plus (dedupSublist)
    }

    Site removeSite (Site site) {
        sites.contains(site) ? sites.remove(site) : false
        site.customer = null
        site
    }

    void removeAllSites (List<Site> siteList) {
        List<Site> dedupSublist = siteList - sites.intersect(sitesList)
        sites.removeAll (dedupSublist)
        dedupSublist.each{it.customer = null}
    }

    String toString() {
        "Customer : $name ($id)"
    }

}
