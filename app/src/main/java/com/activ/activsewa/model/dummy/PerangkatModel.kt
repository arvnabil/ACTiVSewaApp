package com.activ.activsewa.model.dummy

class PerangkatModel (title:String, src:String, duration:String,status:String, brand:String, price:String, stok:String,tag:String, sku:String,description:String){
    var title = ""
    var src = ""
    var duration = ""
    var status = "Tersedia"
    var brand = ""
    var price = ""
    var stok = ""
    var tag = ""
    var sku = ""
    var description = ""

    init {
        this.title = title
        this.src = src
        this.duration = "Durasi $duration Hari"
        this.status = status
        this.brand = brand
        this.price = price
        this.stok = stok
        this.tag = "Tag: $tag"
        this.sku = sku
        this.description = description
    }
}