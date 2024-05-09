package com.example.musicfun.ui.user

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.activity.result.contract.ActivityResultContract
import java.io.File

class CropImage : ActivityResultContract<CropImageResult, PictureResult>() {
    var outUri: Uri? = null

    companion object {
        val instance get() = Helper.obj
    }

    private object Helper {
        val obj = CropImage()
    }

    override fun createIntent(context: Context, input: CropImageResult): Intent {
        //系统裁剪
        val intent = Intent("com.android.camera.action.CROP")
        val mimeType = context.contentResolver.getType(input.uri)
        val imageName = "${input.imageName}.${
            MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
        }"
        outUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, imageName)
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        } else {
            Uri.fromFile(File(context.externalCacheDir!!.absolutePath, imageName))
        }

        context.grantUriPermission(
            context.packageName,
            outUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        //裁剪的设置
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra("noFaceDetection", true)
        intent.setDataAndType(input.uri, mimeType)
        intent.putExtra("crop", "true")
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
        intent.putExtra("outputFormat", "JPEG")
        intent.putExtra("return-data", false)


        if (input.outputX != 0 && input.outputY != 0) {
            intent.putExtra("outputX", input.outputX)
            intent.putExtra("outputY", input.outputY)
        }
        if (input.aspectX != 0 && input.aspectY != 0) {
            if (input.aspectY == input.aspectX && Build.MANUFACTURER == "HUAWEI") {
                intent.putExtra("aspectX", 9999)
                intent.putExtra("aspectY", 9998)
            } else {
                intent.putExtra("aspectX", input.aspectX)
                intent.putExtra("aspectY", input.aspectY)
            }
        }
        val resInfoList = context.packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        )
        //授权google相册权限（部分google手机需要主动加上这个才行）
        context.grantUriPermission(
            "com.google.android.apps.photos",
            outUri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
        )
        //授权三方相册权限
        for (resolveInfo in resInfoList) {
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(
                packageName,
                outUri,
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
        }

        return intent
    }


    override fun parseResult(resultCode: Int, intent: Intent?): PictureResult {
        return PictureResult(outUri, resultCode == Activity.RESULT_OK)
    }

}

class CropImageResult(
    val uri: Uri,
    val aspectX: Int = 1,
    val aspectY: Int = 1,
    @androidx.annotation.IntRange(from = 0, to = 1080)
    val outputX: Int = 0,
    @androidx.annotation.IntRange(from = 0, to = 1080)
    val outputY: Int = 0,
    val imageName: String = "${System.currentTimeMillis()}"
)

class PictureResult(val uri: Uri?, val isSuccess: Boolean)
