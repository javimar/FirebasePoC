package eu.javimar.firebasepoc.core.utils

import android.util.Base64
import eu.javimar.domain.auth.model.TokenInfo
import eu.javimar.domain.auth.model.getSignInProvider
import eu.javimar.firebasepoc.core.loge
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException

class JWTUtils(private val token: String?) {

    fun extractInformation(): TokenInfo {

        var tokenInfo: TokenInfo

        token?.let {
            return try {
                val bodyToken = getDecoded(token)
                val tokenObject = JSONObject(bodyToken)

                tokenObject.run {

                    var identities = ""
                    var signInProvider = ""
                    if (has("firebase")) {
                        val firebaseObject = getJSONObject("firebase")
                        if (firebaseObject.has("identities")) {
                            // Identities is a JSONObject, you could further parse if needed
                            identities = firebaseObject.getJSONObject("identities").toString()
                        }
                        if (firebaseObject.has("sign_in_provider")) {
                            signInProvider = firebaseObject.getString("sign_in_provider")
                        }
                    }
                    tokenInfo = TokenInfo(
                        userId = if(has("user_id")) getString("user_id") else "",
                        issuedAt = if(has("iat")) getLong("iat") else 0L,
                        authTime = if(has("auth_time")) getLong("auth_time") else 0L,
                        expiration = if(has("exp")) getLong("exp") else 0L,
                        identities = identities,
                        signInProvider = getSignInProvider(signInProvider)
                    )
                }
                tokenInfo
            } catch (e: JSONException) {
                loge(e.toString())
                TokenInfo()
            }
        }
        return TokenInfo()
    }

    private fun getDecoded(jwtEncoded: String): String {
        var decoded = ""
        try {
            val split: List<String> = jwtEncoded.split(".")
            if (split.size > 1) {
                decoded = getJson(split[1])
            }
        } catch (e: UnsupportedEncodingException) {
            loge(e.toString())
        } catch (e: Exception) {
            loge(e.toString())
        }
        return decoded
    }

    @Throws(UnsupportedEncodingException::class)
    private fun getJson(strEncoded: String): String {
        val decodedBytes: ByteArray = Base64.decode(strEncoded, Base64.URL_SAFE)
        return String(decodedBytes, charset("UTF-8"))
    }
}