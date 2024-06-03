package com.y.app

import com.y.app.features.home.data.models.Post
import com.y.app.features.login.data.models.User

val author1 = User(
    id = 1,
    name = "John",
    lastName = "Doe",
    email = "john.doe@example.com",
    avatarColor = "#0D47A1"
)

val author2 = User(
    id = 2,
    name = "Jane",
    lastName = "Smith",
    email = "jane.smith@example.com",
    avatarColor = "#C62828"
)

val author3 = User(
    id = 3,
    name = "Alice",
    lastName = "Johnson",
    email = "alice.johnson@example.com",
    avatarColor = "#2E7D32"
)

val posts = listOf(
    Post(
        id = 101,
        author = author1,
        content = "Excited about the new project launch! 🚀 #innovation",
        likesCount = 42,
        commentsCount = 5,
        isLikedByMe = true,
        imageUrl = "https://example.com/image1.jpg",
        date = "2024-05-27T14:00:00Z"
    ),
    Post(
        id = 102,
        author = author2,
        content = "Loving the new features in the latest update! 💻✨",
        likesCount = 35,
        commentsCount = 3,
        isLikedByMe = false,
        imageUrl = "https://example.com/image2.jpg",
        date = "2024-05-28T14:00:00Z"
    ),
    Post(
        id = 103,
        author = author3,
        content = "Can't believe it's already summer! Time for some sun ☀️🌴",
        likesCount = 50,
        commentsCount = 10,
        isLikedByMe = true,
        imageUrl = "https://example.com/image3.jpg",
        date = "2024-05-29T14:00:00Z"
    ),
    Post(
        id = 104,
        author = author1,
        content = "Just finished a great book on leadership. Highly recommend! 📚👏",
        likesCount = 20,
        commentsCount = 1,
        isLikedByMe = false,
        imageUrl = "https://example.com/image4.jpg",
        date = "2024-05-30T14:00:00Z"
    ),
    Post(
        id = 105,
        author = author2,
        content = "Big shoutout to the team for an amazing job this quarter! 🎉🙌",
        likesCount = 60,
        commentsCount = 15,
        isLikedByMe = true,
        imageUrl = "https://example.com/image5.jpg",
        date = "2024-06-01T14:00:00Z"
    ),
    Post(
        id = 106,
        author = author3,
        content = "Looking forward to the weekend! Any plans? 😊🌟",
        likesCount = 10,
        commentsCount = 2,
        isLikedByMe = false,
        imageUrl = "https://example.com/image6.jpg",
        date = "2024-06-02T14:00:00Z"
    ),
    Post(
        id = 107,
        author = author1,
        content = "The latest tech trends are mind-blowing! 🚀📈 #tech",
        likesCount = 70,
        commentsCount = 20,
        isLikedByMe = true,
        imageUrl = "https://example.com/image7.jpg",
        date = "2024-06-03T14:00:00Z"
    ),
    Post(
        id = 108,
        author = author2,
        content = "Grateful for the opportunity to work with such a talented team. 🙏❤️",
        likesCount = 25,
        commentsCount = 7,
        isLikedByMe = false,
        imageUrl = "https://example.com/image8.jpg",
        date = "2024-06-04T14:00:00Z"
    ),
    Post(
        id = 109,
        author = author3,
        content = "Check out my latest blog post on personal development. ✍️💡 #growth",
        likesCount = 45,
        commentsCount = 8,
        isLikedByMe = true,
        imageUrl = "https://example.com/image9.jpg",
        date = "2024-06-05T14:00:00Z"
    ),
    Post(
        id = 110,
        author = author1,
        content = "Reflecting on this past year and all the progress made. 🙌✨ #gratitude",
        likesCount = 55,
        commentsCount = 9,
        isLikedByMe = false,
        imageUrl = "https://example.com/image10.jpg",
        date = "2024-06-06T14:00:00Z"
    )
)

val oneUserPosts = listOf(
    Post(
        id = 101,
        author = author1,
        content = "This is the first sample post content.",
        likesCount = 42,
        commentsCount = 5,
        isLikedByMe = true,
        imageUrl = "https://example.com/image1.jpg",
        date = "2024-05-27T14:00:00Z"
    ),
    Post(
        id = 102,
        author = author1,
        content = "This is the second sample post content.",
        likesCount = 35,
        commentsCount = 3,
        isLikedByMe = false,
        imageUrl = "https://example.com/image2.jpg",
        date = "2024-05-28T14:00:00Z"
    ),
    Post(
        id = 103,
        author = author1,
        content = "This is the third sample post content.",
        likesCount = 50,
        commentsCount = 10,
        isLikedByMe = true,
        imageUrl = "https://example.com/image3.jpg",
        date = "2024-05-29T14:00:00Z"
    ),
    Post(
        id = 104,
        author = author1,
        content = "This is the fourth sample post content.",
        likesCount = 20,
        commentsCount = 1,
        isLikedByMe = false,
        imageUrl = "https://example.com/image4.jpg",
        date = "2024-05-30T14:00:00Z"
    ),
    Post(
        id = 105,
        author = author1,
        content = "This is the fifth sample post content.",
        likesCount = 60,
        commentsCount = 15,
        isLikedByMe = true,
        imageUrl = "https://example.com/image5.jpg",
        date = "2024-06-01T14:00:00Z"
    )
)