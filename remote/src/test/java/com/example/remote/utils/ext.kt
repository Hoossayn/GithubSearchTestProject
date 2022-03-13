package com.example.remote.utils

import com.example.remote.ApiService
import com.google.common.io.Resources
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.URL

private val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder().build()


internal fun makeTestApiService(mockWebServer: MockWebServer): ApiService = Retrofit.Builder()
    .baseUrl(mockWebServer.url("/"))
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(ApiService::class.java)


const val REQUEST_PATH: String = "/users"

const val SEARCH_QUERY = "pawnjester"

const val PER_PAGE = 10
const val PAGE = 1

@Suppress("UnstableApiUsage")
internal fun getJson(path: String): String {
    val uri: URL = Resources.getResource(path)
    val file = File(uri.path)
    return String(file.readBytes())
}