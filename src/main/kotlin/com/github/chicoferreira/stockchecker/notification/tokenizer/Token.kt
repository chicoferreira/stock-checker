package com.github.chicoferreira.stockchecker.notification.tokenizer

open class Token(private val rawValue: String) {
    fun value(): String = rawValue
}

class OperatorToken(val operator: String) : Token(operator)

class NumberToken(val number: String) : Token(number)

class LetterToken(val number: String) : Token(number)

class ParentesisToken(val open: Boolean) : Token(if (open) "(" else ")")