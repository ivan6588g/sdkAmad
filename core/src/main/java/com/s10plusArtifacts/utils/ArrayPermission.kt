package com.s10plusArtifacts.utils

fun getArrayPermission(): List<PermissionModel>{
   return  arrayListOf(
       /* PermissionModel(
            permissionName = "android.permission.READ_PHONE_STATE",
            rationale = ""
        ),
       PermissionModel(
           permissionName = "android.permission.ANSWER_PHONE_CALLS",
            rationale = ""
        ),
        PermissionModel(
            permissionName = "android.permission.CALL_PHONE",
            rationale = ""
        ),
        PermissionModel(
            permissionName = "android.permission.READ_CALL_LOG",
            rationale = ""
        ),*/
       PermissionModel(
           permissionName = "android.permission.ACCESS_COARSE_LOCATION",
           rationale = ""
       ),
       PermissionModel(
           permissionName = "android.permission.ACCESS_FINE_LOCATION",
           rationale = ""
       ),
       PermissionModel(
           permissionName = "android.permission.CAMERA",
           rationale = ""
       ),
       /*PermissionModel(
           permissionName = "android.permission.POST_NOTIFICATIONS",
           rationale = ""
       )*/
    )
}