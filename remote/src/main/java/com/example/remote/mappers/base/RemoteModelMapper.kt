package com.example.remote.mappers.base

interface RemoteModelMapper<M, E> {

    fun mapFromModel(model: M): E

    fun mapToModel(domain: E): M


    fun mapFromEntityList(entities: List<M>): List<E> {
        return entities.mapTo(mutableListOf(), ::mapFromModel)
    }

    fun mapFromDomainList(domainModels: List<E>): List<M> {
        return domainModels.mapTo(mutableListOf(), ::mapToModel)
    }

}