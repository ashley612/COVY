package com.example.covy.model

data class Document(
    val type: DocumentType,
    val number: String,
    val fileUrl: String? = null
)

enum class DocumentType {
    CNI,
    PASSEPORT,
    PERMIS,
    ASSURANCE,
    VISITE_TECHNIQUE
}

