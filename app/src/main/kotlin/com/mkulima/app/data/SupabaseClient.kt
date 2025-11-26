package com.mkulima.app.data

import io.github.supabase.SupabaseClient
import io.github.supabase.createSupabaseClient
import io.github.supabase.gotrue.GoTrue
import io.github.supabase.postgrest.Postgrest

object SupabaseManager {
    private const val SUPABASE_URL = "YOUR_SUPABASE_URL"
    private const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_ANON_KEY"

    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = SUPABASE_URL,
            supabaseKey = SUPABASE_ANON_KEY
        ) {
            install(GoTrue)
            install(Postgrest)
        }
    }

    suspend fun signUp(email: String, password: String, name: String) {
        try {
            client.auth.signUpWith(email, password)
        } catch (e: Exception) {
            throw Exception("Sign up failed: ${e.message}")
        }
    }

    suspend fun signIn(email: String, password: String) {
        try {
            client.auth.signInWith(email, password)
        } catch (e: Exception) {
            throw Exception("Sign in failed: ${e.message}")
        }
    }

    suspend fun signOut() {
        try {
            client.auth.signOut()
        } catch (e: Exception) {
            throw Exception("Sign out failed: ${e.message}")
        }
    }

    fun isAuthenticated(): Boolean {
        return client.auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return client.auth.currentUser?.id
    }
}
