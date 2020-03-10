package ru.gidline.app.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel

class Preferences(context: Context) : KotprefModel(context) {

    override val kotprefName = "${context.packageName}_preferences"

    var avatarPath by nullableStringPref(null, "avatar_path")

    var isMan by booleanPref(true, "is_man")

    var citizenship by intPref(0, "citizenship")

    var phone by nullableStringPref(null, "phone")

    var whatsapp by nullableStringPref(null, "whatsapp")

    var email by nullableStringPref(null, "email")

    var language by intPref(0, "language")

    var hasMigrationData by booleanPref(false, "hasMigrationData")

    var dateEntryRussia by nullableStringPref("10.10.2019", "date_entry_russia")

    var dateFirstPatent by nullableStringPref("01.12.2019", "date_first_patent")
}