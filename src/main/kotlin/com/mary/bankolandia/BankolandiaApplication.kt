package com.mary.bankolandia

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BankolandiaApplication

fun main(args: Array<String>) {
    runApplication<BankolandiaApplication>(*args)
}
