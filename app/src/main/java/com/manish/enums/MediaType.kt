package com.manish.enums

enum class MediaType(val value: String) {
    IMAGE("image/*"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    JPG("image/jpg"),
    PDF("application/pdf"),
    VIDEO("video/*"),
    MP4("video/mp4"),
    MKV("video/mkv")
}