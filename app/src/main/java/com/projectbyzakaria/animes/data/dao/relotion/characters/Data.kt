package com.projectbyzakaria.animes.data.dao.relotion.characters

data class Data(
    val character: Character,
    val favorites: Int?,
    val role: String?,
    val voice_actors: List<VoiceActor>
)