package com.pklein.mariage.presentation.resultat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.pklein.mariage.R
import com.pklein.mariage.data.PlayerViewModel
import com.pklein.mariage.databinding.ActivityResultsBinding
import com.pklein.mariage.presentation.BaseActivity
import com.pklein.mariage.presentation.LAST_ACTIVITY_LAUNCH
import com.pklein.mariage.presentation.SplashActivity

class ResultsActivity : BaseActivity() {

    private lateinit var binding: ActivityResultsBinding
    private val resultsViewModel = ResultsViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        PlayerViewModel.storePage(LAST_ACTIVITY_LAUNCH.RESULT)
        stopCountDown()

        binding.tvResultatTempsReponse.text =
            resultsViewModel.calculateTime() ?: getString(R.string.erreurs)
        binding.tvResultatScoreReponse.text = resultsViewModel.calculateScore()

        binding.buttonResultat.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
        }

        binding.buttonShare.setOnClickListener {

            val bitmap = resultsViewModel.drawTextToBitmap(
                this,
                binding.tvResultatTempsReponse.text.toString(),
                binding.tvResultatScoreReponse.text.toString()
            )
            if (bitmap != null) {
                val contentUri = resultsViewModel.getImageUriToShare(this, bitmap)
                if (contentUri != null) {
                    sharePicture(contentUri)
                }
            }
        }
    }

    private fun sharePicture(contentUri: Uri) {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // temp permission for receiving app to read this file
        shareIntent.setDataAndType(contentUri, contentResolver.getType(contentUri))
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri)
        startActivity(
            Intent.createChooser(
                shareIntent,
                getString(R.string.resultat_share_title)
            )
        )
    }
}