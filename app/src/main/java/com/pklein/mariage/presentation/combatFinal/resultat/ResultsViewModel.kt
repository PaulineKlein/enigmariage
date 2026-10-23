package com.pklein.mariage.presentation.combatFinal.resultat

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.pklein.mariage.R
import com.pklein.mariage.data.repository.PlayerGender
import com.pklein.mariage.data.repository.PlayerRepository
import com.pklein.mariage.data.repository.ScoreRepository
import com.pklein.mariage.presentation.GameStep
import com.pklein.mariage.utils.calculateTimeDifference
import java.util.concurrent.TimeUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

private const val FILE_NAME = "enigMariage.png"
private const val FOLDER_NAME = "images"
private const val FILE_PROVIDER = "com.pklein.mariage.fileprovider"

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {
    val actualNbOfClue: Int
        get() = scoreRepository.actualNbOfClue

    fun calculateScore(): Int {
        val actualNbOfError = scoreRepository.actualNbOfError
        val actualNbOfClue = scoreRepository.actualNbOfClue

        return 100 - (3 * actualNbOfError) - (5 * actualNbOfClue)
    }

    fun calculateTime(): String? {
        val startTimeStr = scoreRepository.startTimeStr
        val endTimeStr = scoreRepository.endTimeStr

        return if (!startTimeStr.isNullOrEmpty() && !endTimeStr.isNullOrEmpty()) {
            calculateTimeDifference(startTimeStr, endTimeStr)
        } else {
            null
        }
    }

    fun isUnderThirtyMinutes(): Boolean {
        val startTimeStr = scoreRepository.startTimeStr
        val endTimeStr = scoreRepository.endTimeStr
        return try {
            val diff = endTimeStr!!.toLong() - startTimeStr!!.toLong()
            diff < TimeUnit.MINUTES.toMillis(30)
        } catch (_: Exception) {
            false
        }
    }

    fun drawTextToBitmap(context: Context, timeDisplay: String, scoreDisplay: String, trophiesDisplay: String): Bitmap? =
        try {
            val resources: Resources = context.resources
            val scale: Float = resources.displayMetrics.density

            val resourceId = if (playerRepository.gender == PlayerGender.RABBIT.name) {
                R.drawable.image_perso_rabbit_result
            } else {
                R.drawable.image_perso_dog_result
            }
            val nameDisplay = playerRepository.name.toString()

            var bitmap = BitmapFactory.decodeResource(resources, resourceId)
            var bitmapConfig = bitmap.config
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888 // set default bitmap config if none
            }
            // resource bitmaps are immutable, so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true)
            val canvas = Canvas(bitmap)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.color = Color.rgb(110, 110, 110) // text color - #3D3D3D
            paint.textSize = 24 * scale // text size in pixels
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY) // text shadow

            // draw text to the Canvas center
            val bounds = Rect()
            paint.getTextBounds(nameDisplay, 0, nameDisplay.length, bounds)
            canvas.drawText(
                nameDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 8) * scale,
                paint
            )
            canvas.drawText(
                timeDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 38) * scale,
                paint
            )
            canvas.drawText(
                scoreDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 68) * scale,
                paint
            )
            canvas.drawText(
                trophiesDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 98) * scale,
                paint
            )
            bitmap
        } catch (_: Exception) {
            null
        }

    fun getImageUriToShare(mContext: Context, mbitmap: Bitmap): Uri? =
        try {
            // save bitmap to cache Directory
            val cachePath = File(mContext.cacheDir, FOLDER_NAME)
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/$FILE_NAME") // overwrites this image every time
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            // get contentUri of this image :
            val newFile = File(cachePath, FILE_NAME)
            FileProvider.getUriForFile(mContext, FILE_PROVIDER, newFile)
        } catch (_: Exception) {
            null
        }
}