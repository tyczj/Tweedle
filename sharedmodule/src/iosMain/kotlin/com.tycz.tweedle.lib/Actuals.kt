package com.tycz.tweedle.lib

import io.ktor.client.engine.ios.*

actual val engine by lazy { Ios.create() }