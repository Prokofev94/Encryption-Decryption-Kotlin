package encryptdecrypt

import java.io.File

var action = "enc"
var data = ""
var key = 0
var readFile = false
var writeFile = false
var fileName = ""
var algorithm = "shift"

fun main(args: Array<String>) {
    readArgs(args)
    handleData()
    outputData()
}

fun readArgs(args: Array<String>) {
    for (i in args.indices step 2) {
        when (args[i]) {
            "-mode" -> action = args[i + 1]
            "-key" -> key = args[i + 1].toInt()
            "-data" -> {
                data = args[i + 1]
                readFile = false
            }
            "-in" -> if (data.isEmpty()) {
                val fileName = args[i + 1]
                val file = File(fileName)
                data = file.readText()
                readFile = true
            }
            "-out" -> {
                fileName = args[i + 1]
                writeFile = true
            }
            "-alg" -> algorithm = args[i + 1]
        }
    }
}

fun handleData() {
    if (action == "dec") {
        key = -key
    }
    when (algorithm) {
        "shift" -> shift()
        "unicode" -> unicode()
    }
}

fun unicode() {
    var result = ""
    for (ch in data) {
        result += ch + key
    }
    data = result
}

fun shift() {
    var result = ""
    if (action == "dec") {
        key += 26
    }
    for (ch in data) {
        result += if (ch.isLowerCase()) {
            ((ch.code - 97 + key) % 26 + 97).toChar()
        } else if (ch.isUpperCase()) {
            ((ch.code - 65 + key) % 26 + 65).toChar()
        } else {
            ch
        }
    }
    data = result
}

fun outputData() {
    if (writeFile) {
        val file = File(fileName)
        file.writeText(data)
    } else {
        println(data)
    }
}