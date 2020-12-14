package com.pklein.mariage.presentation.resultat

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.pklein.mariage.R
import com.pklein.mariage.data.PLAYER_GENDER
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.utils.SHARED_PREFERENCE_KEY
import com.pklein.mariage.utils.SharedPreferenceStored
import com.pklein.mariage.utils.calculateTimeDifference
import java.io.File
import java.io.FileOutputStream

const val nameFile = "enigMariage.png"
const val nameFolder = "images"
const val nameFileProvider = "com.pklein.mariage.fileprovider"

class ResultsViewModel : ViewModel() {

    fun calculateScore(): String {
        var actualNbOfError =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_ERRORS)
        var actualNbOfClue =
            SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_CLUES)

        if (actualNbOfError.isNullOrEmpty()) actualNbOfError = "0"
        if (actualNbOfClue.isNullOrEmpty()) actualNbOfClue = "0"

        val score = 100 - actualNbOfError.toInt() - (5 * actualNbOfClue.toInt())
        return "$score / 100"
    }

    fun calculateTime(): String? {
        val startTimeStr = SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_START_TIME)
        val endTimeStr = SharedPreferenceStored.getValue(SHARED_PREFERENCE_KEY.PLAYER_END_TIME)

        return if (!startTimeStr.isNullOrEmpty() && !endTimeStr.isNullOrEmpty()) {
            calculateTimeDifference(startTimeStr, endTimeStr)
        } else {
            null
        }
    }

    fun drawTextToBitmap(context: Context, timeDisplay: String, scoreDisplay: String): Bitmap? {
        return try {
            val resources: Resources = context.resources
            val scale: Float = resources.displayMetrics.density

            val resourceId = if (PlayerViewModel.getGender() == PLAYER_GENDER.FEMALE.name) {
                R.drawable.image_zelda_result
            } else {
                R.drawable.image_link_result
            }
            val nameDisplay = PlayerViewModel.getName().toString()

            var bitmap = BitmapFactory.decodeResource(resources, resourceId)
            var bitmapConfig = bitmap.config
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888 // set default bitmap config if none
            }
            // resource bitmaps are imutable, so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true)
            val canvas = Canvas(bitmap)

            val paint = Paint(Paint.ANTI_ALIAS_FLAG) // new antialised Paint
            paint.color = Color.rgb(110, 110, 110) // text color - #3D3D3D
            paint.textSize = 24 * scale // text size in pixels
            paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY) // text shadow

            // draw text to the Canvas center
            val bounds = Rect()
            paint.getTextBounds(nameDisplay, 0, nameDisplay.length, bounds)
            canvas.drawText(
                nameDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 5) * scale,
                paint
            )
            canvas.drawText(
                timeDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 45) * scale,
                paint
            )
            canvas.drawText(
                scoreDisplay,
                (bounds.left.toFloat() + 225) * scale,
                (-bounds.top.toFloat() + 85) * scale,
                paint
            )
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    fun getImageUriToShare(mContext: Context, mbitmap: Bitmap): Uri? {
        try {
            // save bitmap to cache Directory
            val cachePath = File(mContext.cacheDir, nameFolder)
            cachePath.mkdirs() // don't forget to make the directory
            val stream =
                FileOutputStream("$cachePath/$nameFile") // overwrites this image every time
            mbitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()

            // get contentUri of this image :
            val newFile = File(cachePath, nameFile)
            return FileProvider.getUriForFile(mContext, nameFileProvider, newFile)
        } catch (e: Exception) {
            return null
        }
    }
}