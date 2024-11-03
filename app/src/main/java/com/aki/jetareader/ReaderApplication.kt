package com.aki.jetareader

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
//this class have access to all bindings and di
@HiltAndroidApp
class ReaderApplication:Application() {}