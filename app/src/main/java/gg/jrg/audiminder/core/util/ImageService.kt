package gg.jrg.audiminder.core.util

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject

interface ImageService {
    suspend fun downloadImage(url: String): Result<Bitmap?>
    suspend fun saveImageToFile(bitmap: Bitmap, fileName: String): Result<String>
    suspend fun deleteImageFile(fileName: String): Result<Boolean>
    suspend fun createCollage(bitmaps: List<Bitmap>): Result<Bitmap>
    suspend fun getBitmapFromFilePath(filePath: String): Result<Bitmap?>
    suspend fun copyAlbumArt(originalPath: String): Result<String>
}

class CoilImageService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ImageService {

    private val imageLoader = ImageLoader(context)

    override suspend fun downloadImage(url: String): Result<Bitmap?> = withContext(ioDispatcher) {
        runCatching {
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()

            when (val result = imageLoader.execute(request)) {
                is SuccessResult -> return@runCatching (result.drawable as? BitmapDrawable)?.bitmap
                is ErrorResult -> throw result.throwable
            }
        }
    }

    override suspend fun saveImageToFile(bitmap: Bitmap, fileName: String): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                val file = File(context.filesDir, fileName)

                FileOutputStream(file).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    outputStream.flush()
                }

                file.absolutePath
            }
        }

    override suspend fun deleteImageFile(fileName: String): Result<Boolean> =
        withContext(ioDispatcher) {
            runCatching {
                val file = File(fileName)

                if (file.exists()) {
                    return@runCatching file.delete()
                }

                return@runCatching false
            }
        }

    override suspend fun createCollage(bitmaps: List<Bitmap>): Result<Bitmap> =
        withContext(ioDispatcher) {
            runCatching {
                val dp = 161
                val pixels = (dp * Resources.getSystem().displayMetrics.density).toInt()

                val collage = Bitmap.createBitmap(pixels, pixels, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(collage)

                val resizedBitmaps =
                    bitmaps.map { Bitmap.createScaledBitmap(it, pixels / 2, pixels / 2, false) }

                for ((index, bitmap) in resizedBitmaps.withIndex()) {
                    val x = (index % 2) * (pixels / 2)
                    val y = (index / 2) * (pixels / 2)
                    canvas.drawBitmap(bitmap, x.toFloat(), y.toFloat(), null)
                }

                return@runCatching collage
            }
        }

    override suspend fun getBitmapFromFilePath(filePath: String): Result<Bitmap?> =
        withContext(ioDispatcher) {
            runCatching {
                val imageLoader = ImageLoader(context)
                val request = ImageRequest.Builder(context)
                    .data(File(filePath))
                    .build()

                when (val result = imageLoader.execute(request)) {
                    is SuccessResult -> return@runCatching (result.drawable as? BitmapDrawable)?.bitmap
                    is ErrorResult -> throw result.throwable
                }
            }
        }

    override suspend fun copyAlbumArt(originalPath: String): Result<String> =
        withContext(ioDispatcher) {
            runCatching {
                val originalBitmap =
                    getBitmapFromFilePath(originalPath).throwIfFailure().getOrNull()!!

                return@runCatching saveImageToFile(
                    originalBitmap,
                    UUID.randomUUID().toString()
                ).throwIfFailure().getOrNull()!!
            }
        }

}
