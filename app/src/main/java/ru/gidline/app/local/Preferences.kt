package ru.gidline.app.local

import android.content.Context
import com.chibatching.kotpref.KotprefModel
import ru.gidline.app.R

class Preferences(context: Context) : KotprefModel(context) {

    override val kotprefName = "${context.packageName}_preferences"

    var avatarPath by nullableStringPref(null, "avatar_path")

    var isMan by booleanPref(true, "is_man")

    var dateEntryRussia by nullableStringPref(null, "date_entry_russia")

    var dateFirstPatent by nullableStringPref(null, "date_first_patent")

    val genderDrawable: Int
        get() = if (isMan) R.drawable.avatar_man else R.drawable.avatar_woman
}