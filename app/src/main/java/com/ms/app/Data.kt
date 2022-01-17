package com.ms.app

sealed class Data() {

    data class Def(val desc: String) : Data()

    data class A(val desc: String) : Data()

    data class B(val title: String) : Data()

}
