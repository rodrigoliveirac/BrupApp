package com.rodcollab.brupapp.multiplayer

import com.google.firebase.firestore.DocumentSnapshot

interface NetworkDataSource<T> {
    fun createDocument(collection: String, document: String, data: T)
    suspend fun loadDocuments(collection: String, field: String, data: String) : List<DocumentSnapshot>
}