package com.iqbal.latihanuploadimagechapter7

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.iqbal.latihanuploadimagechapter7.databinding.ActivityMainBinding
import com.iqbal.latihanuploadimagechapter7.viewmodel.ViewModelCar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class MainActivity : AppCompatActivity() {

    private var imageMultiPart: MultipartBody.Part?= null
    private var imageUri : Uri? = Uri.EMPTY
    private var imagefile : File?= null
    lateinit var viewModeluser : ViewModelCar


    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModeluser = ViewModelProvider(this).get(ViewModelCar::class.java)


        binding.addImage.setOnClickListener {
            openGallery()
        }

        binding.postDataCar.setOnClickListener {
            postDatUser()
        }

    }

        fun postDatUser() {
            val address =
                binding.addressUser.text.toString().toRequestBody("multipart/form-data".toMediaType())
            val city = binding.CityUser.text.toString()
                .toRequestBody("multipart/form-data".toMediaType())
            val email =
                binding.EmailUser.text.toString().toRequestBody("multipart/form-data".toMediaType())
            val fullname =
                binding.fullnameUser.text.toString().toRequestBody("multipart/form-data".toMediaType())
            val password =
                binding.password.text.toString().toRequestBody("multipart/form-data".toMediaType())
            val phonenumber =
                binding.password.text.toString().toRequestBody("multipart/form-data".toMediaType())

            viewModeluser.addLiveDatauser.observe(this, {
                if (it != null) {
                    Toast.makeText(this, "Add Data Car Succeeded", Toast.LENGTH_SHORT).show()
                }
            })
            viewModeluser.postApiUser(address,city, email,fullname,password,phonenumber ,imageMultiPart!!)
        }

        fun openGallery() {
            getContent.launch("image/*")
        }

        private val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                uri?.let {
                    val contentResolver: ContentResolver = this!!.contentResolver
                    val type = contentResolver.getType(it)
                    imageUri = it

                    val fileNameimg = "${System.currentTimeMillis()}.png"
                    binding.addImage.setImageURI(it)
                    Toast.makeText(this, "$imageUri", Toast.LENGTH_SHORT).show()

                    val tempFile = File.createTempFile("and1-", fileNameimg, null)
                    imagefile = tempFile
                    val inputstream = contentResolver.openInputStream(uri)
                    tempFile.outputStream().use { result ->
                        inputstream?.copyTo(result)
                    }
                    val requestBody: RequestBody = tempFile.asRequestBody(type?.toMediaType())
                    imageMultiPart =
                        MultipartBody.Part.createFormData("image", tempFile.name, requestBody)
                }
            }

        }