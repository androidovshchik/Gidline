package ru.gidline.app.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import ru.gidline.app.R

class Preferences(context: Context) : KotprefModel(context) {

    override val kotprefName = "${context.packageName}_preferences"

    var avatarPath by nullableStringPref(null, "avatar_path")

    var isMan by booleanPref(true, "is_man")

    var hasMigrationData by booleanPref(false, "hasMigrationData")

    var dateEntryRussia by nullableStringPref("10.10.2019", "date_entry_russia")

    var dateFirstPatent by nullableStringPref("01.12.2019", "date_first_patent")

    val genderDrawable: Int
        get() = if (isMan) R.drawable.avatar_man else R.drawable.avatar_woman
}