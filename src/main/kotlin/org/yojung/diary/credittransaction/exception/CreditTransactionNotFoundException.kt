package org.yojung.diary.credittransaction.exception

class CreditTransactionNotFoundException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(id: Long) : super("크레딧 트랜잭션이 존재하지 않습니다. ID: $id")
}

