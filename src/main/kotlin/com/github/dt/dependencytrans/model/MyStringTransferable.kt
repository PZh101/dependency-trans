package com.github.dt.dependencytrans.model;

import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.Transferable
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException

class MyStringTransferable(private var str: String) : Transferable {

    private val supported = arrayOf(DataFlavor.stringFlavor)

    override fun getTransferDataFlavors(): Array<DataFlavor>? {
        return supported
    }

    override fun isDataFlavorSupported(flavor: DataFlavor?): Boolean {
        return DataFlavor.stringFlavor.equals(flavor)
    }

    @Throws(UnsupportedFlavorException::class, IOException::class)
    override fun getTransferData(flavor: DataFlavor?): String? {
        if (!isDataFlavorSupported(flavor)) {
            throw UnsupportedFlavorException(flavor)
        }
        return str
    }

}
