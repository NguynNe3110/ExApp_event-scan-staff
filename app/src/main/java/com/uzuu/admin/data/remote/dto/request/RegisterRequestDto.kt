package com.uzuu.admin.data.remote.dto.request

data class RegisterRequestDto(
    	    val username: String,
    	    val password: String,
    	    val email: String,
    	    val fullName: String,
    	    val phone: String? = null,
    	    val address: String? = null,
    	    val role: String? = null
    	)