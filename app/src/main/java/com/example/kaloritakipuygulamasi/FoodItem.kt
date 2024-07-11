package com.example.kaloritakipuygulamasi
class FoodItem {
    var name: String = ""
    var calories: String=""

    constructor() {
        // Boş yapıcı yöntem
    }

    constructor(name: String, calories:String) {
        this.name = name
        this.calories = calories
    }
}

