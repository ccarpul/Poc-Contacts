package com.example.contactspoc.extensions

import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.CornerFamily
import com.google.android.material.textfield.TextInputLayout

fun ShapeableImageView.setShape(){
    shapeAppearanceModel = shapeAppearanceModel
        .toBuilder()
        .setAllCorners(CornerFamily.ROUNDED,100F)
        .build()
}

fun TextInputLayout.blockEdit(attr: String?){
    editText?.setText(attr)
    editText?.let {  it.isEnabled = !isEnabled }
}

fun TextInputLayout.allowEdit(attr: String?){
    editText?.setText(attr)
    editText?.let {  it.isEnabled = isEnabled }
}