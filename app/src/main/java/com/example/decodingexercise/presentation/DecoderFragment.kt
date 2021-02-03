package com.example.decodingexercise.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.decodingexercise.R
import com.example.decodingexercise.framework.DecoderViewModel
import kotlinx.android.synthetic.main.fragment_decoder.*


class DecoderFragment : Fragment() {

    val EXTERNAL_STORAGE_REQ_CODE = 10

    private lateinit var viewModel: DecoderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_decoder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DecoderViewModel::class.java)
        val permission = this.context?.let {
            ActivityCompat.checkSelfPermission(
                it,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // request permission
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    EXTERNAL_STORAGE_REQ_CODE
                )
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                onProceedClicked()
            }
        }
    }

    fun getSelectedFileName() =
        when (radio_group_file.checkedRadioButtonId) {
            R.id.radio_button_1 -> FILE_1_PATH
            R.id.radio_button_2 -> FILE_2_PATH
            else -> FILE_3_PATH
        }


    fun onProceedClicked() {
        button_proceed.setOnClickListener {
            loadingResults.visibility = View.VISIBLE
            val fileName = getSelectedFileName()
            viewModel.launchEncoder(fileName)
            observeViewModel()
        }
    }

    private fun observeViewModel() {
        output_text.setMovementMethod(ScrollingMovementMethod())
        viewModel.bitArray.observe(viewLifecycleOwner, Observer {
            loadingResults.visibility = View.GONE
            output_text.text = it
        })
    }

    companion object {
        const val FILE_1_PATH = "file_1.wav"
        const val FILE_2_PATH = "file_2.wav"
        const val FILE_3_PATH = "file_3.wav"
    }
}