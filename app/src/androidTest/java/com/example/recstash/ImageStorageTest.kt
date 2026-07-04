package com.example.recstash

import android.content.Context
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.recstash.data.copyImageToAppStorage
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class ImageStorageTest {

    @Test
    fun copyImageToAppStorage_createsFile() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val sourceFile = File(context.cacheDir, "test_source.jpg")
        sourceFile.writeBytes(byteArrayOf(1, 2, 3, 4, 5))

        val uri = sourceFile.toUri()

        val copiedPath = copyImageToAppStorage(context, uri)
        val copiedFile = File(copiedPath)

        assert(copiedFile.exists())
        assertEquals(5, copiedFile.length())
    }
}