package com.s10plusArtifacts.sesion

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import com.google.gson.Gson
import com.s10plusArtifacts.utils.PersonalInformation
import android.util.Base64
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys


val Context.dataStore by preferencesDataStore(name = "sessionAmadCore")

// Inicializar Tink para cifrado
object EncryptionManager {
    private const val KEYSET_NAME = "master_keyset_Amad-Demo"
    private const val PREF_FILE = "master_key_preference"

    fun getAead(context: Context): Aead {
        try {
            AeadConfig.register() // Registra la configuración de Aead
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            val keysetHandle = AndroidKeysetManager.Builder()
                .withSharedPref(context.applicationContext, KEYSET_NAME, PREF_FILE)
                .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
                .withMasterKeyUri("android-keystore://$masterKey")
                .build()
                .keysetHandle

            return keysetHandle.getPrimitive(Aead::class.java)
        } catch (e: Exception) {
            println("Error al inicializar Tink: ${e.message}")
            throw e
        }
    }
}

fun encrypt(context: Context, data: String): String {
    return try {
        val aead = EncryptionManager.getAead(context)
        val encryptedBytes = aead.encrypt(data.toByteArray(), null)
        val base64Encrypted = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        base64Encrypted
    } catch (e: Exception) {
        println("Error en encrypt: ${e.message}")
        throw e
    }
}


fun decrypt(context: Context, encryptedData: String): String {
    val aead = EncryptionManager.getAead(context)
    return try {
        val decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
        aead.decrypt(decodedBytes, null).decodeToString()

    } catch (e: Exception) {
        println("Error al descifrar: ${e.message}")
        "" // Retorna una cadena vacía si falla
    }
}

fun saveEncryptedPersonalInformation(context: Context, personalInfo: PersonalInformation) {
    val gson = Gson()
    val jsonString = gson.toJson(personalInfo) // Convertir objeto a JSON
    runBlocking {
        context.dataStore.edit { preferences ->
            preferences[PERSONALINFO] = jsonString
        }
    }
}

fun getEncryptedPersonalInformation(context: Context): PersonalInformation? {
    val gson = Gson()
    return runBlocking {
        val encryptedData = context.dataStore.data.map { preferences ->
            preferences[PERSONALINFO]
        }.firstOrNull()
            gson.fromJson(
                encryptedData,
                PersonalInformation::class.java
            )
         // Convertir JSON a objeto
    }
}


val ACTIVEGEOLOCALIZATION = booleanPreferencesKey("ACTIVEGEOLOCALIZATION")
val INTERCEPTORPHONE = booleanPreferencesKey("INTERCEPTORPHONE")
val OFLINE = booleanPreferencesKey("OFLINE")
val SHOWESTATE = booleanPreferencesKey("SHOWESTATE")
val URLANALYTICS = stringPreferencesKey("URLANALYTICS")
val URLSOUND = stringPreferencesKey("URLSOUND")
val STATUS = stringPreferencesKey("STATUS")
val VIEWS = stringPreferencesKey("VIEWS")
val ISFIRSTSESSION = booleanPreferencesKey("ISFIRSTSESSION")
val PHONE = stringPreferencesKey("PHONE")
val CURRENT_PHONE = stringPreferencesKey("CURRENTPHONE")
val LAT = stringPreferencesKey("LAT")
val LNG = stringPreferencesKey("LNG")
val STATE = stringPreferencesKey("STATE")
val SERIAL = stringPreferencesKey("SERIAL")
val TOKENCLICK = stringPreferencesKey("TOKENCLICK")
val PERSONALINFO = stringPreferencesKey("PERSONALINFO")
val WELCOMEVIDEO = stringPreferencesKey("WELCOMEVIDEO")
val IDAPP = stringPreferencesKey("IDAPP")
val ANALITICOPEN = stringPreferencesKey("ANALITICOPEN")


fun Context.AnaliticOpen(): String = runBlocking {
    this@AnaliticOpen.dataStore.data.map { preference ->
        preference[ANALITICOPEN]
    }.firstOrNull() ?: ""
}
fun Context.AnaliticOpen(value: String) {
    runBlocking {
        this@AnaliticOpen.dataStore.edit {
            it[ANALITICOPEN] = value
        }
    }
}

fun Context.IdApp(): String = runBlocking {
    this@IdApp.dataStore.data.map { preference ->
        preference[IDAPP]
    }.firstOrNull() ?: ""
}

fun Context.IdApp(value: String) {
    runBlocking {
        this@IdApp.dataStore.edit {
            it[IDAPP] = value
        }
    }
}

fun Context.PersonalInfo(): PersonalInformation? = getEncryptedPersonalInformation(this)

fun Context.PersonalInfo(value: PersonalInformation) {
    saveEncryptedPersonalInformation(this, value)
}

fun Context.WelcomeVideo(): String = runBlocking {
    this@WelcomeVideo.dataStore.data.map { preference ->
        preference[WELCOMEVIDEO]
    }.firstOrNull() ?: ""
}

fun Context.WelcomeVideo(value: String) {
    runBlocking {
        this@WelcomeVideo.dataStore.edit {
            it[WELCOMEVIDEO] = value
        }
    }
}

fun Context.TokenClick(): String = runBlocking {
    this@TokenClick.dataStore.data.map { preferences ->
        preferences[TOKENCLICK]
    }.firstOrNull() ?: ""
}

