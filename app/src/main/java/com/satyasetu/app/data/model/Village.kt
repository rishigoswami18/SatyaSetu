package com.satyasetu.app.data.model

/**
 * Model representing a hyperlocal village unit
 */
data class Village(
    val villageId: String = "",
    val name: String = "",
    val lat: Double = 0.0,
    val lng: Double = 0.0,
    val district: String = "",
    val state: String = "",
    val userCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun toMap(): Map<String, Any?> = mapOf(
        "villageId" to villageId,
        "name" to name,
        "lat" to lat,
        "lng" to lng,
        "district" to district,
        "state" to state,
        "userCount" to userCount,
        "createdAt" to createdAt
    )
}
