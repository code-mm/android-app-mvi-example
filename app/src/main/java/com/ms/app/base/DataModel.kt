package com.ms.app.base


sealed class UIDataModel() {

    data class AvatarDataModel(val id: String, val resId: Int) : UIDataModel()

    data class Nickname(val id: String, val nickname: String) : UIDataModel()

}
