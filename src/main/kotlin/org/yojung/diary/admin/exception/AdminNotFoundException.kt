package org.yojung.diary.admin.exception

class AdminNotFoundException(email: String) : RuntimeException("Admin with email '$email' not found")

