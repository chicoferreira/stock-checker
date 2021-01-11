package com.github.chicoferreira.stockchecker.notification.tokenizer

class Tokenizer(val source: String) {

    private val result = mutableListOf<Token>()
    var pos = 0
    val isAtEnd: Boolean get() = pos >= source.length

    companion object {
        val parsers: List<Parser> =
            listOf(OperatorParser(), ParentesisParser(), NumberParser(), LetterParser(), SpaceRemoverParser())
    }

    fun scan(): List<Token> {
        while (!isAtEnd) scanToken()

        return result
    }

    private fun scanToken() {
        val char = peek()

        for (parser in parsers) {
            if (parser.canParse(char)) {
                parser.parse(this)
                return
            }
        }
        throw UnknownTokenException(char, pos)
    }

    fun peekNext(): Char {
        if (pos + 1 >= source.length) return ' '

        return source[pos + 1]
    }

    fun peek(): Char {
        if (isAtEnd) return ' '

        return source[pos]
    }

    fun advance(): Char = source[pos++]

    infix fun add(token: Token) {
        result += token
    }
}

interface Parser {
    fun canParse(char: Char): Boolean

    fun parse(tokenizer: Tokenizer)
}

abstract class ConsequentParser : Parser {

    open fun tokenOf(value: String): Token = Token(value)

    override fun parse(tokenizer: Tokenizer) {
        val startPos: Int = tokenizer.pos

        val builder = StringBuilder()
        builder.append(tokenizer.advance())

        while (!tokenizer.isAtEnd && canParse(tokenizer.peek())) {
            builder.append(tokenizer.advance())
        }
        tokenizer.pos = startPos + builder.length

        tokenizer add tokenOf(builder.toString())
    }
}

class OperatorParser : ConsequentParser() {

    private companion object {
        val operators = listOf('+', '-', '*', '/', '<', '>', '=')
    }

    override fun canParse(char: Char): Boolean = operators.contains(char)
    override fun tokenOf(value: String): Token = OperatorToken(value)
}

class ParentesisParser : Parser {

    private companion object {
        val operators = listOf('(', ')')
    }

    override fun canParse(char: Char): Boolean = operators.contains(char)
    override fun parse(tokenizer: Tokenizer) {
        tokenizer add ParentesisToken(tokenizer.advance() == '(')
    }
}

class NumberParser : ConsequentParser() {
    override fun canParse(char: Char): Boolean = char.isDigit()
    override fun tokenOf(value: String): Token = NumberToken(value)
}

class LetterParser : ConsequentParser() {
    override fun canParse(char: Char): Boolean = char.isLetter()
    override fun tokenOf(value: String): Token = LetterToken(value)
}

class SpaceRemoverParser : Parser {
    override fun canParse(char: Char): Boolean {
        return char == ' '
    }

    override fun parse(tokenizer: Tokenizer) {
        tokenizer.advance()
    }
}

class UnknownTokenException(char: Char, pos: Int) : IllegalArgumentException("Unknown token $char (at pos=$pos)")