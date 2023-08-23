package com.activ.activsewa.model.response.payment

data class User(
    val active: String,
    val avatar: Any,
    val created_at: String,
    val deleted_at: Any,
    val detail_user: DetailUser,
    val email: String,
    val email_verified_at: Any,
    val gender: Any,
    val id: Int,
    val name: String,
    val roles: String,
    val two_factor_confirmed_at: Any,
    val updated_at: String,
    val username: String
)