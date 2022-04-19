package com.adrianbucayan.myrecipeapp.common

import com.adrianbucayan.myrecipeapp.BuildConfig

/** singleton object of Constants value in the application */
object Constants {
    const val APP_VERSION = BuildConfig.VERSION_NAME
    const val BASE_URL = "https://food2fork.ca/api/recipe/"
    const val PAGE_SIZE = 30
    const val RECIPE = "recipe"
}
