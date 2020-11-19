package com.tycz.tweedle.lib

import io.ktor.client.engine.android.*

actual val engine by lazy { Android.create() }