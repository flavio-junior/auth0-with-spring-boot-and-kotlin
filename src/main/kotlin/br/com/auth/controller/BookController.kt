package br.com.auth.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/v1/books")
class BookController {

    @GetMapping
    fun findAll(): ResponseEntity<List<String>> {
        return ResponseEntity.ok(listOf("Book1", "Book2", "Book3"))
    }

    @PostMapping
    fun create(@RequestBody data: String?): ResponseEntity<String> {
        return ResponseEntity.ok(data)
    }
}