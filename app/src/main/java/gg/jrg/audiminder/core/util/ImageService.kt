package gg.jrg.audiminder.core.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

interface ImageService {
    suspend fun downloadImage(url: String): Bitmap?
    suspend fun saveImageToFile(bitmap: Bitmap, fileName: String): String
    suspend fun deleteImageFile(fileName: String): Boolean
}

class CoilImageService @Inject constructor(
    @ApplicationContext private val context: Context
) : ImageService {

    private val imageLoader = ImageLoader(context)

    override suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            val drawable = imageLoader.execute(request).drawable
            (drawable as? BitmapDrawable)?.bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @Throws(IOException::class)
    override suspend fun saveImageToFile(bitmap: Bitmap, fileName: String): String =
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)

            FileOutputStream(file).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
            }

            file.absolutePath
        }

    override suspend fun deleteImageFile(fileName: String): Boolean {
        val file = File(fileName)

        if (file.exists()) {
            return file.delete()
        }

        return false
    }

}
