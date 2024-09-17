@file:Suppress("DEPRECATION")

package com.app.motus4

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun setAppLocale(activity: Activity, languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)

    val resources = activity.resources
    val config = resources.configuration
    config.setLocale(locale)

    resources.updateConfiguration(config, resources.displayMetrics)

}