package com.uzuu.admin.domain.model

data class Register(
    	    val username: String,
    	    val password: String,
        val email: String,
    	    val fullName: String,
    	    val phone: String? = null,
    	    val address: String? = null,
    	    val role: String = "STAFF"
    	)