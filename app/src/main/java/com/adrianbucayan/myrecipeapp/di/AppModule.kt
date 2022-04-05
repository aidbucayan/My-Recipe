package com.adrianbucayan.myrecipeapp.di

import android.content.Context
import android.util.Log
import com.adrianbucayan.myrecipeapp.BuildConfig
import com.adrianbucayan.myrecipeapp.data.remote.MyRecipeApi
import com.adrianbucayan.myrecipeapp.data.repository.MyRecipeRepositoryImpl
import com.adrianbucayan.myrecipeapp.domain.repository.MyRecipeRepository
import com.adrianbucayan.myrecipeapp.presentation.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMyRecipeRepository(api: MyRecipeApi): MyRecipeRepository {
        return MyRecipeRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun providesTimberTree(): Timber.Tree {
        class ReportingTree : Timber.Tree() {
            override fun log(
                priority: Int,
                tag: String?,
                message: String,
                throwable: Throwable?
            ) {
                if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                    return
                }
                // val t = throwable ?: Exception(message)
                // Pass the exception variable t to crash reporting service
            }
        }

        return when(BuildConfig.DEBUG) {
            true -> Timber.DebugTree()
            else -> ReportingTree()
        }
    }

    /**
     * I might include proper authentication later on food2fork.ca
     * For now just hard code a token.
     */
    @Singleton
    @Provides
    @Named("auth_token")
    fun provideAuthToken(): String {
        return "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"
    }

    @Singleton
    @Provides
    fun provideUtils(@ApplicationContext context: Context): Utils {
        return Utils(context)
    }

}