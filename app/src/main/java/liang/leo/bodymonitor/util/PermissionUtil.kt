package liang.leo.bodymonitor.util

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionUtil {
    companion object {

        //动态申请权限
        fun handlePermission(context: Context, permission: String): Boolean {
            val checkPermission =
                context?.let { ActivityCompat.checkSelfPermission(it, permission) }
            if (checkPermission == PackageManager.PERMISSION_GRANTED) {
                //执行到这里说明用户已经申请了权限直接加载数据就可以
                return true;
            } else {
                //执行到这里说明没有权限了
                if (context?.let {
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            it as Activity,
                            permission
                        )
                    }!!) {
                    //用户第一次拒绝后，可能没有看懂提示，第二次的时候就会执行到这里以友善的方式提示用户
                } else {
                    //第一次提示 会以系统默认的弹框提示用户
                    if (alter("该权限决定了您的用户体验，且不会对您的信息造成泄露，望批准！", context)) {
                        requestPermission(context, arrayOf(permission), 1)
                        return true
                    }
                    return false
                }
            }
            return false

        }

        private fun requestPermission(context: Context, permission: Array<String>, i: Int) {
            ActivityCompat.requestPermissions(context as Activity, permission,i)
        }

        private fun alter(s: String, context: Context): Boolean {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setMessage(s)
            alertDialog.create()
            var res: Boolean = false
            alertDialog.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                res = true
            }

            alertDialog.setNegativeButton("No") { _: DialogInterface, _: Int ->
                res = false
            }
            alertDialog.show()

            return res;

        }
    }
}