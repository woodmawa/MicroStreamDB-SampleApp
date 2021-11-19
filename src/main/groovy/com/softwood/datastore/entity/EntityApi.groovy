package com.softwood.datastore.entity

interface EntityApi<E> {
    boolean instanceOf (Class clazz)
    E refresh ()
    E save ()
    List<E> getAll ()
    E insert ()
    E update ()
    void delete()
    boolean isDirty()

}