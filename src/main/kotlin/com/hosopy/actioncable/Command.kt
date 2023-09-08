package com.hosopy.actioncable

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json

internal data class Command(private val command: String, private val identifier: String, private val data: Map<String, Any?> = mapOf()) {

    companion object {
        fun subscribe(identifier: String) = Command("subscribe", identifier)
        fun unsubscribe(identifier: String) = Command("unsubscribe", identifier)
        fun message(identifier: String, data: Map<String, Any?>) = Command("message", identifier, data)
    }

    fun toJsonStr(): String {
        return if (data.isEmpty()) {
            json {
                obj("command" to command, "identifier" to identifier)
            }.let {
                StringBuilder().apply { it.appendJsonString(this,
                    prettyPrint = false,
                    canonical = false
                ) }.toString()
            }
        } else {
            json {
                obj("command" to command, "identifier" to identifier, "data" to JsonObject(data).toJsonString())
            }.let {
                StringBuilder().apply { it.appendJsonString(this,
                    prettyPrint = false,
                    canonical = false
                ) }.toString()
            }
        }
    }
}
