package com.rodcollab.brupapp.multiplayer

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class NetworkDataSourceImpl<T : Any>(
    private val store: FirebaseFirestore
) : NetworkDataSource<T> {

    override fun createDocument(collection: String, document: String, data: T) {
        store.collection(collection)
            .document(document)
            .set(data)
            .addOnSuccessListener {
                Log.d(
                    "SAVE_IN_FIRESTORE",
                    "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e ->
                Log.w(
                    "SAVE_IN_FIRESTORE",
                    "Error writing document",
                    e
                )
            }
    }

    override suspend fun loadDocuments(
        collection: String,
        field: String,
        data: String
    ): List<DocumentSnapshot> {
        val snapshot =
            store.collection(collection)
                .whereEqualTo(field, data)
                .get()
                .addOnSuccessListener { }
                .addOnFailureListener { }
                .await()
        return snapshot.documents
    }
}