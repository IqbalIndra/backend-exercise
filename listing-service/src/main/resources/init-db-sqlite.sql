-- Table structure for table `listings`
CREATE TABLE IF NOT EXISTS `listings` (
     `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
     `user-id` INTEGER NOT NULL,
     `listing_type` TEXT NOT NULL,
     `price` INTEGER NOT NULL,
     `created_at` INTEGER DEFAULT NULL,
     `updated_at` INTEGER DEFAULT NULL
);