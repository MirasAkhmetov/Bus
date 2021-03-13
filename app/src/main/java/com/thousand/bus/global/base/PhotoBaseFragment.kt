package com.thousand.bus.global.base

import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.esafirm.imagepicker.features.ImagePicker
import com.thousand.bus.R
import com.thousand.bus.global.utils.AppConstants
import permissions.dispatcher.*
import java.io.File

@RuntimePermissions
abstract class PhotoBaseFragment: BaseFragment(){

    private var cameraImageUri: Uri? = null
    private var currentType: Int = 0


    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun pickPhotoFromGallery() {
        currentType = PICK_IMAGE_GALLERY
        ImagePicker.create(this)
            .showCamera(false)
            .single()
            .start()
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
    fun pickVideoFromGallery() {
        currentType = PICK_VIDEO_GALLERY
        ImagePicker.create(this)
            .onlyVideo(true)
            .single()
            .start()
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    fun pickPhotoFromCamera() {
        currentType = PICK_IMAGE_CAMERA
        context?.let { cont->
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            context?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.let {
                val photo = File(it.path, "${System.currentTimeMillis()}.jpg")

                cameraImageUri = FileProvider.getUriForFile(
                    cont,
                    "com.thousand.bus.provider",
                    photo
                )
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    cameraImageUri
                )
                startActivityForResult(intent, PICK_IMAGE_CAMERA)
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            val image = ImagePicker.getFirstImageOrNull(data)

            when(currentType){
                PICK_IMAGE_GALLERY -> {
                    setImagePath(image.path)
                }
                PICK_VIDEO_GALLERY -> {
                    setVideoPath(image.path)
                }
            }

        }
        when(currentType){
            PICK_IMAGE_CAMERA -> {
                cameraImageUri?.let {
                    setImage(it)
                    cameraImageUri = null
                }

            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForExternalStorage(request: PermissionRequest) {
        context?.let {
            AlertDialog.Builder(it)
                .setMessage("Permission")
                .setPositiveButton(android.R.string.yes) { _, _ -> request.proceed() }
                .setNegativeButton(android.R.string.no) { _, _ -> request.cancel() }
                .show()
        }
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showDeniedForExternalStorage() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showNeverAskForExternalStorage() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    abstract fun setImage(uri: Uri)

    abstract fun setImagePath(path: String)

    abstract fun setVideo(uri: Uri)

    abstract fun setVideoPath(path: String)

    companion object {
        private const val PICK_IMAGE_CAMERA = 1
        private const val PICK_IMAGE_GALLERY = 2
        private const val PICK_VIDEO_GALLERY = 3
    }

}