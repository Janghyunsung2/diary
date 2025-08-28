package org.yojung.diary.aimode.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "ai_modes")
class AiMode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id : Int? = null

    @Column(name = "mode", nullable = false, unique = true)
    private var mode : String

    @Column(name = "label", nullable = false)
    private var label : String

    @Column(name = "description", nullable = false)
    private var description : String

    @Column(name = "image_url", nullable = false)
    private var imageUrl : String

    @Column(name = "prompt", nullable = false, length = 2000)
    private var prompt : String

    @Column(name = "color_label", nullable = true)
    private var colorLabel : String? = null

    @Column(name = "color_bg", nullable = true)
    private var colorBg : String? = null

    private var creditAmount : Int? = null

    constructor(
        mode: String,
        label: String,
        description: String,
        imageUrl: String,
        prompt: String,
        colorLabel: String? = null,
        colorBg: String? = null,
        creditAmount: Int? = null
    ) {
        this.mode = mode
        this.label = label
        this.description = description
        this.imageUrl = imageUrl
        this.prompt = prompt
        this.colorLabel = colorLabel
        this.colorBg = colorBg
        this.creditAmount = creditAmount
    }

    constructor() {
        // Default constructor for JPA
        this.mode = ""
        this.label = ""
        this.description = ""
        this.imageUrl = ""
        this.prompt = ""
    }

    fun update(
        mode: String,
        label: String,
        description: String,
        imageUrl: String,
        prompt: String,
        colorLabel: String? = null,
        colorBg: String? = null,
        creditAmount: Int? = null
    ) {
        this.mode = mode
        this.label = label
        this.description = description
        this.imageUrl = imageUrl
        this.prompt = prompt
        this.colorLabel = colorLabel
        this.colorBg = colorBg
        this.creditAmount = creditAmount
    }

    fun getId() = id
    fun getMode() = mode
    fun getCreditAmount() = creditAmount ?: 0
    fun getImageUrl() = imageUrl
    fun getLabel() = label
    fun getDescription() = description
    fun getPrompt() = prompt
    fun getColorLabel() = colorLabel ?: ""
    fun getColorBg() = colorBg ?: ""
    fun isColorLabelPresent() = colorLabel != null
    fun isColorBgPresent() = colorBg != null
    fun isCreditAmountPresent() = creditAmount != null
}