package com.ms.app

sealed class DataModel() {

    data class AvatarDataModel(val id: Int) : DataModel()

    data class Nickname(val nickname: String) : DataModel()

}
