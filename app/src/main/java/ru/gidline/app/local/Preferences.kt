package ru.gidline.app.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel

class Preferences(context: Context) : KotprefModel(context) {

    override val kotprefName = "${context.packageName}_preferences"

    var avatarPath by nullableStringPref(null, "avatar_path")

    var username by nullableStringPref(null, "username")

    var surname by nullableStringPref(null, "surname")

    var isMan by booleanPref(true, "is_man")

    var citizenship by intPref(0, "citizenship")

    var phone by nullableStringPref(null, "phone")

    var whatsapp by nullableStringPref(null, "whatsapp")

    var email by nullableStringPref(null, "email")

    var language by intPref(0, "language")

    var hasMigrationData by booleanPref(false, "hasMigrationData")

    var dateEntryRussia by nullableStringPref(null, "date_entry_russia")

    var dateFirstPatent by nullableStringPref(null, "date_first_patent")

    var latitude by floatPref(55.751694f, "latitude")

    var longitude by floatPref(37.617218f, "longitude")
}