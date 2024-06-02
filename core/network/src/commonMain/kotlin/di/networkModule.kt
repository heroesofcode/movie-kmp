package di

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val BASE_URL = "https://example.com"

@OptIn(ExperimentalSerializationApi::class)
val networkModule = module {
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(Json {
                    explicitNulls = false
                    ignoreUnknownKeys = true
                    isLenient = true
                    prettyPrint = true
                    encodeDefaults = true
                    classDiscriminator = "#class"
                })
            }
        }
    }

    single {
        val httpClient: HttpClient = get()

        Ktorfit.Builder()
            .httpClient(httpClient)
            .baseUrl(BASE_URL)
            .build()
    }
}