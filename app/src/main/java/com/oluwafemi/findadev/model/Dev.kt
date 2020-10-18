package com.oluwafemi.findadev.model

import java.io.Serializable


data class Dev(
    val fullName: String = "",
    val devEmail: String = "",
    val devPortfolio: String = "",
    val devStack: String = "",
    val jobType: String = ""
) : Serializable {

    override fun toString(): String {
        return "Interested in $jobType roles"
    }
}

