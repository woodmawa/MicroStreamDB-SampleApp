package com.softwood

import com.softwood.datastore.entity.AnyTrait
import com.softwood.datastore.entity.Database
import com.softwood.datastore.entity.Entity
import one.microstream.concurrency.XThreads
import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager

import java.nio.file.Paths

class Application {


    static main (args) {

        Database.create()
        Database.clearDatabase()

        CustomerRepository crepo = new CustomerRepository<Long, Customer> ()
        SiteRepository srepo = new SiteRepository<Long, Site> ()


        Customer factoryCust1 = crepo.newInstance()
        Customer factoryCust2 = crepo.newInstance(name:"new factory")

        Customer cust1 = new Customer (name:"HSBC")
        Site site1 = new Site(name: "Head Office, Canary Wharf", customer: cust1)
        Site site2 = new Site(name: "Branch Office, Ipswich", customer: cust1 )
        cust1.sites << site1 << site2  //add sites to customer

        Customer cust2 = new Customer (name:"NatWest")
        Site site3 = new Site(name: "Head Office, London ",  customer: cust2)
        Site site4 = new Site(name: "Branch Office, Ipswich",  customer: cust2)
        cust2.sites << site3 << site4  //add sites to customer

        crepo.save (cust1)
        crepo.save (cust2)

        HashMap srefs = srepo.references

        cust1.id = 10  //change cust id  - will fail id is read-only

        crepo.save (cust1)  //update customers graph

        assert Database.entities.size() == 2

        List entityListRef = Database.getEntities ()

        println "saved entity roots  : "+ entityListRef

        assert entityListRef.size() == 2
        println "initial state of sites  : "+ entityListRef[0].sites



        Customer c = entityListRef[0]
        Site rsite = c.removeSite (site2)
        crepo.save (c)
        assert rsite.customer == null
        srepo.delete (rsite)


        entityListRef = Database.getEntities ()
        println "updated state : "+ entityListRef[0].sites

        Map custMap = crepo.dictionary()
        Map siteMap = srepo.dictionary()
        crepo.count() == 2
        srepo.count() == 3
        //println "sites in dictionary $siteMap"

        Database.shutdown()

        System.exit (0)  //need this else its sitting on a thread somewhere
    }
}
