package com.example.covy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.covy.model.DocumentType
import com.example.covy.model.Validation
class InscriptionViewModel : ViewModel() {

    // Passe à true quand l'écran de code SMS (OTP) sera en place
    private val exigerTelephoneVerifie = false

    // ====== Champs du formulaire ======
    var firstName by mutableStateOf("")
        private set

    var lastName by mutableStateOf("")
        private set

    /** Date de naissance : 8 chiffres JJMMAAAA */
    var birthDate by mutableStateOf("")
        private set

    /** Numéro ivoirien à 10 chiffres, sans +225 */
    var phone by mutableStateOf("")
        private set

    var isPhoneVerified by mutableStateOf(false)
        private set

    var password by mutableStateOf("")
        private set

    var photoUri by mutableStateOf<String?>(null)
        private set

    var selectedDocType by mutableStateOf(DocumentType.CNI)
        private set

    var docNumber by mutableStateOf("")
        private set

    var licenseNumber by mutableStateOf("")
        private set

    var licenseCategory by mutableStateOf("B (Véhicules légers / Berlines)")
        private set

    // ====== État de l'UI ======
    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    // ====== Actions sur les champs ======
    fun onFirstNameChange(value: String) {
        firstName = Validation.cleanName(value)   // les chiffres sont ignorés
        errorMessage = null
    }

    fun onLastNameChange(value: String) {
        lastName = Validation.cleanName(value)
        errorMessage = null
    }

    fun onBirthDateChange(value: String) {
        birthDate = Validation.cleanBirthDate(value)
        errorMessage = null
    }

    fun onPhoneChange(value: String) {
        phone = Validation.cleanPhone(value)
        isPhoneVerified = false                    // si le numéro change, il faut reconfirmer
        errorMessage = null
    }

    /** À appeler quand le code SMS saisi est validé par le serveur. */
    fun onPhoneVerified() {
        isPhoneVerified = true
    }

    fun onPasswordChange(value: String) {
        password = value
        errorMessage = null
    }

    fun onPhotoPicked(uri: String?) {
        photoUri = uri
        errorMessage = null
    }

    fun onDocTypeChange(value: DocumentType) {
        selectedDocType = value
        errorMessage = null
    }

    fun onDocNumberChange(value: String) {
        docNumber = value
        errorMessage = null
    }

    fun onLicenseNumberChange(value: String) {
        licenseNumber = value
        errorMessage = null
    }

    fun onLicenseCategoryChange(value: String) {
        licenseCategory = value
        errorMessage = null
    }

    // ====== Étape 1 : Identité ======
    /** @return true si l'étape 1 est valide, sinon remplit errorMessage. */
    fun validerIdentite(): Boolean {
        errorMessage = when {
            !Validation.isValidName(firstName) ->
                "Le prénom est obligatoire (lettres uniquement)"
            !Validation.isValidName(lastName) ->
                "Le nom est obligatoire (lettres uniquement)"
            !Validation.isValidBirthDate(birthDate) ->
                "Date de naissance invalide (${Validation.MIN_AGE_CHAUFFEUR} ans minimum)"
            !Validation.isValidCiPhone(phone) ->
                "Numéro ivoirien invalide (ex : 07 88 12 34 56)"
            exigerTelephoneVerifie && !isPhoneVerified ->
                "Confirmez votre numéro avec le code reçu par SMS"
            password.length < Validation.MIN_PASSWORD_LENGTH ->
                "Le mot de passe doit contenir au moins ${Validation.MIN_PASSWORD_LENGTH} caractères"
            else -> null
        }
        return errorMessage == null
    }

    // ====== Étape 2 : Documents, puis suite vers le véhicule ======
    fun continuerVersVehicule(onSuccess: () -> Unit) {
        // On revérifie l'identité par sécurité
        if (!validerIdentite()) return

        errorMessage = when {
            docNumber.isBlank() -> "Le numéro du document est obligatoire"
            licenseNumber.isBlank() -> "Le numéro du permis est obligatoire"
            else -> null
        }
        if (errorMessage != null) return

        isLoading = true

        // TODO: appel API plus tard (POST /driver/register)
        // Pour l'instant, simulation
        isLoading = false
        onSuccess()
    }
}