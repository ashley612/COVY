package com.example.covy.model

import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Règles de saisie partagées (chauffeur, étudiant, élève).
 * Kotlin pur : aucune dépendance à Compose.
 * Rappel : le serveur doit refaire les mêmes vérifications.
 */
object Validation {

    // Lettres (accents compris), espaces, tirets, apostrophes. Aucun chiffre.
    private val NAME_REGEX = Regex("^\\p{L}[\\p{L} '\\-]*$")

    // Numéro ivoirien à 10 chiffres : 01 (Moov), 05 (MTN), 07 (Orange) + 8 chiffres
    private val CI_PHONE_REGEX = Regex("^(01|05|07)\\d{8}$")

    const val MIN_PASSWORD_LENGTH = 6
    const val MIN_AGE_CHAUFFEUR = 18

    // ---- Nettoyage pendant la frappe ----
    fun cleanName(input: String): String =
        input.filter { it.isLetter() || it == ' ' || it == '-' || it == '\'' }.take(40)

    fun cleanPhone(input: String): String =
        input.filter { it.isDigit() }.take(10)

    fun cleanBirthDate(input: String): String =
        input.filter { it.isDigit() }.take(8)

    // ---- Vérifications ----
    fun isValidName(value: String): Boolean {
        val v = value.trim()
        return v.length >= 2 && NAME_REGEX.matches(v)
    }

    fun isValidCiPhone(value: String): Boolean = CI_PHONE_REGEX.matches(value)

    /** @param ddMMyyyy 8 chiffres, ex. "12062001" */
    fun isValidBirthDate(ddMMyyyy: String, minAge: Int = MIN_AGE_CHAUFFEUR): Boolean {
        if (ddMMyyyy.length != 8 || !ddMMyyyy.all { it.isDigit() }) return false
        val day = ddMMyyyy.substring(0, 2).toInt()
        val month = ddMMyyyy.substring(2, 4).toInt()
        val year = ddMMyyyy.substring(4, 8).toInt()
        return try {
            val birth = GregorianCalendar(year, month - 1, day).apply { isLenient = false }
            birth.time // lève une exception si la date n'existe pas (ex. 31/02)
            val limit = Calendar.getInstance().apply { add(Calendar.YEAR, -minAge) }
            year >= 1900 && !birth.after(limit)
        } catch (e: Exception) {
            false
        }
    }
}