fun Context.TokenClick(value: String) {
    runBlocking {
        this@TokenClick.dataStore.edit {
            it[TOKENCLICK] = value
        }
    }
}

fun Context.Serial(): String = runBlocking {
    this@Serial.dataStore.data.map { preference ->
        preference[SERIAL]
    }.firstOrNull() ?: ""
}

fun Context.Serial(value: String) {
    runBlocking {
        this@Serial.dataStore.edit {
            it[SERIAL] = value
        }
    }
}

fun Context.State(): String = runBlocking {
    this@State.dataStore.data.map { preference ->
        preference[STATE]
    }.firstOrNull() ?: ""
}

fun Context.State(value: String) {
    runBlocking {
        this@State.dataStore.edit {
            it[STATE] = value
        }
    }
}

fun Context.Lng(): String = runBlocking {
    this@Lng.dataStore.data.map { preference ->
        preference[LNG]
    }.firstOrNull() ?: ""
}

fun Context.Lng(value: String) {
    runBlocking {
        this@Lng.dataStore.edit {
            it[LNG] = value
        }
    }
}

fun Context.Lat(): String = runBlocking {
    this@Lat.dataStore.data.map { preference ->
        preference[LAT]
    }.firstOrNull() ?: ""
}

fun Context.Lat(value: String) {
    runBlocking {
        this@Lat.dataStore.edit {
            it[LAT] = value
        }
    }
}

fun Context.CurrentPhone(): String = runBlocking {
    this@CurrentPhone.dataStore.data.map { preference ->
        preference[CURRENT_PHONE]
    }.firstOrNull() ?: ""
}

fun Context.CurrentPhone(value: String) {
    runBlocking {
        this@CurrentPhone.dataStore.edit {
            it[CURRENT_PHONE] = value
        }
    }
}

fun Context.Phone(value: String) {
    runBlocking {
        this@Phone.dataStore.edit {
            it[PHONE] = value
        }
    }
}

fun Context.Phone(): String = runBlocking {
    this@Phone.dataStore.data.map { preference ->
        preference[PHONE]
    }.firstOrNull() ?: ""
}

fun Context.Views(value: String) {
    runBlocking {
        this@Views.dataStore.edit {
            it[VIEWS] =value
        }
    }
}

fun Context.Views(): String = runBlocking {
    this@Views.dataStore.data.map { preference ->
        preference[VIEWS]
    }.firstOrNull() ?: ""
}

fun Context.Status(value: Int) {
    runBlocking {
        this@Status.dataStore.edit {
            it[STATUS] =  value.toString()
        }
    }
}

fun Context.Status(): Int = runBlocking {
    this@Status.dataStore.data.map { preference ->
        preference[STATUS]?.toInt()
    }.firstOrNull() ?: 0
}

fun Context.UrlSound(value: String) {
    runBlocking {
        this@UrlSound.dataStore.edit {
            it[URLSOUND] = value
        }
    }
}

fun Context.UrlSound(): String = runBlocking {
    this@UrlSound.dataStore.data.map { preference ->
        preference[URLSOUND]
    }.firstOrNull() ?: ""
}

fun Context.UrlAnalytics(value: String) {
    runBlocking {
        this@UrlAnalytics.dataStore.edit {
            it[URLANALYTICS] = value
        }
    }
}

fun Context.UrlAnalytics(): String = runBlocking {
    this@UrlAnalytics.dataStore.data.map { preference ->
        preference[URLANALYTICS]
    }.firstOrNull() ?: ""
}

fun Context.isFirstSession(): Boolean = runBlocking {
    this@isFirstSession.dataStore.data.map { preference ->
        preference[ISFIRSTSESSION]
    }.firstOrNull() ?: true
}

fun Context.isFirstSession(value: Boolean) {
    runBlocking {
        this@isFirstSession.dataStore.edit {
            it[ISFIRSTSESSION] = value
        }
    }
}

fun Context.ShowState(value: Boolean) {
    runBlocking {
        this@ShowState.dataStore.edit {
            it[SHOWESTATE] = value
        }
    }
}

fun Context.ShowState(): Boolean = runBlocking {
    this@ShowState.dataStore.data.map { preference ->
        preference[SHOWESTATE]
    }.firstOrNull() ?: false
}

fun Context.Offline(value: Boolean) {
    runBlocking {
        this@Offline.dataStore.edit {
            it[OFLINE] = value
        }
    }
}

fun Context.Offline(): Boolean = runBlocking {
    this@Offline.dataStore.data.map { preference ->
        preference[OFLINE]
    }.firstOrNull() ?: false
}

fun Context.InterceptorPhone(value: Boolean) {
    runBlocking {
        this@InterceptorPhone.dataStore.edit {
            it[INTERCEPTORPHONE] = value
        }
    }
}

fun Context.InterceptorPhone(): Boolean = runBlocking {
    this@InterceptorPhone.dataStore.data.map { preference ->
        preference[INTERCEPTORPHONE]
    }.firstOrNull() ?: false
}

fun Context.ActiveGeoLocalization(value: Boolean) {
    runBlocking {
        this@ActiveGeoLocalization.dataStore.edit {
            it[ACTIVEGEOLOCALIZATION] = value
        }
    }
}

fun Context.ActiveGeoLocalization(): Boolean = runBlocking {
    this@ActiveGeoLocalization.dataStore.data.map { preference ->
        preference[ACTIVEGEOLOCALIZATION]
    }.firstOrNull() ?: true
}