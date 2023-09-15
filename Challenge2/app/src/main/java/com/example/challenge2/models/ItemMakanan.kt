package com.example.challenge2.models

import android.os.Parcel
import android.os.Parcelable

data class ItemMakanan(
    val name: String,
    val image: Int,
    val harga:Int
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(image)
        parcel.writeInt(harga)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemMakanan> {
        override fun createFromParcel(parcel: Parcel): ItemMakanan {
            return ItemMakanan(parcel)
        }

        override fun newArray(size: Int): Array<ItemMakanan?> {
            return arrayOfNulls(size)
        }
    }
}
